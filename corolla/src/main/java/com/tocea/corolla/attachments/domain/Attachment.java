/**
 *
 */
package com.tocea.corolla.attachments.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tocea.corolla.requirements.domain.RequirementRevision;

/**
 * @author sleroy
 *
 */
@Entity()
@Table(name = "attachments")
public class Attachment {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private RequirementRevision	requirement_owner;
}
