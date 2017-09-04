//package subaraki.BMA.capability;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import lib.playerclass.network.NetworkHandler;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.storage.MapStorage;
//import net.minecraft.world.storage.WorldSavedData;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.world.WorldEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import subaraki.BMA.handler.network.CSyncWorldData;
//
//public class WorldDataHandler extends WorldSavedData {
//
//	private static final String BMA_WORLD_SAVE_DATA = "bma_world_save_data";
//
//	private List<UUID> frozenPlayers = new ArrayList<UUID>();
//	private List<Integer> frozenEntities = new ArrayList<Integer>();
//
//	public WorldDataHandler() {
//		super(BMA_WORLD_SAVE_DATA);
//	}
//
//	public WorldDataHandler(String s) {
//		super(s);
//	}
//
//	@Override
//	public void readFromNBT(NBTTagCompound nbt) {
//		List<UUID> entryList = new ArrayList<UUID>();
//
//		NBTTagList taglist = nbt.getTagList("uuids", 10);
//
//		for (int entryTag = 0; entryTag < taglist.tagCount(); entryTag++)
//		{
//			NBTTagCompound tag = (NBTTagCompound) taglist.get(entryTag);
//			String uuid = tag.getString("theUUID");
//			entryList.add(UUID.fromString(uuid));
//		}
//
//		this.frozenPlayers = entryList;
//	}
//
//	@Override
//	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
//
//		NBTTagList taglist = new NBTTagList();
//
//		if(!frozenPlayers.isEmpty())
//			for(UUID entry : frozenPlayers){
//				NBTTagCompound tag = new NBTTagCompound();
//				tag.setString("theUUID", entry.toString());
//				taglist.appendTag(tag);
//			}
//		nbt.setTag("uuids", taglist);
//
//		return nbt;
//	}
//
//	public static WorldDataHandler get(World world){
//		MapStorage storage = world.getMapStorage(); // world.getperworldstorage for dimension precise saving
//		WorldSavedData instance = storage.getOrLoadData(WorldDataHandler.class, BMA_WORLD_SAVE_DATA);
//
//		if(instance == null){
//			instance = new WorldDataHandler();
//			storage.setData(BMA_WORLD_SAVE_DATA, instance);
//		}
//		return (WorldDataHandler) instance;
//	}
//
//	/////////////////////////////////////////////////////////////////////////////
//	public void addEntry(UUID entry){
//		if(!contains(entry))
//			frozenPlayers.add(entry);
//		syncClient();
//	}
//
//	/**checks for position and dimension. you can never place two pads at the same spot.*/
//	public boolean contains(UUID entry){
//		if(frozenPlayers.isEmpty())
//			return false;
//		
//		for(UUID uuid : frozenPlayers)
//			if(uuid.equals(entry))
//				return true;
//		
//		return false;
//	}
//
//	public void removeEntry(UUID entry) {
//		if(contains(entry))
//			frozenPlayers.remove(entry);
//
//		syncClient();
//	}
//
//	public void syncClient(){
//		NetworkHandler.NETWORK.sendToAll(new CSyncWorldData(frozenPlayers));
//	}
//
//	public List<UUID> getEntries(){
//		return frozenPlayers;
//	}
//
//	/**used only to sync server's data to the client*/
//	public void copyOverEntries(List<UUID> allTelepads ){
//		this.frozenPlayers = allTelepads;
//	}
//
//	public static class WorldDataHandlerSaveEvent{
//
//		public WorldDataHandlerSaveEvent() {
//			MinecraftForge.EVENT_BUS.register(this);
//		}
//
//		@SubscribeEvent
//		public void onWorldSave(WorldEvent.Save event){
//			WorldDataHandler.get(event.getWorld()).markDirty();
//		}
//		@SubscribeEvent
//		public void onWorldLoad(WorldEvent.Load event){
//			//simply calling an instance will load it's data
//			WorldDataHandler.get(event.getWorld());
//		}
//	}
//}
