package subaraki.BMA.item.weapons;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.mod.AddonBma;
import subaraki.rpginventory.mod.RpgInventory;

public class ItemWand extends Item {

	public ItemWand() {

		this.setHasSubtypes(true);
		setUnlocalizedName(AddonBma.MODID+":"+"wand");
		setRegistryName("wand");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getMetadata();
		return super.getUnlocalizedName() + "_" + meta ;

	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0; i < 6; ++i)
		{
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		if(entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entityLiving;

			if(player.getCooldownTracker().hasCooldown(this))
				return false;

			if(!RpgInventory.playerClass.contains(BmaItems.mageClass))
				return false;
			
			if(AddonBma.spells.hasSpokenSpell(player, AddonBma.spells.Expelliarmus)){
				if(!player.worldObj.isRemote)
					player.worldObj.spawnEntityInWorld(new EntityExpelliarmus(player.worldObj, player));
				player.worldObj.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.NEUTRAL, 0.2f, -10f, false);
				player.getCooldownTracker().setCooldown(this, 20);
			}

			else if(AddonBma.spells.hasSpokenSpell(player, AddonBma.spells.Augolustra)){
				if(!player.worldObj.isRemote)
					player.worldObj.spawnEntityInWorld(new EntityAugolustra(player.worldObj, player));
				player.worldObj.playSound(player.posX, player.posY, player.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 0.5f, -5f, false);
				player.getCooldownTracker().setCooldown(this, 15);
			}
			
			else if(AddonBma.spells.hasSpokenSpell(player, AddonBma.spells.Episkey)){
				if(!WandInfo.isLoyalWand(player, stack) && player.worldObj.rand.nextInt(5)==0){
					player.worldObj.playSound(player.posX, player.posY, player.posZ, SoundEvents.field_190021_aL, SoundCategory.NEUTRAL, 0.5f, 0.5f, false);
					return false;
				}
				player.heal(WandInfo.isLoyalWand(player, stack) ? 4 : 1);
				player.worldObj.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 0.5f, 5f, false);

				player.getCooldownTracker().setCooldown(this, 60);

				if(player.worldObj.isRemote){
					spawnParticle(5, EnumParticleTypes.HEART, player);
				}
				return true;
			}
		}

		return super.onEntitySwing(entityLiving, stack);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		if(player.getCooldownTracker().hasCooldown(this))
			return false;
		if(!RpgInventory.playerClass.contains(BmaItems.mageClass))
			return false;
		
		if(entity instanceof EntityLivingBase){
			EntityLivingBase elb = (EntityLivingBase)entity;
			World world = elb.worldObj;

			if(AddonBma.spells.hasSpokenSpell(player, AddonBma.spells.Episkey)){
				if(!WandInfo.isLoyalWand(player, stack) && world.rand.nextInt(5)==0){
					world.playSound(player.posX, player.posY, player.posZ, SoundEvents.field_190021_aL, SoundCategory.NEUTRAL, 0.5f, 0.5f, false);
					return false;
				}
				elb.heal(WandInfo.isLoyalWand(player, stack) ? 10 : 3);
				world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 0.5f, 5f, false);

				player.getCooldownTracker().setCooldown(this, 60);

				if(world.isRemote){
					spawnParticle(5, EnumParticleTypes.HEART, elb);
				}
				return true;
			}
		}
		return true;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		if(!RpgInventory.playerClass.contains(BmaItems.mageClass))
			return EnumActionResult.FAIL;
		
		if(AddonBma.spells.hasSpokenSpell(player, AddonBma.spells.AesConverto)){

			Block block = world.getBlockState(pos).getBlock();
			boolean loyal = WandInfo.isLoyalWand(player, stack);
			int rand = world.rand.nextInt(3);

			if(!loyal && rand == 0){
				if(block.equals(Blocks.COAL_ORE) || 
						block.equals(Blocks.GOLD_ORE) || 
						block.equals(Blocks.IRON_ORE) ||
						block.equals(Blocks.DIAMOND_ORE)){
					world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
				}else
					return EnumActionResult.FAIL;
			}
			else{
				if(block.equals(Blocks.COAL_ORE))
					world.setBlockState(pos, Blocks.COAL_BLOCK.getDefaultState(), 2);
				else if(block.equals(Blocks.GOLD_ORE))
					world.setBlockState(pos, Blocks.GOLD_BLOCK.getDefaultState(), 2);
				else if(block.equals(Blocks.IRON_ORE))
					world.setBlockState(pos, Blocks.IRON_BLOCK.getDefaultState(), 2);
				else if(block.equals(Blocks.DIAMOND_ORE))
					world.setBlockState(pos, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
				else{
					return EnumActionResult.FAIL;
				}
				player.worldObj.playSound(player.posX, player.posY, player.posZ, SoundEvents.field_190021_aL, SoundCategory.NEUTRAL, 0.8f, -5f, false);

			}

			if(world.isRemote){
				Random random = world.rand;
				double d0 = 0.0625D;

				for (int i = 0; i < 20; ++i)
				{
					double d1 = (double)((float)pos.getX() - 0.5f + random.nextFloat()*2);
					double d2 = (double)((float)pos.getY() + 1f + random.nextFloat());
					double d3 = (double)((float)pos.getZ() - 0.5f + random.nextFloat()*2);

					world.spawnParticle(EnumParticleTypes.SPELL, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
				}
			}
		}
		return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

		if(stack != null)
			if(stack.getTagCompound() != null){
				if(stack.getTagCompound().hasKey("wood") && !tooltip.contains("wood:"))
					tooltip.add(stack.getTagCompound().getString("wood"));
				if(stack.getTagCompound().hasKey("core") && !tooltip.contains("core:"))
					tooltip.add(stack.getTagCompound().getString("core"));
			}
	}

	private void spawnParticle(int number, EnumParticleTypes particle, EntityLivingBase elb){

		if(elb.worldObj.isRemote){
			Random random = elb.worldObj.rand;
			double d0 = 0.0625D;

			for (int i = 0; i < number; ++i)
			{
				double x = (double)((float)elb.posX + random.nextFloat()*2);
				double y = (double)((float)elb.posY + 1f + random.nextFloat());
				double z = (double)((float)elb.posZ + random.nextFloat()*2);

				elb.worldObj.spawnParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
}