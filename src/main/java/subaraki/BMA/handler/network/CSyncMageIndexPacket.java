package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.BMA.capability.MageDataCapability;
import subaraki.BMA.mod.AddonBma;

public class CSyncMageIndexPacket implements IMessage{

	int core = -1;
	int meta = -1;
	public CSyncMageIndexPacket() {
	}
	public CSyncMageIndexPacket(int core, int meta){
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

	public static class PacketSyncMageIndexHandler implements IMessageHandler<CSyncMageIndexPacket, IMessage>{

		@Override
		public IMessage onMessage(CSyncMageIndexPacket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask( () -> {
				EntityPlayer player = AddonBma.proxy.getClientPlayer();

				if(player != null)
				{
					AddonBma.log.info("wand info communicated to client. Core index : " + message.core + ", Wand meta : " + message.meta);
					player.getCapability(MageDataCapability.CAPABILITY, null).setCoreIndex(message.core);
					player.getCapability(MageDataCapability.CAPABILITY, null).setMageIndex(message.meta);
				}
			});
			return null;
		}
	}
}
