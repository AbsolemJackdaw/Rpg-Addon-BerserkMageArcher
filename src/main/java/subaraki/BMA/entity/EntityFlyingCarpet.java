package subaraki.BMA.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class EntityFlyingCarpet extends EntityLiving {

	private static final DataParameter<Integer> META1 = EntityDataManager.<Integer>createKey(EntityFlyingCarpet.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> META2 = EntityDataManager.<Integer>createKey(EntityFlyingCarpet.class, DataSerializers.VARINT);

	public EntityFlyingCarpet(World worldIn) {
		super(worldIn);
		this.setNoGravity(true);
		this.setSize(1, 0.2f);
		this.enablePersistence();
	}


	public EntityFlyingCarpet(World world, double x, double y, double z) {
		this(world);
		this.setPosition(x, y, z);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(META1, Integer.valueOf(0));
		this.dataManager.register(META2, Integer.valueOf(0));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.getRidingEntity() != null)
		{
			this.getRidingEntity().fallDistance = 0.0F;
		}
		else
		{

		}
		this.fallDistance = 0.0f;
	}

	////////////////////////////////////////////////////////////////////////////////
	///////////////////RIDING DATA////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////

	@Override
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
	}

	@Override
	public boolean canBeSteered() {
		return true;
	}

	@Override
	public double getMountedYOffset() {
		return 0d;
	}

	private void mountTo(EntityPlayer player) {
		player.rotationYaw = this.rotationYaw;
		player.rotationPitch = this.rotationPitch;

		if (!this.world.isRemote){
			player.startRiding(this);
		}
	}

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		if(!world.isRemote){
			this.entityDropItem(new ItemStack(Blocks.CARPET,1,getMeta()[0]), 0);
			this.entityDropItem(new ItemStack(Blocks.CARPET,1,getMeta()[1]), 0);
		}
	}

	@Override
	public void moveRelative(float strafe, float up, float forward, float friction) {

		if (this.isBeingRidden() && this.canBeSteered())
		{
			EntityLivingBase elb = (EntityLivingBase)this.getControllingPassenger();
			this.rotationYaw = elb.rotationYaw;
			this.prevRotationYaw = this.rotationYaw;
			this.rotationPitch = elb.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.renderYawOffset = this.rotationYaw;
			this.rotationYawHead = this.renderYawOffset;

			strafe = elb.moveStrafing * 0.5F;
			forward = elb.moveForward/7f;

			if(elb.posY > 128)
				rotationPitch = rotationPitch < 0 ? 0 : rotationPitch;

			up = -forward* (rotationPitch / 45);

			this.stepHeight = 0.0F;
			this.jumpMovementFactor = this.getAIMoveSpeed() * 3.5F;

			if (forward <= 0.0F)
			{
				forward *= 0.25F;
			}

			if (this.canPassengerSteer())
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				super.moveRelative(strafe, up, forward, friction);
			}
			else if (elb instanceof EntityPlayer)
			{
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

			if (f2 > 1.0F)
			{
				f2 = 1.0F;
			}

			this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.jumpMovementFactor = 0.02F;
			super.moveRelative(strafe, up, forward, friction);
		}
	}

	////////////////////////////////////////////
	///////////////////////////////////////////
	//////////////////////////////////////////

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		mountTo(player);
		return super.processInteract(player, hand);
	}    

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return null;
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return Arrays.asList(new ItemStack[]{ItemStack.EMPTY});
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}

	public int[] getMeta() {
		return new int[]{this.dataManager.get(META1), this.dataManager.get(META2)};
	}

	public void setMeta(int meta1, int meta2)
	{
		this.dataManager.set(META1, meta1);
		this.dataManager.set(META2, meta2);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("meta1", this.dataManager.get(META1));
		nbt.setInteger("meta2", this.dataManager.get(META2));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataManager.set(META1, nbt.getInteger("meta1"));
		this.dataManager.set(META2, nbt.getInteger("meta2"));
	}
}