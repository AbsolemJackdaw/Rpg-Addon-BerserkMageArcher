package subaraki.BMA.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import subaraki.BMA.mod.AddonBma;

public class ItemTome extends Item{

	public ItemTome() {
		super();
		setHasSubtypes(true);
		setUnlocalizedName(AddonBma.MODID+":"+"tome");
		setRegistryName("tome");
		setMaxStackSize(1);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getMetadata();
		return super.getUnlocalizedName() + "_" + meta ;
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0; i < 1; ++i)
		{
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		if(stack.getMetadata() == 0){
			tooltip.add("Aes Converto : Changeth min'rals into gold");
			tooltip.add("Augolustra : Hitteth thy foes");
			tooltip.add("Expelliarmus : Unarm thy foe");
			tooltip.add("Episkey : Unarm thy foe");

		}
	}
}
