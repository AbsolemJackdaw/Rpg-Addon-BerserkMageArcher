package subaraki.BMA.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import subaraki.BMA.config.ConfigurationHandler;

public class EntityBombarda extends EntityThrowable{

	public EntityBombarda(World worldIn){
		super(worldIn);
	}
	public EntityBombarda(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	public EntityBombarda(World worldIn, EntityLivingBase livingBaseIn){
		super(worldIn, livingBaseIn);
		this.shoot(livingBaseIn.getLookVec().x, livingBaseIn.getLookVec().y, livingBaseIn.getLookVec().z, 1.5f, 0.0f);
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		if (!this.world.isRemote){
			if (result.typeOfHit == RayTraceResult.Type.BLOCK || result.typeOfHit == RayTraceResult.Type.ENTITY)
			{
				BlockPos pos = result.typeOfHit == RayTraceResult.Type.ENTITY ? result.entityHit.getPosition() : this.getPosition(); 
				
				this.world.createExplosion(this, this.posX, this.posY, this.posZ, 3f, false);
				
				this.setDead();
			}
		}
	}

	@Override
	protected float getGravityVelocity(){
		return 0F;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(ticksExisted > 50)
			this.setDead();

		if(world.isRemote){
			for(int i = 0 ; i < 5; i++)
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX - 0.5f + world.rand.nextFloat()/2f, posY, posZ - 0.5f + world.rand.nextFloat()/2f, 0.0D, 0.0D, 0.0D, new int[0]);
			
			for(int i = 0 ; i < 5; i++)
				world.spawnParticle(EnumParticleTypes.FLAME,
						posX + (double)i/10d + (this.getLookVec().x == 0 ? 0 : (Math.sin(((double)(this.ticksExisted)+ (double)i/20d)*5d)/1.5d)) ,
						posY, 
						posZ + (double)i/10d + (this.getLookVec().z == 0 ? 0 : (Math.sin(((double)(this.ticksExisted)+ (double)i/20d)*5d)/1.5d)),
						0.0D, 0.0D, 0.0D, new int[0]);
	
		}
	}
}
