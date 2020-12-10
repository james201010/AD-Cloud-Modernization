/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * @author James Schneider
 *
 */
public class ConfigHelper {

	private static ReposConfig CONFIG = null;
	/**
	 * 
	 */
	public ConfigHelper() {
		
	}

	public static ReposConfig getReposConfig() throws Throwable {
		
		
		if (CONFIG == null) {
			String reposConfigFilePath = System.getenv("REPOS_CONFIG_FILE");
			Yaml yaml = new Yaml(new Constructor(ReposConfig.class));
			InputStream inputStream = StringUtils.getFileAsStream(reposConfigFilePath);
			CONFIG = yaml.load(inputStream);	
		}
		
		return CONFIG;
	}
	
}
