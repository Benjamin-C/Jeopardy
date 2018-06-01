package benjaminc.jeopardy;

public interface PickNumberCallback {
	public abstract void whenDone(int n);
	public abstract void whenCanceled();
}
