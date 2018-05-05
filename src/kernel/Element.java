package kernel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import kernel.Looper.LooperNotifier;
import main.World;

public abstract class Element implements LooperNotifier{

	public interface ElementListener {
		public void onUpdate(Element element);
	}
	protected int m_posX;
	protected int m_posY;
	protected Direction m_currentDirection;
	protected Action m_currentAction;
	
	protected final List<Action> m_pendingAction;
	
	protected final List<ElementListener> m_listeners; 
	
	public Element() {
		m_posX = 0;
		m_posY = 0;
		m_currentDirection = Direction.NORTH;
		m_currentAction = Action.NO_ACTION;
		m_pendingAction = Collections.synchronizedList(new LinkedList<>());
		m_listeners = new Vector<>();
	}
	
	public void addListener(ElementListener listener) {
		if (!m_listeners.contains(listener)) {
			m_listeners.add(listener);
			listener.onUpdate(this);
		}
	}
	
	public void removeListener(ElementListener listener) {
		if (m_listeners.contains(listener)) {
			m_listeners.remove(listener);
		}
	}
	
	public void pushAction(Action newAction) {
		m_pendingAction.add(newAction);
		notifyListeners();
	}
	
	public int getX() {
		return m_posX;
	}
	
	public int getY() {
		return m_posY;
	}
	
	public Direction getDirection() {
		return m_currentDirection;
	}
	
	public Action getCurrentAction() {
		return m_currentAction;
	}
	
	public List<Action> getPendingActions() {
		return m_pendingAction;
	}
	
	protected boolean updatePosition() {
		boolean hasBeenUpdate = false;
		
		int rotation = m_currentAction.getRotation();
		hasBeenUpdate = hasBeenUpdate || (rotation != 0);
		m_currentDirection = m_currentDirection.changeDirection(rotation);
		
		int displacement = m_currentAction.getDisplacement();
		hasBeenUpdate = hasBeenUpdate || (displacement != 0);
		m_posX += m_currentDirection.factorX() * displacement;
		m_posY += m_currentDirection.factorY() * displacement;
		
		if (m_posX < 0) {
			m_posX = 0;
		}
		if (m_posX >= World.WIDTH) {
			m_posX = World.WIDTH - 1;
		}
		
		if (m_posY < 0) {
			m_posY = 0;
		}
		if (m_posY >= World.HEIGHT) {
			m_posY = World.HEIGHT - 1;
		}
		System.out.println(m_posX + " " + m_posY + " " + m_currentDirection.toString());
		
		return hasBeenUpdate;
	}
	
	protected void processPendingAction() {
		if (!m_pendingAction.isEmpty()) {
			Action newAction = m_pendingAction.remove(0);
			m_currentAction = newAction;
		}
	}
	
	protected void notifyListeners() {
		for(ElementListener listener : m_listeners) {
			listener.onUpdate(this);
		}
	}
	
	public String print() {
		return getCurrentAction().name() + " : (" + getX() + "," + getY() + ") - " + getDirection();
	}
	
	@Override
	public void onTic() {
		boolean needToNotifiy = updatePosition();
		
		processPendingAction();
		
		if (needToNotifiy) {
			notifyListeners();			
		}
	}
}
