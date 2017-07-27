package subaraki.BMA.item;

import static lib.item.ItemRegistry.registerRender;
import static lib.item.ItemRegistry.registerVanillaRender;

import lib.item.ItemRegistry;
import lib.item.shield.ItemCustomShield;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import subaraki.BMA.item.armor.ItemArcherArmor;
import subaraki.BMA.item.armor.ItemBerserkerArmor;
import subaraki.BMA.item.armor.ItemMageArmor;
import subaraki.BMA.item.weapons.ItemBowArcher;
import subaraki.BMA.item.weapons.ItemCrossBow;
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

	public static ItemBowArcher bow;
	public static ItemCrossBow crossbow;

	public static ItemWand wand;
	public static Item wand_stick, archer_plate, berserker_plate, dart, headless_arrow;

	public static ItemCraftLeather craftLeather;

	public static ItemCustomShield shield_berserker, shield_archer;

	public static final ToolMaterial BERSERKER = EnumHelper.addToolMaterial("rage_hammer", 0, 100, 5, 8, 0);

	public static final ArmorMaterial ARMOR_BERSERKER = EnumHelper.addArmorMaterial("berserker", AddonBma.MODID+":berserker", 250, new int[] { 2, 4, 3, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
	public static final ArmorMaterial ARMOR_MAGE = EnumHelper.addArmorMaterial("mage", AddonBma.MODID+":mage", 250, new int[] { 2, 3, 2, 1 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);
	public static final ArmorMaterial ARMOR_ARCHER = EnumHelper.addArmorMaterial("archer", AddonBma.MODID+":archer", 250, new int[] { 3, 2, 2, 1 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

	public static final String berserkerClass = "Berserker";
	public static final String mageClass = "Mage";
	public static final String archerClass = "Archer";

	public static final CreativeTabs tab = new CreativeTabs("BmaTab") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(berserker_head);
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

		archer_head = (ItemArcherArmor) new ItemArcherArmor(ARMOR_ARCHER, EntityEquipmentSlot.HEAD).setCreativeTab(tab);
		archer_chest = (ItemArcherArmor)new ItemArcherArmor(ARMOR_ARCHER, EntityEquipmentSlot.CHEST).setCreativeTab(tab);
		archer_legs = (ItemArcherArmor) new ItemArcherArmor(ARMOR_ARCHER, EntityEquipmentSlot.LEGS).setCreativeTab(tab);
		archer_feet = (ItemArcherArmor) new ItemArcherArmor(ARMOR_ARCHER, EntityEquipmentSlot.FEET).setCreativeTab(tab);

		craftLeather = (ItemCraftLeather) new ItemCraftLeather().setCreativeTab(tab);

		hammer = (ItemHammer) new ItemHammer(BERSERKER).setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".hammer").setRegistryName("hammer");
		bow = (ItemBowArcher) new ItemBowArcher().setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".archer_bow").setRegistryName("archer_bow");
		crossbow = (ItemCrossBow) new ItemCrossBow().setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".crossbow").setRegistryName("crossbow");
		wand = (ItemWand) new ItemWand().setCreativeTab(tab);

		dart = new Item().setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".dart").setRegistryName("dart");
		headless_arrow = new Item().setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".headless_arrow").setRegistryName("headless_arrow");
		archer_plate = new Item().setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".archer_plate").setRegistryName("archer_plate");
		berserker_plate = new Item().setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".berserker_plate").setRegistryName("berserker_plate");

		shield_berserker = (ItemCustomShield) new ItemCustomShield(){
			@Override
			public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
				return repair.getItem().equals(Items.IRON_INGOT) ? true : super.getIsRepairable(toRepair, repair);
			}	
		}.setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".berserker_shield").setRegistryName("berserker_shield").setMaxDamage(100);

		shield_archer = (ItemCustomShield) new ItemCustomShield(){
			@Override
			public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
				return repair.getItem().equals(Blocks.LOG) ? true : super.getIsRepairable(toRepair, repair);
			}
		}.setCreativeTab(tab).setUnlocalizedName(AddonBma.MODID+".archer_shield").setRegistryName("archer_shield").setMaxDamage(100);

		wand_stick = new Item(){
			@Override
			public int getItemEnchantability() {
				return 1;
			}
			public boolean isItemTool(ItemStack stack) {return stack.getCount() == 1;}

		}.setMaxDamage(1).setMaxStackSize(1).setRegistryName("wand_stick").setUnlocalizedName(AddonBma.MODID+".wand_stick").setCreativeTab(tab);

		register();

		addToOreDict();
	}

	public static void register(){
		ItemRegistry.registerItem(berserker_head);
		ItemRegistry.registerItem(berserker_chest);
		ItemRegistry.registerItem(berserker_legs);
		ItemRegistry.registerItem(berserker_feet);

		ItemRegistry.registerItem(mage_head);
		ItemRegistry.registerItem(mage_chest);
		ItemRegistry.registerItem(mage_legs);
		ItemRegistry.registerItem(mage_feet);

		ItemRegistry.registerItem(archer_head);
		ItemRegistry.registerItem(archer_chest);
		ItemRegistry.registerItem(archer_legs);
		ItemRegistry.registerItem(archer_feet);

		ItemRegistry.registerItem(hammer);
		ItemRegistry.registerItem(wand);
		ItemRegistry.registerItem(bow);
		ItemRegistry.registerItem(crossbow);

		ItemRegistry.registerItem(wand_stick);
		ItemRegistry.registerItem(craftLeather);

		ItemRegistry.registerItem(archer_plate);
		ItemRegistry.registerItem(berserker_plate);

		ItemRegistry.registerItem(dart);
		ItemRegistry.registerItem(headless_arrow);

		ItemRegistry.registerItem(shield_berserker);
		ItemRegistry.registerItem(shield_archer);

	}

	public static void registerRenderers(){
		String mod = AddonBma.MODID;
		registerRender(berserker_head, berserker_head.getModeltextureLocation(),mod);
		registerRender(berserker_chest, berserker_chest.getModeltextureLocation(),mod);
		registerRender(berserker_legs, berserker_legs.getModeltextureLocation(),mod);
		registerRender(berserker_feet, berserker_feet.getModeltextureLocation(),mod);

		registerRender(mage_head, mage_head.getModeltextureLocation(),mod);
		registerRender(mage_chest, mage_chest.getModeltextureLocation(),mod);
		registerRender(mage_legs, mage_legs.getModeltextureLocation(),mod);
		registerRender(mage_feet, mage_feet.getModeltextureLocation(),mod);

		registerRender(archer_head, archer_head.getModeltextureLocation(),mod);
		registerRender(archer_chest, archer_chest.getModeltextureLocation(),mod);
		registerRender(archer_legs, archer_legs.getModeltextureLocation(),mod);
		registerRender(archer_feet, archer_feet.getModeltextureLocation(),mod);

		registerRender(shield_berserker, "shield_berserker",mod);
		registerRender(shield_archer, "shield_archer",mod);

		registerRender(hammer, "weapon/hammer",mod);
		registerRender(bow, "weapon/archer_bow",mod);
		registerRender(crossbow, "weapon/crossbow",mod);
		
		registerRender(headless_arrow, "headless_arrow",mod);

		for(int i = 0; i < 16; i++)
			registerRender(wand, "weapon/wand_"+i, mod, i);

		registerRender(archer_plate, "plate_archer",mod);
		registerRender(berserker_plate, "plate_berserker",mod);

		registerVanillaRender(wand_stick, "stick", 0);
		registerVanillaRender(dart, "arrow", 0);

		for(int i = 0; i < 3; i++)
			registerVanillaRender(craftLeather, "leather", i);

	}

	private static void addToOreDict() {
		OreDictionary.registerOre("plate", berserker_plate);
		OreDictionary.registerOre("plate", archer_plate);

		OreDictionary.registerOre("wandCore",Items.GOLD_NUGGET);
		OreDictionary.registerOre("wandCore",Items.SPECKLED_MELON);
		OreDictionary.registerOre("wandCore",Items.EMERALD);
		OreDictionary.registerOre("wandCore",Items.DIAMOND);
		OreDictionary.registerOre("wandCore",Items.BLAZE_ROD);
		OreDictionary.registerOre("wandCore",new ItemStack(Items.DYE,1,4));
		OreDictionary.registerOre("wandCore",Items.BLAZE_POWDER);
		OreDictionary.registerOre("wandCore",Items.BONE);
		OreDictionary.registerOre("wandCore",Items.ENDER_PEARL);
		OreDictionary.registerOre("wandCore",Items.GLOWSTONE_DUST);
		OreDictionary.registerOre("wandCore",Items.SPIDER_EYE);
		OreDictionary.registerOre("wandCore",Items.FERMENTED_SPIDER_EYE);
		OreDictionary.registerOre("wandCore",Items.RABBIT_FOOT);
		OreDictionary.registerOre("wandCore",Items.CHORUS_FRUIT);
		OreDictionary.registerOre("wandCore",new ItemStack(Items.FISH,1,2));
		OreDictionary.registerOre("wandCore",new ItemStack(Items.FISH,1,3));
	}
}
