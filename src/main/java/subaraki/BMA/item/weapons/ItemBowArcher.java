package subaraki.BMA.item.weapons;

import javax.annotation.Nullable;

import lib.playerclass.capability.PlayerClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.handler.network.PacketHandler;
import subaraki.BMA.handler.network.SSyncBowShot;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.mod.AddonBma;

public class ItemBowArcher extends Item
{
	private boolean isFlipped;

	public ItemBowArcher()
	{
		this.maxStackSize = 1;
		this.addPropertyOverride(new ResourceLocation(AddonBma.MODID+":pull_amount"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				float value = entityIn == null ? 0.0F :
					(entityIn.getActiveItemStack().getItem() != BmaItems.bow ? 0.0F :
						(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F);
				return value;
			}
		});
		this.addPropertyOverride(new ResourceLocation(AddonBma.MODID+":ispulling"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				float value = entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
				return value;
			}
		});
		this.addPropertyOverride(new ResourceLocation(AddonBma.MODID+":flipped"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return stack.getItem() == BmaItems.bow && ((ItemBowArcher)stack.getItem()).isFlipped() ? 1F : 0F;
			}
		});
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{

		if (entityLiving instanceof EntityPlayer)
		{
			//hacky way to fix server/client pullback(timeLeft) desync issue.
			//this should prevent the server being out of sync with the client cues
			//send time left data over a packet to the server, and let the packet handle the actual arrow shooting
			
			EntityPlayer player = (EntityPlayer)entityLiving;

			ItemStack arrowStack = this.findAmmo((EntityPlayer) entityLiving);

			if(!PlayerClass.get(player).isPlayerClass(BmaItems.archerClass))
				return;
			if(arrowStack.isEmpty())
				return;

			int pullbackPower = this.getMaxItemUseDuration(stack) - timeLeft;
			pullbackPower = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer)entityLiving, pullbackPower, true);
			if (pullbackPower < 0) return;

			System.out.println("A. Client ? " + worldIn.isRemote + " " +pullbackPower);
			
			if(!worldIn.isRemote)
				return;
			PacketHandler.NETWORK.sendToServer(new SSyncBowShot(pullbackPower, stack, arrowStack));
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase elb, int count) {

		if(elb instanceof EntityPlayer)
			if(!PlayerClass.get((EntityPlayer)elb).isPlayerClass(BmaItems.archerClass))
				return;

		if(getArrowVelocity((int)((stack.getMaxItemUseDuration() - elb.getItemInUseCount())))*2f >= 4f && !isFlipped)
			if(elb.world.isRemote){
				World world = elb.world;

				for(int i = 0; i < 5; i++)
					world.spawnParticle(EnumParticleTypes.FLAME, 
							elb.posX + 0.25d+(world.rand.nextDouble()*2) - 1.25D, 
							elb.posY , 
							elb.posZ + 0.25d+(world.rand.nextDouble()*2) - 1.25D,
							0, 0.1d, 0, new int[0]);
			}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		ItemStack stack = playerIn.getHeldItem(hand);

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(stack, worldIn, playerIn, hand, false);
		if (ret != null) return ret;

		if(!PlayerClass.get(playerIn).isPlayerClass(BmaItems.archerClass) || findAmmo(playerIn).isEmpty())
			return  new ActionResult(EnumActionResult.FAIL, stack);

		playerIn.setActiveHand(hand);
		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		if(entityLiving.isSwingInProgress)
		{
			if(stack.getItem() == BmaItems.bow && !entityLiving.world.isRemote)
			{
				ItemBowArcher bow = (ItemBowArcher)stack.getItem();
				bow.setFlipped(!bow.isFlipped());
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge)
	{
		float f = (float)charge / 30.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		return f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getItemEnchantability()
	{
		return 0;
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

	public void setFlipped(boolean isFlipped) {
		this.isFlipped = isFlipped;
	}

	public boolean isFlipped() {
		return isFlipped;
	}

	private ItemStack findAmmo(EntityPlayer player)
	{
		if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isArrow(itemstack))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isArrow(ItemStack stack)
	{
		return stack.getItem() instanceof ItemArrow;
	}
}
