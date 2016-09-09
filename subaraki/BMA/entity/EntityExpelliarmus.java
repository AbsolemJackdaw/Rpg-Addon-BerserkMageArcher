package subaraki.BMA.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityExpelliarmus extends EntityThrowable{

	public EntityExpelliarmus(World worldIn){
		super(worldIn);
	}
	public EntityExpelliarmus(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	public EntityExpelliarmus(World worldIn, EntityLivingBase livingBaseIn){
		super(worldIn, livingBaseIn);
		this.setThrowableHeading(livingBaseIn.getLookVec().xCoord, livingBaseIn.getLookVec().yCoord, livingBaseIn.getLookVec().zCoord, 1.5f, 0.0f);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.worldObj.isRemote){
			if (result.entityHit != null){
				EntityLivingBase elb = (EntityLivingBase)result.entityHit;
				if(elb.getHeldItemMainhand() == null){
					return;
				}
				ItemStack held = elb.getHeldItemMainhand().copy();
				EntityItem ei = new EntityItem(elb.worldObj, elb.posX, elb.posY, elb.posZ, held);
				ei.motionX *= 2;
				ei.motionZ *= 2;
				elb.setHeldItem(EnumHand.MAIN_HAND, null);
				elb.worldObj.spawnEntityInWorld(ei);
			}

			this.setDead();
		}
	}

	@Override
	protected float getGravityVelocity(){
		return 0F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(ticksExisted > 10)
			this.setDead();
		
		if(worldObj.isRemote){
			for(int i = 0 ; i < 5; i++)
			worldObj.spawnParticle(EnumParticleTypes.REDSTONE,
					posX - 0.5f + worldObj.rand.nextFloat()/2f,
					posY,
					posZ - 0.5f + worldObj.rand.nextFloat()/2f,
					0.0D, 0.0D, 0.0D,
					new int[0]);
		}
	}
}
