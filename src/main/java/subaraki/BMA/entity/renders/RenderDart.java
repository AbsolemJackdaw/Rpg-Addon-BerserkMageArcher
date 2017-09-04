package subaraki.BMA.entity.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import subaraki.BMA.entity.EntityDart;
import subaraki.BMA.mod.AddonBma;

public class RenderDart extends RenderArrow<EntityDart> implements IRenderFactory{

	public static final ResourceLocation RES_ARROW = new ResourceLocation(AddonBma.MODID+":textures/entity/dart_arrow.png");

	public RenderDart(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.1F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDart entity) {
		return RES_ARROW;
	}

	@Override
	public Render createRenderFor(RenderManager manager) {
		return this;
	}
}
