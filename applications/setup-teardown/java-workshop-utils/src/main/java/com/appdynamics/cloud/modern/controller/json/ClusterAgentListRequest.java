/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class ClusterAgentListRequest {

	
	/**
	 * 
	 */
	public ClusterAgentListRequest() {
		
	}

	public String getJsonRequest() {
		
		String json = "{\r\n" + 
				"  \"requestFilter\": {\r\n" + 
				"    \"queryParams\": {\r\n" + 
				"      \"applicationAssociationType\": \"ALL\"\r\n" + 
				"    },\r\n" + 
				"    \"filters\": []\r\n" + 
				"  },\r\n" + 
				"  \"resultColumns\": [\r\n" + 
				"    \"MACHINE_ID\"\r\n" + 
				"  ],\r\n" + 
				"  \"offset\": 0,\r\n" + 
				"  \"limit\": -1,\r\n" + 
				"  \"searchFilters\": [],\r\n" + 
				"  \"columnSorts\": [\r\n" + 
				"    {\r\n" + 
				"      \"column\": \"HOST_NAME\",\r\n" + 
				"      \"direction\": \"ASC\"\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"timeRangeStart\": -1,\r\n" + 
				"  \"timeRangeEnd\": -1\r\n" + 
				"}";
		
		return json;
	}
	
}
