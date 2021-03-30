/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

import com.appdynamics.cloud.modern.controller.BrumApp;

/**
 * @author James Schneider
 *
 */
public class BrumAppConfigUpdateRequest {

	/**
	 * 
	 */
	public BrumAppConfigUpdateRequest() {
		
	}

	
	public static String getRequestJson(BrumApp app) {
		
		String json = "{\r\n" + 
				"  \"thresholds\": {\r\n" + 
				"    \"slowThreshold\": {\r\n" + 
				"      \"type\": \"" + app.slowThresholdType + "\",\r\n" + 
				"      \"value\": " + app.slowThresholdValue + "\r\n" + 
				"    },\r\n" + 
				"    \"verySlowThreshold\": {\r\n" + 
				"      \"type\": \"" + app.verySlowThresholdType + "\",\r\n" + 
				"      \"value\": " + app.verySlowThresholdValue + "\r\n" + 
				"    },\r\n" + 
				"    \"stallThreshold\": {\r\n" + 
				"      \"type\": \"STATIC_MS\",\r\n" + 
				"      \"value\": 45000\r\n" + 
				"    }\r\n" + 
				"  },\r\n" + 
				"  \"percentileMetrics\": [\r\n" + 
				"    50,\r\n" + 
				"    90,\r\n" + 
				"    95,\r\n" + 
				"    99\r\n" + 
				"  ],\r\n" + 
				"  \"sessionsMonitor\": {\r\n" + 
				"    \"sessionTimeoutMins\": 5\r\n" + 
				"  },\r\n" + 
				"  \"ipAddressDisplayed\": true,\r\n" + 
				"  \"eventPolicy\": {\r\n" + 
				"    \"enableSlowSnapshotCollection\": true,\r\n" + 
				"    \"enablePeriodicSnapshotCollection\": true,\r\n" + 
				"    \"enableErrorSnapshotCollection\": true\r\n" + 
				"  },\r\n" + 
				"  \"primaryMetricsName\": \"END_USER_RESPONSE_TIME\",\r\n" + 
				"  \"userJourneyBrowserConfig\": {\r\n" + 
				"    \"dropOffEnabled\": true,\r\n" + 
				"    \"dropOffRateThreshold\": 10,\r\n" + 
				"    \"slowEnabled\": true,\r\n" + 
				"    \"slowThreshold\": 20,\r\n" + 
				"    \"verySlowEnabled\": true,\r\n" + 
				"    \"verySlowThreshold\": 10,\r\n" + 
				"    \"stallEnabled\": true,\r\n" + 
				"    \"stallThreshold\": 0,\r\n" + 
				"    \"jsErrorEnabled\": true,\r\n" + 
				"    \"jsErrorThreshold\": 20,\r\n" + 
				"    \"ajaxErrorEnabled\": true,\r\n" + 
				"    \"ajaxErrorThreshold\": 10\r\n" + 
				"  }\r\n" + 
				"}";
		
		return json;
	}
	
	
//	public String getPostModRequestJson() {
//		String json = "{\r\n" + 
//				"  \"thresholds\": {\r\n" + 
//				"    \"slowThreshold\": {\r\n" + 
//				"      \"type\": \"STANDARD_DEVIATION\",\r\n" + 
//				"      \"value\": 3\r\n" + 
//				"    },\r\n" + 
//				"    \"verySlowThreshold\": {\r\n" + 
//				"      \"type\": \"STANDARD_DEVIATION\",\r\n" + 
//				"      \"value\": 4\r\n" + 
//				"    },\r\n" + 
//				"    \"stallThreshold\": {\r\n" + 
//				"      \"type\": \"STATIC_MS\",\r\n" + 
//				"      \"value\": 45000\r\n" + 
//				"    }\r\n" + 
//				"  },\r\n" + 
//				"  \"percentileMetrics\": [\r\n" + 
//				"    50,\r\n" + 
//				"    90,\r\n" + 
//				"    95,\r\n" + 
//				"    99\r\n" + 
//				"  ],\r\n" + 
//				"  \"sessionsMonitor\": {\r\n" + 
//				"    \"sessionTimeoutMins\": 5\r\n" + 
//				"  },\r\n" + 
//				"  \"ipAddressDisplayed\": true,\r\n" + 
//				"  \"eventPolicy\": {\r\n" + 
//				"    \"enableSlowSnapshotCollection\": true,\r\n" + 
//				"    \"enablePeriodicSnapshotCollection\": true,\r\n" + 
//				"    \"enableErrorSnapshotCollection\": true\r\n" + 
//				"  },\r\n" + 
//				"  \"primaryMetricsName\": \"END_USER_RESPONSE_TIME\",\r\n" + 
//				"  \"userJourneyBrowserConfig\": {\r\n" + 
//				"    \"dropOffEnabled\": true,\r\n" + 
//				"    \"dropOffRateThreshold\": 10,\r\n" + 
//				"    \"slowEnabled\": true,\r\n" + 
//				"    \"slowThreshold\": 20,\r\n" + 
//				"    \"verySlowEnabled\": true,\r\n" + 
//				"    \"verySlowThreshold\": 10,\r\n" + 
//				"    \"stallEnabled\": true,\r\n" + 
//				"    \"stallThreshold\": 0,\r\n" + 
//				"    \"jsErrorEnabled\": true,\r\n" + 
//				"    \"jsErrorThreshold\": 20,\r\n" + 
//				"    \"ajaxErrorEnabled\": true,\r\n" + 
//				"    \"ajaxErrorThreshold\": 10\r\n" + 
//				"  }\r\n" + 
//				"}";
//		
//		return json;
//	}
	
	
	
}
