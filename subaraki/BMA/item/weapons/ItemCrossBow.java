package subaraki.BMA.item.weapons;

import javax.annotation.Nullable;

import lib.playerclass.capability.PlayerClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.BMA.entity.EntityDart;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.mod.AddonBma;

public class ItemCrossBow extends Item {

	public ItemCrossBow() {
		this.setMaxDamage(384); //Same as a bow

		this.addPropertyOverride(new ResourceLocation(AddonBma.MODID+":loaded"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return isLoaded(stack) ? 1f : 0f;
			}
		});
		this.addPropertyOverride(new ResourceLocation(AddonBma.MODID+":pull_amount"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				float archerClassModifier = 45.0f;

				if(entityIn instanceof EntityPlayer)
					if(PlayerClass.get((EntityPlayer)entityIn).isPlayerClass(BmaItems.archerClass))
						archerClassModifier = 20.0F;

				float value = entityIn == null ? 0.0F : (entityIn.getActiveItemStack().getItem() != BmaItems.crossbow ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / archerClassModifier);
				return value;
			}
		});
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

		if (!(player instanceof EntityPlayer))
			return;

		int passed = getMaxItemUseDuration(stack)- count;

		int timeToLoad = 45;
		if(PlayerClass.get((EntityPlayer)player).isPlayerClass(BmaItems.archerClass))
			timeToLoad = 20;

		if(passed == timeToLoad && !isLoaded(stack))
		{
			player.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			player.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_CLOTH_STEP, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
	{
		if (!(entityLiving instanceof EntityPlayer))
			return;

		int timeToLoad = 45;
		if(PlayerClass.get((EntityPlayer)entityLiving).isPlayerClass(BmaItems.archerClass))
			timeToLoad = 20;

		if(getMaxItemUseDuration(stack)-timeLeft >= timeToLoad)
			if(entityLiving instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)entityLiving;
				if(!player.capabilities.isCreativeMode)
					this.findAmmo((EntityPlayer)player).shrink(1);
				setLoaded(stack, true);
			}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		ItemStack arrows = findAmmo(player);

		if(!isLoaded(stack) && !arrows.isEmpty())
		{
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		else if (isLoaded(stack))
		{
			setLoaded(stack, false);

			spawnArrow(player, world, stack, 5.0F, 0.0f);
			world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 5 * 0.5F);
			player.addStat(StatList.getObjectUseStats(this));

			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	private void spawnArrow(EntityPlayer entityplayer, World world, ItemStack stack, float power, float yaw){

		boolean isArcher = false;
		if(PlayerClass.get(entityplayer).isPlayerClass(BmaItems.archerClass))
			isArcher = true;

		EntityDart entityDart = new EntityDart(world, entityplayer);
		entityDart.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw+yaw, 0.0F, power * 3.0f, isArcher ? 0.01f : 1.0f);

		if (world.rand.nextInt(3) == 0)
			entityDart.setIsCritical(false);
		entityDart.setDamage(entityDart.getDamage());
		entityDart.setKnockbackStrength(1);
		stack.damageItem(1, entityplayer);
		entityDart.pickupStatus = EntityArrow.PickupStatus.ALLOWED;

		if(!world.isRemote)
			world.spawnEntity(entityDart);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
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
		return stack.getItem() == BmaItems.dart;
	}

	public boolean isLoaded(ItemStack stack) {
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("loaded"))
			return stack.getTagCompound().getBoolean("loaded") ;
		else 
			return false;
	}

	public void setLoaded(ItemStack stack, boolean flag){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setBoolean("loaded", flag);
	}
}
