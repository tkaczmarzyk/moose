package net.kaczmarzyk.moose.core.utils;

import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.expression.Constant;


public final class ExpressionUtil {

	private ExpressionUtil() {
	}
	
	public static <T> Constant constant(T obj) {
		return new Constant(new Scalar<T>(obj));
	}
}
