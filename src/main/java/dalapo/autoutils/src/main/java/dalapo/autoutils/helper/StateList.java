package dalapo.autoutils.helper;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public final class StateList {
	private StateList() {}
	
	public static final PropertyEnum<EnumFacing> DIRECTIONS = PropertyEnum.create("facing", EnumFacing.class);
}