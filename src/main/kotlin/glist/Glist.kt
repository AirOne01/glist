package glist

import glist.block.Morph
import glist.block.MorphBlockEntity
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


class Glist : ModInitializer {
    private val MORPH_BLOCK: Block = Morph(FabricBlockSettings.of(Material.STONE))

    companion object {
        lateinit var MORPH_BLOCK_ENTITY: BlockEntityType<MorphBlockEntity>
    }

    @Override
    override fun onInitialize() {
        //ModelLoadingRegistry.INSTANCE.registerResourceProvider { ModelProvider() }
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(ResourceReloadListener())

        MORPH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier("glist", "morph"), FabricBlockEntityTypeBuilder.create(::MorphBlockEntity, MORPH_BLOCK).build())
        Registry.register(
            Registry.ITEM,
            Identifier("tutorial", "example_block"),
            BlockItem(MORPH_BLOCK, FabricItemSettings().group(ItemGroup.MISC))
        )
    }
}