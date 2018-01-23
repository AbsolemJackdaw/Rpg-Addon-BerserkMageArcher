package subaraki.BMA.block;

import lib.block.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;

public class BmaBlocks {

	public static Block airLuminence;
	
	public static Material asAir = new MaterialTransparent(MapColor.AIR);
	
	public static void loadBlocks(){
		airLuminence = new BlockLuminence();
		register();
	}
	
	private static void register(){
		BlockRegistry.registerBlock(airLuminence);
	}
}
