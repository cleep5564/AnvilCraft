package dev.dubhe.anvilcraft.data.generator.tags;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.dubhe.anvilcraft.init.ModBlockTags;
import dev.dubhe.anvilcraft.init.ModBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class BlockTagLoader {
    /**
     * 初始化方块标签
     *
     * @param provider 提供器
     */
    public static void init(@NotNull RegistrateTagsProvider<Block> provider) {
        provider.addTag(ModBlockTags.REDSTONE_TORCH).setReplace(false)
            .add(Blocks.REDSTONE_WALL_TORCH)
            .add(Blocks.REDSTONE_TORCH);
        provider.addTag(ModBlockTags.MUSHROOM_BLOCK).setReplace(false)
            .add(Blocks.BROWN_MUSHROOM_BLOCK)
            .add(Blocks.RED_MUSHROOM_BLOCK)
            .add(Blocks.MUSHROOM_STEM);
        provider.addTag(ModBlockTags.HAMMER_CHANGEABLE).setReplace(false)
            .add(Blocks.OBSERVER)
            .add(Blocks.HOPPER)
            .add(Blocks.DROPPER)
            .add(Blocks.DISPENSER)
            .add(Blocks.LIGHTNING_ROD);
        provider.addTag(ModBlockTags.HAMMER_REMOVABLE).setReplace(false)
            .add(Blocks.BELL)
            .add(Blocks.REDSTONE_LAMP)
            .add(Blocks.IRON_DOOR)
            .add(Blocks.RAIL)
            .add(Blocks.ACTIVATOR_RAIL)
            .add(Blocks.DETECTOR_RAIL)
            .add(Blocks.POWERED_RAIL)
            .add(Blocks.NOTE_BLOCK)
            .add(Blocks.OBSERVER)
            .add(Blocks.HOPPER)
            .add(Blocks.DROPPER)
            .add(Blocks.DISPENSER)
            .add(Blocks.HONEY_BLOCK)
            .add(Blocks.SLIME_BLOCK)
            .add(Blocks.PISTON)
            .add(Blocks.STICKY_PISTON)
            .add(Blocks.LIGHTNING_ROD)
            .add(Blocks.DAYLIGHT_DETECTOR)
            .add(Blocks.LECTERN)
            .add(Blocks.TRIPWIRE_HOOK)
            .add(Blocks.SCULK_SHRIEKER)
            .add(Blocks.LEVER)
            .add(Blocks.STONE_BUTTON)
            .add(Blocks.OAK_PRESSURE_PLATE)
            .add(Blocks.STONE_PRESSURE_PLATE)
            .add(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
            .add(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
            .add(Blocks.SCULK_SENSOR)
            .add(Blocks.CALIBRATED_SCULK_SENSOR)
            .add(Blocks.REDSTONE_WIRE)
            .add(Blocks.REDSTONE_TORCH)
            .add(Blocks.REDSTONE_WALL_TORCH)
            .add(Blocks.REDSTONE_BLOCK)
            .add(Blocks.REPEATER)
            .add(Blocks.COMPARATOR)
            .add(Blocks.TARGET)
            .add(Blocks.IRON_TRAPDOOR)
            .add(Blocks.CAULDRON)
            .add(Blocks.LAVA_CAULDRON)
            .add(Blocks.WATER_CAULDRON)
            .add(Blocks.POWDER_SNOW_CAULDRON)
            .add(Blocks.CAMPFIRE)
            .add(Blocks.ANVIL)
            .add(Blocks.CHIPPED_ANVIL)
            .add(Blocks.DAMAGED_ANVIL)
            .add(ModBlocks.HEAVY_IRON_BLOCK.get())
            .add(ModBlocks.HEAVY_IRON_BEAM.get())
            .add(ModBlocks.HEAVY_IRON_COLUMN.get())
            .add(ModBlocks.HEAVY_IRON_PLATE.get())
            .add(ModBlocks.CUT_HEAVY_IRON_BLOCK.get())
            .add(ModBlocks.CUT_HEAVY_IRON_SLAB.get())
            .add(ModBlocks.CUT_HEAVY_IRON_STAIRS.get())
            .add(ModBlocks.POLISHED_HEAVY_IRON_BLOCK.get())
            .add(ModBlocks.POLISHED_HEAVY_IRON_SLAB.get())
            .add(ModBlocks.POLISHED_HEAVY_IRON_STAIRS.get());


        provider.addTag(ModBlockTags.UNDER_CAULDRON).setReplace(false)
            .forceAddTag(BlockTags.CAMPFIRES)
            .add(Blocks.MAGMA_BLOCK)
            .add(ModBlocks.HEATER.get())
            .add(ModBlocks.CORRUPTED_BEACON.get());
        provider.addTag(ModBlockTags.BLOCK_DEVOURER_PROBABILITY_DROPPING).setReplace(false)
            .add(Blocks.STONE)
            .add(Blocks.DEEPSLATE)
            .add(Blocks.ANDESITE)
            .add(Blocks.DIORITE)
            .add(Blocks.GRANITE)
            .add(Blocks.TUFF)
            .add(Blocks.NETHERRACK)
            .add(Blocks.BASALT)
            .add(Blocks.BLACKSTONE)
            .add(Blocks.END_STONE);
        provider.addTag(ModBlockTags.GLASS_BLOCKS).setReplace(false)
            .add(ModBlocks.TEMPERING_GLASS.get());
        provider.addTag(ModBlockTags.FORGE_GLASS_BLOCKS).setReplace(false)
            .add(ModBlocks.TEMPERING_GLASS.get());
        provider.addTag(ModBlockTags.LASE_CAN_PASS_THROUGH).setReplace(false)
            .forceAddTag(ModBlockTags.GLASS_BLOCKS)
            .forceAddTag(ModBlockTags.GLASS_PANES)
            .forceAddTag(ModBlockTags.FORGE_GLASS_BLOCKS)
            .forceAddTag(ModBlockTags.FORGE_GLASS_PANES)
            .forceAddTag(BlockTags.REPLACEABLE);
        provider.addTag(ModBlockTags.DEEPSLATE_METAL).setReplace(false)
            .add(Blocks.DEEPSLATE_GOLD_ORE)
            .add(Blocks.DEEPSLATE_IRON_ORE)
            .add(Blocks.DEEPSLATE_COPPER_ORE)
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_TIN_ORE.get())
            .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
            .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get())
            .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
            .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
            .add(ModBlocks.DEEPSLATE_URANIUM_ORE.get());
        provider.addTag(ModBlockTags.ORES).setReplace(false)
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_TIN_ORE.get())
            .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
            .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get())
            .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
            .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
            .add(ModBlocks.DEEPSLATE_URANIUM_ORE.get())
            .add(ModBlocks.VOID_STONE.get())
            .add(ModBlocks.EARTH_CORE_SHARD_ORE.get());
        provider.addTag(ModBlockTags.FORGE_ORES).setReplace(false)
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_TIN_ORE.get())
            .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
            .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get())
            .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
            .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
            .add(ModBlocks.DEEPSLATE_URANIUM_ORE.get())
            .add(ModBlocks.VOID_STONE.get())
            .add(ModBlocks.EARTH_CORE_SHARD_ORE.get());
        provider.addTag(ModBlockTags.ORES_IN_GROUND_DEEPSLATE).setReplace(false)
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_TIN_ORE.get())
            .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
            .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get())
            .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
            .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
            .add(ModBlocks.DEEPSLATE_URANIUM_ORE.get())
            .add(ModBlocks.VOID_STONE.get())
            .add(ModBlocks.EARTH_CORE_SHARD_ORE.get());
        provider.addTag(ModBlockTags.FORGE_ORES_IN_GROUND_DEEPSLATE).setReplace(false)
            .add(ModBlocks.DEEPSLATE_ZINC_ORE.get())
            .add(ModBlocks.DEEPSLATE_TIN_ORE.get())
            .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
            .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get())
            .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
            .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
            .add(ModBlocks.DEEPSLATE_URANIUM_ORE.get())
            .add(ModBlocks.VOID_STONE.get())
            .add(ModBlocks.EARTH_CORE_SHARD_ORE.get());
        provider.addTag(ModBlockTags.END_PORTAL_UNABLE_CHANGE).setReplace(false)
            .add(Blocks.DRAGON_EGG);

        provider.addTag(ModBlockTags.PLAYER_WORKSTATIONS_CRAFTING_TABLES).setReplace(false)
            .add(Blocks.CRAFTING_TABLE);
    }
}
