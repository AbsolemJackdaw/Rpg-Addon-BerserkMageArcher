package subaraki.BMA.handler.event;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import subaraki.BMA.handler.network.CSyncSpellListPacket;
import subaraki.BMA.handler.network.PacketHandler;
import subaraki.BMA.item.weapons.ItemWand;

public class SpellHandler {

	private HashMap<String, String> spellSpoken = new HashMap<String, String>();
	private ArrayList<String> spells = new ArrayList();

	public static final String Augolustra = "augolustra";
	public static final String AesConverto = "aes converto";
	public static final String Episkey = "episkey";
	public static final String ContegoAspida = "contego aspida";
	public static final String Expelliarmus = "expelliarmus";

	public SpellHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		spells.add(Augolustra);//attack spell
		spells.add(AesConverto);//alchemy
		spells.add(Episkey);//heal
		spells.add(ContegoAspida);//shield spell
		spells.add(Expelliarmus);//fires a throwable entity, and when it hits a player, tosses weapons out of their hand.
	}

	@SubscribeEvent
	public void onSpellSpoken(ServerChatEvent event){
		if(event.getPlayer().inventory.getCurrentItem() == ItemStack.EMPTY)
			return;
		if(!(event.getPlayer().inventory.getCurrentItem().getItem() instanceof ItemWand))
			return;

		if(event.getMessage().toLowerCase().equals("libra"))
			addSpokenSpell(event.getUsername(), "none");
		
		if(!spells.contains(event.getMessage().toLowerCase()))
			return;

		addSpokenSpell(event.getUsername(), event.getMessage());
		PacketHandler.NETWORK.sendToAll(new CSyncSpellListPacket(event.getUsername(), event.getMessage()));//send message to add spell to list client side
	}

	public boolean hasSpokenSpell(EntityPlayer player, String spell){

		if(!spellSpoken.containsKey(player.getName()))
			return false;
		if(!spellSpoken.get(player.getName()).toLowerCase().equals(spell))
			return false;
		
		return true;
	}
	
	public String getSpokenSpell(EntityPlayer player){
		if(spellSpoken.containsKey(player.getName()))
			return spellSpoken.get(player.getName());
		return "none";
	}
	
	public void addSpokenSpell(String playerName, String spell){
		spellSpoken.put(playerName, spell);
	}
}
