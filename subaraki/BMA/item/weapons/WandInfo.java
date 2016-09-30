package subaraki.BMA.item.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import subaraki.BMA.capability.MageDataCapability;
import subaraki.BMA.item.BmaItems;

public class WandInfo {

	public static final String core [] = new String[]{
			"Pig skin",
			"Creeper skin",
			"Dragon heart",
			"Pigman tusk",
			"Ghast tear",
			"Blaze powder",
			"Rose petal",
			"Chorus petal",
			"Bat fur",
			"Chicken Jockey Feather",
			"Bone of Undead Horse",
			"Spun gold nugget",
			"Ocelot fur",
			"Spider jockey web",
			"Inner Slime slime",
			"Peoney Petal"
	};

	public static final String wood [] = new String[]{
			"Holly",
			"Sacred Birch",
			"Soft HornBeam",
			"Dark HornBeam",
			"Cedar",
			"Walnut",
			"Spruce",
			"Oak",
			"Birch",
			"Dark Oak",
			"Jungle",
			"Acacia",
			"Bamboo",
			"Dark Bamboo",
			"Elder",
			"Light Elder",
	};

	public static boolean isLoyalWand(EntityPlayer player, ItemStack stack){
		if(stack == null)
			return false;
		if(!stack.getItem().equals(BmaItems.wand))
			return false;
		if(!stack.hasTagCompound())
			return false;
		if(!stack.getTagCompound().hasKey("core_index"))
			return false;
		
		int meta = stack.getMetadata();
		int core = stack.getTagCompound().getInteger("core_index");
		int playerMeta = player.getCapability(MageDataCapability.CAPABILITY, null).getMageIndex();
		int coreMeta = player.getCapability(MageDataCapability.CAPABILITY, null).getCoreIndex();

		if(coreMeta == core && meta == playerMeta)
			return true;
		
		return false;
	}
}
