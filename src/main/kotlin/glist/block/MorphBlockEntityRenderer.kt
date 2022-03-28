package glist.block

import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.Vec3f
import kotlin.math.sin


class MorphBlockEntityRenderer<T : BlockEntity>(ctx : BlockEntityRendererFactory.Context) : BlockEntityRenderer<T> {
    private val stack : ItemStack = ItemStack(Items.JUKEBOX)

    @Override
    override fun render(
        entity: T,
        tickDelta: Float,
        matrices: MatrixStack?,
        vertexConsumers: VertexConsumerProvider?,
        _light: Int,
        overlay: Int
    ) {
        matrices?.push()
        // Calculate the current offset in the y value
        val offset = sin((entity.world!!.time + tickDelta) / 8.0) / 4.0
        // Move item
        matrices?.translate(0.5, 1.25 + offset, 0.5)
        // Rotate item
        matrices?.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.world!!.time + tickDelta) * 4))

        val light: Int = WorldRenderer.getLightmapCoordinates(entity.world!!, entity.pos.up())
        MinecraftClient.getInstance().itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0)

        // Mandatory call after GL calls
        matrices?.pop()
    }
}