package com.miimber.back.forum.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.miimber.back.organization.model.Organization;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="categories_forum")
@Getter @Setter
public class CategoryForum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name= "title")
	private String title;
	
	@Column(name= "position")
	private Integer position;

    @ManyToOne
	@NotNull
	private Organization organization;
    
	@OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<SubjectForum> subjects;
}
