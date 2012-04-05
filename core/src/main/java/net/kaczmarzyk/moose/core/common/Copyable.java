package net.kaczmarzyk.moose.core.common;


public interface Copyable<T extends Copyable<T>> {

	T copy();
}
