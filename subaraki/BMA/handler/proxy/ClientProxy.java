package subaraki.BMA.handler.proxy;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.entity.RenderAugolustra;
import subaraki.BMA.entity.RenderExpelliarmus;
import subaraki.BMA.entity.RenderHammerSmash;
import subaraki.BMA.entity.RenderHellArrow;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.item.armor.model.ModelArcherArmor;
import subaraki.BMA.item.armor.model.ModelBerserkerArmor;
import subaraki.BMA.item.armor.model.ModelMageArmor;
import subaraki.BMA.mod.AddonBma;
import subaraki.BMA.render.LayerMageProtection;

public class ClientProxy extends ServerProxy {

	private static final ModelBerserkerArmor armorBerserkChest = new ModelBerserkerArmor(1.0f);
	private static final ModelBerserkerArmor armorBerserk = new ModelBerserkerArmor(0.5f);
	public static final String berserker_chest = "berserker_chest";
	public static final String berserker_rest = "berserker_rest";

	private static final ModelMageArmor armorMageChest = new ModelMageArmor(1.0f);
	private static final ModelMageArmor armorMage = new ModelMageArmor(0.5f);
	public static final String mage_chest = "mage_chest";
	public static final String mage_rest= "mage_rest";

	private static final ModelArcherArmor armorArcherChest = new ModelArcherArmor(1.0f);
	private static final ModelArcherArmor armorarcher = new ModelArcherArmor(0.5f);
	public static final String archer_chest = "archer_chest";
	public static final String archer_rest= "archer_rest";

	@Override
	public ModelBiped getArmorModel(String id) {

		switch (id) {
		case berserker_rest:
			return armorBerserk;
		case berserker_chest:
			return armorBerserkChest;

		case mage_rest:
			return armorMage;
		case mage_chest:
			return armorMageChest;

		case archer_rest:
			return armorarcher;
		case archer_chest:
			return armorArcherChest;
		}
		return super.getArmorModel(id);
	}

	public void registerRenders(){
		BmaItems.registerRenderers();

		RenderingRegistry.registerEntityRenderingHandler(EntityAugolustra.class, RenderAugolustra::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityExpelliarmus.class, RenderExpelliarmus::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityHammerSmash.class, RenderHammerSmash::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityHellArrow.class, RenderHellArrow::new);

	}

	public void registerClientEvents(){
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

	@Override
	public void registerColors(){
		ItemColors ic = Minecraft.getMinecraft().getItemColors();

		//for capes
		ic.registerItemColorHandler(

				new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack, int tintIndex) {
						if(stack != ItemStack.EMPTY){
							switch(stack.getMetadata())
							{
							case 0 : 
								return 0xa24203;
							case 1 : 
								return 0x3333FF;
							case 2 : 
								return 0x71544f;
							}
						}

						return 0xffffff;
					}
				}, 
				BmaItems.craftLeather
				);
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}

	public static int outsideSphereID;
	public static int insideSphereID;

	@Override
	public int getSphereID(boolean isFirstPerson) {
		return isFirstPerson ? insideSphereID : outsideSphereID;
	}

	@Override
	public void registerRenderInformation() {

		Sphere sphere = new Sphere();
		// GLU_POINT will render it as dots.
		// GLU_LINE will render as wireframe
		// GLU_SILHOUETTE will render as ?shadowed? wireframe
		// GLU_FILL as a solid.
		sphere.setDrawStyle(GLU.GLU_FILL);
		// GLU_SMOOTH will try to smoothly apply lighting
		// GLU_FLAT will have a solid brightness per face, and will not shade.
		// GLU_NONE will be completely solid, and probably will have no depth to
		// it's appearance.
		sphere.setNormals(GLU.GLU_SMOOTH);
		// GLU_INSIDE will render as if you are inside the sphere, making it
		// appear inside out.(Similar to how ender portals are rendered)
		sphere.setOrientation(GLU.GLU_OUTSIDE);

		sphere.setTextureFlag(true);
		// Simple 1x1 red texture to serve as the spheres skin, the only pixel
		// in this image is red.
		// sphereID is returned from our sphereID() method
		outsideSphereID = GL11.glGenLists(1);
		// Create a new list to hold our sphere data.
		GL11.glNewList(outsideSphereID, GL11.GL_COMPILE);
		// Offset the sphere by it's radius so it will be centered
		GL11.glTranslatef(0.50F, 0.50F, 0.50F);

		sphere.draw(0.5F, 12, 24);
		// Drawing done, unbind our texture
		// Tell LWJGL that we are done creating our list.
		GL11.glEndList();

		Sphere sphereInside = new Sphere();
		sphereInside.setDrawStyle(GLU.GLU_FILL);
		sphereInside.setNormals(GLU.GLU_NONE);
		sphereInside.setOrientation(GLU.GLU_INSIDE);

		sphereInside.setTextureFlag(true);
		insideSphereID = GL11.glGenLists(1);
		GL11.glNewList(insideSphereID, GL11.GL_COMPILE);
		GL11.glTranslatef(0.50F, 0.50F, 0.50F);

		sphereInside.draw(0.5F, 12, 24);
		GL11.glEndList();

	}
	
	@Override
	public void addRenderLayers(){

		String types[] = new String[]{"default","slim"};

		for(String type : types){
			RenderPlayer renderer = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get(type));
			renderer.addLayer(new LayerMageProtection(renderer));
		}
	}
}