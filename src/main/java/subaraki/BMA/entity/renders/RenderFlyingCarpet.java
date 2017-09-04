package subaraki.BMA.entity.renders;

import static net.minecraft.client.renderer.GlStateManager.*;

import lib.modelloader.ModelHandle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import subaraki.BMA.entity.EntityFlyingCarpet;

public class RenderFlyingCarpet extends Render<EntityFlyingCarpet> implements IRenderFactory{

	private ModelHandle model = ModelHandle.of(new ResourceLocation("bma_addon","carpet/flyingcarpet"));

	public RenderFlyingCarpet(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.0F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingCarpet entity) {
		return new ResourceLocation("blocks/wool_colored_white");
	}

	@Override
	public void doRender(EntityFlyingCarpet entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		pushMatrix();
//
		translate(x , y , z);
		rotate(-entityYaw, 0, 1, 0);
		
		for(float f = 0; f < 16; f++)
		{
			pushMatrix();
			translate(-0.5, 0.1, 0);
			translate(0, MathHelper.sin(f + (float)entity.ticksExisted/10f)/50f, 0.0625f*f);
			String texture = "blocks/wool_colored_"+EnumDyeColor.byMetadata(entity.getMeta()[1]).getDyeColorName();
			//replace json texture path with corresponding wool path
			if (!model.getTextureReplacements().containsKey("0") || model.getTextureReplacements().containsKey("0") && !model.getTextureReplacements().get("0").equals(texture))
				model = model.replace("0", texture );
			model.render();
			popMatrix();
			
			pushMatrix();
			translate(-0.5, 0.1, -1);
			translate(0, -MathHelper.sin(f + (float)entity.ticksExisted/10f)/50f, 0.0625f*f);
			texture = "blocks/wool_colored_"+EnumDyeColor.byMetadata(entity.getMeta()[0]).getDyeColorName();
			//replace json texture path with corresponding wool path 
			if (!model.getTextureReplacements().containsKey("0") || model.getTextureReplacements().containsKey("0") && !model.getTextureReplacements().get("0").equals(texture))
				model = model.replace("0", texture );
			model.render();
			popMatrix();
		}
		
		popMatrix();
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}
}
