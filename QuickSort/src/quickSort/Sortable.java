package quickSort;

public class Sortable {
	private Object o;
	private double i;
	
	public Sortable(Object object, double index) {
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
	public double getIndex() {
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
