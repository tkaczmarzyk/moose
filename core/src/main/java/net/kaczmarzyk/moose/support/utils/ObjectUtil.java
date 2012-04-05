package net.kaczmarzyk.moose.support.utils;

import java.util.HashSet;
import java.util.Set;

import net.kaczmarzyk.moose.core.common.Copyable;

import org.apache.commons.lang3.ObjectUtils;


public final class ObjectUtil {

	private static final Set<Class<?>> registeredImmutables = new HashSet<>();

	static {
		registeredImmutables.add(String.class);
		registeredImmutables.add(Double.class);
	}
	
	private ObjectUtil() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T copy(T obj) {
		if (isImmutable(obj)) {
			return obj;
		}
		if (obj instanceof Copyable) {
			return (T) ((Copyable<?>)obj).copy();
		}
		return ObjectUtils.clone(obj);
	}
	
	private static boolean isImmutable(Object obj) {
		return registeredImmutables.contains(obj.getClass());
	}
}
