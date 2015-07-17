package com.tocea.corolla.requirements.utils;

import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.tocea.corolla.requirements.domain.Requirement;

public class RequirementUtils {

	/**
	 * Clones a requirement instance
	 * @param requirement
	 * @return
	 */
	public static Requirement clone(Requirement requirement) {
		
		Requirement clone = null;
		
		try {
			clone = (Requirement) BeanUtils.cloneBean(requirement);
			clone.setId(null);
			clone.setProjectBranchId(null);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return clone;
		
	}
	
	/**
	 * Clones a requirement instance and sets a new project branch ID
	 * @param requirement
	 * @param projectBranchID
	 * @return
	 */
	public static Requirement clone(Requirement requirement, String projectBranchID) {
		
		Requirement clone = clone(requirement);
		
		if (clone != null) {
			clone.setProjectBranchId(projectBranchID);
		}
		
		return clone;
	}
	
	
	/**
	 * Finds a requirement from its key in a collection of requirements
	 * @param requirements
	 * @param key
	 * @return
	 */
	public static Requirement findByKey(Collection<Requirement> requirements, final String key) {
		
		Collection<Requirement> match = Collections2.filter(requirements, new Predicate<Requirement>() {

			@Override
			public boolean apply(Requirement requirement) {
				return requirement.getKey().equals(key);
			}
			
		});
		
		return match.size() > 0 ? Lists.newArrayList(match).get(0) : null;	
	}
	
}
