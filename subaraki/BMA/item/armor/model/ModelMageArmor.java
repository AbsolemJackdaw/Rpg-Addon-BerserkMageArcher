package subaraki.BMA.item.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMageArmor extends ModelBiped {
	// fields
	private ModelRenderer hat_part_1;
	private ModelRenderer hat_part_2;
	private ModelRenderer hat_part_3;
	private ModelRenderer hat_part_4;
	private ModelRenderer hat_part_5;
	private ModelRenderer hat_part_6;
	private ModelRenderer hat_part_7;
	private ModelRenderer hat_part_8;
	private ModelRenderer hat_part_9;
	private ModelRenderer hat_part_10;
	private ModelRenderer hat_part_11;
	private ModelRenderer hat_part_12;
	private ModelRenderer hat_part_13;
	private ModelRenderer arms_part_L;
	private ModelRenderer arms_part_2;
	private ModelRenderer chest;
	private ModelRenderer feet_part_L3;
	private ModelRenderer feet_part_L2;
	private ModelRenderer feet_part_L1;
	private ModelRenderer feet_part_R3;
	private ModelRenderer feet_part_R2;
	private ModelRenderer feet_part_R1;
	
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightarm;
	private ModelRenderer leftarm;
	private ModelRenderer rightleg;
	private ModelRenderer leftleg;

	public ModelMageArmor(float par1) {

		super(par1, 0, 65, 64);

		textureWidth = 65;
		textureHeight = 64;
		hat_part_1 = new ModelRenderer(this, 0, 51);
		hat_part_1.addBox(-2F, -8F, -6F, 4, 1, 12, par1);
		hat_part_1.setRotationPoint(0F, 0F, 0F);
		hat_part_1.setTextureSize(65, 64);
		hat_part_1.mirror = true;
		setRotation(hat_part_1, 0F, 0F, 0F);
		hat_part_2 = new ModelRenderer(this, 0, 59);
		hat_part_2.addBox(-6F, -8F, -2F, 12, 1, 4, par1);
		hat_part_2.setRotationPoint(0F, 0F, 0F);
		hat_part_2.setTextureSize(65, 64);
		hat_part_2.mirror = true;
		setRotation(hat_part_2, 0F, 0F, 0F);
		hat_part_3 = new ModelRenderer(this, 0, 62);
		hat_part_3.addBox(-4F, -8F, -5F, 8, 1, 1, par1);
		hat_part_3.setRotationPoint(0F, 0F, 0F);
		hat_part_3.setTextureSize(65, 64);
		hat_part_3.mirror = true;
		setRotation(hat_part_3, 0F, 0F, 0F);
		hat_part_4 = new ModelRenderer(this, 0, 55);
		hat_part_4.addBox(4F, -8F, -4F, 1, 1, 8, par1);
		hat_part_4.setRotationPoint(0F, 0F, 0F);
		hat_part_4.setTextureSize(65, 64);
		hat_part_4.mirror = true;
		setRotation(hat_part_4, 0F, 0F, 0F);
		hat_part_5 = new ModelRenderer(this, 0, 55);
		hat_part_5.addBox(-5F, -8F, -4F, 1, 1, 8, par1);
		hat_part_5.setRotationPoint(0F, 0F, 0F);
		hat_part_5.setTextureSize(65, 64);
		hat_part_5.mirror = true;
		setRotation(hat_part_5, 0F, 0F, 0F);
		hat_part_6 = new ModelRenderer(this, 0, 62);
		hat_part_6.addBox(-4F, -8F, 4F, 8, 1, 1, par1);
		hat_part_6.setRotationPoint(0F, 0F, 0F);
		hat_part_6.setTextureSize(65, 64);
		hat_part_6.mirror = true;
		setRotation(hat_part_6, 0F, 0F, 0F);
		hat_part_7 = new ModelRenderer(this, 0, 56);
		hat_part_7.addBox(-3.5F, -9F, -3.5F, 7, 1, 7);
		hat_part_7.setRotationPoint(0F, 0F, 0F);
		hat_part_7.setTextureSize(65, 64);
		hat_part_7.mirror = true;
		setRotation(hat_part_7, 0F, 0F, 0F);
		hat_part_8 = new ModelRenderer(this, 0, 56);
		hat_part_8.addBox(-3F, -11F, -3F, 6, 2, 6);
		hat_part_8.setRotationPoint(0F, 0F, 0F);
		hat_part_8.setTextureSize(65, 64);
		hat_part_8.mirror = true;
		setRotation(hat_part_8, 0F, 0F, 0F);
		hat_part_9 = new ModelRenderer(this, 0, 57);
		hat_part_9.addBox(-2.5F, -13F, -2.5F, 5, 2, 5);
		hat_part_9.setRotationPoint(0F, 0F, 0F);
		hat_part_9.setTextureSize(65, 64);
		hat_part_9.mirror = true;
		setRotation(hat_part_9, 0F, 0F, 0F);
		hat_part_10 = new ModelRenderer(this, 0, 58);
		hat_part_10.addBox(-1.5F, -14F, -2F, 4, 1, 4);
		hat_part_10.setRotationPoint(0F, 0F, 0F);
		hat_part_10.setTextureSize(65, 64);
		hat_part_10.mirror = true;
		setRotation(hat_part_10, 0F, 0F, 0F);
		hat_part_11 = new ModelRenderer(this, 0, 58);
		hat_part_11.addBox(-2F, -15F, -1.5F, 4, 1, 3);
		hat_part_11.setRotationPoint(0F, 0F, 0F);
		hat_part_11.setTextureSize(65, 64);
		hat_part_11.mirror = true;
		setRotation(hat_part_11, 0F, 0F, 0F);
		hat_part_12 = new ModelRenderer(this, 0, 58);
		hat_part_12.addBox(-3F, -16F, -1F, 4, 1, 2);
		hat_part_12.setRotationPoint(0F, 0F, 0F);
		hat_part_12.setTextureSize(65, 64);
		hat_part_12.mirror = true;
		setRotation(hat_part_12, 0F, 0F, 0F);
		hat_part_13 = new ModelRenderer(this, 0, 55);
		hat_part_13.addBox(-2F, -17F, -0.5F, 2, 1, 1);
		hat_part_13.setRotationPoint(0F, 0F, 0F);
		hat_part_13.setTextureSize(65, 64);
		hat_part_13.mirror = true;
		setRotation(hat_part_13, 0F, 0F, 0F);
		arms_part_L = new ModelRenderer(this, 0, 39);
		arms_part_L.addBox(-1F, -1F, -2.5F, 5, 2, 5, par1);
		arms_part_L.setRotationPoint(0F, 0F, 0F);
		arms_part_L.setTextureSize(65, 64);
		arms_part_L.mirror = true;
		setRotation(arms_part_L, 0F, 0F, 0.296706F);
		arms_part_2 = new ModelRenderer(this, 0, 39);
		arms_part_2.addBox(-4F, -1F, -2.5F, 5, 2, 5, par1);
		arms_part_2.setRotationPoint(0F, 0F, 0F);
		arms_part_2.setTextureSize(65, 64);
		arms_part_2.mirror = true;
		setRotation(arms_part_2, 0F, 0F, -0.296706F);
		chest = new ModelRenderer(this, 47, 50);
		chest.addBox(-4F, 9F, -2F, 8, 13, 1);
		chest.setRotationPoint(0F, 0F, 0F);
		chest.setTextureSize(65, 64);
		chest.mirror = true;
		setRotation(chest, 0.3316126F, 0F, 0F);
		feet_part_L3 = new ModelRenderer(this, 20, 43);
		feet_part_L3.addBox(-2F, 10F, -3F, 4, 2, 1, par1 / 2);
		feet_part_L3.setRotationPoint(0F, 0F, 0F);
		feet_part_L3.setTextureSize(65, 64);
		feet_part_L3.mirror = true;
		setRotation(feet_part_L3, 0F, 0F, 0F);
		feet_part_L2 = new ModelRenderer(this, 20, 40);
		feet_part_L2.addBox(-1.5F, 9.5F, -4F, 3, 2, 1, par1 / 2);
		feet_part_L2.setRotationPoint(0F, 0F, 0F);
		feet_part_L2.setTextureSize(65, 64);
		feet_part_L2.mirror = true;
		setRotation(feet_part_L2, 0F, 0F, 0F);
		feet_part_L1 = new ModelRenderer(this, 20, 38);
		feet_part_L1.addBox(-0.5F, 9F, -5F, 1, 1, 1, par1 / 2);
		feet_part_L1.setRotationPoint(0F, 0F, 0F);
		feet_part_L1.setTextureSize(65, 64);
		feet_part_L1.mirror = true;
		setRotation(feet_part_L1, 0F, 0F, 0F);
		feet_part_R3 = new ModelRenderer(this, 20, 43);
		feet_part_R3.addBox(-2F, 10F, -3F, 4, 2, 1, par1 / 2);
		feet_part_R3.setRotationPoint(0F, 0F, 0F);
		feet_part_R3.setTextureSize(65, 64);
		feet_part_R3.mirror = true;
		setRotation(feet_part_R3, 0F, 0F, 0F);
		feet_part_R2 = new ModelRenderer(this, 20, 40);
		feet_part_R2.addBox(-1F, 9.5F, -4F, 3, 2, 1, par1 / 2);
		feet_part_R2.setRotationPoint(0F, 0F, 0F);
		feet_part_R2.setTextureSize(65, 64);
		feet_part_R2.mirror = true;
		setRotation(feet_part_R2, 0F, 0F, 0F);
		feet_part_R1 = new ModelRenderer(this, 20, 38);
		feet_part_R1.addBox(0F, 9F, -5F, 1, 1, 1, par1 / 2);
		feet_part_R1.setRotationPoint(0F, 0F, 0F);
		feet_part_R1.setTextureSize(65, 64);
		feet_part_R1.mirror = true;
		setRotation(feet_part_R1, 0F, 0F, 0F);

		bipedRightLeg.addChild(feet_part_R1);
		bipedRightLeg.addChild(feet_part_R2);
		bipedRightLeg.addChild(feet_part_R3);
		bipedLeftLeg.addChild(feet_part_L1);
		bipedLeftLeg.addChild(feet_part_L2);
		bipedLeftLeg.addChild(feet_part_L3);
		bipedBody.addChild(chest);
		bipedLeftArm.addChild(arms_part_L);
		bipedRightArm.addChild(arms_part_2);
		bipedHead.addChild(hat_part_13);
		bipedHead.addChild(hat_part_12);
		bipedHead.addChild(hat_part_11);
		bipedHead.addChild(hat_part_10);
		bipedHead.addChild(hat_part_9);
		bipedHead.addChild(hat_part_8);
		bipedHead.addChild(hat_part_7);
		bipedHead.addChild(hat_part_6);
		bipedHead.addChild(hat_part_5);
		bipedHead.addChild(hat_part_4);
		bipedHead.addChild(hat_part_3);
		bipedHead.addChild(hat_part_2);
		bipedHead.addChild(hat_part_1);

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
