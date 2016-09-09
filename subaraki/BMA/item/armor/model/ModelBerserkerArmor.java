package subaraki.BMA.item.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBerserkerArmor extends ModelBiped {
	
	// fields
	private ModelRenderer horns_1;
	private ModelRenderer horns_2;
	private ModelRenderer horns_3;
	private ModelRenderer horns_4;
	private ModelRenderer horns_5;
	private ModelRenderer spaulder_L3;
	private ModelRenderer spaulder_L2;
	private ModelRenderer spaulder_L1;
	private ModelRenderer spaulder_R3;
	private ModelRenderer spaulder_R2;
	private ModelRenderer spaulder_R1;

	public ModelBerserkerArmor(float par1) {
		super(par1, 0, 65, 64);

		textureWidth = 65;
		textureHeight = 64;

		horns_1 = new ModelRenderer(this, 0, 40);
		horns_1.addBox(4F, -8F, 0F, 1, 3, 3, par1 / 2);
		horns_1.setRotationPoint(0F, 0F, 0F);
		horns_1.setTextureSize(65, 64);
		horns_1.mirror = true;
		setRotation(horns_1, 0F, 0F, 0F);
		horns_2 = new ModelRenderer(this, 0, 32);
		horns_2.addBox(5F, -8.5F, 0.5F, 1, 2, 2, par1 / 2);
		horns_2.setRotationPoint(0F, 0F, 0F);
		horns_2.setTextureSize(65, 64);
		horns_2.mirror = true;
		setRotation(horns_2, 0F, 0F, 0F);
		horns_3 = new ModelRenderer(this, 0, 36);
		horns_3.addBox(6F, -10F, 1F, 1, 3, 1, par1 / 2);
		horns_3.setRotationPoint(0F, 0F, 0F);
		horns_3.setTextureSize(65, 64);
		horns_3.mirror = true;
		setRotation(horns_3, 0F, 0F, 0F);
		horns_4 = new ModelRenderer(this, 0, 40);
		horns_4.addBox(-5F, -8F, -1F, 1, 3, 3, par1 / 2);
		horns_4.setRotationPoint(0F, 0F, 0F);
		horns_4.setTextureSize(65, 64);
		horns_4.mirror = true;
		setRotation(horns_4, 0F, 0F, 0F);
		horns_5 = new ModelRenderer(this, 0, 32);
		horns_5.addBox(-6F, -8.5F, -0.5F, 1, 2, 2, par1 / 2);
		horns_5.setRotationPoint(0F, 0F, 0F);
		horns_5.setTextureSize(65, 64);
		horns_5.mirror = true;
		setRotation(horns_5, 0F, 0F, 0F);
		spaulder_L3 = new ModelRenderer(this, 8, 39);
		spaulder_L3.addBox(-1F, -3F, -2.5F, 5, 2, 5, par1);
		spaulder_L3.setRotationPoint(0F, 0F, 0F);
		spaulder_L3.setTextureSize(65, 64);
		spaulder_L3.mirror = true;
		setRotation(spaulder_L3, 0F, 0F, 0.3490659F);
		spaulder_L2 = new ModelRenderer(this, 10, 32);
		spaulder_L2.addBox(0F, -5.5F, -0.5F, 1, 3, 1, par1 / 2);
		spaulder_L2.setRotationPoint(0F, 0F, 0F);
		spaulder_L2.setTextureSize(65, 64);
		spaulder_L2.mirror = true;
		setRotation(spaulder_L2, 0F, 0F, 0.4014257F);
		spaulder_L1 = new ModelRenderer(this, 14, 32);
		spaulder_L1.addBox(1F, -5.5F, -0.5F, 1, 2, 1, par1 / 2);
		spaulder_L1.setRotationPoint(0F, 0F, 0F);
		spaulder_L1.setTextureSize(65, 64);
		spaulder_L1.mirror = true;
		setRotation(spaulder_L1, 0F, 0F, 0.8203047F);
		spaulder_R3 = new ModelRenderer(this, 8, 39);
		spaulder_R3.addBox(-4F, -3F, -2.5F, 5, 2, 5, par1);
		spaulder_R3.setRotationPoint(0F, 0F, 0F);
		spaulder_R3.setTextureSize(65, 64);
		spaulder_R3.mirror = true;
		setRotation(spaulder_R3, 0F, 0F, -0.3490659F);
		spaulder_R2 = new ModelRenderer(this, 14, 32);
		spaulder_R2.addBox(-2F, -5.5F, -0.5F, 1, 2, 1, par1 / 2);
		spaulder_R2.setRotationPoint(0F, 0F, 0F);
		spaulder_R2.setTextureSize(65, 64);
		spaulder_R2.mirror = true;
		setRotation(spaulder_R2, 0F, 0F, -0.8203047F);
		spaulder_R1 = new ModelRenderer(this, 10, 32);
		spaulder_R1.addBox(-1F, -5.5F, -0.5F, 1, 3, 1, par1 / 2);
		spaulder_R1.setRotationPoint(0F, 0F, 0F);
		spaulder_R1.setTextureSize(65, 64);
		spaulder_R1.mirror = true;
		setRotation(spaulder_R1, 0F, 0F, -0.3490659F);

		bipedRightArm.addChild(spaulder_R1);
		bipedRightArm.addChild(spaulder_R2);
		bipedRightArm.addChild(spaulder_R3);
		
		bipedLeftArm.addChild(spaulder_L1);
		bipedLeftArm.addChild(spaulder_L2);
		bipedLeftArm.addChild(spaulder_L3);
		
		bipedHead.addChild(horns_1);
		bipedHead.addChild(horns_2);
		bipedHead.addChild(horns_3);
		bipedHead.addChild(horns_4);
		bipedHead.addChild(horns_5);

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

	public void showHorns(boolean show) {
		horns_1.showModel = horns_2.showModel = horns_3.showModel = horns_4.showModel = horns_5.showModel = show;
	}

	public void showSpaulders(boolean show) {
		spaulder_L3.showModel = spaulder_L2.showModel = spaulder_L1.showModel = spaulder_R3.showModel = spaulder_R2.showModel = spaulder_R1.showModel = show;
	}
}