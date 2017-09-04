package subaraki.BMA.entity.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import subaraki.BMA.entity.EntityBombarda;

public class RenderBombarda extends Render<EntityBombarda> implements IRenderFactory{

	public RenderBombarda(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.1F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBombarda entity) {
		return null;
	}

	@Override
	public void doRender(EntityBombarda entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}
}
