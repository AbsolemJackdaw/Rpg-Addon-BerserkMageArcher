package subaraki.BMA.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class MageIndexData {

	private EntityPlayer player;

	/**an integer between 0-16 that determines what kind of wand this person has.*/
	private int mageIndex = -1;
	/**an integer between 0-16 that determines what kind of core this person has.*/
	private int coreIndex = -1;
	
	public MageIndexData(){
	}

	public EntityPlayer getPlayer() { 
		return player; 
	}

	public void setPlayer(EntityPlayer newPlayer){
		this.player = newPlayer;
	}

	public NBTBase writeData(){
		//hook into the tagcompound of the ItemStackHandler
		NBTTagCompound tag = new NBTTagCompound();
		//add our own tags
		tag.setInteger("mage_type", mageIndex);
		tag.setInteger("core_type", coreIndex);
		//save mix of itemstacks and personal tags
		return tag;
	}
	
	public void readData(NBTBase nbt){
		mageIndex = ((NBTTagCompound)nbt).getInteger("mage_type");
		coreIndex = ((NBTTagCompound)nbt).getInteger("core_type");

	}
	
	public int getMageIndex() {
		return mageIndex;
	}
	
	public void setMageIndex(int mageIndex) {
		this.mageIndex = mageIndex;
	}
	
	public int getCoreIndex() {
		return coreIndex;
	}
	
	public void setCoreIndex(int coreIndex) {
		this.coreIndex = coreIndex;
	}
}
