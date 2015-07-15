package com.tocea.corolla.revisions.domain;

import java.util.Date;

public interface ICommit {

	public String getId();
	
	public String getAuthor();
	
	public Date getDate();
	
}
