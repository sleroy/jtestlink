package com.tocea.corolla.timetracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Activity {
	@Id
	@Field("_id")
	private String id;

	@Indexed
	private String activityName;
	
	public Activity() {
	}
	
	public Activity(final String _id, final String _activityName) {
		super();
		id = _id;
		activityName = _activityName;
	}
	
	public String getActivityName() {
		return activityName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setActivityName(final String _activityName) {
		activityName = _activityName;
	}
	
	public void setId(final String _id) {
		id = _id;
	}
	
	@Override
	public String toString() {
		return "Activity [id=" + id + ", activityName=" + activityName + "]";
	}
	
}