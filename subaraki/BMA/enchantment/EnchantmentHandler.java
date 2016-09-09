package subaraki.BMA.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EnchantmentHandler {

	public static final EnchantmentWand wand_enchantment = new EnchantmentWand();
	public static final EnumEnchantmentType WAND = (EnumHelper.addEnchantmentType("WAND"));
	
	public EnchantmentHandler() {
		addEnchantings();
	}
	
	public static void addEnchantings(){
		GameRegistry.register(wand_enchantment);
	}
}
