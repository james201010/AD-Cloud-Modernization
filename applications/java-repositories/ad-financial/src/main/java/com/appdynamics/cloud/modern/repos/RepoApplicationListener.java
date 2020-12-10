/**
 * 
 */
package com.appdynamics.cloud.modern.repos;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.appdynamics.cloud.modern.repos.utils.ApplicationConstants;
import com.appdynamics.cloud.modern.repos.utils.SQLDataHelper;
import com.appdynamics.cloud.modern.repos.utils.CloudServicesHelper;

/**
 * @author James Schneider
 *
 */
public class RepoApplicationListener implements ApplicationListener<ApplicationEvent> {

	
	private static Thread SQL_THREAD;
	
	/**
	 * 
	 */
	public RepoApplicationListener() {
		
	}

	@Override
	public void onApplicationEvent(ApplicationEvent appEvent) {
		
		if (appEvent instanceof AvailabilityChangeEvent) {
			AvailabilityChangeEvent<?> ace = (AvailabilityChangeEvent<?>)appEvent;
			if (ace.getState().equals(ReadinessState.ACCEPTING_TRAFFIC)) {
				
				System.out.println("###############################    ReadinessState.ACCEPTING_TRAFFIC Event Received    ###############################");
				
				try {
					String ADWRKSHP_CLOUD_MODE = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_MODE).toLowerCase();
					
					System.out.println("###############################    ADWRKSHP_CLOUD_MODE = " + ADWRKSHP_CLOUD_MODE + "    ###############################");
					
					if (!ADWRKSHP_CLOUD_MODE.equals(ApplicationConstants.ENV_CLOUD_MODE_NONE)) {
						CloudServicesHelper.createCloudStorageInstances();
					}

					SQLDataHelper loader = new SQLDataHelper();
					
					SQL_THREAD = new Thread(loader);
					SQL_THREAD.start();
					
					
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
				
				
			}
			
		} else if (appEvent instanceof ApplicationReadyEvent) {
			
			System.out.println("###############################    ApplicationReadyEvent Event Received    ###############################");
			
		}
		
	}


		//System.out.println("###############################    Application is shutting down    ###############################");
		


}
