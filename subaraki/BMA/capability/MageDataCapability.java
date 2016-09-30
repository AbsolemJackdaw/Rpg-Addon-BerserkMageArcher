package subaraki.BMA.capability;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class MageDataCapability {

	/*
	 * This field will contain the forge-allocated Capability class.
	 * This instance will be initialized internally by Forge, upon calling register.
	 */
	@CapabilityInject(MageIndexData.class)
	public static Capability<MageIndexData> CAPABILITY;

	/*
	 * This registers our capability to the manager
	 */
	public void register(){
		CapabilityManager.INSTANCE.register(

				// This is the class the capability works with
				MageIndexData.class,

				// This is a helper for users to save and load
				new StorageHelper(),

				// This is a factory for default instances
				new DefaultInstanceFactory()
				);
	}

	/*
	 * This class handles saving and loading the data.
	 */
	public static class StorageHelper implements Capability.IStorage<MageIndexData>{

		@Override
		public NBTBase writeNBT(Capability<MageIndexData> capability, MageIndexData instance, EnumFacing side)		{
			return instance.writeData();
		}

		@Override
		public void readNBT(Capability<MageIndexData> capability, MageIndexData instance, EnumFacing side, NBTBase nbt){
			instance.readData(nbt);
		}
	}

	/*
	 * This class handles constructing new instances for this capability
	 */
	public static class DefaultInstanceFactory implements Callable<MageIndexData>{
		@Override
		public MageIndexData call() throws Exception{
			return new MageIndexData();
		}
	}
}
