package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import lib.playerclass.capability.PlayerClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.BMA.config.ConfigurationHandler;
import subaraki.BMA.item.weapons.ItemBowArcher;

public class SSyncBowShot implements IMessage {

	public SSyncBowShot() {
	}

	public int timer;
	public ItemStack item;
	public ItemStack arrowStack;

	public SSyncBowShot(int timer, ItemStack stack, ItemStack arrowStack) {
		this.timer = timer;
		this.item = stack;
		this.arrowStack = arrowStack;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.timer = buf.readInt();
		item = ByteBufUtils.readItemStack(buf);
		arrowStack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(timer);
		ByteBufUtils.writeItemStack(buf, item);
		ByteBufUtils.writeItemStack(buf, arrowStack);
	}

	public static class SSyncBowShotHandler implements IMessageHandler<SSyncBowShot, IMessage>{

		@Override
		public IMessage onMessage(SSyncBowShot message, MessageContext ctx) {

			EntityPlayer player = ctx.getServerHandler().player;
			World world = player.world;

			((WorldServer)world).addScheduledTask(() -> {

				int timer = message.timer;
				ItemStack stack = message.item;
				ItemStack arrowStack = this.findAmmo(player);
				
				float arrowPower = (float)getArrowVelocity(timer)*2f;

				ItemBowArcher bow = (ItemBowArcher) stack.getItem();

				if ((double)arrowPower >= 0.1D)
				{
					
					System.out.println("B. "+timer);
					
					float time = (float)timer / 20f;

					System.out.println("C. "+ time);
					
					if(time >= 1.5f && bow.isFlipped())
					{
						if(PlayerClass.get(player).isShielded())
						{
							//if arrows = 8, then it would shoot :
							//8/2 = 4 
							//-4, -2 , 0 , 2 , 4 = 5 arrows
							//so count = arrows / 2 + 1
							int arrows = (int) (arrowPower / 2f) ;
							if (arrows > arrowStack.getCount())
								arrows = arrowStack.getCount()*2-2;

							for(int yaw = (-arrows/2); yaw <= (arrows/2); yaw+=2)
								spawnArrow(player, world, stack, arrowPower > 1F ? 1f : arrowPower, yaw);
							arrowStack.shrink(arrows/2+1);
						}

						else
						{
							int arrows = arrowStack.getCount() ;
							if (arrows >= 3)
								arrows = 8;

							for(int yaw = (-arrows/2); yaw <= (arrows/2); yaw+=2)
								spawnArrow(player, world, stack, arrowPower > 1F ? 1f : arrowPower, yaw);
							arrowStack.shrink(arrows/2+1);
						}
					}

					else
					{
						spawnArrow(player, world, stack, arrowPower > 5F ? 5f : arrowPower, 0);
						arrowStack.shrink(1);
					}

					world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F) + arrowPower * 0.5F);

					player.addStat(StatList.getObjectUseStats(stack.getItem()));
				}

			});

			return null;
		}
		
		//copied from the item bow archer class
		private ItemStack findAmmo(EntityPlayer player)
		{
			if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
			{
				return player.getHeldItem(EnumHand.OFF_HAND);
			}
			else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
			{
				return player.getHeldItem(EnumHand.MAIN_HAND);
			}
			else
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
				{
					ItemStack itemstack = player.inventory.getStackInSlot(i);

					if (this.isArrow(itemstack))
					{
						return itemstack;
					}
				}

				return ItemStack.EMPTY;
			}
		}

		protected boolean isArrow(ItemStack stack)
		{
			return stack.getItem() instanceof ItemArrow;
		}
	}

	private static void spawnArrow(EntityPlayer entityplayer, World worldIn, ItemStack stack, float power, float yaw){
		if(worldIn.isRemote)
			return;

		ItemBowArcher bow = ((ItemBowArcher)stack.getItem());

		ItemArrow itemarrow = (ItemArrow) Items.ARROW;
		EntityArrow entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), entityplayer);
		entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;
		entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw+yaw, 0.0F, power * (bow.isFlipped()? 3.0F : 1.5f), power > 1F ? 0.0F : 0.3f);

		if (power> 1.0F && bow.isFlipped())
			entityarrow.setIsCritical(true);
		entityarrow.setDamage(entityarrow.getDamage() + ConfigurationHandler.instance.bow_arrow_damage);
		entityarrow.setKnockbackStrength(1);
		stack.damageItem(1, entityplayer);
		entityarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
		worldIn.spawnEntity(entityarrow);
	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge)
	{
		float f = (float)charge / 30.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		return f;
	}
}