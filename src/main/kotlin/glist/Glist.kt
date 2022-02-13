package glist

import glist.block.Morph
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


class Glist : ModInitializer {
    private val blockMorph: Block = Morph(FabricBlockSettings.of(Material.STONE))

    @Override
    override fun onInitialize() {
        //ModelLoadingRegistry.INSTANCE.registerResourceProvider { ModelProvider() }

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(ResourceReloadListener())

        Registry.register(Registry.BLOCK, Identifier("glist", "morph"), blockMorph)
        Registry.register(
            Registry.ITEM,
            Identifier("tutorial", "example_block"),
            BlockItem(blockMorph, FabricItemSettings().group(ItemGroup.MISC))
        )
    }
}