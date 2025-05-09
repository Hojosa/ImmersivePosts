package twistedgate.immersiveposts.common.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import twistedgate.immersiveposts.IPOMod;
import twistedgate.immersiveposts.common.IPOContent.Items;
import twistedgate.immersiveposts.common.IPOTags;

/**
 * @author TwistedGate
 */
public class IPOItemTags extends ItemTagsProvider{
	public IPOItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(packOutput, lookupProvider, pBlockTagsProvider.contentsGetter(), IPOMod.ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(Provider p_256380_){
		tag(IPOTags.Rods.ALL)
			.add(Items.ROD_GOLD.get())
			.add(Items.ROD_COPPER.get())
			.add(Items.ROD_LEAD.get())
			.add(Items.ROD_SILVER.get())
			.add(Items.ROD_NICKEL.get())
			.add(Items.ROD_CONSTANTAN.get())
			.add(Items.ROD_ELECTRUM.get())
			.add(Items.ROD_URANIUM.get());
		
		tag(IPOTags.Rods.GOLD)
			.add(Items.ROD_GOLD.get());
		
		tag(IPOTags.Rods.COPPER)
			.add(Items.ROD_COPPER.get());
		
		tag(IPOTags.Rods.LEAD)
			.add(Items.ROD_LEAD.get());
		
		tag(IPOTags.Rods.SILVER)
			.add(Items.ROD_SILVER.get());
		
		tag(IPOTags.Rods.NICKEL)
			.add(Items.ROD_NICKEL.get());
		
		tag(IPOTags.Rods.CONSTANTAN)
			.add(Items.ROD_CONSTANTAN.get());
		
		tag(IPOTags.Rods.ELECTRUM)
			.add(Items.ROD_ELECTRUM.get());
		
		tag(IPOTags.Rods.URANIUM)
			.add(Items.ROD_URANIUM.get());
	}
}
