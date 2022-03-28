package glist.block

import glist.Glist
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MorphBlockEntity(pos: BlockPos?, state: BlockState?) : BlockEntity(Glist.MORPH_BLOCK_ENTITY, pos, state) {
    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, be: MorphBlockEntity) {}
    }
}