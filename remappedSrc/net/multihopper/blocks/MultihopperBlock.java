package net.multihopper.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.multihopper.entities.MultihopperBlockEntity;
import net.multihopper.initializers.Entities;
import net.multihopper.loaders.MultihopperLoader;
import org.jetbrains.annotations.Nullable;

public class MultihopperBlock extends BlockWithEntity {
    public static final Identifier ID = new Identifier(MultihopperLoader.MOD_ID, "multihopper");

    public static final DirectionProperty FACING;
    public static final BooleanProperty ENABLED;

    private static final VoxelShape TOP_SHAPE;
    private static final VoxelShape MIDDLE_SHAPE;
    private static final VoxelShape OUTSIDE_SHAPE;
    private static final VoxelShape DEFAULT_SHAPE;
    private static final VoxelShape DOWN_SHAPE;
    private static final VoxelShape INSIDE_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape DOWN_RAY_TRACE_SHAPE;
    private static final VoxelShape EAST_RAY_TRACE_SHAPE;
    private static final VoxelShape NORTH_RAY_TRACE_SHAPE;
    private static final VoxelShape SOUTH_RAY_TRACE_SHAPE;
    private static final VoxelShape WEST_RAY_TRACE_SHAPE;

    static {
        FACING = FacingBlock.FACING;
        ENABLED = BooleanProperty.of("enabled");
        TOP_SHAPE = Block.createCuboidShape(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        MIDDLE_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
        OUTSIDE_SHAPE = VoxelShapes.union(MIDDLE_SHAPE, TOP_SHAPE);
        INSIDE_SHAPE = createCuboidShape(2.0, 11.0, 2.0, 14.0, 16.0, 14.0);
        DEFAULT_SHAPE = VoxelShapes.combineAndSimplify(OUTSIDE_SHAPE, INSIDE_SHAPE, BooleanBiFunction.ONLY_FIRST);
        DOWN_SHAPE = VoxelShapes.union(DEFAULT_SHAPE, Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D));
        EAST_SHAPE = VoxelShapes.union(DEFAULT_SHAPE, Block.createCuboidShape(12.0D, 4.0D, 6.0D, 16.0D, 8.0D, 10.0D));
        NORTH_SHAPE = VoxelShapes.union(DEFAULT_SHAPE, Block.createCuboidShape(6.0D, 4.0D, 0.0D, 10.0D, 8.0D, 4.0D));
        SOUTH_SHAPE = VoxelShapes.union(DEFAULT_SHAPE, Block.createCuboidShape(6.0D, 4.0D, 12.0D, 10.0D, 8.0D, 16.0D));
        WEST_SHAPE = VoxelShapes.union(DEFAULT_SHAPE, Block.createCuboidShape(0.0D, 4.0D, 6.0D, 4.0D, 8.0D, 10.0D));
        DOWN_RAY_TRACE_SHAPE = INSIDE_SHAPE;
        EAST_RAY_TRACE_SHAPE = VoxelShapes.union(INSIDE_SHAPE, Block.createCuboidShape(12.0D, 8.0D, 6.0D, 16.0D, 10.0D, 10.0D));
        NORTH_RAY_TRACE_SHAPE = VoxelShapes.union(INSIDE_SHAPE, Block.createCuboidShape(6.0D, 8.0D, 0.0D, 10.0D, 10.0D, 4.0D));
        SOUTH_RAY_TRACE_SHAPE = VoxelShapes.union(INSIDE_SHAPE, Block.createCuboidShape(6.0D, 8.0D, 12.0D, 10.0D, 10.0D, 16.0D));
        WEST_RAY_TRACE_SHAPE = VoxelShapes.union(INSIDE_SHAPE, Block.createCuboidShape(0.0D, 8.0D, 6.0D, 4.0D, 10.0D, 10.0D));
    }

    public MultihopperBlock() {
        super(FabricBlockSettings.create().sounds(BlockSoundGroup.METAL).strength(1F, 1F).nonOpaque());
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MultihopperBlockEntity) {
            MultihopperBlockEntity.onEntityCollided(world, pos, state, entity, (MultihopperBlockEntity) blockEntity);
        }
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            this.updateEnabled(world, pos, state);
        }
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        this.updateEnabled(world, pos, state);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos);
    }

    private void updateEnabled(World world, BlockPos pos, BlockState state) {
        boolean bl = !world.isReceivingRedstonePower(pos);
        if (bl != state.get(ENABLED)) {
            world.setBlockState(pos, state.with(ENABLED, bl), 4);
        }
    }

    @Override
    protected VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return switch (state.get(FACING)) {
            case DOWN -> DOWN_RAY_TRACE_SHAPE;
            case NORTH -> NORTH_RAY_TRACE_SHAPE;
            case SOUTH -> SOUTH_RAY_TRACE_SHAPE;
            case WEST -> WEST_RAY_TRACE_SHAPE;
            case EAST -> EAST_RAY_TRACE_SHAPE;
            default -> INSIDE_SHAPE;
        };
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(ENABLED);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case DOWN -> DOWN_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> DEFAULT_SHAPE;
        };
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ItemScatterer.onStateReplaced(state, newState, world, pos);
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            return validateTicker(type, Entities.MULTIHOPPER_BLOCK_ENTITY, MultihopperBlockEntity::serverTick);
        }
        return super.getTicker(world, state, type);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide().getOpposite();
        return this.getDefaultState().with(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction).with(ENABLED, true);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MultihopperBlockEntity(pos, state);
    }
}
