package subaraki.BMA.item.weapons;

import javax.annotation.Nullable;

import lib.playerclass.capability.PlayerClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.item.BmaItems;

public class ItemBowArcher extends Item
{
	public ItemBowArcher()
	{
		this.maxStackSize = 1;
		this.addPropertyOverride(new ResourceLocation("pull_archer"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				if (entityIn == null)
				{
					return 0.0F;
				}
				else
				{
					ItemStack itemstack = entityIn.getActiveItemStack();
					float timer = itemstack != ItemStack.EMPTY && itemstack.getItem() == BmaItems.bow ? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;

					return timer;
				}
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling_archer"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityLiving;
			
			if(!PlayerClass.armorClass(player).isInstanceOf(BmaItems.archerClass))
				return;
			
			int i = this.getMaxItemUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer)entityLiving, i, true);
			if (i < 0) return;

			float f = getArrowVelocity(i)*2f;
			if(f > 1f)
				f=1f;

			if ((double)f >= 0.1D)
			{
				if (!worldIn.isRemote)
				{
					float time = (stack.getMaxItemUseDuration() - entityLiving.getItemInUseCount()) / 20.0F;

					if(time < 2f)
						spawnArrow(player, worldIn, stack, f, 0);

					else if( time >= 2f && time < 3f)
						if(PlayerClass.armorClass(player).isShielded())
							for(int yaw = -4; yaw < 6; yaw+=2)
								spawnArrow(player, worldIn, stack, f, yaw);
						else
							for(int yaw = -2; yaw < 4; yaw+=2)
								spawnArrow(player, worldIn, stack, f, yaw);

					else if(time >= 3f)
						spawnHellArrow(worldIn, player, f);
				}

				worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				player.addStat(StatList.getObjectUseStats(this));
			}
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase elb, int count) {
		
		if(elb instanceof EntityPlayer)
		if(!PlayerClass.armorClass((EntityPlayer)elb).isInstanceOf(BmaItems.archerClass))
			return;

		if((stack.getMaxItemUseDuration() - elb.getItemInUseCount()) / 20.0F >= 3f)
			if(elb.world.isRemote){
				World world = elb.world;

				for(int i = 0; i < 5; i++)
					world.spawnParticle(EnumParticleTypes.FLAME, 
							elb.posX + (0.5D + world.rand.nextDouble()*2) - 1.25D, 
							elb.posY + world.rand.nextDouble(), 
							elb.posZ + (0.5D + world.rand.nextDouble()*2) - 1.25D,
							0, 0, 0, new int[0]);

			}

	}

	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, false);
		if (ret != null) return ret;

		if(!PlayerClass.armorClass(playerIn).isInstanceOf(BmaItems.archerClass))
			return  new ActionResult(EnumActionResult.FAIL, itemStackIn);

		playerIn.setActiveHand(hand);
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}
	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge)
	{
		float f = (float)charge / 30.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		return f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack stack)
	{
		//		System.out.println(RpgInventory.playerClass);
		//		if(!RpgInventory.playerClass.contains(BmaItems.archerClass))
		//			return EnumAction.NONE;

		return EnumAction.BOW;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability()
	{
		return 0;
	}

	private void spawnArrow(EntityPlayer entityplayer, World worldIn, ItemStack stack, float power, float yaw){
		ItemArrow itemarrow = (ItemArrow) Items.ARROW;
		EntityArrow entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), entityplayer);
		entityarrow.setAim(entityplayer, entityplayer.rotationPitch + yaw / 2f, entityplayer.rotationYaw, 0.0F, power * 3.0F, 0.3F);

		if (power> 1.0F)
			entityarrow.setIsCritical(true);
		entityarrow.setDamage(entityarrow.getDamage() + (double)2 * 0.5D + 0.5D);
		entityarrow.setKnockbackStrength(1);
		stack.damageItem(1, entityplayer);
		entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
		worldIn.spawnEntity(entityarrow);
	}

	private void spawnHellArrow(World world, EntityPlayer player, float force){
		if(!world.isRemote){
			EntityHellArrow arrow = new EntityHellArrow(world, player, force);
			world.spawnEntity(arrow);
		}
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem().equals(Items.EMERALD) ? true : super.getIsRepairable(toRepair, repair);
	}
}
