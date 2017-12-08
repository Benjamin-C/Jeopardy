package quickSort;

public class Sortable {
	private Object o;
	private int i;
	
	public Sortable(Object object, int index) {
		super();
		o = object;
		i = index;
	}
	
	public Object getObject() {
		return o;
	}
	public void setObject(Object object) {
		o = object;
	}
	public int getIndex() {
		return i;
	}
	public void setIndex(int index) {
		i = index;
	}
	
	@Override
	public String toString() {
		return o.toString();
	}
}
