package subaraki.BMA.handler.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.RenderAugolustra;
import subaraki.BMA.entity.RenderExpelliarmus;
import subaraki.BMA.entity.RenderHammerSmash;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.item.armor.model.ModelBerserkerArmor;
import subaraki.BMA.item.armor.model.ModelMageArmor;
import subaraki.BMA.mod.AddonBma;

public class ClientProxy extends ServerProxy {

	private static final ModelBerserkerArmor armorBerserkChest = new ModelBerserkerArmor(1.0f);
	private static final ModelBerserkerArmor armorBerserk = new ModelBerserkerArmor(0.5f);
	public static final String berserker_chest = "berserker_chest";
	public static final String berserker_rest = "berserker_rest";

	private static final ModelMageArmor armorMageChest = new ModelMageArmor(1.0f);
	private static final ModelMageArmor armorMage = new ModelMageArmor(0.5f);
	public static final String mage_chest = "mage_chest";
	public static final String mage_rest= "mage_rest";

	@Override
	public ModelBiped getArmorModel(String id) {

		switch (id) {
		case berserker_rest:
			return armorBerserk;
		case berserker_chest:
			return armorBerserkChest;
			
		case mage_rest:
			return armorMage;
		case mage_chest:
			return armorMageChest;
		}
		return super.getArmorModel(id);
	}
	
	public void registerRenders(){
		BmaItems.registerRenderers();

		RenderingRegistry.registerEntityRenderingHandler(EntityAugolustra.class, RenderAugolustra::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityExpelliarmus.class, RenderExpelliarmus::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityHammerSmash.class, RenderHammerSmash::new);

	}
	
	public void registerClientEvents(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void overlay(RenderGameOverlayEvent event){

		if(!event.getType().equals(ElementType.HOTBAR))
			return;
		
		net.minecraft.client.gui.GuiIngame gui = net.minecraft.client.Minecraft.getMinecraft().ingameGUI;
		EntityPlayer player = net.minecraft.client.Minecraft.getMinecraft().thePlayer;

		int x = event.getResolution().getScaledWidth();
		int y = event.getResolution().getScaledHeight();

		int x1 = x/2 - 90 + 9 * 20 + 5;
		int y1 = y - 20;

		String spokenSpell = " ¸¸.•*|*•.¸¸ ";
		
		if(player.getHeldItem(EnumHand.MAIN_HAND)== null || ! player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(BmaItems.wand))
			return;

		String spell = AddonBma.spells.getSpokenSpell(player);
		
		if(spell != null && spell.length() > 4)
			spokenSpell = spell;
		
		gui.drawString(gui.getFontRenderer(), TextFormatting.ITALIC+spokenSpell, x1,y1, 0xffffff);

	}
}
