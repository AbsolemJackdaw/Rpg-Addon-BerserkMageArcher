package subaraki.BMA.item.armor;

import lib.item.armor.ModeledArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import subaraki.BMA.handler.proxy.ClientProxy;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.mod.AddonBma;

public class ItemMageArmor extends ModeledArmor {

	public ItemMageArmor(ArmorMaterial enumArmorMaterial, EntityEquipmentSlot slot) {
		super(slot, enumArmorMaterial, "mage_"+slot.getName());
	}

	@Override
	public String armorClassName() {
		return BmaItems.mageClass;
	}

	@Override
	protected void get3DArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot armorSlot){
		if (stack != ItemStack.EMPTY) {
			if (stack.getItem() instanceof ItemArmor) {

				EntityEquipmentSlot type = ((ItemArmor) stack.getItem()).armorType;

				switch (type) {
				case HEAD:
				case LEGS:
					setArmorModel(AddonBma.proxy.getArmorModel(ClientProxy.mage_rest));
					break;
				case FEET:
				case CHEST:
					setArmorModel(AddonBma.proxy.getArmorModel(ClientProxy.mage_chest));
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public Item getLinkedShieldItem() {
		return Items.ACACIA_BOAT;
	}
}