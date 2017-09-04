package subaraki.BMA.entity.renders;

import static net.minecraft.client.renderer.GlStateManager.popMatrix;
import static net.minecraft.client.renderer.GlStateManager.pushMatrix;
import static net.minecraft.client.renderer.GlStateManager.scale;
import static net.minecraft.client.renderer.GlStateManager.translate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import subaraki.BMA.entity.EntityHellArrow;

public class RenderHellArrow extends Render<EntityHellArrow> implements IRenderFactory{

	public RenderHellArrow(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.1F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHellArrow entity) {
		return null;
	}

	@Override
	public void doRender(EntityHellArrow entity, double x, double y, double z, float entityYaw, float partialTicks) {
		RenderItem render = Minecraft.getMinecraft().getRenderItem();

		pushMatrix();
		translate(x, y, z);
		scale(0.5f,0.5f,0.5f);
		render.renderItem(new ItemStack(Blocks.REDSTONE_BLOCK), ItemCameraTransforms.TransformType.FIXED);
		popMatrix();
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}
}
