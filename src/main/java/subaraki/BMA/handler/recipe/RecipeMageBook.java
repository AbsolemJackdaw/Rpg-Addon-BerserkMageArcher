package subaraki.BMA.handler.recipe;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import subaraki.BMA.mod.AddonBma;

public class RecipeMageBook implements IRecipeFactory {
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

			ITextComponent pages[] = getMagePages();

			tag.setString("author", "Rebentar Strepitus");
			tag.setString("title", "Magic For Beginners");

			NBTTagList pageList = new NBTTagList();
			
			for(int i = 0; i < pages.length;i++){
				NBTTagString page = new NBTTagString(ITextComponent.Serializer.componentToJson(pages[i]));
				pageList.appendTag(page);
			}
			
			tag.setTag("pages", pageList);

			newOutput.setTagCompound(tag);

			return newOutput;
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////BOOK STUFF : making the pages/////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	public static ITextComponent[] getMagePages() {

		ITextComponent page2 = new TextComponentString("");
		page2.appendSibling(alinea("page2intro"));
		skipLine(page2);
		skipLine(page2);
		page2.appendSibling(alinea("page2text"));

		ITextComponent page11 = new TextComponentString("");
		page11.appendSibling(alinea("page11_0", true, true));
		skipLine(page11);skipLine(page11);
		page11.appendSibling(alinea("page11_1", false, true));
		skipLine(page11);skipLine(page11);
		for(int i = 2; i < 7; i++)
		{
			page11.appendSibling(alinea("page11_"+i));
			skipLine(page11);
		}
		skipLine(page11);
		page11.appendSibling(alinea("page11_8"));

		return new ITextComponent[]{
				bookPage("page1title", "page1intro", "page1text"),
				page2,
				bookPage("page3title", "page3intro", "page3text"),
				bookSpell("page4intro", "page4text", "page4text2"),
				bookSpell("page5intro", "page5text", "page5text2"),
				bookSpell("page6intro", "page6text", "page6text2"),
				bookPage("page7title", "page7intro", "page7text"),
				bookSpell("page8intro", "page8text", "page8text2"),
				bookPage("page9title", "page9intro", "page9text"),
				bookSpell("page10intro", "page10text", "page10text2"),
				bookPage("page12title", "page12intro", "page12text"),
				bookSpell("page13title", "page13intro", "page13text"),
				page11
		};
	}

	private static ITextComponent bookPage(String title, String intro, String text){
		ITextComponent page = new TextComponentString("");

		page.appendSibling(alinea(title, true, true));

		skipLine(page);
		skipLine(page);


		page.appendSibling(alinea(intro, false, true));

		skipLine(page);

		page.appendSibling(alinea(text));

		return page;
	}

	private static ITextComponent bookSpell(String spell, String info, String info2){

		ITextComponent page = new TextComponentString("");

		page.appendSibling(alinea(spell, false, true));

		skipLine(page);
		skipLine(page);

		page.appendSibling(alinea(info));

		skipLine(page);
		skipLine(page);

		page.appendSibling(alinea(info2));

		return page;
	}

	private static TextComponentTranslation alinea(String text, boolean bold, boolean underlined){
		TextComponentTranslation tct = new TextComponentTranslation(text);

		tct.getStyle().setBold(bold);
		tct.getStyle().setUnderlined(underlined);

		return tct;
	}

	private static TextComponentTranslation alinea(String text){
		return alinea(text,false,false);
	}

	private static void skipLine(ITextComponent page){
		page.appendSibling(alinea("\n"));
	}
}