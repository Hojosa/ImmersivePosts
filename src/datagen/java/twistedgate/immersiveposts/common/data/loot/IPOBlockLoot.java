package twistedgate.immersiveposts.common.data.loot;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import twistedgate.immersiveposts.IPOMod;
import twistedgate.immersiveposts.common.IPOContent;
import twistedgate.immersiveposts.common.IPORegistries;
import twistedgate.immersiveposts.common.blocks.PostBlock;
import twistedgate.immersiveposts.enums.EnumPostMaterial;
import twistedgate.immersiveposts.util.loot.PostMaterialDropLootEntry;

public class IPOBlockLoot extends LootTableProvider{
//	private BiConsumer<ResourceLocation, LootTable.Builder> out;
	public IPOBlockLoot(PackOutput output){
		super(output, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(LootyLoot::new, LootContextParamSets.BLOCK)));
//		this.out = output;
	}
	
//	@Override
//	public String getName(){
//		return "IPOLootTables";
//	}
	
//	@Override
//	public List getTables(){
//		return List.of(new LootTableProvider.SubProviderEntry(LootyLoot::new, LootContextParamSets.BLOCK));
//	}
	
//	@Override
//	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker){
//		map.forEach((id, table) -> {
//			LootTables.validate(validationtracker, id, table);
//		});
//	}
	
	public static class LootyLoot extends VanillaBlockLoot {
		private final Set<ResourceLocation> generatedTables = new HashSet<>();
//		private BiConsumer<ResourceLocation, LootTable.Builder> out;
		
		@Override
		protected void generate() {
			
			// Fences
			for(RegistryObject<FenceBlock> b:IPOContent.Blocks.Fences.ALL_FENCES){
				System.out.println(b.get());
				dropSelf(b.get());
			}
			
			// Posts
			for(EnumPostMaterial mat:EnumPostMaterial.values()){
				RegistryObject<PostBlock> b = IPOContent.Blocks.Posts.getRegObject(mat);
				System.out.println(b.get());
//				dropSelf(b.get());
//				drop
//				register(b, createPoolBuilder().add(PostMaterialDropLootEntry.builder()));
				add(b.get(), LootTable.lootTable().withPool(createPoolBuilder().add(PostMaterialDropLootEntry.builder())));
			}
			
			
			
//			registerSelfDropping(IPOContent.Blocks.POST_BASE, createPoolBuilder().add(BaseCoverDropLootEntry.builder()));
			dropSelf(IPOContent.Blocks.POST_BASE.get());
		}
		
		@Override
		protected Iterable<Block> getKnownBlocks() {
			List<Block> allBlocks =  IPORegistries.BLOCK_REGISTER.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
			allBlocks.removeAll(IPORegistries.BLOCK_REGISTER.getEntries().stream().filter(e -> e.getKey().location().getPath().contains("truss")).map(RegistryObject::get).toList());
//					ForgeRegistries.BLOCKS.getEntries().stream().filter(e -> e.getKey().location().getNamespace().equals(IPOMod.ID)).map(Map.Entry::getValue).collect(Collectors.toList());
			return allBlocks;
		}
		
		private <B extends Block> void registerSelfDropping(RegistryObject<B> b, LootPool.Builder... pool){
			LootPool.Builder[] withSelf = Arrays.copyOf(pool, pool.length + 1);
			withSelf[withSelf.length - 1] = singleItem(b.get());
			register(b, withSelf);
		}
		
		private LootPool.Builder singleItem(ItemLike in){
			return createPoolBuilder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(in));
		}
		
		private <B extends Block> void register(RegistryObject<B> b, LootPool.Builder... pools){
			LootTable.Builder builder = LootTable.lootTable();
			for(LootPool.Builder pool:pools)
				builder.withPool(pool);
			register(b, builder);
		}
		
		private <B extends Block> void register(RegistryObject<B> b, LootTable.Builder table){
			register(b.getId(), table);
		}
		
		private void register(ResourceLocation name, LootTable.Builder table){
			ResourceLocation loc = toTableLoc(name);
			if(!generatedTables.add(loc))
				throw new IllegalStateException("Duplicate loot table " + name);
//			out.accept(loc, table.setParamSet(LootContextParamSets.BLOCK));
			
		}
		
		private LootPool.Builder createPoolBuilder(){
			return LootPool.lootPool().when(ExplosionCondition.survivesExplosion());
		}
		
		private ResourceLocation toTableLoc(ResourceLocation in){
			return new ResourceLocation(in.getNamespace(), "blocks/" + in.getPath());
		}
	}
}
