package subaraki.BMA.handler.recipe;

import com.google.gson.JsonObject;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import subaraki.BMA.mod.AddonBma;

public class RecipeWand implements IRecipeFactory {
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);

		ShapedPrimer primer = new ShapedPrimer();
		primer.width = recipe.getWidth();
		primer.height = recipe.getHeight();
		primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
		primer.input = recipe.getIngredients();

		return new CraftCapes(new ResourceLocation(AddonBma.MODID, "craft_wand"), recipe.getRecipeOutput(), primer);
	}

	public static class CraftCapes extends ShapedOreRecipe {

		public CraftCapes(ResourceLocation group, ItemStack result, ShapedPrimer primer) {
			super(group, result, primer);
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting var1) {
			
			ItemStack newOutput = output.copy();
			
			NBTTagCompound tag = new NBTTagCompound();
			
			if(var1.getStackInSlot(0).isEmpty()&& var1.getStackInSlot(8).isEmpty())
				tag.setString("core", var1.getStackInSlot(2).getDisplayName()+" & "+ var1.getStackInSlot(6).getDisplayName());
			else
				tag.setString("core", var1.getStackInSlot(0).getDisplayName()+" & "+ var1.getStackInSlot(8).getDisplayName());

			newOutput.setTagCompound(tag);
			
			return newOutput;
		}
	}
}