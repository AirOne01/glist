package glist.model

import com.mojang.datafixers.util.Pair
import net.fabricmc.fabric.api.renderer.v1.Renderer
import net.fabricmc.fabric.api.renderer.v1.RendererAccess
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.render.model.*
import net.minecraft.client.render.model.json.ModelOverrideList
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockRenderView
import java.util.*
import java.util.function.Function
import java.util.function.Supplier


class MorphModel : UnbakedModel, BakedModel, FabricBakedModel {
    private val spriteIDs: MutableCollection<SpriteIdentifier> = mutableListOf(
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/furnace_front_on")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/furnace_top")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/sponge")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/acacia_planks")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/dirt")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/iron_block")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/diamond_block")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/gold_block")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/redstone_block")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/glass")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/white_wool")),
        SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier("minecraft:block/oak_planks")),
    )
    private val sprites = arrayOfNulls<Sprite>(2)

    private var mesh: Mesh? = null

    @Override
    /**
     * Tell the game which textures are needed for this model.
     *
     * @see net.minecraft.client.render.model.UnbakedModel.getModelDependencies
     * @return A collection of textures identifiers that are needed for this model.
     */
    override fun getModelDependencies(): MutableCollection<Identifier> {
        return Collections.emptyList()
    }

    @Override
    override fun getTextureDependencies(
        unbakedModelGetter: Function<Identifier?, UnbakedModel?>?,
        unresolvedTextureReferences: Set<Pair<String?, String?>?>?
    ): MutableCollection<SpriteIdentifier> {
        return spriteIDs
    }

    @Override
    override fun bake(
        loader: ModelLoader?,
        textureGetter: Function<SpriteIdentifier, Sprite>?,
        rotationContainer: ModelBakeSettings?,
        modelId: Identifier?
    ): BakedModel {
        // Get the sprites
        sprites.forEachIndexed { index, _ ->
            sprites[index] = textureGetter?.apply(spriteIDs.random())
        }
        // Build the mesh using the rendered API
        val renderer: Renderer? = RendererAccess.INSTANCE.renderer
        val builder: MeshBuilder? = renderer?.meshBuilder()
        val emitter: QuadEmitter? = builder?.emitter

        Direction.values().forEach { direction: Direction ->
            val spriteIdx: Int = if (direction == Direction.UP || direction == Direction.DOWN) 1 else 0
            // Add a new face to the mesh
            emitter?.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f)
            // Set the sprite of the face, must be called after .square()
            // We haven't specified any UV coordinates, so we want to use the whole texture. BAKE_LOCK_UV does exactly that.
            emitter?.spriteBake(0, sprites[spriteIdx], MutableQuadView.BAKE_LOCK_UV)
            // enable texture usage
            emitter?.spriteColor(0, -1, -1, -1, -1)
            // add the quad to the mesh
            emitter?.emit()
        }
        this.mesh = builder?.build()

        return this
    }

    @Override
    override fun getQuads(state: BlockState?, face: Direction?, random: Random?): MutableList<BakedQuad> {
        // best not to return null if a mod needs this
        return Collections.emptyList()
    }

    @Override
    override fun useAmbientOcclusion(): Boolean {
        return true
    }

    @Override
    override fun hasDepth(): Boolean {
        return false
    }

    @Override
    override fun isSideLit(): Boolean {
        return true
    }

    @Override
    override fun isBuiltin(): Boolean {
        return false
    }

    @Override
    override fun getParticleSprite(): Sprite? {
        // break particles
        return sprites[1]
    }

    @Override
    override fun getTransformation(): ModelTransformation? {
        return null
    }

    @Override
    override fun getOverrides(): ModelOverrideList? {
        return null
    }

    @Override
    override fun isVanillaAdapter(): Boolean {
        return false
    }

    @Override
    override fun emitBlockQuads(
        blockView: BlockRenderView?,
        state: BlockState?,
        pos: BlockPos?,
        randomSupplier: Supplier<Random>?,
        context: RenderContext?
    ) {
        // Render function
        // We just render the mesh
        context?.meshConsumer()?.accept(mesh)
    }

    @Override
    override fun emitItemQuads(stack: ItemStack?, randomSupplier: Supplier<Random>?, context: RenderContext?) {
    }
}