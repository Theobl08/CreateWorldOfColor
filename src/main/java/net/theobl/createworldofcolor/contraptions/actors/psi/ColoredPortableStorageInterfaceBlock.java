//package net.theobl.createworldofcolor.contraptions.actors.psi;
//
//import com.simibubi.create.AllShapes;
//import com.simibubi.create.content.contraptions.actors.psi.PortableStorageInterfaceBlockEntity;
//import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
//import com.simibubi.create.foundation.block.IBE;
//import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
//import net.minecraft.MethodsReturnNonnullByDefault;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.context.BlockPlaceContext;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraft.world.phys.shapes.VoxelShape;
//
//import javax.annotation.Nullable;
//import javax.annotation.ParametersAreNonnullByDefault;
//
//@ParametersAreNonnullByDefault
//@MethodsReturnNonnullByDefault
//public class ColoredPortableStorageInterfaceBlock extends WrenchableDirectionalBlock implements IBE<ColoredPortableStorageInterfaceBlockEntity> {
//    boolean fluids;
//    protected final DyeColor color;
//
//    public static ColoredPortableStorageInterfaceBlock forItems(Properties properties, DyeColor color) {
//        return new ColoredPortableStorageInterfaceBlock(properties, color, false);
//    }
//
//    public static ColoredPortableStorageInterfaceBlock forFluids(Properties properties, DyeColor color) {
//        return new ColoredPortableStorageInterfaceBlock(properties, color, true);
//    }
//
//    private ColoredPortableStorageInterfaceBlock(Properties properties, DyeColor color, boolean fluids) {
//        super(properties);
//        this.fluids = fluids;
//        this.color = color;
//    }
//
//    @Override
//    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos neighborPos,
//                                boolean movedByPiston) {
//        withBlockEntityDo(world, pos, PortableStorageInterfaceBlockEntity::neighbourChanged);
//    }
//
//    @Override
//    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
//        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
//        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
//    }
//
//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        Direction direction = context.getNearestLookingDirection();
//        if (context.getPlayer() != null && context.getPlayer()
//                .isShiftKeyDown())
//            direction = direction.getOpposite();
//        return defaultBlockState().setValue(FACING, direction.getOpposite());
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
//        return AllShapes.PORTABLE_STORAGE_INTERFACE.get(state.getValue(FACING));
//    }
//
//    @Override
//    public boolean hasAnalogOutputSignal(BlockState state) {
//        return true;
//    }
//
//    @Override
//    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
//        return getBlockEntityOptional(worldIn, pos).map(be -> be.isConnected() ? 15 : 0)
//                .orElse(0);
//    }
//
//    @Override
//    public Class<ColoredPortableStorageInterfaceBlockEntity> getBlockEntityClass() {
//        return ColoredPortableStorageInterfaceBlockEntity.class;
//    }
//
//    @Override
//    public BlockEntityType<? extends ColoredPortableStorageInterfaceBlockEntity> getBlockEntityType() {
////        return fluids ? ModBlockEntityTypes.PORTABLE_FLUID_INTERFACES.get(color).get()
////                : AllBlockEntityTypes.PORTABLE_STORAGE_INTERFACE.get();
////        return ModBlockEntityTypes.PORTABLE_FLUID_INTERFACES.get(color).get();
//        return null;
//    }
//}
