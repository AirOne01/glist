package glist.block

import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Morph(settings: Settings?) : Block(settings), BlockEntityProvider {
    init {
        super.settings
    }

    @Override
    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (!world?.isClient()!!) {
            player?.sendMessage(LiteralText("Hello world"), false)
        }

        return ActionResult.SUCCESS
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return MorphBlockEntity(pos, state)
        //val BLOCKENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "glist:morph", FabricBlockEnityTypeBuilder.create(MorphBlockEntity(), MorphBlock).build(null))
    }
}