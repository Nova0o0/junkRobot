package kernel;

import java.util.List;
import java.util.Vector;

public class Looper implements Runnable{
	
	public interface LooperNotifier {
		public void onTic();
	}

	private final int m_emittingTime;
	private final List<LooperNotifier> m_notifiers;
	private boolean m_processing = true;

	public Looper(int emittingTime) {
		m_emittingTime = emittingTime;
		m_notifiers = new Vector<>();
	}
	
	public void addLooperNotifier(LooperNotifier notifier) {
		if (!m_notifiers.contains(notifier)) {
			m_notifiers.add(notifier);
		}
	}
	
	public void launchLooper() {
		Thread thread = new Thread(this);
		
		m_processing = true;
		thread.start();
	}
	
	public void stopLooper() {
		m_processing = false;
	}
	
	@Override
	public void run() {
		while (m_processing) {
			try {
				Thread.sleep(m_emittingTime);
			} catch(Exception ee) {
				// TODO
			}
			
			for (LooperNotifier notifier : m_notifiers) {
				notifier.onTic();
			}
		}
	}
}
