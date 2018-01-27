package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.item.weapons.ItemBowArcher;
import subaraki.BMA.mod.AddonBma;

public class CSyncFlip implements IMessage{

	public CSyncFlip() {
	}

	boolean isFlipped = false;

	public CSyncFlip(boolean flipped)
	{
		this.isFlipped = flipped;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		 this.isFlipped = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.isFlipped);
	}

	public static class CSyncFlipHandler implements IMessageHandler<CSyncFlip, IMessage> 
	{

		@Override
		public IMessage onMessage(CSyncFlip message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().player;
			World world = player.world;

			((WorldServer)world).addScheduledTask(() -> {
				if(!player.getHeldItemMainhand().isEmpty())
					if(player.getHeldItemMainhand().getItem().equals(BmaItems.bow))
						((ItemBowArcher)player.getHeldItemMainhand().getItem()).setFlipped(message.isFlipped);
				
			});
			
			return null;
		}
	}
}
