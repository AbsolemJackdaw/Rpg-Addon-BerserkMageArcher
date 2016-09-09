package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketExpelliarmus implements IMessage {

	public int entity_ID = -1;
	
	public PacketExpelliarmus() {}
	
	public PacketExpelliarmus(int id) {
		entity_ID = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entity_ID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entity_ID);
	}
	
	public static class PacketExpelliarmusHandler implements IMessageHandler<PacketExpelliarmus, IMessage>{

		@Override
		public IMessage onMessage(PacketExpelliarmus message, MessageContext ctx) {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			Entity entity = world.getEntityByID(message.entity_ID);
			
			if(entity instanceof EntityLivingBase){
				EntityLivingBase elb = (EntityLivingBase)entity;
				ItemStack held = elb.getHeldItemMainhand().copy();
				EntityItem ei = new EntityItem(elb.worldObj, elb.posX, elb.posY, elb.posZ, held);
				ei.motionX *= 2;
				ei.motionZ *= 2;
				elb.setHeldItem(EnumHand.MAIN_HAND, null);
				elb.worldObj.spawnEntityInWorld(ei);
			}
			
			return null;
		}
	}

}
