/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class BrumAppsGetResponse {
	
	  private String name;
	  private String appKey;
	  private int id;
	  private boolean monitoringEnabled;
	  private float synTotalJobs;
	  Metrics MetricsObject;
	  
	/**
	 * 
	 */
	public BrumAppsGetResponse() {
		// TODO Auto-generated constructor stub
	}



	 // Getter Methods 

	  public String getName() {
	    return name;
	  }

	  public String getAppKey() {
	    return appKey;
	  }

	  public int getId() {
	    return id;
	  }

	  public boolean getMonitoringEnabled() {
	    return monitoringEnabled;
	  }

	  public float getSynTotalJobs() {
	    return synTotalJobs;
	  }

	  public Metrics getMetrics() {
	    return MetricsObject;
	  }

	 // Setter Methods 

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setAppKey( String appKey ) {
	    this.appKey = appKey;
	  }

	  public void setId( int id ) {
	    this.id = id;
	  }

	  public void setMonitoringEnabled( boolean monitoringEnabled ) {
	    this.monitoringEnabled = monitoringEnabled;
	  }

	  public void setSynTotalJobs( float synTotalJobs ) {
	    this.synTotalJobs = synTotalJobs;
	  }

	  public void setMetrics( Metrics metricsObject ) {
	    this.MetricsObject = metricsObject;
	  }
	}

	class Metrics {
	  SyntheticEndUserResponseTime SyntheticEndUserResponseTimeObject;
	  SyntheticPageRequestsPerMin SyntheticPageRequestsPerMinObject;
	  PageRequestsPerMin PageRequestsPerMinObject;
	  JavascriptErrorsPerMin JavascriptErrorsPerMinObject;
	  EndUserResponseTime EndUserResponseTimeObject;
	  ErrorRate ErrorRateObject;
	  SyntheticAvailability SyntheticAvailabilityObject;


	 // Getter Methods 

	  public SyntheticEndUserResponseTime getSyntheticEndUserResponseTime() {
	    return SyntheticEndUserResponseTimeObject;
	  }

	  public SyntheticPageRequestsPerMin getSyntheticPageRequestsPerMin() {
	    return SyntheticPageRequestsPerMinObject;
	  }

	  public PageRequestsPerMin getPageRequestsPerMin() {
	    return PageRequestsPerMinObject;
	  }

	  public JavascriptErrorsPerMin getJavascriptErrorsPerMin() {
	    return JavascriptErrorsPerMinObject;
	  }

	  public EndUserResponseTime getEndUserResponseTime() {
	    return EndUserResponseTimeObject;
	  }

	  public ErrorRate getErrorRate() {
	    return ErrorRateObject;
	  }

	  public SyntheticAvailability getSyntheticAvailability() {
	    return SyntheticAvailabilityObject;
	  }

	 // Setter Methods 

	  public void setSyntheticEndUserResponseTime( SyntheticEndUserResponseTime syntheticEndUserResponseTimeObject ) {
	    this.SyntheticEndUserResponseTimeObject = syntheticEndUserResponseTimeObject;
	  }

	  public void setSyntheticPageRequestsPerMin( SyntheticPageRequestsPerMin syntheticPageRequestsPerMinObject ) {
	    this.SyntheticPageRequestsPerMinObject = syntheticPageRequestsPerMinObject;
	  }

	  public void setPageRequestsPerMin( PageRequestsPerMin pageRequestsPerMinObject ) {
	    this.PageRequestsPerMinObject = pageRequestsPerMinObject;
	  }

	  public void setJavascriptErrorsPerMin( JavascriptErrorsPerMin javascriptErrorsPerMinObject ) {
	    this.JavascriptErrorsPerMinObject = javascriptErrorsPerMinObject;
	  }

	  public void setEndUserResponseTime( EndUserResponseTime endUserResponseTimeObject ) {
	    this.EndUserResponseTimeObject = endUserResponseTimeObject;
	  }

	  public void setErrorRate( ErrorRate errorRateObject ) {
	    this.ErrorRateObject = errorRateObject;
	  }

	  public void setSyntheticAvailability( SyntheticAvailability syntheticAvailabilityObject ) {
	    this.SyntheticAvailabilityObject = syntheticAvailabilityObject;
	  }
	}
	
	class SyntheticAvailability {
	  private float metricId;
	  private String name;
	  private float value;
	  private float sum;
	  private float count;
	  private String graphData = null;


	 // Getter Methods 

	  public float getMetricId() {
	    return metricId;
	  }

	  public String getName() {
	    return name;
	  }

	  public float getValue() {
	    return value;
	  }

	  public float getSum() {
	    return sum;
	  }

	  public float getCount() {
	    return count;
	  }

	  public String getGraphData() {
	    return graphData;
	  }

	 // Setter Methods 

	  public void setMetricId( float metricId ) {
	    this.metricId = metricId;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setValue( float value ) {
	    this.value = value;
	  }

	  public void setSum( float sum ) {
	    this.sum = sum;
	  }

	  public void setCount( float count ) {
	    this.count = count;
	  }

	  public void setGraphData( String graphData ) {
	    this.graphData = graphData;
	  }
	}
	
	class ErrorRate {
	  private float metricId;
	  private String name = null;
	  private float value;
	  private float sum;
	  private float count;
	  private String graphData = null;


	 // Getter Methods 

	  public float getMetricId() {
	    return metricId;
	  }

	  public String getName() {
	    return name;
	  }

	  public float getValue() {
	    return value;
	  }

	  public float getSum() {
	    return sum;
	  }

	  public float getCount() {
	    return count;
	  }

	  public String getGraphData() {
	    return graphData;
	  }

	 // Setter Methods 

	  public void setMetricId( float metricId ) {
	    this.metricId = metricId;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setValue( float value ) {
	    this.value = value;
	  }

	  public void setSum( float sum ) {
	    this.sum = sum;
	  }

	  public void setCount( float count ) {
	    this.count = count;
	  }

	  public void setGraphData( String graphData ) {
	    this.graphData = graphData;
	  }
	}
	
	class EndUserResponseTime {
	  private float metricId;
	  private String name;
	  private float value;
	  private float sum;
	  private float count;
	  private String graphData = null;


	 // Getter Methods 

	  public float getMetricId() {
	    return metricId;
	  }

	  public String getName() {
	    return name;
	  }

	  public float getValue() {
	    return value;
	  }

	  public float getSum() {
	    return sum;
	  }

	  public float getCount() {
	    return count;
	  }

	  public String getGraphData() {
	    return graphData;
	  }

	 // Setter Methods 

	  public void setMetricId( float metricId ) {
	    this.metricId = metricId;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setValue( float value ) {
	    this.value = value;
	  }

	  public void setSum( float sum ) {
	    this.sum = sum;
	  }

	  public void setCount( float count ) {
	    this.count = count;
	  }

	  public void setGraphData( String graphData ) {
	    this.graphData = graphData;
	  }
	}
	
	class JavascriptErrorsPerMin {
	  private float metricId;
	  private String name;
	  private float value;
	  private float sum;
	  private float count;
	  private String graphData = null;


	 // Getter Methods 

	  public float getMetricId() {
	    return metricId;
	  }

	  public String getName() {
	    return name;
	  }

	  public float getValue() {
	    return value;
	  }

	  public float getSum() {
	    return sum;
	  }

	  public float getCount() {
	    return count;
	  }

	  public String getGraphData() {
	    return graphData;
	  }

	 // Setter Methods 

	  public void setMetricId( float metricId ) {
	    this.metricId = metricId;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setValue( float value ) {
	    this.value = value;
	  }

	  public void setSum( float sum ) {
	    this.sum = sum;
	  }

	  public void setCount( float count ) {
	    this.count = count;
	  }

	  public void setGraphData( String graphData ) {
	    this.graphData = graphData;
	  }
	}
	
	class PageRequestsPerMin {
	  private float metricId;
	  private String name;
	  private float value;
	  private float sum;
	  private float count;
	  private String graphData = null;


	 // Getter Methods 

	  public float getMetricId() {
	    return metricId;
	  }

	  public String getName() {
	    return name;
	  }

	  public float getValue() {
	    return value;
	  }

	  public float getSum() {
	    return sum;
	  }

	  public float getCount() {
	    return count;
	  }

	  public String getGraphData() {
	    return graphData;
	  }

	 // Setter Methods 

	  public void setMetricId( float metricId ) {
	    this.metricId = metricId;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setValue( float value ) {
	    this.value = value;
	  }

	  public void setSum( float sum ) {
	    this.sum = sum;
	  }

	  public void setCount( float count ) {
	    this.count = count;
	  }

	  public void setGraphData( String graphData ) {
	    this.graphData = graphData;
	  }
	}
	
	class SyntheticPageRequestsPerMin {
	  private float metricId;
	  private String name;
	  private float value;
	  private float sum;
	  private float count;
	  private String graphData = null;


	 // Getter Methods 

	  public float getMetricId() {
	    return metricId;
	  }

	  public String getName() {
	    return name;
	  }

	  public float getValue() {
	    return value;
	  }

	  public float getSum() {
	    return sum;
	  }

	  public float getCount() {
	    return count;
	  }

	  public String getGraphData() {
	    return graphData;
	  }

	 // Setter Methods 

	  public void setMetricId( float metricId ) {
	    this.metricId = metricId;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setValue( float value ) {
	    this.value = value;
	  }

	  public void setSum( float sum ) {
	    this.sum = sum;
	  }

	  public void setCount( float count ) {
	    this.count = count;
	  }

	  public void setGraphData( String graphData ) {
	    this.graphData = graphData;
	  }
	}
	
	class SyntheticEndUserResponseTime {
	  private float metricId;
	  private String name;
	  private float value;
	  private float sum;
	  private float count;
	  private String graphData = null;


	 // Getter Methods 

	  public float getMetricId() {
	    return metricId;
	  }

	  public String getName() {
	    return name;
	  }

	  public float getValue() {
	    return value;
	  }

	  public float getSum() {
	    return sum;
	  }

	  public float getCount() {
	    return count;
	  }

	  public String getGraphData() {
	    return graphData;
	  }

	 // Setter Methods 

	  public void setMetricId( float metricId ) {
	    this.metricId = metricId;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setValue( float value ) {
	    this.value = value;
	  }

	  public void setSum( float sum ) {
	    this.sum = sum;
	  }

	  public void setCount( float count ) {
	    this.count = count;
	  }

	  public void setGraphData( String graphData ) {
	    this.graphData = graphData;
	  }
}
