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
		this.setThrowableHeading(livingBaseIn.getLookVec().x, livingBaseIn.getLookVec().y, livingBaseIn.getLookVec().z, 1.5f, 0.0f);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote){
			if (result.entityHit != null){
				EntityLivingBase elb = (EntityLivingBase)result.entityHit;
				if(elb.getHeldItemMainhand() == ItemStack.EMPTY){
					return;
				}
				ItemStack held = elb.getHeldItemMainhand().copy();
				EntityItem ei = new EntityItem(elb.world, elb.posX, elb.posY, elb.posZ, held);
				ei.motionX *= 2;
				ei.motionZ *= 2;
				elb.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
				elb.world.spawnEntity(ei);
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
		
		if(world.isRemote){
			for(int i = 0 ; i < 5; i++)
			world.spawnParticle(EnumParticleTypes.REDSTONE,
					posX - 0.5f + world.rand.nextFloat()/2f,
					posY,
					posZ - 0.5f + world.rand.nextFloat()/2f,
					0.0D, 0.0D, 0.0D,
					new int[0]);
		}
	}
}
