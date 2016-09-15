package subaraki.BMA.item.weapons;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.item.BmaItems;
import subaraki.rpginventory.mod.RpgInventory;

public class ItemHammer extends ItemSword{

	public ItemHammer(ToolMaterial material) {
		super(material);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {

		if(!RpgInventory.playerClass.contains(BmaItems.berserkerClass))
			return super.onItemRightClick(itemStack, world, player, hand);

		Vec3d vec = player.getLook(1);

		EntityHammerSmash ehs = new EntityHammerSmash(world);
		ehs.setLocationAndAngles(player.posX + vec.xCoord*1.5f, player.posY, player.posZ + vec.zCoord*1.5f, player.rotationYaw, player.rotationPitch);
		ehs.setItemStackToSlot(null, itemStack.copy());
		UUID uuid = player.getUniqueID();
		ehs.setOwnerId(uuid);

		player.setHeldItem(hand, null);

		if(!world.isRemote)
			world.spawnEntityInWorld(ehs);

		player.getCooldownTracker().setCooldown(this, 500);

		return super.onItemRightClick(itemStack, world, player, hand);
	}

}
