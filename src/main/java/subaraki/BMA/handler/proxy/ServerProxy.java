package subaraki.BMA.handler.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

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
