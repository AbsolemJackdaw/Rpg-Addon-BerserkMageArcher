package subaraki.BMA.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import subaraki.BMA.item.armor.ItemArcherArmor;
import subaraki.BMA.item.armor.ItemBerserkerArmor;
import subaraki.BMA.item.armor.ItemMageArmor;
import subaraki.BMA.item.armor.shields.ItemCustomShield;
import subaraki.BMA.item.weapons.ItemHammer;
import subaraki.BMA.item.weapons.ItemWand;
import subaraki.BMA.mod.AddonBma;

public class BmaItems {

	public static ItemBerserkerArmor 
	berserker_head, berserker_chest, berserker_legs, berserker_feet;

	public static ItemMageArmor 
	mage_head, mage_chest, mage_legs, mage_feet;

	public static ItemArcherArmor 
	archer_head, archer_chest, archer_legs, archer_feet;
	
	public static ItemHammer hammer;

	public static ItemWand wand;
	public static Item wand_stick;

	public static ItemCraftLeather craftLeather;

	public static ItemCustomShield shield_berserker;

	public static final ArmorMaterial ARMOR_BERSERKER = EnumHelper.addArmorMaterial("berserker", AddonBma.MODID+":berserker", 40, new int[] { 2, 4, 3, 2 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ToolMaterial BERSERKER = EnumHelper.addToolMaterial("rage_hammer", 0, 100, 5, 8, 0);

	public static final ArmorMaterial ARMOR_MAGE = EnumHelper.addArmorMaterial("mage", AddonBma.MODID+":mage", 40, new int[] { 2, 3, 2, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);
	public static final ArmorMaterial ARMOR_ARCHER = EnumHelper.addArmorMaterial("archer", AddonBma.MODID+":archer", 40, new int[] { 3, 2, 2, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

	public static final String berserkerClass = "Berserker";
	public static final String mageClass = "Mage";
	public static final String archerClass = "Archer";

	public static final CreativeTabs tab = new CreativeTabs("BmaTab") {
		@Override
		public Item getTabIconItem() {
			return berserker_head;
		}
	};

	public static void loadItems(){

		berserker_head = (ItemBerserkerArmor) new ItemBerserkerArmor(ARMOR_BERSERKER, EntityEquipmentSlot.HEAD).setCreativeTab(tab);
		berserker_chest = (ItemBerserkerArmor)new ItemBerserkerArmor(ARMOR_BERSERKER, EntityEquipmentSlot.CHEST).setCreativeTab(tab);
		berserker_legs = (ItemBerserkerArmor) new ItemBerserkerArmor(ARMOR_BERSERKER, EntityEquipmentSlot.LEGS).setCreativeTab(tab);
		berserker_feet = (ItemBerserkerArmor) new ItemBerserkerArmor(ARMOR_BERSERKER, EntityEquipmentSlot.FEET).setCreativeTab(tab);

		mage_head = (ItemMageArmor) new ItemMageArmor(ARMOR_MAGE, EntityEquipmentSlot.HEAD).setCreativeTab(tab);
		mage_chest = (ItemMageArmor)new ItemMageArmor(ARMOR_MAGE, EntityEquipmentSlot.CHEST).setCreativeTab(tab);
		mage_legs = (ItemMageArmor) new ItemMageArmor(ARMOR_MAGE, EntityEquipmentSlot.LEGS).setCreativeTab(tab);
		mage_feet = (ItemMageArmor) new ItemMageArmor(ARMOR_MAGE, EntityEquipmentSlot.FEET).setCreativeTab(tab);

		archer_head = (ItemArcherArmor) new ItemArcherArmor(ARMOR_MAGE, EntityEquipmentSlot.HEAD).setCreativeTab(tab);
		archer_chest = (ItemArcherArmor)new ItemArcherArmor(ARMOR_MAGE, EntityEquipmentSlot.CHEST).setCreativeTab(tab);
		archer_legs = (ItemArcherArmor) new ItemArcherArmor(ARMOR_MAGE, EntityEquipmentSlot.LEGS).setCreativeTab(tab);
		archer_feet = (ItemArcherArmor) new ItemArcherArmor(ARMOR_MAGE, EntityEquipmentSlot.FEET).setCreativeTab(tab);

		
		craftLeather = (ItemCraftLeather) new ItemCraftLeather().setCreativeTab(tab);

		hammer = (ItemHammer) new ItemHammer(BERSERKER).setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+":"+"hammer").setRegistryName("hammer");

		shield_berserker = (ItemCustomShield) new ItemCustomShield().setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+":"+"berserker_shield").setRegistryName("berserker_shield");

		wand = (ItemWand) new ItemWand().setCreativeTab(tab);

		wand_stick = new Item(){
			@Override
			public int getItemEnchantability() {
				return 1;
			}
			public boolean isItemTool(ItemStack stack) {return stack.stackSize == 1;}

		}.setMaxDamage(1).setMaxStackSize(1).setRegistryName("wand_stick").setUnlocalizedName(AddonBma.MODID+":"+"wand_stick").setCreativeTab(tab);
	}

	public static void register(){
		GameRegistry.register(berserker_head);
		GameRegistry.register(berserker_chest);
		GameRegistry.register(berserker_legs);
		GameRegistry.register(berserker_feet);

		GameRegistry.register(mage_head);
		GameRegistry.register(mage_chest);
		GameRegistry.register(mage_legs);
		GameRegistry.register(mage_feet);

		GameRegistry.register(archer_head);
		GameRegistry.register(archer_chest);
		GameRegistry.register(archer_legs);
		GameRegistry.register(archer_feet);
		
		GameRegistry.register(hammer);
		GameRegistry.register(wand);
		GameRegistry.register(wand_stick);

		GameRegistry.register(shield_berserker);

		GameRegistry.register(craftLeather);

	}

	public static void registerRenderers(){
		registerRender(berserker_head, berserker_head.getModeltextureLocation());
		registerRender(berserker_chest, berserker_chest.getModeltextureLocation());
		registerRender(berserker_legs, berserker_legs.getModeltextureLocation());
		registerRender(berserker_feet, berserker_feet.getModeltextureLocation());

		registerRender(mage_head, mage_head.getModeltextureLocation());
		registerRender(mage_chest, mage_chest.getModeltextureLocation());
		registerRender(mage_legs, mage_legs.getModeltextureLocation());
		registerRender(mage_feet, mage_feet.getModeltextureLocation());

		registerRender(archer_head, archer_head.getModeltextureLocation());
		registerRender(archer_chest, archer_chest.getModeltextureLocation());
		registerRender(archer_legs, archer_legs.getModeltextureLocation());
		registerRender(archer_feet, archer_feet.getModeltextureLocation());

		
		registerRender(shield_berserker, "shield_berserker");
		registerRender(hammer, "hammer");

		registerVanillaRender(wand_stick, "stick", 0);

		for(int i = 0; i < 3; i++)
			registerVanillaRender(craftLeather, "leather", i);

		for(int i = 0; i < 6; i++)
			registerRender(wand, "wand_"+i, i);
	}

	/**basic registering for model*/
	private static void registerVanillaRender(Item item, String location, int metadata){
		ModelLoader.setCustomModelResourceLocation(
				item,
				metadata,
				new ModelResourceLocation(location));
	}

	/**basic registering for model*/
	private static void registerRender(Item item, String location){
		registerRender(item, location, 0);
	}

	/**with meta*/
	private static void registerRender(Item item, String location, int metadata){
		ModelLoader.setCustomModelResourceLocation(
				item,
				metadata,
				new ModelResourceLocation(AddonBma.MODID+":"+location));
	}

	public static void loadRecipes(){
		GameRegistry.addShapelessRecipe(new ItemStack(craftLeather, 0),
				new Object[] { Items.LEATHER, Items.IRON_INGOT, new ItemStack(Blocks.WOOL, 1, 0) });

		GameRegistry.addRecipe(new ItemStack(craftLeather, 1, 1), new Object[] {
				"WWW", "WLW", "WWW", 'W', new ItemStack(Items.DYE, 1, 4), 'L',
				Items.LEATHER });

		GameRegistry.addRecipe(new ItemStack(craftLeather, 1, 2), new Object[] {
				"WWW", "WLW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 12),
				'L', Items.LEATHER });

		GameRegistry.addRecipe(new ItemStack(hammer, 1), new Object[] { "SWS",
				" I ", " I ", 'I', Items.IRON_INGOT, 'S', Blocks.IRON_BLOCK,
				'W', new ItemStack(Blocks.WOOL, 1, 1) });

		// ARMOR
		String [][] recipePatterns = new String[][] { { "XXX", "X X" },
			{ "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" },
			{ "X X", "X X" } };

			Object[][] recipeItems = new Object[][] { { new ItemStack(craftLeather,1,0), new ItemStack(craftLeather,1,1), new ItemStack(craftLeather,1,2) },
				{ archer_head, mage_head, berserker_head },
				{ archer_chest, mage_chest, berserker_chest },
				{ archer_legs, mage_legs, berserker_legs },
				{ archer_feet, mage_feet, berserker_feet } };

				for (int var2 = 0; var2 < recipeItems[0].length; ++var2) {
					Object var3 = recipeItems[0][var2];

					for (int var4 = 0; var4 < (recipeItems.length - 1); ++var4) {
						Item var5 = (Item) recipeItems[var4 + 1][var2];
						GameRegistry.addRecipe(new ItemStack(var5), new Object[] {
								recipePatterns[var4], 'X', var3 });
					}
				}
	}

}
