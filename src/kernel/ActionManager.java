package kernel;

import java.util.ArrayList;
import java.util.List;

import kernel.Looper.LooperNotifier;

public class ActionManager implements LooperNotifier{
	
	private int m_nbActions;	
	private final List<Action> m_currentActions;
	private final List<Integer> m_actionLifetimes;
	
	public ActionManager(int nbActions) {
		m_currentActions = new ArrayList<>();
		m_actionLifetimes = new ArrayList<>();
		m_nbActions = nbActions;
	}
	
	public void populate() {
		for(int i=0 ; i<m_nbActions ; i++) {
			m_currentActions.add(Action.getNewRandomAction());
			m_actionLifetimes.add(Action.getLifetime());
		}
	}
	
	public Action useAction(int actionIndex) {
		if (actionIndex < 0 || actionIndex >= m_nbActions)
			return null;
		
		Action action = m_currentActions.get(actionIndex);		
		replaceAction(actionIndex);
		
		return action;
	}
	
	protected void replaceAction(int actionIndex) {
		m_currentActions.set(actionIndex, Action.getNewRandomAction());
		m_actionLifetimes.set(actionIndex, Action.getLifetime());
	}
	
	@Override
	public void onTic() {
		for(int i=0 ; i<m_nbActions ; i++) {
			int lifetime = m_actionLifetimes.get(i) - 1;
			if (lifetime <= 0) {
				replaceAction(i);
			} else {				
				m_actionLifetimes.set(i, lifetime);
			}
		}
	}
}
