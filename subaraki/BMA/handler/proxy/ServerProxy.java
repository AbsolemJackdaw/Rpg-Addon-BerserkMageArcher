package subaraki.BMA.handler.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.mod.AddonBma;

public class ServerProxy {

	public ModelBiped getArmorModel(String id){return null;}
	
	public void registerRenders(){};
	
	public void registerClientEvents(){}

	public void registerColors() {};
	
	public EntityPlayer getClientPlayer(){return null;}
	
	public void registerEntities(){
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","augolustra"),EntityAugolustra.class, "augolustra", 0, AddonBma.MODID, 64, 15, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","expelliarmus"),EntityExpelliarmus.class, "expelliarmus", 1, AddonBma.MODID, 64, 15, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","hammersmash"),EntityHammerSmash.class, "hammersmash", 2, AddonBma.MODID, 64, 15, false);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","hellarrow"),EntityHellArrow.class, "hellarrow", 3, AddonBma.MODID, 256, 20, true);
	}
}
