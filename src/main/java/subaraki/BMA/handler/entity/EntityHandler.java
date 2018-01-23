package subaraki.BMA.handler.entity;

import java.util.ArrayList;

import lib.entity.EntityRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityBombarda;
import subaraki.BMA.entity.EntityDart;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityFlyingCarpet;
import subaraki.BMA.entity.EntityFreezeSpell;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.entity.EntityScintilla;
import subaraki.BMA.mod.AddonBma;

public class EntityHandler {

	public EntityHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		init();
	}

	ArrayList<EntityEntryBuilder> entities = new ArrayList<EntityEntryBuilder>();

	private void init()
	{
		EntityRegistry er = new EntityRegistry();
		
		int id = 0;
		EntityEntryBuilder augolustra = er.registerEntity(EntityAugolustra.class, EntityAugolustra::new, AddonBma.MODID, "augolustra", id++, 64, 15, true);
		EntityEntryBuilder expelliarmus = er.registerEntity(EntityExpelliarmus.class, EntityExpelliarmus::new, AddonBma.MODID, "expelliarmus", id++, 64, 15, true);
		EntityEntryBuilder hammer = er.registerEntity(EntityHammerSmash.class, EntityHammerSmash::new, AddonBma.MODID, "hammersmash", id++, 64, 15, false);
		EntityEntryBuilder hellarrow = er.registerEntity(EntityHellArrow.class, EntityHellArrow::new, AddonBma.MODID, "hellarrow", id++, 256, 20, true);
		EntityEntryBuilder dart = er.registerEntity(EntityDart.class, EntityDart::new, AddonBma.MODID, "dart_entity", id++, 64, 1, true);
		EntityEntryBuilder carpet = er.registerEntity(EntityFlyingCarpet.class, EntityFlyingCarpet::new, AddonBma.MODID, "carpet_entity", id++, 256, 20, true);
		EntityEntryBuilder freeze = er.registerEntity(EntityFreezeSpell.class, EntityFreezeSpell::new, AddonBma.MODID, "freeze_entity", id++, 64, 15, true);
		EntityEntryBuilder bombarda = er.registerEntity(EntityBombarda.class, EntityBombarda::new, AddonBma.MODID, "bombarda_entity", id++, 64, 20, true);
		EntityEntryBuilder scintilla = er.registerEntity(EntityScintilla.class, EntityScintilla::new, AddonBma.MODID, "scintilla_entity", id++, 64, 20, true);

		entities.add(augolustra);
		entities.add(expelliarmus);
		entities.add(hammer);
		entities.add(hellarrow);
		entities.add(dart);
		entities.add(carpet);
		entities.add(freeze);
		entities.add(bombarda);
		entities.add(scintilla);
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> e){

		for(EntityEntryBuilder eeb : entities)
			e.getRegistry().register(eeb.build());
	}
}