package subaraki.BMA.entity.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityScintilla;

public class RenderScintilla extends Render<EntityScintilla> implements IRenderFactory{

	public RenderScintilla(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.1F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityScintilla entity) {
		return null;
	}

	@Override
	public void doRender(EntityScintilla entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}
}
