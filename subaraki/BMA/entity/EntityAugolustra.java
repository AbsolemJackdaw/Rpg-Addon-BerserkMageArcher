package subaraki.BMA.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityAugolustra extends EntityThrowable{

	public EntityAugolustra(World worldIn){
		super(worldIn);
	}
	public EntityAugolustra(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	public EntityAugolustra(World worldIn, EntityLivingBase livingBaseIn){
		super(worldIn, livingBaseIn);
		this.setThrowableHeading(livingBaseIn.getLookVec().xCoord, livingBaseIn.getLookVec().yCoord, livingBaseIn.getLookVec().zCoord, 1.5f, 0.0f);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.worldObj.isRemote){
			if (result.entityHit != null)
				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5);

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
		
		if(ticksExisted > 30)
			this.setDead();
		
		if(worldObj.isRemote){
			for(int i = 0 ; i < 5; i++)
			worldObj.spawnParticle(EnumParticleTypes.LAVA, posX - 0.5f + worldObj.rand.nextFloat()/2f, posY, posZ - 0.5f + worldObj.rand.nextFloat()/2f, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}
}