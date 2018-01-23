package subaraki.BMA.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import subaraki.BMA.block.BmaBlocks;

public class EntityScintilla extends EntityThrowable{

	public EntityScintilla(World worldIn){
		super(worldIn);
	}
	public EntityScintilla(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	public EntityScintilla(World worldIn, EntityLivingBase livingBaseIn){
		super(worldIn, livingBaseIn);
		this.shoot(livingBaseIn.getLookVec().x, livingBaseIn.getLookVec().y, livingBaseIn.getLookVec().z, 1.5f, 0.0f);
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		if (!this.world.isRemote)
		{
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

		if(ticksExisted > 20)
			this.setDead();

		BlockPos pos = this.getPosition();
		if(world.isAirBlock(pos))
		{
			world.setBlockState(pos, BmaBlocks.airLuminence.getDefaultState());
			world.notifyLightSet(pos);
		}

		if(world.isRemote){
			for(int i = 0 ; i < 5; i++)
				world.spawnParticle(EnumParticleTypes.SNOWBALL, 
						posX + (double)i/10d + ((Math.sin(((double)(this.ticksExisted)+ (double)i/20d)*5d)/1.5d)) ,
						posY + (double)i/10d + (Math.cos(((double)(this.ticksExisted)+ (double)i/20d)*5d)/1.5d), 
						posZ + (double)i/10d + ((Math.sin(((double)(this.ticksExisted)+ (double)i/20d)*5d)/1.5d)),
						0.0D, 0.0D, 0.0D, new int[0]);
		}
	}
}
