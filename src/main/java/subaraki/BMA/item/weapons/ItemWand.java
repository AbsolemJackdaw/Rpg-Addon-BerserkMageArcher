package subaraki.BMA.item.weapons;

import java.util.List;
import java.util.Random;

import lib.playerclass.capability.PlayerClass;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import subaraki.BMA.capability.FreezeData;
import subaraki.BMA.capability.MageIndexData;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityFlyingCarpet;
import subaraki.BMA.handler.network.CSyncShieldPacket;
import subaraki.BMA.handler.network.PacketHandler;
import subaraki.BMA.handler.spells.SpellHandler;
import subaraki.BMA.handler.spells.SpellHandler.EnumSpell;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.mod.AddonBma;

public class ItemWand extends Item {

	public ItemWand() {

		this.setHasSubtypes(true);
		setUnlocalizedName(AddonBma.MODID+"."+"wand");
		setRegistryName("wand");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getMetadata();
		return super.getUnlocalizedName();
	}


	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {

		if(!this.isInCreativeTab(tab))
			return;

		for (int i = 0; i < 16; ++i)
		{
			subItems.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		return super.onItemRightClick(worldIn, playerIn, hand);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		if(entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entityLiving;

			if(player.getCooldownTracker().hasCooldown(this))
				return false;

			if(!PlayerClass.get(player).isPlayerClass(BmaItems.mageClass))
				return false;

			if(AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.EXPELLIARMUS))
				AddonBma.spell.execute(EnumSpell.EXPELLIARMUS, player, this);

			else if (AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.CONTEGO))
				AddonBma.spell.execute(EnumSpell.CONTEGO, player);

			else if(AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.AUGOLUSTRA))
				AddonBma.spell.execute(EnumSpell.AUGOLUSTRA, player, this);

			else if(AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.FREEZE))
				AddonBma.spell.execute(EnumSpell.FREEZE, player, this);
			
			else if(AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.BOMBARDA))
				AddonBma.spell.execute(EnumSpell.BOMBARDA, player, this);
			
			else if(AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.EPISKEY)){
				
				//trigger the spell cycling
				AddonBma.spell.execute(EnumSpell.EPISKEY, player);
					
				if(!WandInfo.isLoyalWand(player, stack) && player.world.rand.nextInt(5)==0){
					player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 0.5f, 0.5f, false);
					return false;
				}
				player.heal(WandInfo.isLoyalWand(player, stack) ? 5 : 1);
				int hunger = player.getFoodStats().getFoodLevel();
				player.getFoodStats().setFoodLevel(hunger > 1 ? hunger-1 : 0);
				player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 0.5f, 5f, false);

				player.getCooldownTracker().setCooldown(this, 60);

				if(player.world.isRemote){
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
		if(!PlayerClass.get(player).isPlayerClass(BmaItems.mageClass))
			return false;

		if(entity instanceof EntityLivingBase){
			EntityLivingBase elb = (EntityLivingBase)entity;
			World world = elb.world;

			if(AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.EPISKEY)){
				//trigger the spell cycling
				AddonBma.spell.execute(EnumSpell.EPISKEY, player);
				
				if(!WandInfo.isLoyalWand(player, stack) && world.rand.nextInt(5)==0){
					world.playSound(player.posX, player.posY, player.posZ, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 0.5f, 0.5f, false);
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
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);

		if(!PlayerClass.get(player).isPlayerClass(BmaItems.mageClass))
			return EnumActionResult.FAIL;

		if(AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.AESCONVERTO))
			AddonBma.spell.execute(EnumSpell.AESCONVERTO, player, pos, stack);

		else if (AddonBma.spellHandler.hasSpokenSpell(player, EnumSpell.PERMOVEO))
			AddonBma.spell.execute(EnumSpell.PERMOVEO, player, pos);

		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}


	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack != ItemStack.EMPTY)
			if(stack.getTagCompound() != null){
				if(stack.getTagCompound().hasKey("wood") && !tooltip.contains("wood:"))
					tooltip.add(stack.getTagCompound().getString("wood"));
				if(stack.getTagCompound().hasKey("core") && !tooltip.contains("core:"))
					tooltip.add(stack.getTagCompound().getString("core"));
			}
	}

	private void spawnParticle(int number, EnumParticleTypes particle, EntityLivingBase elb){

		if(elb.world.isRemote){
			Random random = elb.world.rand;

			for (int i = 0; i < number; ++i)
			{
				double x = (double)((float)elb.posX + random.nextFloat()*2);
				double y = (double)((float)elb.posY + 1f + random.nextFloat());
				double z = (double)((float)elb.posZ + random.nextFloat()*2);

				elb.world.spawnParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
}
