package subaraki.BMA.entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class EntityHammerSmash extends EntityLivingBase{

	private ItemStack[] inventory = new ItemStack[1];
	private float rotationAngle;

	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(EntityTameable.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public EntityHammerSmash(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit(){
		super.entityInit();
		this.dataManager.register(OWNER_UNIQUE_ID, Optional.<UUID>absent());
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return Arrays.asList(this.inventory);
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return inventory[0];
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		if(stack == null)
			return;
		inventory[0] = stack.copy();
	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return null;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		String uuid = "";

		if (compound.hasKey("OwnerUUID", 8))
			uuid = compound.getString("OwnerUUID");
		if (!uuid.isEmpty())
			this.setOwnerId(UUID.fromString(uuid));

		NBTTagCompound stacktag = new NBTTagCompound();
		if(inventory[0] != null){
			inventory[0].writeToNBT(stacktag);
			compound.setTag("stack", stacktag);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (this.getOwnerId() == null)
			compound.setString("OwnerUUID", "");
		else
			compound.setString("OwnerUUID", this.getOwnerId().toString());

		if(compound.hasKey("stack")){
			NBTTagCompound stackTag = compound.getCompoundTag("stack");
			inventory[0] = ItemStack.loadItemStackFromNBT(stackTag);
		}
	}

	@Override
	public void onUpdate() {
		motionX = motionY = motionZ = 0;
		super.onUpdate();

		if(rotationAngle < 90)
			rotationAngle = ticksExisted*16;
		else
			rotationAngle = 90;

		if(ticksExisted == 1)
			worldObj.playSound(posX, posY, posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.NEUTRAL, 1F, 0.5F, true);

		if(ticksExisted == 4)
			worldObj.playSound(posX, posY, posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.NEUTRAL, 0.5F, 0.005F, true);

		if(ticksExisted > 9){
			
			if(worldObj.isRemote){
				for(float fx = -5; fx < 5; fx+=0.5f){
					for(float fz = -5; fz < 5; fz+=0.5f){
						double x = (double)((float)posX + fx + rand.nextFloat()/4);
						double y = (double)((float)posY + rand.nextFloat());
						double z = (double)((float)posZ + fz + rand.nextFloat()/4);

						worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, -0.05D, 0.0D, new int[0]);
					}
				}
			}
			
			AxisAlignedBB pool = getEntityBoundingBox().expand(5, 2, 5);
			List<EntityLivingBase> entl = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, pool);

			for(EntityLivingBase el : entl){
				
				if(el instanceof EntityPlayer)
					if(((EntityPlayer)el).getUniqueID().equals(getOwnerId()))
						continue;
				if(el instanceof EntityHammerSmash)
					continue;
				if(el instanceof EntityTameable && ((EntityTameable)el).getOwnerId().equals(getOwnerId()))
					continue;
				
				double xdir = el.posX - posX;
				double zdir = el.posZ - posZ;

				el.motionX = xdir* ( 2F);
				el.motionY =  1.5F;
				el.motionZ = zdir* (2F);

				el.attackEntityFrom(DamageSource.causeIndirectDamage(this, el),20);
			}
			if(getOwner() == null )
				if(inventory[0] != null)
					if(!worldObj.isRemote)
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, inventory[0].copy()));

			if(getOwner()!= null && inventory[0] != null)
				getOwner().inventory.addItemStackToInventory(inventory[0].copy());

			this.setDead();
		}
	}

	public float getRotationAngle() {
		return rotationAngle;
	}

	@Nullable
	public UUID getOwnerId(){
		return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
	}

	public void setOwnerId(@Nullable UUID owner){
		this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(owner));
	}

	public EntityPlayer getOwner(){
		UUID uuid = this.getOwnerId();
		return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
	}
}
