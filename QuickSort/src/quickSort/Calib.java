package quickSort;

import java.util.ArrayList;
import java.util.Random;

public class Calib {
	@SuppressWarnings("unchecked")
	public static int calibrate(int guess) {
		ArrayList<Sortable> list = new ArrayList<Sortable>();
		Random r = new Random();
		long s = 0;
		long e = 0;
		long bubble = 0;
		long quick = 0;
		int step = 2;
		int size = guess;
		int dir = 1;
		boolean hasDec = false;
		long dif = 0;
		do {
			for(int i = 0; i < size; i++) {
				int n = r.nextInt(89) + 10;
				list.add(new Sortable(n, n));
			}
			ArrayList<Sortable> orig = (ArrayList<Sortable>) list.clone();
			s = System.nanoTime();
			list = QuickSort.sort(orig);
			e = System.nanoTime();
			quick = e - s;
			s = System.nanoTime();
			list = BubbleSort.sort(orig);
			e = System.nanoTime();
			bubble = e - s;
			if(hasDec == false) {
				if(quick < bubble) {
					hasDec = true;
					step = size;
					size = size - step;
					dir = -1;
				} else {
					step = step * 2;
					size = size + step;
				}
			} else {
				if(bubble < quick) {
					if(dir == 1) {
						
					}
					step = step / 2;
					size = size + (dir * step);
					dir = (int) Math.signum(step * dir);
				} else {
					step = step / 2;
					size = size - (dir * step);
					dir = (int) Math.signum(step * dir * -1);
				}
			}
			dif = bubble - quick;
			System.out.println("Size: " + size + "|\tStep: " + step + "|\tBubbleSort: " + bubble + "|\tQuickSort: " + quick + "|\tDif: " + dif);
		} while(Math.abs(dif) > 1000);
		return size;
	}
}
