/**
 * 
 */
package com.appdynamics.cloud.modern;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.appdynamics.cloud.modern.analytics.AnalyticsEventsDriver;
import com.appdynamics.cloud.modern.analytics.AnalyticsEventsSource;
import com.appdynamics.cloud.modern.config.AnalyticsEventsSourceConfig;
import com.appdynamics.cloud.modern.config.ControllerConfig;
import com.appdynamics.cloud.modern.config.ControllerLoginConfig;
import com.appdynamics.cloud.modern.config.EventsServiceConfig;
import com.appdynamics.cloud.modern.config.ServicesConfig;
import com.appdynamics.cloud.modern.utils.StringUtils;
import com.appdynamics.cloud.modern.vault.WorkshopVault;
import com.google.gson.Gson;

/**
 * @author James Schneider
 *
 */
public class ServicesApplicationListener implements ApplicationConstants, ApplicationListener<ApplicationEvent> {

	private static Logger logr;
	private static ServicesConfig SRVCS_CONF;
	private static List<Thread> ANALYTICS_DRIVER_THREADS;
	
	/**
	 * 
	 */
	public ServicesApplicationListener() {
		logr = new Logger(ServicesApplicationListener.class.getSimpleName());
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		if (event instanceof AvailabilityChangeEvent) {
			
			AvailabilityChangeEvent<?> ace = (AvailabilityChangeEvent<?>)event;
			
			if (ace.getState().equals(ReadinessState.ACCEPTING_TRAFFIC)) {
		
				try {
					
					logr.printBanner(true);
					
					this.initializeServices();
					
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
				
				
			}
			
		}
	}
	
	private void initializeServices() throws Throwable {
		
		String confPath = System.getProperty(ADWRKSHP_SRVCS_CONF_KEY);
		Yaml yaml = new Yaml(new Constructor(ServicesConfig.class));
		InputStream inputStream = StringUtils.getFileAsStream(confPath);
		
		SRVCS_CONF = yaml.load(inputStream);	
		
		WorkshopVault vault = new WorkshopVault(SRVCS_CONF.getVaultInfo());
		Gson gson = new Gson();
				
       // ControllerConfig controllerConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-info")), ControllerConfig.class);
       // ControllerLoginConfig loginConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-login-info")), ControllerLoginConfig.class);	
        EventsServiceConfig eventsSrvcConfig = SRVCS_CONF.getEventsServiceConfig();
        		
		
        List<AnalyticsEventsSourceConfig> aescList = SRVCS_CONF.getAnalyticsEventsSources();
        if (aescList != null) {
        	
        	ANALYTICS_DRIVER_THREADS = new ArrayList<Thread>();
        	
        	for (AnalyticsEventsSourceConfig aesConf : aescList) {
        		
        		
        		//if (aesConf.getSchemaName().equals("loans_premod")) {
        			
                	AnalyticsEventsDriver driver;
                	AnalyticsEventsSource source;
        			
        			Class<?> clazz = Class.forName(aesConf.getEventsSourceClass());
        			Object object = clazz.newInstance();
        			source = (AnalyticsEventsSource)object;
        			
        			source.initialize(aesConf);
        			driver = new AnalyticsEventsDriver(eventsSrvcConfig, source);
        			Thread driverThread = new Thread(driver);
        			driverThread.start();
        			ANALYTICS_DRIVER_THREADS.add(driverThread);
        			
        		//}
        		
        	}
        }
		
	}

}
