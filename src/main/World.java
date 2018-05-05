package main;

import java.util.List;
import java.util.Vector;

import kernel.Action;
import kernel.ActionManager;
import kernel.Element;
import kernel.Looper;
import kernel.Player;

public class World {

	private final List<Element> m_elements;
	private final Player m_player;
	
	private final static int DEFAULT_lOOPER_EVENT = 1000;
	private final Looper m_looper;

	private final static int DEFAULT_NUMBER_OF_ACTIONS = 4;

	private final ActionManager m_actionManager;
	
	public final static int CELL_SIZE = 50;
	public final static int WIDTH = 20;
	public final static int HEIGHT = 15;
	
	public World() {
		m_player = new Player();
		m_elements = new Vector<>();
		m_elements.add(m_player);
		m_looper = new Looper(DEFAULT_lOOPER_EVENT);
		m_actionManager = new ActionManager(DEFAULT_NUMBER_OF_ACTIONS);
	}
	
	public List<Element> getElements() {
		return m_elements;
	}
	
	public Player getPlayer() {
		return m_player;
	}
	
	public void startGame() {
		m_actionManager.populate();
		m_looper.addLooperNotifier(m_actionManager);
		
		m_looper.addLooperNotifier(m_player);
		for(Element element : m_elements) {
			m_looper.addLooperNotifier(element);
		}
		
		m_looper.launchLooper();
	}
	
	public void stopGame() {
		m_looper.stopLooper();
	}
	
	public void setActionForPlayer(Action newAction) {
		m_player.pushAction(newAction);
	}
}
