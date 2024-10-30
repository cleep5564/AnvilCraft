package dev.dubhe.anvilcraft.block;

import dev.dubhe.anvilcraft.item.AnvilHammerItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HeavyIronTrapdoorBlock extends TrapDoorBlock {
    public HeavyIronTrapdoorBlock(Properties properties) {
        super(properties, BlockSetType.IRON);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState bs = super.getStateForPlacement(context);
        if (bs == null) return null;
        boolean hasSignal = context.getLevel().getBestNeighborSignal(context.getClickedPos()) >= 15;
        return bs.setValue(POWERED, hasSignal).setValue(OPEN, hasSignal);
    }

    @Override
    public InteractionResult use(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hit
    ) {
        if (player.getItemInHand(hand).getItem() instanceof AnvilHammerItem) {
            state = state.cycle(OPEN);
            level.setBlock(pos, state, 2);
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            this.playSound(player, level, pos, state.getValue(OPEN));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void neighborChanged(
        BlockState state,
        Level level,
        BlockPos pos,
        Block block,
        BlockPos fromPos,
        boolean isMoving
    ) {
        boolean flag = level.getBestNeighborSignal(pos) >= 15;
        if (flag != state.getValue(POWERED)) {
            if (state.getValue(OPEN) != flag) {
                state = state.setValue(OPEN, flag);
                this.playSound(null, level, pos, flag);
            }

            level.setBlock(pos, state.setValue(POWERED, flag), 2);
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }
    }
}
