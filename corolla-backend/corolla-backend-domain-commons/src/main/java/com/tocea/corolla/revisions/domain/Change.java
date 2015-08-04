package com.tocea.corolla.revisions.domain;

import org.javers.core.diff.changetype.ValueChange;

public class Change implements IChange {

	private String propertyName;
	
	private Class<?> propertyType;
	
	private Object leftValue;
	
	private Object rightValue;
	
	public Change() {
		
	}
	
	public Change(ValueChange valueChange) {
		
		this.propertyName = valueChange.getPropertyName();
		this.propertyType = valueChange.getProperty().getType();
		this.leftValue = valueChange.getLeft();
		this.rightValue = valueChange.getRight();
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class<?> propertyType) {
		this.propertyType = propertyType;
	}

	public Object getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(Object leftValue) {
		this.leftValue = leftValue;
	}

	public Object getRightValue() {
		return rightValue;
	}

	public void setRightValue(Object rightValue) {
		this.rightValue = rightValue;
	}
	
}