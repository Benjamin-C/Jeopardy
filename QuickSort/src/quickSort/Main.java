package quickSort;

import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main(String args[]) {
		ArrayList<Sortable> list = new ArrayList<Sortable>();
		Random r = new Random();
		int width = 16;
		long s = 0;
		long e = 0;
		long t = 0;
		long quickTime = 0;
		int count = Calib.calibrate(32);
		for(int i = 0; i < count; i++) {
			int n = r.nextInt(89) + 10;
			list.add(new Sortable(n, n));
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Sortable> orig = (ArrayList<Sortable>) list.clone();
		
		printList(list, width);
		s = System.nanoTime();
		list = QuickSort.sort(orig);
		e = System.nanoTime();
		quickTime = e - s;
		printList(list, width);
		System.out.println("Sorted in " + niceNum(quickTime) + "ns using QuickSort");
		s = System.nanoTime();
		list = BubbleSort.sort(orig);
		e = System.nanoTime();
		t = e - s;
		printList(list, width);
		System.out.println("Sorted in " + niceNum(t) + "ns using BubbleSort");
		System.out.println("QuickSort:\t"  + niceNum(quickTime) + "ns\nBubbleSort:\t" + niceNum(t) + "ns");
		System.out.println("QuickSort is\t" + niceNum(t -quickTime) + "ns faster");
	}
	
	@SuppressWarnings("rawtypes")
	public static void printList(ArrayList list, int width) {
		System.out.print("<-- Begin List --> Size: " + list.size());
		for(int i = 0; i < list.size(); i++) {
			if(i % width == 0) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
			System.out.print(list.get(i).toString());
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
	
	public static String niceNum(long num) {
		boolean foundLegnth = false;
		long sizeMult = 1000;
		long mod = 0;
		String output = "";
		String tempOut = "";
		
		do {
			mod = num % sizeMult;
			if(num / sizeMult < 1) {
				foundLegnth = true;
				output = String.valueOf(mod) + output;
			} else {
				tempOut = String.valueOf(mod);
				while(tempOut.length() < 3) {
					tempOut = "0" + tempOut;
				}
				output = "," + tempOut + output;
				num = (num - mod) / sizeMult;
			}
		} while (foundLegnth == false);
		
		return output;
	}
}
