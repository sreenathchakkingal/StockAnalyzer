package com.analyzer.services.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigReader {

	private final static String PROJECT_SETTING_YAML_FILE = "./src/main/resources/stockanayzer-app.yaml";

	private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());
	private static ProjectSetting projectSetting = null;

	public static ProjectSetting getProjectSetting() {
		try {
			if (projectSetting == null) {
				projectSetting = MAPPER.readValue(new File(PROJECT_SETTING_YAML_FILE), ProjectSetting.class);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return projectSetting;

	}

}
