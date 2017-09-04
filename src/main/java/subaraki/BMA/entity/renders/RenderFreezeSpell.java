package subaraki.BMA.entity.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import subaraki.BMA.entity.EntityFreezeSpell;

public class RenderFreezeSpell extends Render<EntityFreezeSpell> implements IRenderFactory{

	public RenderFreezeSpell(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.1F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFreezeSpell entity) {
		return null;
	}

	@Override
	public void doRender(EntityFreezeSpell entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}
}
