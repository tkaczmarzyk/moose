package net.kaczmarzyk.moose.web.document.zk;

import net.kaczmarzyk.moose.core.document.Scalar;

import org.zkoss.zk.ui.Component;


public interface PropertyRenderer {

	public Component render(Scalar<?> value);
}
