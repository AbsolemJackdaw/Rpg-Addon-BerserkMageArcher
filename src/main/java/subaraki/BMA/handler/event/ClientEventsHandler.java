package subaraki.BMA.handler.event;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import subaraki.BMA.capability.MageIndexData;
import subaraki.BMA.handler.proxy.ClientProxy;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.mod.AddonBma;

public class ClientEventsHandler {

	public ClientEventsHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void overlay(RenderGameOverlayEvent event){

		if(!event.getType().equals(ElementType.HOTBAR))
			return;

		GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
		EntityPlayer player = Minecraft.getMinecraft().player;

		int x = event.getResolution().getScaledWidth();
		int y = event.getResolution().getScaledHeight();

		int x1 = x/2 - 90 + 9 * 20 + 5;
		int y1 = y - 20;

		String spokenSpell = " ¸¸.•*|*•.¸¸ ";

		if(player.getHeldItem(EnumHand.MAIN_HAND)== null || ! player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(BmaItems.wand))
			return;

		String spell = AddonBma.spells.getSpokenSpell(player);

		if(spell != null && spell.length() > 4)
			spokenSpell = spell;

		gui.drawString(gui.getFontRenderer(), TextFormatting.ITALIC+spokenSpell, x1,y1, 0xffffff);

	}
	
	@SubscribeEvent
	public void firstperson(RenderHandEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.gameSettings.thirdPersonView == 0 && !AddonBma.proxy.getClientPlayer().isPlayerSleeping() && !mc.gameSettings.hideGUI )
		{
			if(event.getRenderPass() == 2) //2 is only option
			{
				MageIndexData data = MageIndexData.get(AddonBma.proxy.getClientPlayer());
				if(data.isProtectedByMagic())
				{
					renderMantle(AddonBma.proxy.getClientPlayer());
				}
			}
		}
	}

	private float rotation;
	private void renderMantle(EntityPlayer player) {

		ResourceLocation shield = new ResourceLocation(AddonBma.MODID+":textures/item/talisman.png");

		rotation += Minecraft.getMinecraft().isGamePaused() ? 0f : 1f;
		if (rotation == 360) {
			rotation = 0;
		}

		Minecraft.getMinecraft().getTextureManager().bindTexture(shield);

		MageIndexData data = MageIndexData.get(player);
		
		if(data.getShieldCapacity() <= 0)
			return;
		
		int health = data.getShieldCapacity();
		float scaled = Math.max(1, (float)health * 0.15f); //40 * 0.15 = 6 //math max to prevent 0.9 end under values to be lost 
		float max = 360 / (int)scaled; //360 / 6 = 60 
		for(float i = 0; i < 360 ; i+= max)
		{
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

			GL11.glPushMatrix();

			float yaw = i;

			float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
			float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
			float f2 = -MathHelper.cos(-0 * 0.017453292F);
			float f3 = MathHelper.sin(-0 * 0.017453292F);
			Vec3d vec = new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));

			GL11.glRotatef(player.rotationPitch, 1F, 0F, 0F);

			GL11.glRotatef(rotation, 0F, 1F, 0F);
			
			GL11.glTranslatef(
					-0.5f - (float)vec.xCoord*1.5f,
					-1.4f,
					-0.5f - (float)vec.zCoord*1.5f
					);

			GL11.glScalef(1F, 1F, 1F);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			GL11.glRotatef(rotation, 1F, 1F, 1F);

			GL11.glCallList(ClientProxy.outsideSphereID);

			GL11.glColor4f(1, 1, 1, 1);

			GL11.glPopMatrix();
			GL11.glPopAttrib();	
		}
	}
}
