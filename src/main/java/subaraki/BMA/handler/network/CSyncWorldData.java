//package subaraki.BMA.handler.network;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import io.netty.buffer.ByteBuf;
//import net.minecraftforge.fml.common.network.ByteBufUtils;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//import subaraki.BMA.capability.WorldDataHandler;
//import subaraki.BMA.mod.AddonBma;
//
//public class CSyncWorldData implements IMessage{
//
//	public CSyncWorldData() {
//	}
//
//	public List<UUID> list = null;
//
//	public CSyncWorldData(List<UUID> list) {
//		this.list = list;
//	}
//
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		list = new ArrayList<UUID>();
//		int size = buf.readInt();
//		for(int i = 0; i < size; i++){
//			list.add(UUID.fromString(ByteBufUtils.readUTF8String(buf)));
//		}
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(list.size());
//		for(UUID entry : list)
//			ByteBufUtils.writeUTF8String(buf, entry.toString());
//	}
//
//	public static class CSyncWorldDataHandler implements IMessageHandler<CSyncWorldData, IMessage>
//	{
//		@Override
//		public IMessage onMessage(CSyncWorldData message, MessageContext ctx) {
//			AddonBma.proxy.getClientMinecraft().addScheduledTask(() -> {
//				WorldDataHandler wdh = WorldDataHandler.get(AddonBma.proxy.getClientPlayer().world);
//				wdh.copyOverEntries(message.list);
//				wdh.markDirty();
//			});
//			return null;
//		}
//	}
//}