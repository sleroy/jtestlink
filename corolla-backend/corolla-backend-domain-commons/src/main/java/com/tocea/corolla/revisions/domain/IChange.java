package com.tocea.corolla.revisions.domain;

public interface IChange {

	public String getPropertyName();
	
	public Class<?> getPropertyType();
	
	public Object getLeftValue();
	
	public Object getRightValue();
	
}
