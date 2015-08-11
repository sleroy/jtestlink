package com.tocea.corolla.products.utils;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.tocea.corolla.products.domain.Project;

public class ProjectUtils {
	
	public static Project findByID(final String ID, Collection<Project> projects) {
		
		Collection<Project> match = Collections2.filter(projects, new Predicate<Project>() {
			@Override
			public boolean apply(Project project) {
				return project.getId().equals(ID);
			}
			
		});
		
		return match.isEmpty() ? null : Lists.newArrayList(match).get(0);
		
	}
	
	/**
	 * Extract all tags that can be found in a given collection of projects
	 * (remove duplicates)
	 * @param projects
	 * @return
	 */
	public static Iterable<String> extractTags(Collection<Project> projects) {
		
		Iterable<String> itTags = null;
		
		for(Project project : projects) {
			itTags = itTags != null ? Iterables.concat(itTags, project.getTags()) : project.getTags();
		}
		
		return ImmutableSet.copyOf(itTags).asList();
	}

}
