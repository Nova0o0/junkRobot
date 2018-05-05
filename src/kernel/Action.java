package kernel;

import java.util.Random;

import javafx.scene.control.Button;
import main.World;

public enum Action {
	NO_ACTION,
	STAND_STILL,
	MOVE_FORWARD_SLOW(1, 0),
	MOVE_FORWARD(2, 0),
	MOVE_FORWARD_FAST(3, 0),
	MOVE_FORWARD_VERY_FAST (4, 0),
	MOVE_BACKWARD (-1, 0),
	MOVE_BACKWARD_FAST (-2, 0),
	TURN_LEFT (0, 1),
	TURN_RIGHT (0, -1),
	TURN_BACK (0, 2),
	;
	
	private int m_displacement;
	private int m_rotation;
	
	private static Random m_random = new Random(System.currentTimeMillis());
	
	private Action ( ) {
		this(0, 0);
	}
	
	private Action(int displacement, int rotation) {
		m_displacement = displacement;
		m_rotation = rotation;
	}
	
	public int getDisplacement() {
		return m_displacement;
	}
	
	public int getRotation() {
		return m_rotation;
	}
	
	public static Action getNewRandomAction() {
		
		int newActionOrdinal = m_random.nextInt(values().length-1) + 1;
		
		return values() [newActionOrdinal];
	}
	
	public static int getLifetime() {
		return m_random.nextInt(5) + 2;
	}
	
	public Button createButton(final World world) {
        Button button = new Button(this.name());
        button.setOnAction(e -> {
        	world.setActionForPlayer(this);
        });
        
        return button;
        
	}
}
