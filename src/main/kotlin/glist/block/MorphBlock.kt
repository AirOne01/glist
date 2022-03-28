package glist.block

import glist.Glist
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World


class MorphBlock(settings: Settings?) : BlockWithEntity(settings), BlockEntityProvider {
    @Override
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity {
        return MorphBlockEntity(pos, state)
    }

    @Override
    override fun getRenderType(state: BlockState?): BlockRenderType {
        return BlockRenderType.MODEL
    }

    @Override
    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? {
        return checkType(type, Glist.MORPH_BLOCK_ENTITY) { world1, pos, state1, be ->
            MorphBlockEntity.tick(
                world1,
                pos,
                state1,
                be
            )
        }
    }
}