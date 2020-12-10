/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

/**
 * @author James Schneider
 *
 */
public class JsonHelper {

	/**
	 * 
	 */
	public JsonHelper() {
		
	}

	public static String getJsonFromHttpResponse(HttpResponse response) throws Throwable {
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		return resp;
	}
	
}
