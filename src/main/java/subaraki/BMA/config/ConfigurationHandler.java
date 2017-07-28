package subaraki.BMA.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;

public class ConfigurationHandler
{
	public static ConfigurationHandler instance = new ConfigurationHandler();

	public int hammer_uses;
	public int hammer_damage;
	public double bow_arrow_damage;
	public double dart_arrow_damage;
	public int augolustra_damage;

	public String mage_armor[];
	public String berserker_armor[];
	public String archer_armor[];

	public void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
		loadSettings(config);
		config.save();
	}

	private void loadSettings(Configuration config)
	{
		config.addCustomCategoryComment("Weapon Uses", "define the number of maximum uses for the weapons - min 100, max : " + Integer.MAX_VALUE);
		config.addCustomCategoryComment("Damage", "define the ammount of damage dealt by mentioned object");
		config.addCustomCategoryComment("Armor", "define the armor's uses, resistance, enchantabilty and toughness");

		hammer_uses = config.getInt("hammer uses", "Weapon Uses", 100, 100, Integer.MAX_VALUE, "");
		bow_arrow_damage = (double)config.getFloat("arrow damage", "Damage", 1.5F, 0, Float.MAX_VALUE, "minimum arrow damage for the advanced bow");
		dart_arrow_damage = (double)config.getFloat("dart damage", "Damage", 0.5F, 0, Float.MAX_VALUE, "minimum dart damage for the crossbow");
		augolustra_damage = config.getInt("spell damage", "Damage", 5, 0, Integer.MAX_VALUE, "minimum spell damage for the augolustra");
		hammer_damage = config.getInt("hammer damage", "Damage", 8, 0, Integer.MAX_VALUE, "minimum damage for the hammer (affects smash too)");

		mage_armor = config.getStringList("mage armor material", "armor", new String[]{"250", "2", "3", "2", "1", "10", "0.0"}, "uses, armor reduction(head, chest, legs, feet), toughness");
		berserker_armor = config.getStringList("berserker armor material", "armor", new String[]{"250", "2", "4", "3", "2", "25", "0.0"}, "uses, armor reduction(head, chest, legs, feet), toughness");
		archer_armor = config.getStringList("archer armor material", "armor", new String[]{"250", "3", "2", "2", "1", "20", "0.0"}, "uses, armor reduction(head, chest, legs, feet), toughness");
	
	}
}