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
public class TemplateTarget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2922518597368061739L;
	private String templateName;
	private List<Tag> targetTags;
	
	/**
	 * 
	 */
	public TemplateTarget() {
		
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<Tag> getTargetTags() {
		return targetTags;
	}

	public void setTargetTags(List<Tag> targetTags) {
		this.targetTags = targetTags;
	}

	
	
}
