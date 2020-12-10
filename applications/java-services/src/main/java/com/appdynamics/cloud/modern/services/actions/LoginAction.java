package com.appdynamics.cloud.modern.services.actions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import com.appdynamics.cloud.modern.services.beans.HttpJsonTransport;
import com.google.gson.Gson;

public final class LoginAction extends Action {

    private LoginActionProperties props;

    public LoginAction(final String name, final Map<String, Object> properties) {
        super(name, properties);
        this.type = "LoginAction";
        Gson gson = new Gson();
        this.props = gson.fromJson(gson.toJson(this.properties), LoginActionProperties.class);

        logger.info("LoginActionProperties for " + this.name + ": " + gson.toJson(this.props));
    }

    @Override
    public String execute(HttpServletRequest request, String priorActionReturn) {
        logger.info("Executing LoginAction");
        
        Gson gson = new Gson();
		HttpJsonTransport trans = new HttpJsonTransport();
		
		trans.setServiceOperation("authenticate");
		trans.setResponseCode("200");
		Map<String, String> params = new HashMap<String, String>();
        
    	String parmName = "";
    	String parmValue = "";
        
        Enumeration<String> parmNamesEnum = request.getParameterNames();
        while (parmNamesEnum.hasMoreElements()) {
        	parmName = parmNamesEnum.nextElement();
        	parmValue = request.getParameter(parmName);
        	params.put(parmName, parmValue);
        	logger.debug("Parm Found: Key=" + parmName + " Value=" + parmValue);
        }  
        
        trans.setRequestParameters(params);
        
        //String cashStr = "[{\"accountName\":\"Personal Checking\",\"accountNumber\":\"577662211-1\",\"availableBalance\":3201.33,\"currentBalance\":3345.02},{\"accountName\":\"Personal Savings\",\"accountNumber\":\"577662211-0\",\"availableBalance\":5040.93,\"currentBalance\":5045.93}]";
        //String loanStr = "[{\"accountName\":\"Auto Loan\",\"accountNumber\":\"577662255-1\",\"currentBalance\":18345.55,\"paymentDueAmt\":683.42,\"paymentDueDate\":\"08-23-2020\"},{\"accountName\":\"Home Loan\",\"accountNumber\":\"577662256-2\",\"currentBalance\":243678.41,\"paymentDueAmt\":2892.0,\"paymentDueDate\":\"09-01-2020\"}]";
        
		//Map<String, String> payLoads = new HashMap<String, String>();
		//payLoads.put("AUTHENTICATION", cashStr);
		//payLoads.put("loanAccounts", loanStr);
		
		//trans.setJsonPayloads(payLoads);
        
        String currentActionReturn = gson.toJson(trans);
        
        currentActionReturn = this.processActionReturns(priorActionReturn, currentActionReturn);
        
        
        long load_duration = ThreadLocalRandom.current().nextLong(this.props.minDuration, this.props.maxDuration);

        if (this.props.isCanSpike()) {
            Random rnd = new Random();
            if (rnd.nextDouble() <= this.props.getPctSlowTxns()) {
                logger.info("Spiking processing");
                load_duration = (long) Math.round(load_duration * this.props.getSpikeMultiplier());
            }
        }

        ArrayList<BusyThread> threads = new ArrayList<>();

        // Start threads
        for (int thread = 0; thread < this.props.numCores * this.props.numThreadsPerCore; thread++) {
            BusyThread t = new BusyThread("Thread" + thread, this.props.load, load_duration);
            t.start();
            threads.add(t);
        }

        // Wait for threads to finish
        try {
            threads.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException ie) {
                    logger.error("InterruptedException in ProcessingAction", ie);
                }
            });
        } catch (Exception e) {
            logger.error("Exception in ProcessingAction", e);
        }
        
        return currentActionReturn;
    }

    /**
     * LoginActionProperties
     */
    public class LoginActionProperties {
        private long minDuration;
        private long maxDuration;
        private int numCores;
        private int numThreadsPerCore;
        private double load;
        private boolean canSpike;
        private double pctSlowTxns;
        private double spikeMultiplier;

        /**
         * Gets the minimum duration of the load
         * 
         * @return Minimum duration in milliseconds
         */
        public long getMinDuration() {
            return minDuration;
        }

        /**
         * Sets the minimum duration of the load
         * 
         * @param minDuration Minimum duration in milliseconds
         */
        public void setMinDuration(long minDuration) {
            this.minDuration = minDuration;
        }

        /**
         * Gets the maximum duration of the load
         * 
         * @return Maximum duration in milliseconds
         */
        public long getMaxDuration() {
            return maxDuration;
        }

        /**
         * Sets the maximum duration of the load
         * 
         * @param maxDuration Max duration in milliseconds
         */
        public void setMaxDuration(long maxDuration) {
            this.maxDuration = maxDuration;
        }

        /**
         * Gets the number of CPU cores for the load
         * 
         * @return Number of CPU cores
         */
        public int getNumCores() {
            return numCores;
        }

        /**
         * Sets the number of CPU cores for the load
         * 
         * @param numCores Number of CPU cores
         */
        public void setNumCores(int numCores) {
            this.numCores = numCores;
        }

        /**
         * Gets the number of threads per core for the load
         * 
         * @return Number of threads per core
         */
        public int getNumThreadsPerCore() {
            return numThreadsPerCore;
        }

        /**
         * Sets the number of threads per core for the load
         * 
         * @param numThreadsPerCore Number of threads per core
         */
        public void setNumThreadsPerCore(int numThreadsPerCore) {
            this.numThreadsPerCore = numThreadsPerCore;
        }

        /**
         * Gets the CPU load percentage
         * 
         * @return CPU load percentage (0.0-1.0)
         */
        public double getLoad() {
            return load;
        }

        /**
         * Sets the CPU load percentage
         * 
         * @param load CPU load percentage (0.0-1.0)
         */
        public void setLoad(double load) {
            this.load = load;
        }

        /**
         * Returns whether or not this can spike
         * 
         * @return true/false
         */
        public boolean isCanSpike() {
            return canSpike;
        }

        /**
         * Sets whether or not the processing action can spike
         * 
         * @param canSpike
         */
        public void setCanSpike(boolean canSpike) {
            this.canSpike = canSpike;
        }

        /**
         * Gets the percentage of times to spike the processing
         * 
         * @return Percentage (0.0-1.0)
         */
        public double getPctSlowTxns() {
            return pctSlowTxns;
        }

        /**
         * Sets the percentage of times to spike the processing
         * 
         * @param pctSlowTxns Percentage (0.0-1.0)
         */
        public void setPctSlowTxns(double pctSlowTxns) {
            this.pctSlowTxns = pctSlowTxns;
        }

        /**
         * Gets the spike multiplier
         * 
         * @return Value by which to multiply the load duration
         */
        public double getSpikeMultiplier() {
            if (this.spikeMultiplier == 0.0) {
                return 1.0;
            } else {
                return spikeMultiplier;
            }
        }

        /**
         * Sets the spike multiplier
         * 
         * @param spikeMultiplier Value by which to multiply the duration
         */
        public void setSpikeMultiplier(double spikeMultiplier) {
            if (spikeMultiplier <= 1.0) {
                this.spikeMultiplier = 1.0;
            } else {
                this.spikeMultiplier = spikeMultiplier;
            }
        }

    }

    /**
     * Thread that actually generates the given load
     */
    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * 
         * @param name     Name of this thread
         * @param load     Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}