package ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import kernel.Element;
import kernel.Element.ElementListener;
import main.World;

public class DisplayedElement implements ElementListener {

	private final Rectangle m_rect;
	private final Line m_direction;

	public DisplayedElement(Pane group) {
		m_rect = new Rectangle();
		m_direction = new Line();
		group.getChildren().add(m_rect);
		group.getChildren().add(m_direction);
	}
	
	@Override
	public void onUpdate(Element element) {
		int x = element.getX() * World.CELL_SIZE;
		int y = element.getY() * World.CELL_SIZE;
		m_rect.setX(x+1);
		m_rect.setY(y+1);
		m_rect.setWidth(World.CELL_SIZE-2);
		m_rect.setHeight(World.CELL_SIZE-2);
		m_rect.setStroke(Color.RED);
		m_rect.setFill(null);
		
		m_direction.setStroke(Color.RED);
		m_direction.setStartX(x + World.CELL_SIZE/2);
		m_direction.setStartY(y + World.CELL_SIZE/2);
		
		switch(element.getDirection()) {
		case EAST:
			m_direction.setEndX(x + World.CELL_SIZE-1);
			m_direction.setEndY(y + World.CELL_SIZE/2);
			break;
		case NORTH:
			m_direction.setEndX(x + World.CELL_SIZE/2);
			m_direction.setEndY(y + World.CELL_SIZE-1);
			break;
		case WEST:
			m_direction.setEndX(x + 1);
			m_direction.setEndY(y + World.CELL_SIZE/2);
			break;
		case SOUTH:
			m_direction.setEndX(x + World.CELL_SIZE/2);
			m_direction.setEndY(y + 1);
			break;

		}
	}
}
