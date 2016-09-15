package subaraki.BMA.mod;

import java.util.Arrays;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import subaraki.BMA.enchantment.EnchantmentHandler;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.handler.event.BmaEventHandler;
import subaraki.BMA.handler.event.SpellHandler;
import subaraki.BMA.handler.network.PacketHandler;
import subaraki.BMA.handler.proxy.ServerProxy;
import subaraki.BMA.item.BmaItems;

@Mod(modid = AddonBma.MODID, name = AddonBma.NAME, version = AddonBma.VERSION, dependencies = "required-after:rpginventory")
public class AddonBma {

	public static final String MODID = "bma_addon";
	public static final String NAME = "Berserker, Mage, Archer Addon";
	public static final String VERSION = "1.0 for 1.10.2";

	@SidedProxy(clientSide = "subaraki.BMA.handler.proxy.ClientProxy", serverSide = "subaraki.BMA.handler.proxy.ServerProxy")
	public static ServerProxy proxy;

	public static SpellHandler spells;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){

		ModMetadata modMeta = event.getModMetadata();
		modMeta.authorList = Arrays.asList(new String[] { "Subaraki" });
		modMeta.autogenerated = false;
		modMeta.credits = "ZetaHunter and Richard Digits (retired team members)";
		modMeta.description = "Class Armor for Mage, Berserker and Archer";
		modMeta.url = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1287896-/";

		BmaItems.loadItems();
		BmaItems.register();
		BmaItems.loadRecipes();
		
		proxy.registerRenders();
		proxy.registerClientEvents();
		proxy.registerEntities();
		
		new PacketHandler();
		new EnchantmentHandler();
		new BmaEventHandler();
		
		spells = new SpellHandler();

	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		proxy.registerColors();
	}
}
