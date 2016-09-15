package subaraki.BMA.item.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArcherArmor extends ModelBiped {
	// fields
	private ModelRenderer KneeL;
	private ModelRenderer KneeR;
	private ModelRenderer ToetipL;
	private ModelRenderer ToetipR;

	public ModelArcherArmor(float par1) {
		super(par1, 0, 65, 64);

		textureWidth = 65;
		textureHeight = 64;

		KneeL = new ModelRenderer(this, 0, 32);
		KneeL.addBox(-1.5F, 3F, -2.5F, 3, 2, 1, 0.9f);
		KneeL.setRotationPoint(0F, 0F, 0F);
		KneeL.setTextureSize(65, 64);
		KneeL.mirror = true;
		setRotation(KneeL, 0F, 0F, 0F);
		KneeR = new ModelRenderer(this, 0, 32);
		KneeR.addBox(-1.5F, 3F, -2.5F, 3, 2, 1, 0.9f);
		KneeR.setRotationPoint(0F, 0F, 0F);
		KneeR.setTextureSize(65, 64);
		KneeR.mirror = true;
		setRotation(KneeR, 0F, 0F, 0F);
		ToetipL = new ModelRenderer(this, 0, 37);
		ToetipL.addBox(-2F, 10F, -3.5F, 4, 2, 1, 0.5f);
		ToetipL.setRotationPoint(0F, 0F, 0F);
		ToetipL.setTextureSize(65, 64);
		ToetipL.mirror = true;
		setRotation(ToetipL, 0F, 0F, 0F);
		ToetipR = new ModelRenderer(this, 0, 37);
		ToetipR.addBox(-2F, 10F, -3.5F, 4, 2, 1, 0.5f);
		ToetipR.setRotationPoint(0F, 0F, 0F);
		ToetipR.setTextureSize(65, 64);
		ToetipR.mirror = true;
		setRotation(ToetipR, 0F, 0F, 0F);

		bipedRightLeg.addChild(KneeR);
		bipedRightLeg.addChild(ToetipR);
		bipedLeftLeg.addChild(ToetipL);
		bipedLeftLeg.addChild(KneeL);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}