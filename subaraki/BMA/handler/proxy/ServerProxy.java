package subaraki.BMA.handler.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.mod.AddonBma;

public class ServerProxy {

	@Mod.Instance(AddonBma.MODID)
	private static AddonBma instance;
	
	public ModelBiped getArmorModel(String id){return null;}
	
	public void registerRenders(){};
	
	public void registerClientEvents(){}

	public void registerColors() {};
	
	public EntityPlayer getClientPlayer(){return null;}
	
	public void registerEntities(){
		EntityRegistry.registerModEntity(new ResourceLocation("augolustra"),EntityAugolustra.class, "Augolustra", 0, instance, 64, 15, true);
		EntityRegistry.registerModEntity(new ResourceLocation("expelliarmus"),EntityExpelliarmus.class, "Expelliarmus", 1, instance, 64, 15, true);
		EntityRegistry.registerModEntity(new ResourceLocation("hammersmash"),EntityHammerSmash.class, "HammerSmash", 2, instance, 64, 15, false);
		EntityRegistry.registerModEntity(new ResourceLocation("hellarrow"),EntityHellArrow.class, "HellArrow", 3, instance, 256, 20, true);
	}
}
