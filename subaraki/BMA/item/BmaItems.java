package subaraki.BMA.item;

import static lib.item.ItemRegistry.registerRender;
import static lib.item.ItemRegistry.registerVanillaRender;

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
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import subaraki.BMA.item.armor.ItemArcherArmor;
import subaraki.BMA.item.armor.ItemBerserkerArmor;
import subaraki.BMA.item.armor.ItemMageArmor;
import subaraki.BMA.item.weapons.ItemBowArcher;
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
	
	public static ItemWand wand;
	public static Item wand_stick, archer_plate, berserker_plate;

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
		wand = (ItemWand) new ItemWand().setCreativeTab(tab);

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
		
		loadRecipes();
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
		GameRegistry.register(bow);

		GameRegistry.register(wand_stick);
		GameRegistry.register(craftLeather);

		GameRegistry.register(archer_plate);
		GameRegistry.register(berserker_plate);
		
		GameRegistry.register(shield_berserker);
		GameRegistry.register(shield_archer);

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
		
		for(int i = 0; i < 16; i++)
			registerRender(wand, "weapon/wand_"+i, mod, i);
		
		registerRender(archer_plate, "plate_archer",mod);
		registerRender(berserker_plate, "plate_berserker",mod);

		registerVanillaRender(wand_stick, "stick", 0);

		for(int i = 0; i < 3; i++)
			registerVanillaRender(craftLeather, "leather", i);
		
	}

	public static void loadRecipes(){
		loadMageBook();
		
		//leather
		GameRegistry.addShapelessRecipe(new ItemStack(craftLeather, 1, 0),
				new Object[] { Items.LEATHER, Items.IRON_INGOT, new ItemStack(Blocks.WOOL, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(craftLeather, 1, 1), new Object[] {
				"WWW", "WLW", "WWW", 'W', new ItemStack(Items.DYE, 1, 4), 'L',
				Items.LEATHER });
		GameRegistry.addRecipe(new ItemStack(craftLeather, 1, 2), new Object[] {
				"WWW", "WLW", "WWW", 'W', new ItemStack(Blocks.WOOL, 1, 12),
				'L', Items.LEATHER });

		//plates / shields
		GameRegistry.addRecipe(new ItemStack(archer_plate,1),
				new Object[] { "IPI","P#P","IPI", 'I', Items.IRON_INGOT, 'P', Blocks.PLANKS });
		GameRegistry.addRecipe(new ItemStack(shield_archer,1),
				new Object[] { "#P#","PIP", 'I', Items.IRON_INGOT, 'P', archer_plate });
		
		GameRegistry.addRecipe(new ItemStack(berserker_plate,1),
				new Object[] { "II","PP","II", 'I', Items.IRON_INGOT, 'P', Blocks.PLANKS });
		GameRegistry.addRecipe(new ItemStack(shield_berserker,1),
				new Object[] { "PP","PP","PP", 'P', berserker_plate });
		
		//weapons
		GameRegistry.addRecipe(new ItemStack(hammer, 1), new Object[] { "SWS",
				" I ", " I ", 'I', Items.IRON_INGOT, 'S', Blocks.IRON_BLOCK,
				'W', new ItemStack(Blocks.WOOL, 1, 1) });
		
		GameRegistry.addRecipe(new ItemStack(bow, 1), new Object[] { "EBR",
				"B#S", "RS#", 'E', Items.EMERALD, 'S', Items.STRING,
				'B', new ItemStack(Blocks.LOG, 1, 2), 'R' , new ItemStack(Blocks.WOOL,1,14) });
		
		addWandRecipe();
		
		//armor
		addArmorRecipe();
	}

	private static void loadMageBook() {
		//text needs to be surounded bu quote control characters ! " \"mypage text goes here\" ";
		String pages[] = new String[]{
				"\"§l§nChapter One:\\nIntroductions§r\\n\\nMagic asks for a lot of imagination. It is not just waving around a wand and blurting out odd words. It requires a mind, set to believe things he might never see, energy he may feel or foul with negative thoughts.\"",
				"\"Beware,for the arcane in you might manifest wrongly.\\n\\nSo focus, stay on the path you feel is righteous, and take it with a little wit, for serious mages never have gained the knowledge that connects nature with the arcane !\"",
				"\"§l§nChapter Two:\\nSurvival\\n§rOn your journey troughout the world to study the arcane, you will most likely encounter dangerous foes. There are two nifty basic spells that can aid you in preventing early death.\"",
				"\"§nAugolustra:\\n§r\\nCalling forth a small combustion, propulsing a stream of hot air towards your foe. \\n\\nStay steady, articulate well, and give a little left-flick with your wand to keep your enemy at bay.\"",
				"\"§nExpelliarmus\\n\\n§rCall forth a stream of red arcane dust that disarms your foe.\\n\\nStay steady, articulate well, and left-flick with your wand when ready to disarm your foe.\"",
				"\"§l§nChapter three:\\nFirst Aid\\n\\n§rMay you have harmed a friend or yourself while messing around with our basic survival spell, remember then that there is always aid to be given for small wounds and barely broken bones.\"",
				"\"§nEpiskey\\n\\n§rHeals the targetted living person or creature.\\n\\nArticulate clearely, stay close to wounded subject,and give a soft swish.\\n\\nImportant : do not be hasty, you might end up hurting your ally.\"",
				"\"§l§nChapter Three\\nTransmutation\\n\\n§rThe art of making oneself rich. This branch of magic should be only used when needed, because greed has never led to any good... you might be swimming in gold, but there are places where nothing can be bought !\"",
				"\"§nAes Converto\\n\\n§rConvert raw ore into gold.\\n\\nBreath softly, speak the named spell, and strike softly over the ore with a slow, right-flick.\"",
				"\"§l§nChapter Fi   \\u003e\\nAdvanced M/\\n§r               /\\nFor the Ma \\u003e\\ngotten th /\\nwithout /\\u003e\\nYou c/\\n/\\u003e/\\n\\u003e/\\n/\\n\\n(the pages seem to be torn out ...)\""
			};
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("author", "Rebentar Strepitus");
			tag.setString("title", "Magic For Beginners");
			 
			NBTTagList taglist = new NBTTagList();
			for(int i = 0; i < 10;i++){
				NBTTagString page = new NBTTagString(pages[i]);
				taglist.appendTag(page);
			}
			tag.setTag("pages", taglist);
			
			ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
	        stack.setTagCompound(tag);
	        
			GameRegistry.addRecipe(stack, new Object[] {
					"LP", "#P", "#P", 'P', new ItemStack(Items.PAPER, 1), 'L',
					new ItemStack(craftLeather,1,1) });
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
	
	private static void addWandRecipe(){
		
		NonNullList<ItemStack> wandRecipeItems = OreDictionary.getOres("wandCore");
		
		for(int i = 0; i < wandRecipeItems.size(); i++){
			ItemStack o1 = wandRecipeItems.get(i);
			for(int j = 0; j < wandRecipeItems.size(); j++){
				ItemStack o2 = wandRecipeItems.get(j);
				
				if(o1.equals(o2))
					continue; //if both are the same item, do not register
				
				ItemStack thewand = new ItemStack(wand_stick);
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("core", o1.getDisplayName()+" and "+o2.getDisplayName());
				thewand.setTagCompound(tag);
				
				GameRegistry.addRecipe(thewand, new Object[] {
						"X##","#S#","##Z", 'S', Items.STICK, 'X', o1, 'Z', o2});
			}
		}
	}
	
	private static void addArmorRecipe(){
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
