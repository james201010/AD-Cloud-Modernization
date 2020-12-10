/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class MachineAgentListRequest {

	public Filter filter;
	public Sorter sorter;

	/**
	 * 
	 */
	public MachineAgentListRequest() {
		
	}

	  public Filter getFilter() {
	    return filter;
	  }
	  public Sorter getSorter() {
	    return sorter;
	  }

	  public void setFilter( Filter filter ) {
	    this.filter = filter;
	  }
	  public void setSorter( Sorter sorter ) {
	    this.sorter = sorter;
	  }
	

	    public class Sorter {
	    	
		    private String field;
		    private String direction;
	
		    public String getField() {
		      return field;
		    }
		    public String getDirection() {
		      return direction;
		    }
	
		    public void setField( String field ) {
		      this.field = field;
		    }
		    public void setDirection( String direction ) {
		      this.direction = direction;
		    }
	  
	    }
	
	    public class Filter {
		
		    public int[] appIds;
		    public int[] nodeIds;
		    public int[] tierIds;
		    public String[] types;
		    private long timeRangeStart;
		    private long timeRangeEnd;
	
		    public long getTimeRangeStart() {
		      return timeRangeStart;
		    }
	
		    public long getTimeRangeEnd() {
		      return timeRangeEnd;
		    }
	
		    public void setTimeRangeStart( long timeRangeStart ) {
		      this.timeRangeStart = timeRangeStart;
		    }
	
		    public void setTimeRangeEnd( long timeRangeEnd ) {
		      this.timeRangeEnd = timeRangeEnd;
		    }
	
			public int[] getAppIds() {
				return appIds;
			}
		
			public void setAppIds(int[] appIds) {
				this.appIds = appIds;
			}
		
			public int[] getNodeIds() {
				return nodeIds;
			}
		
			public void setNodeIds(int[] nodeIds) {
				this.nodeIds = nodeIds;
			}
		
			public int[] getTierIds() {
				return tierIds;
			}
		
			public void setTierIds(int[] tierIds) {
				this.tierIds = tierIds;
			}
		
			public String[] getTypes() {
				return types;
			}
		
			public void setTypes(String[] types) {
				this.types = types;
			}	
	  
	    }
	
	
}
