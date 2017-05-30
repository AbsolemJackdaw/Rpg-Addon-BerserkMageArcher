package subaraki.BMA.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import subaraki.BMA.capability.MageIndexData;
import subaraki.BMA.handler.proxy.ClientProxy;
import subaraki.BMA.mod.AddonBma;

public class LayerMageProtection implements LayerRenderer<AbstractClientPlayer>{

	RenderPlayer rp;
	private final ResourceLocation shield = new ResourceLocation(AddonBma.MODID+":textures/item/talisman.png");

	public LayerMageProtection(RenderPlayer rp) {
		this.rp = rp;
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		MageIndexData data = MageIndexData.get(entitylivingbaseIn);
		if(data.isProtectedByMagic())
		{
			renderMantle(entitylivingbaseIn);
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	private int rotation;

	private void renderMantle(EntityPlayer player) {

		ResourceLocation shield = new ResourceLocation(AddonBma.MODID+":textures/item/talisman.png");

		rotation += 1f;
		if (rotation == 360) {
			rotation = 0;
		}

		rp.bindTexture(shield);

		MageIndexData data = MageIndexData.get(player);
		int health = data.getShieldCapacity();
		float scaled = (float)health * 0.15f; //40 * 0.15 = 6 
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
	       
	        GL11.glRotatef(-rotation*2, 0F, 1F, 0F);
			
	        GL11.glTranslatef(
					-0.25f - (float)vec.xCoord,
					-0.25f + 0.5f,
					-0.25f - (float)vec.zCoord
					);
			
			GL11.glScalef(0.5F, 0.5F, 0.5F);

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
