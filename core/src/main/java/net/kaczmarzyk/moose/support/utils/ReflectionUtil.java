package net.kaczmarzyk.moose.support.utils;

import java.lang.reflect.Field;


public final class ReflectionUtil {

	private ReflectionUtil() {
	}
	
	public static Object get(Object source, String fieldname) {
		try {
			Class<?> clazz = source.getClass();
			Field field = clazz.getDeclaredField(fieldname);
			field.setAccessible(true);
			return field.get(source);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static void set(Object target, String fieldname, Object value) {
		try {
			Class<?> clazz = target.getClass();
			Field field = clazz.getDeclaredField(fieldname);
			field.setAccessible(true);
			field.set(target, value);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
