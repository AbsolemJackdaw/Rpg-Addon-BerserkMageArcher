package subaraki.BMA.handler.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityBombarda;
import subaraki.BMA.entity.EntityDart;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityFlyingCarpet;
import subaraki.BMA.entity.EntityFreezeSpell;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.mod.AddonBma;

public class ServerProxy {

	public ModelBiped getArmorModel(String id){return null;}
	
	public void registerRenders(){};
	
	public void registerClientEvents(){}

	public void registerColors() {};
	
	public EntityPlayer getClientPlayer(){return null;}
	public Minecraft getClientMinecraft(){return null;}
	
	public void registerRenderInformation(){}
	
	public int getSphereID(boolean isFirstPerson){return 0;}
	
	public void registerEntities(){
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","augolustra"),EntityAugolustra.class, "augolustra", 0, AddonBma.MODID, 64, 15, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","expelliarmus"),EntityExpelliarmus.class, "expelliarmus", 1, AddonBma.MODID, 64, 15, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","hammersmash"),EntityHammerSmash.class, "hammersmash", 2, AddonBma.MODID, 64, 15, false);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","hellarrow"),EntityHellArrow.class, "hellarrow", 3, AddonBma.MODID, 256, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","dart_entity"),EntityDart.class, "dart_entity", 4, AddonBma.MODID, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","flying_carpet"),EntityFlyingCarpet.class, "carpet_entity", 5, AddonBma.MODID, 256, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","freeze_spell"),EntityFreezeSpell.class, "freeze_entity", 6, AddonBma.MODID, 64, 15, true);
		EntityRegistry.registerModEntity(new ResourceLocation("bma_addon","bomb_spell"),EntityBombarda.class, "bombarda_entity", 7, AddonBma.MODID, 64, 20, true);
	}

	public void addRenderLayers() {
	}
}
