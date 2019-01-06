package com.analyzer.domain.dbo.legacy;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity	
@NoArgsConstructor
@Getter
@Setter
public class AllScripsDbObject{

	@Id Long id;
	
	private String bseId;
	
	private String fairValue;
	
	private String industry;
	
	private String isBlackListed;
	
	private String isWatchListed;
	
	private String isin;
	
	private String moneycontrolName;
	
	private String nseId;
	
	private List<String> ratingNameToValue;
	
	private String stockName;
	
	private String yahooName;
}
