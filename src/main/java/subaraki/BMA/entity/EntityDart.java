package subaraki.BMA.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import subaraki.BMA.config.ConfigurationHandler;
import subaraki.BMA.item.BmaItems;

public class EntityDart extends EntityArrow{

	public EntityDart(World world) {
		super(world);
	}
	
	public EntityDart(World world, EntityLivingBase shooter) {
		super(world, shooter);
		setDamage(ConfigurationHandler.instance.dart_arrow_damage);
	}
	
	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(BmaItems.dart);
	}
}
