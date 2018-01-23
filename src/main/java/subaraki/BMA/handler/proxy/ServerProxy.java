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
import subaraki.BMA.entity.EntityScintilla;
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
	
	public void addRenderLayers() {
	}
}
