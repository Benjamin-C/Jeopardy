package quickSort;

import java.util.ArrayList;
import java.util.Random;

public class QuickSort {
	public static void main(String args[]) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		Random r = new Random();
		int width = 16;
		
		for(int i = 0; i < 32; i++) {
			l.add(r.nextInt(89) + 10);
		}
		
		l = sort(l);
		
		System.out.print("<-- Begin List -->");
		for(int i = 0; i < l.size(); i++) {
			if(i % width == 0) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
			System.out.print(l.get(i));
		}
		System.out.println();
		System.out.println("<-- End List -->");
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> sort(ArrayList<Integer> o) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		ArrayList<Integer> h = new ArrayList<Integer>();
		ArrayList<Integer> out = new ArrayList<Integer>();
		
		if(o.size() > 1) {
			int pivot = o.size() / 2;
			System.out.println(o.get(pivot));
			for(int i = 0; i < o.size(); i++) {
				if(o.get(i) < o.get(pivot)) {
					l.add(o.get(i));
				} else {
					h.add(o.get(i));
				}
			}
			sortp(l);
			sortp(h);
			//h = sort(h);
		}
		
		out = l;
		out.addAll(h);
		
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> sortp(ArrayList<Integer> o) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		ArrayList<Integer> h = new ArrayList<Integer>();
		ArrayList<Integer> out = (ArrayList<Integer>) o.clone();
		
		if(o.size() > 1) {
			int pivot = o.size() / 2;
			System.out.println("p" + o.get(pivot));
			for(int i = 0; i < o.size(); i++) {
				if(o.get(i) < o.get(pivot)) {
					l.add(o.get(i));
				} else {
					h.add(o.get(i));
				}
			}
			System.out.println(l);
			System.out.println(h);
			if(l.size() > 1) {
				l = sortp(l);
			}
			if(h.size() > 1) {
				h = sortp(h);
			}
			
			out = (ArrayList<Integer>) l.clone();
			out.addAll(h);
		}

		return out;
	}
}
