package com.Sakamochanq.otukai.task

import com.Sakamochanq.otukai.task.item.ItemTask
import com.Sakamochanq.otukai.task.kill.KillTask
import com.Sakamochanq.otukai.task.use.UseItemTask
import com.Sakamochanq.otukai.task.breakblock.BreakBlockTask
import com.Sakamochanq.otukai.task.craft.CraftTask
import com.Sakamochanq.otukai.task.fish.FishTask
import com.Sakamochanq.otukai.task.location.LocationTask
import com.Sakamochanq.otukai.task.use.UseType
import org.bukkit.block.Biome
import org.bukkit.Material
import kotlin.time.Duration.Companion.minutes
import org.bukkit.entity.EntityType

object TaskList {

    val tasks: List<Task> = listOf(
        ItemTask(
            item = Material.OAK_LOG,
            amount = 8,
            displayName = "原木",
            timeLimit = 5.minutes
        ),
        ItemTask(
            item = Material.WHEAT_SEEDS,
            amount = 6,
            displayName = "小麦の種",
            timeLimit = 8.minutes
        ),
        ItemTask(
            item = Material.SUGAR_CANE,
            amount = 8,
            displayName = "サトウキビ",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.FLINT,
            amount = 3,
            displayName = "火打石",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.COAL,
            amount = 6,
            displayName = "石炭",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.IRON_INGOT,
            amount = 3,
            displayName = "鉄インゴット",
            timeLimit = 12.minutes
        ),
        ItemTask(
            item = Material.CLAY_BALL,
            amount = 8,
            displayName = "粘土玉",
            timeLimit = 12.minutes
        ),
        ItemTask(
            item = Material.APPLE,
            amount = 2,
            displayName = "リンゴ",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.CARROT,
            amount = 4,
            displayName = "ニンジン",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.POTATO,
            amount = 4,
            displayName = "ジャガイモ",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.PUMPKIN,
            amount = 1,
            displayName = "カボチャ",
            timeLimit = 12.minutes
        ),
        ItemTask(
            item = Material.MELON_SLICE,
            amount = 6,
            displayName = "スイカ",
            timeLimit = 12.minutes
        ),
        ItemTask(
            item = Material.KELP,
            amount = 8,
            displayName = "昆布",
            timeLimit = 12.minutes
        ),
        ItemTask(
            item = Material.FEATHER,
            amount = 4,
            displayName = "羽根",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.LEATHER,
            amount = 3,
            displayName = "革",
            timeLimit = 10.minutes
        ),
        ItemTask(
            item = Material.BONE,
            amount = 3,
            displayName = "骨",
            timeLimit = 12.minutes
        ),
        ItemTask(
            item = Material.STRING,
            amount = 4,
            displayName = "糸",
            timeLimit = 12.minutes
        ),
        ItemTask(
            item = Material.INK_SAC,
            amount = 2,
            displayName = "イカスミ",
            timeLimit = 12.minutes
        ),

        CraftTask(
            item = Material.CRAFTING_TABLE,
            amount = 1,
            displayName = "作業台",
            timeLimit = 5.minutes
        ),
        CraftTask(
            item = Material.CHEST,
            amount = 1,
            displayName = "チェスト",
            timeLimit = 8.minutes
        ),
        CraftTask(
            item = Material.TORCH,
            amount = 8,
            displayName = "松明",
            timeLimit = 8.minutes
        ),
        CraftTask(
            item = Material.LADDER,
            amount = 8,
            displayName = "はしご",
            timeLimit = 8.minutes
        ),
        CraftTask(
            item = Material.BREAD,
            amount = 3,
            displayName = "パン",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.FISHING_ROD,
            amount = 1,
            displayName = "釣り竿",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.FURNACE,
            amount = 1,
            displayName = "かまど",
            timeLimit = 8.minutes
        ),
        CraftTask(
            item = Material.BARREL,
            amount = 1,
            displayName = "樽",
            timeLimit = 8.minutes
        ),
        CraftTask(
            item = Material.CAMPFIRE,
            amount = 1,
            displayName = "焚き火",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.SHIELD,
            amount = 1,
            displayName = "盾",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.BOW,
            amount = 1,
            displayName = "弓",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.PAPER,
            amount = 3,
            displayName = "紙",
            timeLimit = 8.minutes
        ),
        CraftTask(
            item = Material.BOOK,
            amount = 1,
            displayName = "本",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.ITEM_FRAME,
            amount = 1,
            displayName = "額縁",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.PAINTING,
            amount = 1,
            displayName = "絵画",
            timeLimit = 10.minutes
        ),
        CraftTask(
            item = Material.FLOWER_POT,
            amount = 1,
            displayName = "植木鉢",
            timeLimit = 10.minutes
        ),

        BreakBlockTask(
            block = Material.STONE,
            amount = 12,
            displayName = "石",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.DIRT,
            amount = 12,
            displayName = "土",
            timeLimit = 8.minutes
        ),
        BreakBlockTask(
            block = Material.SAND,
            amount = 12,
            displayName = "砂",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.GRAVEL,
            amount = 8,
            displayName = "砂利",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.OAK_LOG,
            amount = 8,
            displayName = "オークの原木",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.COAL_ORE,
            amount = 4,
            displayName = "石炭鉱石",
            timeLimit = 12.minutes
        ),
        BreakBlockTask(
            block = Material.COPPER_ORE,
            amount = 5,
            displayName = "銅鉱石",
            timeLimit = 15.minutes
        ),
        BreakBlockTask(
            block = Material.IRON_ORE,
            amount = 3,
            displayName = "鉄鉱石",
            timeLimit = 15.minutes
        ),
        BreakBlockTask(
            block = Material.GRASS_BLOCK,
            amount = 12,
            displayName = "草ブロック",
            timeLimit = 8.minutes
        ),
        BreakBlockTask(
            block = Material.COBBLESTONE,
            amount = 12,
            displayName = "丸石",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.OAK_LEAVES,
            amount = 12,
            displayName = "オークの葉",
            timeLimit = 8.minutes
        ),
        BreakBlockTask(
            block = Material.CLAY,
            amount = 6,
            displayName = "粘土ブロック",
            timeLimit = 12.minutes
        ),
        BreakBlockTask(
            block = Material.CACTUS,
            amount = 4,
            displayName = "サボテン",
            timeLimit = 12.minutes
        ),
        BreakBlockTask(
            block = Material.SANDSTONE,
            amount = 8,
            displayName = "砂岩",
            timeLimit = 12.minutes
        ),
        BreakBlockTask(
            block = Material.BIRCH_LOG,
            amount = 8,
            displayName = "シラカバの原木",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.SPRUCE_LOG,
            amount = 8,
            displayName = "トウヒの原木",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.ACACIA_LOG,
            amount = 8,
            displayName = "アカシアの原木",
            timeLimit = 10.minutes
        ),
        BreakBlockTask(
            block = Material.BIRCH_LEAVES,
            amount = 12,
            displayName = "シラカバの葉",
            timeLimit = 8.minutes
        ),
        BreakBlockTask(
            block = Material.MOSS_BLOCK,
            amount = 6,
            displayName = "苔ブロック",
            timeLimit = 15.minutes
        ),
        BreakBlockTask(
            block = Material.ICE,
            amount = 8,
            displayName = "氷",
            timeLimit = 20.minutes
        ),

        KillTask(
            entityType = EntityType.COW,
            amount = 1,
            displayName = "牛",
            timeLimit = 10.minutes
        ),
        KillTask(
            entityType = EntityType.SHEEP,
            amount = 1,
            displayName = "羊",
            timeLimit = 10.minutes
        ),
        KillTask(
            entityType = EntityType.CHICKEN,
            amount = 2,
            displayName = "ニワトリ",
            timeLimit = 10.minutes
        ),
        KillTask(
            entityType = EntityType.PIG,
            amount = 1,
            displayName = "ブタ",
            timeLimit = 10.minutes
        ),
        KillTask(
            entityType = EntityType.SPIDER,
            amount = 1,
            displayName = "クモ",
            timeLimit = 12.minutes
        ),
        KillTask(
            entityType = EntityType.ZOMBIE,
            amount = 1,
            displayName = "ゾンビ",
            timeLimit = 12.minutes
        ),
        KillTask(
            entityType = EntityType.SKELETON,
            amount = 1,
            displayName = "スケルトン",
            timeLimit = 12.minutes
        ),
        KillTask(
            entityType = EntityType.RABBIT,
            amount = 1,
            displayName = "ウサギ",
            timeLimit = 12.minutes
        ),
        KillTask(
            entityType = EntityType.SQUID,
            amount = 1,
            displayName = "イカ",
            timeLimit = 12.minutes
        ),
        KillTask(
            entityType = EntityType.COD,
            amount = 1,
            displayName = "タラ",
            timeLimit = 12.minutes
        ),
        KillTask(
            entityType = EntityType.SALMON,
            amount = 1,
            displayName = "サケ",
            timeLimit = 12.minutes
        ),
        KillTask(
            entityType = EntityType.HORSE,
            amount = 1,
            displayName = "ウマ",
            timeLimit = 15.minutes
        ),
        KillTask(
            entityType = EntityType.GOAT,
            amount = 1,
            displayName = "ヤギ",
            timeLimit = 15.minutes
        ),
        KillTask(
            entityType = EntityType.BEE,
            amount = 1,
            displayName = "ミツバチ",
            timeLimit = 15.minutes
        ),
        KillTask(
            entityType = EntityType.DROWNED,
            amount = 1,
            displayName = "ドラウンド",
            timeLimit = 15.minutes
        ),

        UseItemTask(
            item = Material.WOODEN_HOE,
            amount = 2,
            displayName = "クワ",
            useType = UseType.HOE_TILL,
            timeLimit = 12.minutes
        ),
        UseItemTask(
            item = Material.BUCKET,
            amount = 1,
            displayName = "バケツ",
            useType = UseType.BUCKET_FILL,
            timeLimit = 12.minutes
        ),
        UseItemTask(
            item = Material.SHEARS,
            amount = 1,
            displayName = "ハサミ",
            useType = UseType.SHEARS_CUT,
            timeLimit = 12.minutes
        ),
        FishTask(
            amount = 1,
            displayName = "魚",
            timeLimit = 15.minutes
        ),

        LocationTask(
            biome = Biome.PLAINS,
            displayName = "平原バイオーム",
            timeLimit = 12.minutes
        ),
        LocationTask(
            biome = Biome.FOREST,
            displayName = "森林バイオーム",
            timeLimit = 12.minutes
        ),
        LocationTask(
            biome = Biome.DESERT,
            displayName = "砂漠バイオーム",
            timeLimit = 15.minutes
        ),
        LocationTask(
            biome = Biome.SWAMP,
            displayName = "沼地バイオーム",
            timeLimit = 15.minutes
        ),
        LocationTask(
            biome = Biome.TAIGA,
            displayName = "タイガバイオーム",
            timeLimit = 15.minutes
        ),
        LocationTask(
            biome = Biome.BEACH,
            displayName = "砂浜バイオーム",
            timeLimit = 12.minutes
        ),
        LocationTask(
            biome = Biome.RIVER,
            displayName = "川バイオーム",
            timeLimit = 12.minutes
        ),
        LocationTask(
            biome = Biome.JUNGLE,
            displayName = "ジャングルバイオーム",
            timeLimit = 20.minutes
        ),
        LocationTask(
            biome = Biome.SAVANNA,
            displayName = "サバンナバイオーム",
            timeLimit = 15.minutes
        ),
        LocationTask(
            biome = Biome.SNOWY_PLAINS,
            displayName = "雪原バイオーム",
            timeLimit = 20.minutes
        ),
        LocationTask(
            biome = Biome.DARK_FOREST,
            displayName = "ダークオークの森バイオーム",
            timeLimit = 20.minutes
        ),
        LocationTask(
            biome = Biome.MEADOW,
            displayName = "草地バイオーム",
            timeLimit = 20.minutes
        ),
        LocationTask(
            biome = Biome.STONY_SHORE,
            displayName = "石だらけの海岸バイオーム",
            timeLimit = 20.minutes
        ),
        LocationTask(
            biome = Biome.MANGROVE_SWAMP,
            displayName = "マングローブの沼地バイオーム",
            timeLimit = 20.minutes
        ),
        LocationTask(
            biome = Biome.BADLANDS,
            displayName = "荒野バイオーム",
            timeLimit = 20.minutes
        ),
        LocationTask(
            biome = Biome.FROZEN_OCEAN,
            displayName = "凍った海バイオーム",
            timeLimit = 25.minutes
        ),
    )
}