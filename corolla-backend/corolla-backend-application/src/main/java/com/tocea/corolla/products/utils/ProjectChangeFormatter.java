package com.tocea.corolla.products.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.tocea.corolla.products.dao.IProjectStatusDAO;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.revisions.domain.IChange;
import com.tocea.corolla.revisions.services.IChangeFormatter;

@Component
public class ProjectChangeFormatter implements IChangeFormatter {

	@Autowired
	private IProjectStatusDAO statusDAO;
	
	@Override
	public String format(IChange change, String target) {
		
		Object value = target.equals("R") ? change.getRightValue() : change.getLeftValue();
		
		if (change.getPropertyType().equals(List.class)) {
			return Joiner.on(',').join((Iterable<?>) value);
		}

		if (change.getPropertyName().equals("statusId")) {
			ProjectStatus status = statusDAO.findOne((String) value);
			return status.getName();
		}
		
		return value != null ? value.toString() : "";
	}
	
}