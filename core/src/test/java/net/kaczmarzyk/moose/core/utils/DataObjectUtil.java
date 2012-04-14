package net.kaczmarzyk.moose.core.utils;

import java.util.ArrayList;
import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.document.Sheet;


public final class DataObjectUtil {

	private DataObjectUtil() {
	}

	public static ObjectAddress relObjAddr(Sheet sheet, Path path, int... shifts) {
		return new ObjectAddress(new CellAddress(sheet, coords(sheet, false, shifts)), path);
	}

	private static List<Coordinate> coords(Sheet sheet, boolean absolute, int[] shifts) {
		List<Coordinate> result = new ArrayList<>();
		for (int i = 0; i<shifts.length; i++) {
			result.add(new Coordinate(sheet.getDimensions().get(i), shifts[i], absolute));
		}
		return result;
	}

	public static CellAddress absCellAddr(Sheet sheet, int... shifts) {
		return new CellAddress(sheet, coords(sheet, true, shifts));
	}
}