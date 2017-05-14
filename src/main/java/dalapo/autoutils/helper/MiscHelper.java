package dalapo.autoutils.helper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class MiscHelper {
	private MiscHelper() {}
	
	public static boolean isInRange(int x, int min, int max)
	{
		return x >= min && x < max;
	}
	
	public static int sum(int[] arr)
	{
		int acc = 0;
		for (int i=0; i<arr.length; i++)
		{
			acc += arr[i];
		}
		return acc;
	}
	
	public static BlockPos getAdjacent(BlockPos block, EnumFacing side)
	{
		switch (side)
		{
		case UP:
			return block.up();
		case DOWN:
			return block.down();
		case NORTH:
			return block.north();
		case SOUTH:
			return block.south();
		case WEST:
			return block.west();
		case EAST:
			return block.east();
		}
		return block;
	}
	
	public static EnumFacing getDirectionFromEntity(BlockPos clicked, EntityLivingBase elb)
	{
		return EnumFacing.getFacingFromVector(
				(float)(elb.posX - clicked.getX()),
				(float)(elb.posY - clicked.getY()),
				(float)(elb.posZ - clicked.getZ()));
	}
}