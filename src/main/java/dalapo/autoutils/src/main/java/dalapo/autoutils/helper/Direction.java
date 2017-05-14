package dalapo.autoutils.helper;

public enum Direction {
	
	DOWN(0, -1, 0),
	UP(0, 1, 0),
	NORTH(0, 0, -1),
	SOUTH(0, 0, 1),
	WEST(1, 0, 0),
	EAST(-1, 0, 0);
	
	Direction(int xOff, int yOff, int zOff)
	{
		xOffset = xOff;
		yOffset = yOff;
		zOffset = zOff;
	}
	
	int xOffset;
	int yOffset;
	int zOffset;
	
	public int[] OPPOSITES = new int[] {1, 0, 3, 2, 5, 4};
}