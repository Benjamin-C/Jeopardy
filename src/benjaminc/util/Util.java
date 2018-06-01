package benjaminc.util;

import java.util.Set;

public class Util {

	public static void pause(Object sync) {
        synchronized(sync) {
            try {
                sync.wait();
            } catch (InterruptedException e) {
                // Happens if someone interrupts your thread.
            }
        }
    }

    public static void resume(Object sync) {
        synchronized(sync) {
            sync.notify();
        }
    }
    
    public static void showThreads() {
    	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		for(int i = 0; i < threadArray.length; i++) {
			System.out.println("[" + i + "]: " + threadArray[i]);
		}
    }
}
