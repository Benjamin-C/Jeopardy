package quickSort;

import java.util.ArrayList;

public class BubbleSort {
	public static ArrayList<Sortable> sort(ArrayList<Sortable> o) {
		return sort(o, true);
	}
	
	public static ArrayList<Sortable> sort(ArrayList<Sortable> o, boolean newList) {
		ArrayList<Sortable> out = new ArrayList<Sortable>();
		if(newList = true) {
			out.addAll(o);
		} else {
			out = o;
		}
		for(int i = 0; i < out.size(); i++) {
			for(int j = 1; j < out.size() - i; j++) {
				if(out.get(j).getIndex() < out.get(j - 1).getIndex()) {
					out.add(j, out.remove(j - 1));
				}
			}
		}
		return out;
	}
}
