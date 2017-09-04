package subaraki.BMA.handler.spells;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import subaraki.BMA.capability.MageIndexData;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityBombarda;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityFlyingCarpet;
import subaraki.BMA.entity.EntityFreezeSpell;
import subaraki.BMA.handler.network.CSyncShieldPacket;
import subaraki.BMA.handler.network.CSyncSpellListPacket;
import subaraki.BMA.handler.network.PacketHandler;
import subaraki.BMA.handler.spells.SpellHandler.EnumSpell;
import subaraki.BMA.item.weapons.ItemWand;
import subaraki.BMA.item.weapons.WandInfo;
import subaraki.BMA.mod.AddonBma;

public class Spell {

	public Object execute(EnumSpell spell, EntityPlayer player, Object ...o)
	{
//		if(!spell.canCycle)
//		{
//			if(!player.world.isRemote)
//			{
//				AddonBma.spellHandler.addSpokenSpell(player.getGameProfile().getName(), EnumSpell.NONE);
//				PacketHandler.NETWORK.sendToAll(new CSyncSpellListPacket(player.getGameProfile().getName(), EnumSpell.NONE.getLowerName()));//send message to add spell to list client side
//			}
//		}
		
		switch (spell) {
		case AESCONVERTO:
			return Aes(player,(BlockPos)o[0],(ItemStack)o[1]);

		case EXPELLIARMUS:
			Expelliarmus(player, (ItemWand)o[0]);
			return null;
			
		case CONTEGO:
			Aspida(player);
			return null;
			
		case AUGOLUSTRA:
			Augolustra(player,(ItemWand)o[0]);
			return null;	
			
		case EPISKEY:
			//hardcoded into wand
			return null;
			
		case FREEZE:
			Freeze(player, (ItemWand)o[0]);
			return null;
			
		case PERMOVEO:
			Permoveo(player,(BlockPos)o[0]);
			return null;
			
		case BOMBARDA:
			Bombarda(player, (ItemWand)o[0]);
			return null;
			
		default:
			return null;
		}
	}

	private EnumActionResult Aes(EntityPlayer player, BlockPos pos, ItemStack stack){
		World world = player.world;
		
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
			player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 0.8f, -5f, false);

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
		return EnumActionResult.SUCCESS;
	}

	private void Expelliarmus(EntityPlayer player, ItemWand wand){
		if(!player.world.isRemote)
		{
			player.world.spawnEntity(new EntityExpelliarmus(player.world, player));
			player.getCooldownTracker().setCooldown(wand, 20);
		}
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.NEUTRAL, 0.2f, -10f, false);
	}
	
	private void Aspida(EntityPlayer player){
		MageIndexData data = MageIndexData.get(player);
		if(!player.world.isRemote)
		{
			int cap = 20+player.world.rand.nextInt(21);

			if(!data.isProtectedByMagic() || cap > data.getShieldCapacity()) //allow for a refresh without the shield having to be worn out
			{

				data.setShieldCapacity(cap);
				data.setProtectedByMagic(true);

				if(player instanceof EntityPlayerMP)
					PacketHandler.NETWORK.sendTo(new CSyncShieldPacket(true, cap), (EntityPlayerMP)player);
			}
		}
	}
	
	private void Augolustra(EntityPlayer player, ItemWand wand){
		if(!player.world.isRemote)
		{
			player.world.spawnEntity(new EntityAugolustra(player.world, player));
			player.getCooldownTracker().setCooldown(wand, 15);
		}
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 0.5f, -5f, false);

	}
	
	private void Bombarda(EntityPlayer player, ItemWand wand){
		if(!player.world.isRemote)
		{
			player.world.spawnEntity(new EntityBombarda(player.world, player));
			player.getCooldownTracker().setCooldown(wand, 50);
		}
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 0.5f, -5f, false);
	}
	
	private void Permoveo(EntityPlayer player, BlockPos pos){
		World world = player.world;
		
		if(world.getBlockState(pos).getBlock() == Blocks.CARPET)
		{
			EnumFacing carpetSide = null;
			for(EnumFacing face : EnumFacing.VALUES)
			{
				if(face == EnumFacing.UP || face == EnumFacing.DOWN)
					continue;
				if(world.getBlockState(pos.offset(face,1)).getBlock() == Blocks.CARPET)
				{
					carpetSide = face;
					break;
				}
			}

			if(carpetSide != null)
			{
				int[] meta = new int[]{world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)),
						world.getBlockState(pos.offset(carpetSide,1)).getBlock().getMetaFromState(world.getBlockState(pos.offset(carpetSide,1)))};

				world.setBlockToAir(pos);
				world.setBlockToAir(pos.offset(carpetSide,1));

				Vec3i vec = carpetSide.getDirectionVec();
				System.out.println(vec);
				
				float f = 0.5f;
				if(vec.getX() > 0 ||vec.getX() < 0)
					f = -0.5f;
				
				EntityFlyingCarpet carpet = new EntityFlyingCarpet(world, pos.getX()-((float)vec.getX()/2f)+f, pos.getY(), pos.getZ()+((float)vec.getZ()/2f)+0.5f);
				carpet.setMeta(meta[0], meta[1]);
				carpet.rotationYaw = vec.getZ() > 0 ? 0 : vec.getZ() < 0 ? 180 : vec.getX() > 0 ? 270 : 90;
				
				if(!world.isRemote)
					world.spawnEntity(carpet);
			}
		}
	}
	
	private void Freeze(EntityPlayer player, ItemWand wand){
		if(!player.world.isRemote)
		{
			player.world.spawnEntity(new EntityFreezeSpell(player.world, player));
			player.getCooldownTracker().setCooldown(wand, 30);
		}
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 0.05f, -10f, false);
		player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.NEUTRAL, 0.2f, -10f, false);
	}
}
