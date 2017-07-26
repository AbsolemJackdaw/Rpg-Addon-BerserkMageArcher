package subaraki.BMA.handler.event;

import java.util.Map;

import lib.playerclass.capability.PlayerClass;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import subaraki.BMA.capability.CapabilityMageProvider;
import subaraki.BMA.capability.MageDataCapability;
import subaraki.BMA.capability.MageIndexData;
import subaraki.BMA.enchantment.EnchantmentHandler;
import subaraki.BMA.handler.network.CSyncMageIndexPacket;
import subaraki.BMA.handler.network.CSyncShieldPacket;
import subaraki.BMA.handler.network.PacketHandler;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.item.weapons.WandInfo;
import subaraki.BMA.mod.AddonBma;

public class BmaEventHandler {

	public BmaEventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void playerLogin(PlayerLoggedInEvent event){
		//set mage index always, no matter what, so if the player changes name, his wand will change
		//and this way the data in the player save file is 'unalterable'
		setMageIndex(event.player);
	}

	@SubscribeEvent
	public void heldItemTick(PlayerTickEvent event){
		if(event.side == Side.SERVER)
		{
			EntityPlayer player = event.player;
			transformToWand(player);
		}
	}

	@SubscribeEvent
	public void onPlayerUpdateTick(LivingUpdateEvent event){
		if(!event.getEntityLiving().world.isRemote)
		{
			calculateBerserkerBonus(event);
		}
	}

	@SubscribeEvent
	public void onDamage(LivingHurtEvent event){
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();

			MageIndexData data = MageIndexData.get(player);

			if(data.isProtectedByMagic())
			{
				event.setAmount(event.getAmount()/4f); //decreased by 75%
				data.decreaseShieldCapacity();

				if(data.getShieldCapacity() <= 0)
					data.setProtectedByMagic(false);

				if(player instanceof EntityPlayerMP)
					PacketHandler.NETWORK.sendTo(new CSyncShieldPacket(data.getShieldCapacity() > 0, data.getShieldCapacity()), (EntityPlayerMP)player);
			}
		}
	}

	@SubscribeEvent
	public void onCapabilityAttach(AttachCapabilitiesEvent.Entity event){
		final Entity entity = event.getEntity();

		if (entity instanceof EntityPlayer)
			event.addCapability(CapabilityMageProvider.KEY, new CapabilityMageProvider((EntityPlayer)entity)); 
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void transformToWand(EntityPlayer player){
		if(player == null)
			return;

		ItemStack heldStackMain = player.getHeldItem(EnumHand.MAIN_HAND);

		if(heldStackMain.isEmpty())
			return;

		if(heldStackMain.getItem() != BmaItems.wand_stick)
			return;

		if(heldStackMain.isItemEnchanted()){
			for(Enchantment enchantment : EnchantmentHelper.getEnchantments(heldStackMain).keySet()){
				if(enchantment.getRegistryName().equals(EnchantmentHandler.wand_enchantment.getRegistryName())){
					int metadata = player.getCapability(MageDataCapability.CAPABILITY, null).getMageIndex();
					int core = player.getCapability(MageDataCapability.CAPABILITY, null).getCoreIndex();

					if(!heldStackMain.getTagCompound().hasKey("core")){
						player.world.playSound(player, new BlockPos(player), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0f, 1f);
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STICK,1));
						return;
					}

					String extraCore = heldStackMain.getTagCompound().getString("core");

					ItemStack stack = new ItemStack(BmaItems.wand, 1, metadata);
					stack.setStackDisplayName(player.getName() + "'s Wand");

					stack.getTagCompound().setString("core", "Core: " + extraCore);
					stack.getTagCompound().setString("wood", "Wood: " + WandInfo.wood[metadata]);
					stack.getTagCompound().setInteger("core_index",core);

					player.setHeldItem(EnumHand.MAIN_HAND, stack);
					break;
				}
			}
		}
	}

	/**sets the index for the player that determines what kind of mage wand he has*/
	private void setMageIndex(EntityPlayer player){

		MageIndexData data = MageIndexData.get(player);
		
		String playerName = player.getDisplayNameString();
		String name = playerName.substring(0, 4); //only get the four first letters
		//a reference alphabet for index retrieving
		final String alphabet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
		//make an itterable array out of the 4 letter name
		char[] nameArray = name.toCharArray();

		String pre_mageIndex = "";

		for(int i = 0; i < name.length(); i++){
			if(alphabet.indexOf(nameArray[i]) > -1) //-1 means non-indexed
				pre_mageIndex += alphabet.indexOf(nameArray[i]);
			else 
				pre_mageIndex+=1; //add 1 for special/any other characters
		}
		//convert string to long (int could be/has been to short)
		long mageIdentifier = Long.parseLong(pre_mageIndex);
		//convert that long into hexadecimal
		String mid_mageIndex = Long.toHexString(mageIdentifier);
		//get the last index of the hexadecimal string
		String post_mageIndex = mid_mageIndex.substring(mid_mageIndex.length()-1, mid_mageIndex.length());
		//parse it back into an int to get an index that can be used as metadata
		int mageIndex = Integer.parseInt(post_mageIndex, 16); //16 stands for hex

		String core_id = mid_mageIndex.substring(0, 1);
		int coreIndex = Integer.parseInt(core_id, 16);

		AddonBma.log.info("Mage Index Calculated for " + name + ". Values are :");
		AddonBma.log.info(mageIdentifier + " " + mid_mageIndex + " " + mageIndex + " " + coreIndex);

		data.setMageIndex(mageIndex);
		data.setCoreIndex(coreIndex);

		if (!player.world.isRemote)
		{
			if(player instanceof EntityPlayerMP)
			{
				PacketHandler.NETWORK.sendTo(new CSyncShieldPacket(data.getShieldCapacity() > 0, data.getShieldCapacity()), (EntityPlayerMP)player);
				PacketHandler.NETWORK.sendTo(new CSyncMageIndexPacket(coreIndex,mageIndex), (EntityPlayerMP) player);
			}
		}
	}

	private void calculateBerserkerBonus(LivingUpdateEvent event){
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		if(player == null)
			return;

		ItemStack heldItem = player.getHeldItemMainhand();

		if(heldItem == ItemStack.EMPTY)
			return;
		if(heldItem.getItem().equals(BmaItems.hammer))

			if(PlayerClass.get(player).isPlayerClass(BmaItems.berserkerClass)){

				if(PlayerClass.get(player).isShielded()){
					if (((player.getFoodStats().getFoodLevel() < 5) || (player.getHealth() < 5)))
						addEnchantment(Enchantments.KNOCKBACK, 3, heldItem);
					else
						addEnchantment(Enchantments.KNOCKBACK, 2, heldItem);
				}else{
					addEnchantment(Enchantments.KNOCKBACK, 1, heldItem);
				}
			}else{
				removeEnchantment(Enchantments.KNOCKBACK, heldItem);
			}
	}

	private void addEnchantment(Enchantment ench, int level, ItemStack stack){
		Map<Enchantment, Integer> tmp = EnchantmentHelper.getEnchantments(stack);

		if(!tmp.containsKey(ench))
			tmp.put(ench, level);
		else if(tmp.get(ench) != level)
			tmp.put(ench, level);

		EnchantmentHelper.setEnchantments(tmp,stack);
	}

	private void removeEnchantment(Enchantment ench, ItemStack stack){
		Map<Enchantment, Integer> tmp = EnchantmentHelper.getEnchantments(stack);
		if(!tmp.containsKey(ench))
			return;
		tmp.remove(ench);
		EnchantmentHelper.setEnchantments(tmp, stack);
	}
}
