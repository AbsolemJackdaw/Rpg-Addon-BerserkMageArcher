package subaraki.BMA.handler.spells;

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

	private HashMap<String, EnumSpell> spellSpoken = new HashMap<String, EnumSpell>();
	private ArrayList<String> spells = new ArrayList();

	public SpellHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		spells.add(EnumSpell.AUGOLUSTRA.getLowerName());//attack spell
		spells.add(EnumSpell.AESCONVERTO.getLowerName());//alchemy
		spells.add(EnumSpell.EPISKEY.getLowerName());//heal
		spells.add(EnumSpell.CONTEGO.getLowerName());//shield spell
		spells.add(EnumSpell.EXPELLIARMUS.getLowerName());//fires a throwable entity, and when it hits a player, tosses weapons out of their hand.
		spells.add(EnumSpell.PERMOVEO.getLowerName()); // turns carpet into a flying carpet
		spells.add(EnumSpell.FREEZE.getLowerName());// freezes entities into place
		spells.add(EnumSpell.BOMBARDA.getLowerName());
	}

	@SubscribeEvent
	public void onSpellSpoken(ServerChatEvent event){
		if(event.getPlayer().inventory.getCurrentItem() == ItemStack.EMPTY)
			return;
		if(!(event.getPlayer().inventory.getCurrentItem().getItem() instanceof ItemWand))
			return;

		if(event.getMessage().toLowerCase().equals("libra"))
			addSpokenSpell(event.getUsername(), EnumSpell.NONE);
		
		if(!spells.contains(event.getMessage().toLowerCase()))
			return;

		addSpokenSpell(event.getUsername(), EnumSpell.fromString(event.getMessage().toLowerCase()));
		PacketHandler.NETWORK.sendToAll(new CSyncSpellListPacket(event.getUsername(), event.getMessage().toLowerCase()));//send message to add spell to list client side
	}

	public boolean hasSpokenSpell(EntityPlayer player, EnumSpell spell){
		
		if(!spellSpoken.containsKey(player.getName()))
			return false;
		
		if(!spellSpoken.get(player.getName()).getLowerName().equals(spell.getLowerName()))
			return false;
		
		return true;
	}
	
	public EnumSpell getSpokenSpell(EntityPlayer player){
		if(spellSpoken.containsKey(player.getName()))
			return spellSpoken.get(player.getName());
		return EnumSpell.NONE;
	}
	
	public void addSpokenSpell(String playerName, EnumSpell spell){
		spellSpoken.put(playerName, spell);
	}
	
	public static enum EnumSpell{
		
		NONE("libra", true),
		AUGOLUSTRA("Augolustra", true),
		EPISKEY("Episkey", false),
		CONTEGO("Contego Aspida", false),
		EXPELLIARMUS("Expelliarmus", false),
		PERMOVEO("Permoveo Vestis", true),
		FREEZE("Locum Tenentem", true),
		BOMBARDA("Bombarda", false),
		AESCONVERTO("Aes Converto", true);

		private String name = "aSpell";
		public boolean canCycle;
		
		EnumSpell(String name, boolean cycle)
		{
			this.name= name;
			this.canCycle = cycle;
		}
		
		public static EnumSpell fromString(String name){
			for(EnumSpell spell : EnumSpell.values())
			{
				if(spell.name.toLowerCase().equals(name))
					return spell;
			}
			
			return NONE;
		}
		
		public String getLowerName() {
			return name.toLowerCase();
		}
		
		public String getName() {
			return name;
		}
	}
}
