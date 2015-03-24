package com.tocea.corolla.testcases.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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

	@NotNull
	@Min(value = 0)
	@Column(nullable = false)
	private int		stepNumber;

	@NotNull
	@Column(nullable = false)
	@Lob
	private String	actions;

	@NotNull
	@Column(nullable = false)
	@Lob
	private String	expected_results;

	@NotNull
	@Column(nullable = false)
	private Boolean	active;

	@NotNull
	@Column(nullable = false)
	@NotBlank
	private String	execution_type;

	@NotNull
	@Column(nullable = false)
	private Double	estimatedTime	= 0d;

	/**
	 * Returns a revision.
	 */
	@Column(nullable = false)
	@NotNull
	private Integer	testCaseRevision;

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
	 * @return the stepNumber
	 */
	public int getStepNumber() {
		return this.stepNumber;
	}

	/**
	 * @return the testCaseRevision
	 */
	public Integer getTestCaseRevision() {
		return this.testCaseRevision;
	}

	/**
	 * @return the active
	 */
	public Boolean isActive() {
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
	public void setActive(final Boolean _active) {
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
	 * @param _stepNumber
	 *            the stepNumber to set
	 */
	public void setStepNumber(final int _stepNumber) {
		this.stepNumber = _stepNumber;
	}

	/**
	 * @param _owner
	 *            the testCaseRevision to set
	 */
	public void setTestCaseRevision(final Integer _owner) {
		this.testCaseRevision = _owner;
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
				+ ", testCaseRevision=" + this.testCaseRevision + "]";
	}

}