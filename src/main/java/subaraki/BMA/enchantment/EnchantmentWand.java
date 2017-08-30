package subaraki.BMA.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import subaraki.BMA.item.BmaItems;

public class EnchantmentWand extends Enchantment{

	public EnchantmentWand() {
		super(Rarity.COMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
		setName("wand_enchantment");
		setRegistryName("wand_enchantment");
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 5; //minimum of 5 levels in the enchantment table
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public int getMinLevel() {
		return 1;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() == BmaItems.wand_stick ? true : false;
	}
}
