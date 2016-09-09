package subaraki.BMA.handler.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import subaraki.BMA.handler.network.PacketExpelliarmus.PacketExpelliarmusHandler;
import subaraki.BMA.handler.network.PacketSyncMageIndex.PacketSyncMageIndexHandler;
import subaraki.BMA.handler.network.PacketSyncSpellList.PacketSyncSpellListHandler;

public class PacketHandler {

	private static final String CHANNEL = "B_M_A_Channel";
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
	
	public PacketHandler() {
		NETWORK.registerMessage(PacketSyncMageIndexHandler.class, PacketSyncMageIndex.class, 0, Side.CLIENT);
		NETWORK.registerMessage(PacketExpelliarmusHandler.class, PacketExpelliarmus.class, 1, Side.SERVER);
		NETWORK.registerMessage(PacketSyncSpellListHandler.class, PacketSyncSpellList.class, 2, Side.CLIENT);
	}
}
