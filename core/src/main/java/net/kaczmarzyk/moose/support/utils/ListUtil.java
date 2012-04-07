package net.kaczmarzyk.moose.support.utils;

import java.util.ArrayList;
import java.util.List;


public final class ListUtil {

	private ListUtil() {
	}

	public static <T> List<List<T>> cartesianProduct(List<List<T>> lists) { //TODO iterator version which does not preload entire result into memory
		List<List<T>> resultLists = new ArrayList<>();
		if (lists.size() == 0) {
			resultLists.add(new ArrayList<T>());
			return resultLists;
		} else {
			List<T> firstList = lists.get(0);
			List<List<T>> remainingLists = cartesianProduct(lists.subList(1, lists.size()));
			for (T condition : firstList) {
				for (List<T> remainingList : remainingLists) {
					ArrayList<T> resultList = new ArrayList<>();
					resultList.add(condition);
					resultList.addAll(remainingList);
					resultLists.add(resultList);
				}
			}
		}
		return resultLists;
	}

}
