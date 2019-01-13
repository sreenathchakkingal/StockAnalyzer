package com.analyzer.services.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigReader {

	public final static String PROJECT_SETTING_YAML_FILE_DEV_PATH = "./src/main/webapp/WEB-INF/stockanayzer-app.yaml";
	public final static String PROJECT_SETTING_YAML_FILE_PROD_PATH = "WEB-INF/stockanayzer-app.yaml";

	private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());
	private static ProjectSetting projectSetting = null;

	public static ProjectSetting getProjectSetting() {
		try {
			if (projectSetting == null) {
				String path = Environment.isNoEnv() ? PROJECT_SETTING_YAML_FILE_DEV_PATH : PROJECT_SETTING_YAML_FILE_PROD_PATH;
				projectSetting = MAPPER.readValue(new File(path), ProjectSetting.class);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return projectSetting;

	}

}
