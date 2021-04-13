package com.common.exam.microservice.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "exams")
public class Exam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@JsonIgnoreProperties(value = {"exam", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	@OneToMany(mappedBy = "exam",fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Question> questions;
	
	@JsonIgnoreProperties(value= {"handler", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "should not be empty")
	private Subject subjectParent;
	
	@JsonIgnoreProperties(value= {"handler", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "should not be empty")
	private Subject subjectChild;
	
	@Transient
	private boolean responseVal;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	 
	public Exam() {
		this.questions = new ArrayList<>();
	}
	

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void setQuestions(List<Question> questions) {
		this.questions.clear();	
		questions.forEach(this::addQuestion);
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
		question.setExam(this);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
		
		question.setExam(null);
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Exam)) {
			return false;
		}
		
		Exam ex = (Exam) obj;
	
		return this.id != null && this.id.equals(ex.getId());
	}

	

	public void setResponseVal(boolean responseVal) {
		this.responseVal = responseVal;
	}

	public void setSubjectParent(Subject subjectParent) {
		this.subjectParent = subjectParent;
	}

	public void setSubjectChild(Subject subjectChild) {
		this.subjectChild = subjectChild;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public Subject getSubjectParent() {
		return subjectParent;
	}

	public Subject getSubjectChild() {
		return subjectChild;
	}

	public boolean isResponseVal() {
		return responseVal;
	}

	
}
