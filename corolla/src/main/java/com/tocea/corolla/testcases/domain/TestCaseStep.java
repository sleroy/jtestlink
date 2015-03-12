package com.tocea.corolla.testcases.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity()
@Table(name = "testcase_steps")
public class TestCaseStep implements Serializable {

	@Id
	@GeneratedValue
	private Integer	id;

	@Min(value = 0)
	@Column(nullable = false)
	private int		stepNumber;

	@Column(nullable = false)
	@Lob
	private String	actions;

	@Column(nullable = false)
	@Lob
	private String	expected_results;

	@Column(nullable = false)
	private boolean	active;

	@Column(nullable = false)
	@NotBlank
	private String	execution_type;

	@NotNull
	@Column(nullable = false)
	private Double	estimatedTime	= 0d;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private TestCaseRevision	owner;

	/**
	 * @return the actions
	 */
	public String getActions() {
		return this.actions;
	}

	/**
	 * @return the estimatedTime
	 */
	public Double getEstimatedTime() {
		return this.estimatedTime;
	}

	/**
	 * @return the execution_type
	 */
	public String getExecution_type() {
		return this.execution_type;
	}

	/**
	 * @return the expected_results
	 */
	public String getExpected_results() {
		return this.expected_results;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the owner
	 */
	public TestCaseRevision getOwner() {
		return this.owner;
	}

	/**
	 * @return the stepNumber
	 */
	public int getStepNumber() {
		return this.stepNumber;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @param _actions
	 *            the actions to set
	 */
	public void setActions(final String _actions) {
		this.actions = _actions;
	}

	/**
	 * @param _active
	 *            the active to set
	 */
	public void setActive(final boolean _active) {
		this.active = _active;
	}

	/**
	 * @param _estimatedTime
	 *            the estimatedTime to set
	 */
	public void setEstimatedTime(final Double _estimatedTime) {
		this.estimatedTime = _estimatedTime;
	}

	/**
	 * @param _execution_type
	 *            the execution_type to set
	 */
	public void setExecution_type(final String _execution_type) {
		this.execution_type = _execution_type;
	}

	/**
	 * @param _expected_results
	 *            the expected_results to set
	 */
	public void setExpected_results(final String _expected_results) {
		this.expected_results = _expected_results;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _owner
	 *            the owner to set
	 */
	public void setOwner(final TestCaseRevision _owner) {
		this.owner = _owner;
	}

	/**
	 * @param _stepNumber
	 *            the stepNumber to set
	 */
	public void setStepNumber(final int _stepNumber) {
		this.stepNumber = _stepNumber;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCaseStep [id=" + this.id + ", stepNumber=" + this.stepNumber
				+ ", actions=" + this.actions + ", expected_results="
				+ this.expected_results + ", active=" + this.active + ", execution_type="
				+ this.execution_type + ", estimatedTime=" + this.estimatedTime
				+ ", owner=" + this.owner + "]";
	}

}