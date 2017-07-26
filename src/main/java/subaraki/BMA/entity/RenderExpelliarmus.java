package subaraki.BMA.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderExpelliarmus extends Render<EntityExpelliarmus> implements IRenderFactory{

	public RenderExpelliarmus(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.1F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityExpelliarmus entity) {
		return null;
	}

	@Override
	public void doRender(EntityExpelliarmus entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}
}
