package subaraki.BMA.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import subaraki.BMA.capability.FreezeData;
import subaraki.BMA.capability.FreezeDataCapability;

public class LayerColdEffect implements LayerRenderer<EntityLivingBase> {

	private final RenderLivingBase<EntityLivingBase> renderer;

	private ModelBiped square = new ModelBiped();
	
	public LayerColdEffect(RenderLivingBase<EntityLivingBase> renderer) {
		this.renderer = renderer;
		
		square.bipedHead.setTextureSize(16, 16);
		square.bipedHead.addBox(-8, -12, -8, 16, 16, 16);
	}

	@Override
	public void doRenderLayer(EntityLivingBase elb, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		if(elb.hasCapability(FreezeDataCapability.CAPABILITY, null))
		{
			FreezeData data = FreezeData.get(elb);

			if(data.shouldApplyFreeze())
			{
				
				GlStateManager.pushMatrix();
				GlStateManager.translate(0, 1.25f - (elb.getEyeHeight() - 0.4f/*block size / 2*/) + elb.height/2f, 0);

				GlStateManager.scale(elb.width, elb.height, elb.width);

				renderer.bindTexture(new ResourceLocation("textures/blocks/frosted_ice_0.png"));
				
				square.bipedHead.setTextureSize((int)(elb.width*16f), (int)(elb.height*16f));
				
				square.bipedHead.render(0.0625f);
				
				GlStateManager.popMatrix();
				
				
				
				
				GlStateManager.pushMatrix();

				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();

				float alpha = (float)data.getTimer() / (float)data.maxTimer();

				//System.out.println(alpha + " " + data.getTimer());
				
				GlStateManager.color(0, 0.5f, 1f, alpha);
				
				renderer.bindTexture(new ResourceLocation("textures/blocks/frosted_ice_0.png"));

				renderer.getMainModel().render(elb, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

				GlStateManager.color(1f,1f,1f,1f);

				GlStateManager.disableAlpha();
				GlStateManager.disableBlend();

				GlStateManager.popMatrix();
				
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
