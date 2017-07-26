package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.BMA.capability.MageIndexData;
import subaraki.BMA.mod.AddonBma;

public class CSyncShieldPacket implements IMessage{

	public boolean shieldActive;
	public int cap; 
	
	public CSyncShieldPacket() {
	}
	
	public CSyncShieldPacket(boolean shieldActive, int cap){
		this.shieldActive = shieldActive;
		this.cap = cap;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		shieldActive = buf.readBoolean();
		cap = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(shieldActive);
		buf.writeInt(cap);
	}

	public static class CSyncShieldPacketHandler implements IMessageHandler<CSyncShieldPacket, IMessage>{

		@Override
		public IMessage onMessage(CSyncShieldPacket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask( () -> {
				EntityPlayer player = AddonBma.proxy.getClientPlayer();

				if(player != null)
				{
					MageIndexData.get(player).setProtectedByMagic(message.shieldActive);
					MageIndexData.get(player).setShieldCapacity(message.cap);
				}
			});
			return null;
		}
	}
}
