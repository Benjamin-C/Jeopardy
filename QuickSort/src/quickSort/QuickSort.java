package quickSort;

import java.util.ArrayList;
import java.util.Random;

public class QuickSort {
	public static void main(String args[]) {
		ArrayList<Sortable> list = new ArrayList<Sortable>();
		Random r = new Random();
		int width = 16;
		
		for(int i = 0; i < 32; i++) {
			int n = r.nextInt(89) + 10;
			list.add(new Sortable(n, n));
		}
		
		System.out.print("<-- Begin List -->" + list.size());
		for(int i = 0; i < list.size(); i++) {
			if(i % width == 0) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
			System.out.print(list.get(i));
		}
		System.out.println();
		System.out.println("<-- End List -->");
		
		list = sort(list);
		
		System.out.print("<-- Begin List -->" + list.size());
		for(int i = 0; i < list.size(); i++) {
			if(i % width == 0) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
			System.out.print(list.get(i));
		}
		System.out.println();
		System.out.println("<-- End List -->");
	}

	public static ArrayList<Sortable> toSortable(ArrayList<Integer> o) {
		ArrayList<Sortable> out = new ArrayList<Sortable>();
		for(int i = 0; i < o.size(); i++) {
			out.add(new Sortable(o.get(i), o.get(i)));
		}
		return out;
	}
	public static ArrayList<Integer> toInteger(ArrayList<Sortable> o) {
		ArrayList<Integer> out = new ArrayList<Integer>();
		for(int i = 0; i < o.size(); i++) {
			out.add((Integer) o.get(i).getObject());
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Sortable> sort(ArrayList<Sortable> o) {
		ArrayList<Sortable> l = new ArrayList<Sortable>();
		ArrayList<Sortable> h = new ArrayList<Sortable>();
		ArrayList<Sortable> out = new ArrayList<Sortable>();
		
		if(o.size() > 2) {
			int pivot = o.size() / 2;
			for(int i = 0; i < o.size(); i++) {
				if(i != pivot) {
					if(o.get(i).getIndex() < o.get(pivot).getIndex()) {
						l.add(o.get(i));
					} else {
						h.add(o.get(i));
					}
				}
			}

			if(l.size() > 1) {
				l = sort(l);
			}
			if(h.size() > 1) {
				h = sort(h);
			}
			
			out = (ArrayList<Sortable>) l.clone();
			out.add(o.get(pivot));
			out.addAll(h);
			
		} else {
			if(o.size() == 2) {
				int a = Math.min(o.get(0).getIndex(), o.get(1).getIndex());
				int b = Math.max(o.get(0).getIndex(), o.get(1).getIndex());
				out.add(new Sortable(a, a));
				out.add(new Sortable(b, b));
			} else {
				out = (ArrayList<Sortable>) o.clone();
			}
			/*
			if(o.size() == 2) {
				if(o.get(0).getIndex() > o.get(1).getIndex()) {
					out.add(o.get(1));
					out.add(o.get(0));
				} else {
					if(o.get(0).getIndex() > o.get(1).getIndex()) {
						out.add(o.get(1));
						out.add(o.get(0));
					}
				}
			} else {
				out = (ArrayList<Sortable>) o.clone();
			}*/
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> sortp(ArrayList<Integer> o) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		ArrayList<Integer> h = new ArrayList<Integer>();
		ArrayList<Integer> out = new ArrayList<Integer>();
		
		if(o.size() > 2) {
			int pivot = o.size() / 2;
			System.out.println("piv: " + o.get(pivot));
			for(int i = 0; i < o.size(); i++) {
				if(i != pivot) {
					if(o.get(i) < o.get(pivot)) {
						l.add(o.get(i));
					} else {
						h.add(o.get(i));
					}
				}
			}
			
			System.out.println("low: " + l);
			System.out.println("hig: " + h);
			if(l.size() > 1) {
				l = sortp(l);
			}
			if(h.size() > 1) {
				h = sortp(h);
			}
			
			System.out.println("lst: " + l);
			System.out.println("hst: " + h);
			out = (ArrayList<Integer>) l.clone();
			out.add(o.get(pivot));
			out.addAll(h);
			
		} else {
			if(o.size() == 2) {
				out.add(Math.min(o.get(0), o.get(1)));
				out.add(Math.max(o.get(0), o.get(1)));
			} else {
				out = (ArrayList<Integer>) o.clone();
			}
		}
		System.out.println("ret: " + out);
		return out;
	}
}
