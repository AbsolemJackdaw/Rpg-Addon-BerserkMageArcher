package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.rpginventory.capability.playerinventory.RpgInventoryCapability;
import subaraki.rpginventory.mod.RpgInventory;

public class PacketSyncMageIndex implements IMessage{

	int core = -1;
	int meta = -1;
	public PacketSyncMageIndex() {
	}
	public PacketSyncMageIndex(int core, int meta){
		this.core=core;
		this.meta=meta;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		core = buf.readInt();
		meta = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(core);
		buf.writeInt(meta);
	}

	public static class PacketSyncMageIndexHandler implements IMessageHandler<PacketSyncMageIndex, IMessage>{

		@Override
		public IMessage onMessage(PacketSyncMageIndex message, MessageContext ctx) {
			EntityPlayer player = RpgInventory.proxy.getClientPlayer();

			if(message.core > -1)
				player.getCapability(RpgInventoryCapability.CAPABILITY, null).setCoreIndex(message.core);
			if(message.meta > -1)
				player.getCapability(RpgInventoryCapability.CAPABILITY, null).setMageIndex(message.meta);

			return null;
		}
	}
}