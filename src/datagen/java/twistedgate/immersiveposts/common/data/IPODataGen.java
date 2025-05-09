package twistedgate.immersiveposts.common.data;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import twistedgate.immersiveposts.IPOMod;
import twistedgate.immersiveposts.common.data.loot.IPOBlockLoot;

/**
 * @author TwistedGate
 */
@EventBusSubscriber(modid = IPOMod.ID, bus = Bus.MOD)
public class IPODataGen{
	public static final Logger log = LogManager.getLogger(IPOMod.ID + "/DataGenerator");
	
	@SubscribeEvent
	public static void generate(GatherDataEvent event){
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper exhelper = event.getExistingFileHelper();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		
		if(event.includeServer()){
			IPOBlockTags blocktags = new IPOBlockTags(packOutput, lookupProvider, exhelper);
			generator.addProvider(true, blocktags);
			generator.addProvider(true, new IPOItemTags(packOutput, lookupProvider, blocktags, exhelper));
			generator.addProvider(true, new IPOBlockLoot(packOutput));
			generator.addProvider(true, new IPORecipes(packOutput));
			
		}
		
		if(event.includeClient()){
			generator.addProvider(true, new IPOBlockStates(packOutput, exhelper));
			generator.addProvider(true, new IPOItemModels(packOutput, exhelper));
		}
	}
}
