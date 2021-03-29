/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

import com.appdynamics.cloud.modern.controller.LicenseRule;

/**
 * @author James Schneider
 *
 */
public class LicenseRuleCreateRequest {

	/**
	 * 
	 */
	public LicenseRuleCreateRequest() {
		
	}

	public String getJsonRequest(LicenseRule rule) {
		
		String json = "{\r\n" + 
				"  \"id\": \"" + rule.requestId + "\",\r\n" + 
				"  \"account_id\": \"\",\r\n" + 
				"  \"name\": \"" + rule.ruleName + "\",\r\n" + 
				"  \"access_key\": \"" + rule.licenseKey + "\",\r\n" + 
				"  \"constraints\": [\r\n" + 
				"    {\r\n" + 
				"      \"entity_type_id\": \"com.appdynamics.modules.apm.topology.impl.persistenceapi.model.ApplicationEntity\",\r\n" + 
				"      \"constraint_type\": \"ALLOW_ALL\",\r\n" + 
				"      \"match_conditions\": []\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"entity_type_id\": \"com.appdynamics.modules.apm.topology.impl.persistenceapi.model.MachineEntity\",\r\n" + 
				"      \"constraint_type\": \"ALLOW_ALL\",\r\n" + 
				"      \"match_conditions\": []\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"entitlements\": [\r\n" + 
				"    {\r\n" + 
				"      \"license_module_type\": \"APM\",\r\n" + 
				"      \"number_of_licenses\": " + rule.numberOfApm + "\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"license_module_type\": \"MACHINE_AGENT\",\r\n" + 
				"      \"number_of_licenses\": " + rule.numberOfMA + "\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"license_module_type\": \"SIM_MACHINE_AGENT\",\r\n" + 
				"      \"number_of_licenses\": " + rule.numberOfSim + "\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"license_module_type\": \"NETVIZ\",\r\n" + 
				"      \"number_of_licenses\": " + rule.numberOfNet + "\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"enabled\": true\r\n" + 
				"}";
		
		return json;
	}
}
