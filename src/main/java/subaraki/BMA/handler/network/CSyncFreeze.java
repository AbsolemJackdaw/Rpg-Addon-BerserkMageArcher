package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import subaraki.BMA.capability.FreezeData;
import subaraki.BMA.capability.FreezeDataCapability;
import subaraki.BMA.mod.AddonBma;

public class CSyncFreeze implements IMessage{

	public CSyncFreeze() {
	}

	public boolean freeze;
	public int entityId;

	public CSyncFreeze(boolean freeze, int entityId) {
		this.freeze = freeze;
		this.entityId=entityId;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		freeze = buf.readBoolean();
		entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(freeze);
		buf.writeInt(entityId);
	}

	public static class CSyncFreezeHandler implements IMessageHandler<CSyncFreeze, IMessage>
	{
		@Override
		public IMessage onMessage(CSyncFreeze message, MessageContext ctx) {

			AddonBma.proxy.getClientMinecraft().addScheduledTask(() ->{
				Entity e = AddonBma.proxy.getClientPlayer().world.getEntityByID(message.entityId);

				if(e instanceof EntityLivingBase)
				{
					EntityLivingBase el = (EntityLivingBase)e;

					if(el.hasCapability(FreezeDataCapability.CAPABILITY, null))
					{
						FreezeData data = FreezeData.get(el);

						if(message.freeze)
						{
							data.startToFreeze();

							//jitter fix ?
							el.prevLimbSwingAmount = el.limbSwingAmount;
							el.prevCameraPitch = el.cameraPitch;
							el.prevPosX = el.posX;
							el.prevPosY = el.posY;
							el.prevPosZ = el.posZ;
							el.prevSwingProgress = el.swingProgressInt;
							el.prevRenderYawOffset = el.renderYawOffset;
							el.prevRotationPitch = el.rotationPitch;
							el.prevRotationYaw = el.rotationYaw;
							el.prevRotationYawHead = el.rotationYawHead;
						}
						else
							data.stopFreeze();
					}
				}
			});

			return null;
		}
	}
}
