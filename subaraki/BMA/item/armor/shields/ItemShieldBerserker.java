package subaraki.BMA.item.armor.shields;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemShieldBerserker extends ItemCustomShield {

	public ItemShieldBerserker() {
		super();
		this.setMaxDamage(100);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem().equals(Items.IRON_INGOT) ? true : super.getIsRepairable(toRepair, repair);
	}
}
