package subaraki.BMA.capability;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import subaraki.BMA.mod.AddonBma;

public class CapabilityFreezeProvider implements ICapabilitySerializable<NBTTagCompound>
{
    /**
     * Unique key to identify the attached provider from others
     */
    public static final ResourceLocation KEY = new ResourceLocation(AddonBma.MODID, "freeze_data");

    /**
     * The instance that we are providing
     */
    final FreezeData slots = new FreezeData();

    /**gets called before world is initiated. player.worldObj will return null here !*/
    public CapabilityFreezeProvider(EntityLivingBase entity){
        slots.setPlayer(entity);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == FreezeDataCapability.CAPABILITY)
            return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if (capability == FreezeDataCapability.CAPABILITY)
            return (T)slots;
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT(){
        return (NBTTagCompound) FreezeDataCapability.CAPABILITY.writeNBT(slots, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt){
    	FreezeDataCapability.CAPABILITY.readNBT(slots, null, nbt);
    }
}
