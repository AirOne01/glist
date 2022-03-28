package glist

import glist.block.MorphBlockEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry

class GlistClient : ClientModInitializer {
    @Override
    override fun onInitializeClient() {
        // here goes the client-only registration stuff
        BlockEntityRendererRegistry.register(Glist.MORPH_BLOCK_ENTITY, ::MorphBlockEntityRenderer)
    }
}