package com.analyzer.domain.dbo;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class RatingDbObject{

	@Id Long id;
	
	private String ratingName;

	public RatingDbObject(String ratingName) {
		this.ratingName = ratingName;
	}

	public String getRatingName() {
		return ratingName;
	}

}