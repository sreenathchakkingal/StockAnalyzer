package com.analyzer.services.config;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSetting {
	
	private String appId;
	private Map<String, String> hostName;
	private String endPointPrefix;
	private String datastoreDevHost;
	
}
