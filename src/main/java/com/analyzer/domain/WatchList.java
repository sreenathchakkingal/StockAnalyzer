package com.analyzer.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity	
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class WatchList {
	@Id Long id; 
	
	String nseId;
}
