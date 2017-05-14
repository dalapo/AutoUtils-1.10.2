package dalapo.autoutils.reference;

import net.minecraft.util.EnumFacing;

public class UsefulLists {
	private UsefulLists() {}
	
	public static final EnumFacing[] directions = {EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
	public static final EnumFacing[] directionsMinusUp = {EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
}