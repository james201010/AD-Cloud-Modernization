/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author James Schneider
 *
 */
public class Template implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6881374456821019085L;
	private String templateName;
	private String sourceFile;
	private String destinationFile;
	
	private List<Tag> tags;
	
	
	/**
	 * 
	 */
	public Template() {
		
	}


	public String getTemplateName() {
		return templateName;
	}


	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	public String getSourceFile() {
		return sourceFile;
	}


	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}


	public String getDestinationFile() {
		return destinationFile;
	}


	public void setDestinationFile(String destinationFile) {
		this.destinationFile = destinationFile;
	}


	public List<Tag> getTags() {
		return tags;
	}


	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	
}
