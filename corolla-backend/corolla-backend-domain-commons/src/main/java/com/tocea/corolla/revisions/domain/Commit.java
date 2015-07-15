package com.tocea.corolla.revisions.domain;

import java.util.Date;

import org.javers.core.metamodel.object.CdoSnapshot;

public class Commit implements ICommit {

	private String id;
	
	private Date date;
	
	private String author;

	public Commit(CdoSnapshot snapshot) {
		
		this.id = snapshot.getCommitId().value();
		this.author = snapshot.getCommitMetadata().getAuthor();
		this.date = snapshot.getCommitMetadata().getCommitDate().toDate();
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
