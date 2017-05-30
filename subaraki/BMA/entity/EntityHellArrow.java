package subaraki.BMA.entity;

import lib.playerclass.capability.PlayerClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityHellArrow extends EntityThrowable{

	private int arrows = 0;
	private float force = 0;
	private float attackPower = 0.5F;

	public EntityHellArrow(World worldIn){
		super(worldIn);
	}
	public EntityHellArrow(World worldIn, EntityHellArrow prevArrow){
		super(worldIn,prevArrow.posX, prevArrow.posY, prevArrow.posZ);
		posX+=rand.nextDouble()*4 - 2D;
		posZ+=rand.nextDouble()*4 - 2D;

		this.motionX = prevArrow.motionX;
		this.motionY = prevArrow.motionY;
		this.motionZ = prevArrow.motionZ;

		this.rotationPitch = prevArrow.rotationPitch;
		setRotation(prevArrow.rotationYaw, prevArrow.rotationPitch);

		arrows = rand.nextInt(3)+1;
		force = prevArrow.getForce();
		attackPower = prevArrow.getAttackPower()+1.8F;
	}

	public EntityHellArrow(World worldIn, EntityLivingBase elb, float force){
		super(worldIn, elb);
		this.setThrowableHeading(elb.getLookVec().xCoord, elb.getLookVec().yCoord, elb.getLookVec().zCoord, force*3.0f, 0f);
		arrows = rand.nextInt(3)+1;
		this.force = force;
		if(elb instanceof EntityPlayer)
			if(PlayerClass.get((EntityPlayer)elb).isShielded()){
				attackPower = 3F;
			}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote){
			if (result.entityHit != null)
				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), attackPower);

			this.setDead();
		}
	}

	@Override
	protected float getGravityVelocity(){
		return 0.1F;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(ticksExisted == 8){
			for(int i = 0; i < arrows; i++){
				EntityHellArrow arrow = new EntityHellArrow(world, this);
				if(!world.isRemote)
					world.spawnEntity(arrow);
			}
		}

		if(ticksExisted > 15)
			this.setDead();

		if(world.isRemote){
			for(int i = 0 ; i < 5; i++)
				world.spawnParticle(EnumParticleTypes.FLAME, posX - 0.5f + world.rand.nextFloat()/2f, posY, posZ - 0.5f + world.rand.nextFloat()/2f, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	public float getForce() {
		return force;
	}

	public float getAttackPower() {
		return attackPower;
	}
}
