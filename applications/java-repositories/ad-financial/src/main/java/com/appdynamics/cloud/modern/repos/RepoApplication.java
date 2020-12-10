package com.appdynamics.cloud.modern.repos;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.appdynamics.cloud.modern.repos.utils.ApplicationConstants;
import com.appdynamics.cloud.modern.repos.utils.CloudServicesHelper;

/**
 * @author James Schneider
 *
 */

@SpringBootApplication
public class RepoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(RepoApplication.class, args);
		
	    SpringApplication springApplication = new SpringApplication(RepoApplication.class);
	    springApplication.addListeners(new RepoApplicationListener());
	    springApplication.run(args);
	    
	}

	  @PreDestroy
	  public void onExit() {
		  System.out.println("###############################    Application is shutting down    ###############################");
			try {
				//String APP_CLOUD_MODE = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_MODE).toLowerCase();
				//if (!APP_CLOUD_MODE.equals("none")) {
					//CloudServicesHelper.deleteCloudStorageInstances();
				//}
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		  
	  }
}
