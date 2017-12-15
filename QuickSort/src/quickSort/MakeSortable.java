package quickSort;

import java.util.ArrayList;

public class MakeSortable {
	public static ArrayList<Sortable> makeStringSortable(ArrayList<String> s) {
		int max = 0;
		ArrayList<Sortable> out = new ArrayList<Sortable>();
		for(int i = 0; i < s.size(); i++) {
			max = Math.max(s.get(i).length(), max);
		}
		double loc = 0;
		for(int i = 0; i < s.size(); i++) {
			loc = 0;
			for(int j = 0; j < s.get(i).length(); j++) {
				char c = s.get(i).charAt(j);
				loc = loc + (c * Math.pow(10, ((max - j - 1) * 3)));
			}
			out.add(new Sortable(s.get(i), loc));
		}
		return out;
	}
}
