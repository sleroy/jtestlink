package com.tocea.corolla.revisions.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class SnapshotBuildFailureException extends CorollaDomainException {

	private static String MESSAGE = "Failed to build a snapshot of a Corolla domain object";
	
	public SnapshotBuildFailureException(String msg) {
		super(msg);
	}
	
	public SnapshotBuildFailureException() {
		super(MESSAGE);
	}
	
}
