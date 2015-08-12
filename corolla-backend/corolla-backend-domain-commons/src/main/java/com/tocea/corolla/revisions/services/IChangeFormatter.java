package com.tocea.corolla.revisions.services;

import com.tocea.corolla.revisions.domain.IChange;

public interface IChangeFormatter {

	public String format(IChange change, String target);
	
}
