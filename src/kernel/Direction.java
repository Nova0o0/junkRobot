package kernel;

public enum Direction {
	NORTH(0, 1),
	EAST( 1, 0),
	SOUTH(0, -1),
	WEST(-1,0),
	;
	
	private int m_factorX, m_factorY;
	
	private Direction(int factorX, int factorY) {
		m_factorX = factorX;
		m_factorY = factorY;
	}
	public static Direction getDirection(int value) {
		int modValue = value % 4;
		if (modValue < 0)
			modValue += 4;
		
		return values()[modValue];
	}
	
	public int getDirectionValue() {
		 return ordinal();
	}
	
	public Direction changeDirection(int rotation) {
		return getDirection(getDirectionValue() + rotation);
	}
	
	public int factorX() {
		return m_factorX;
	}
	
	public int factorY() {
		return m_factorY;
	}
}
