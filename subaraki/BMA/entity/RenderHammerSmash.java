package subaraki.BMA.entity;

import static net.minecraft.client.renderer.GlStateManager.popMatrix;
import static net.minecraft.client.renderer.GlStateManager.pushMatrix;
import static net.minecraft.client.renderer.GlStateManager.rotate;
import static net.minecraft.client.renderer.GlStateManager.scale;
import static net.minecraft.client.renderer.GlStateManager.translate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import subaraki.BMA.item.BmaItems;

public class RenderHammerSmash extends Render<EntityHammerSmash>{

	public RenderHammerSmash(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHammerSmash entity) {
		return null;
	}

	@Override
	public void doRender(EntityHammerSmash entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		ItemStack hammer = entity.getItemStackFromSlot(null);
		if(hammer == null)
			hammer = new ItemStack(BmaItems.hammer);

		RenderItem render = Minecraft.getMinecraft().getRenderItem();

		pushMatrix();
		translate(x, y, z);
		scale(1.2f,1.2f,1.2f);
		translate(0, 0.6f, 0);

		rotate(180,0,1,0);
		rotate(-entity.rotationYaw, 0, 1, 0);

		if(entity.getRotationAngle() > 0){
			translate(0, -entity.getRotationAngle()/300f, 0);
			rotate(-entity.getRotationAngle(), 1, 0, 0);
		}
		render.renderItem(hammer, ItemCameraTransforms.TransformType.FIXED);
		popMatrix();

	}

}
