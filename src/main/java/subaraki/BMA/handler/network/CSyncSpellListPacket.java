package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.BMA.handler.spells.SpellHandler.EnumSpell;
import subaraki.BMA.mod.AddonBma;

public class CSyncSpellListPacket implements IMessage {

	public String spell = "none";
	public String playerName = "none";

	public CSyncSpellListPacket() {}

	public CSyncSpellListPacket(String playername, String spell) {
		this.spell = spell;
		this.playerName = playername;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		spell = ByteBufUtils.readUTF8String(buf);
		playerName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, spell);
		ByteBufUtils.writeUTF8String(buf, playerName);
	}

	public static class PacketSyncSpellListHandler implements IMessageHandler<CSyncSpellListPacket, IMessage>{

		@Override
		public IMessage onMessage(CSyncSpellListPacket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask( ()->{
				AddonBma.spellHandler.addSpokenSpell(message.playerName, EnumSpell.fromString(message.spell));
			});
			return null;
		}
	}
}
