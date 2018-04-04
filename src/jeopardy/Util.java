package jeopardy;

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
}
