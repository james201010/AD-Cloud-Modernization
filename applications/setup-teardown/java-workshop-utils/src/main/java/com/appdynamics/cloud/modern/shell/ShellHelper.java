/**
 * 
 */
package com.appdynamics.cloud.modern.shell;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceBlockDeviceMapping;
import com.amazonaws.services.ec2.model.ModifyVolumeRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.EC2MetadataUtils;
import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.cloud.CloudHelper;
import com.appdynamics.cloud.modern.config.CloudAuthConfig;
import com.appdynamics.cloud.modern.config.ControllerConfig;
import com.appdynamics.cloud.modern.config.SetupConfig;
import com.appdynamics.cloud.modern.utils.StringUtils;
import com.appdynamics.cloud.modern.vault.WorkshopVault;
import com.google.gson.Gson;



/**
 * @author James Schneider
 *
 */
public class ShellHelper {

	public static Logger logr = new Logger(ShellHelper.class.getSimpleName());
	
	/**
	 * 
	 */
	public ShellHelper() {
		
	}

	public static boolean downloadDBAgent(SetupConfig setupConfig, String downloadTargetDir) throws Throwable {
		
		boolean success = false;
		try {
			WorkshopVault vault = new WorkshopVault(setupConfig.getVaultInfo());
			Gson gson = new Gson();
					
	        CloudAuthConfig cloudAuthConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "dbagent-s3-auth")), CloudAuthConfig.class);
			
			AmazonS3 s3 = null;
			
			AWSCredentialsProvider credentials = new
					AWSStaticCredentialsProvider(new
							BasicAWSCredentials(cloudAuthConfig.getAccessKey(),cloudAuthConfig.getAccessSecret()));	
			
			
			String bucketName = "appd-cloud-kickstart-tools";
			String keyName = "apm-platform/db-agent.zip";		

			s3 = AmazonS3ClientBuilder.standard().withCredentials(credentials).withRegion(Regions.US_EAST_2).build();

			GetObjectRequest request = new GetObjectRequest(bucketName, keyName);

			S3Object object = s3.getObject(request);
			
			S3ObjectInputStream objectContent = object.getObjectContent();		

			FileOutputStream target = new FileOutputStream(downloadTargetDir + "/dbagent.zip");

			byte[] buf = new byte[8192];
			int length;
			int cntr = 0;
			String downloadMsg = "   - deploying agent ....................";
			
			logr.info(downloadMsg);
			while ((length = objectContent.read(buf)) > 0) {
				cntr++;
				if (cntr == 2000) {
					
					downloadMsg = downloadMsg.substring(0, downloadMsg.length()-1);
					logr.info(downloadMsg);
					cntr = 0;
				}
				
				target.write(buf, 0, length);
			}		
			
			target.close();
		    
			success = true;
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		return success;
		
	}

	
	public static void downloadSIMAgent(SetupConfig setupConfig, String downloadTargetDir) throws Throwable {
		
		// takes right at 30 seconds to download
		
		//logr.info(" - Starting server agent deployment");
		//logr.info(" - Starting server agent download");
	
		WorkshopVault vault = new WorkshopVault(setupConfig.getVaultInfo());
		Gson gson = new Gson();
				
        CloudAuthConfig cloudAuthConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "dbagent-s3-auth")), CloudAuthConfig.class);
		
		AmazonS3 s3 = null;
		
		AWSCredentialsProvider credentials = new
				AWSStaticCredentialsProvider(new
						BasicAWSCredentials(cloudAuthConfig.getAccessKey(),cloudAuthConfig.getAccessSecret()));	
		
		
		String bucketName = "appd-cloud-kickstart-tools";
		String keyName = "apm-platform/sim-agent.zip";		

		s3 = AmazonS3ClientBuilder.standard().withCredentials(credentials).withRegion(Regions.US_EAST_2).build();

		GetObjectRequest request = new GetObjectRequest(bucketName, keyName);

		S3Object object = s3.getObject(request);
		
		S3ObjectInputStream objectContent = object.getObjectContent();		

		FileOutputStream target = new FileOutputStream(downloadTargetDir + "/simagent.zip");

		byte[] buf = new byte[8192];
		int length;
		int cntr = 0;
		String downloadMsg = "   - deploying agent ....................";
		
		logr.info(downloadMsg);
		while ((length = objectContent.read(buf)) > 0) {
			cntr++;
			if (cntr == 2000) {
				
				downloadMsg = downloadMsg.substring(0, downloadMsg.length()-1);
				logr.info(downloadMsg);
				cntr = 0;
			}
			
			target.write(buf, 0, length);
		}		
		
		target.close();
	    
		//logr.info(" - Finished server agent download");
	}
	
	public static void executeTeardownScript(String shellScriptFile, boolean inheritIO) throws Throwable {
		
		ProcessBuilder pb;
		Process p;
        	
		pb = new ProcessBuilder(shellScriptFile);
		
		if (inheritIO) {
			pb.inheritIO();
		}
		
		p = pb.start();
		p.waitFor();
		
		
	}
	
	public static boolean executeShellScript(SetupConfig setupConfig, String shellScriptFile, String[] shellArguments, ShellTaskResults shellResults, boolean inheritIO) throws Throwable {
		
		boolean success = false;
		try {
			ProcessBuilder pb;
			Process p;
	        
			if (shellArguments != null && shellArguments.length > 0) {
				
				List <String> commands = new ArrayList <String>();
				commands.add(shellScriptFile);
				
				for (int i = 0; i < shellArguments.length; i++) {
					commands.add(shellArguments[i]);
				}
				
				pb = new ProcessBuilder(commands);
				
			} else {
				
				pb = new ProcessBuilder(shellScriptFile);
			}
			
			if (inheritIO) {
				pb.inheritIO();
			}
			
			p = pb.start();
			
			int exitStatus = p.waitFor();
			
			logr.info(" - Exit status = " + exitStatus);
			
			if (exitStatus == 0) {
				success =  true;
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		return success;
	}
	
	public static boolean launchDBAgent(String uniqueHostId, ControllerConfig controllerConfig, String dbagentHomeDir, String dbagentName, ShellTaskResults shellResults) throws Throwable {
		
		boolean success = false;
		
		try {
			ProcessBuilder pb;
			Process p;
			File dir = new File(dbagentHomeDir);
			
			//logr.info(" - Starting database agent deployment");
			pb = new ProcessBuilder("unzip", "dbagent.zip");
			pb.directory(dir);
			//pb.inheritIO();
			p = pb.start();
			p.waitFor();
	        
			StringBuffer buff = new StringBuffer();
			
			List <String> commands = new ArrayList <String>();
			commands.add("nohup");
			//buff.append("nohup");
			
			commands.add("java");
			buff.append("java");
			
			commands.add("-Dappdynamics.controller.hostName=" + controllerConfig.getControllerHostName());
			buff.append(" -Dappdynamics.controller.hostName=" + controllerConfig.getControllerHostName());
			
			commands.add("-Dappdynamics.controller.port=" + controllerConfig.getControllerPort());
			buff.append(" -Dappdynamics.controller.port=" + controllerConfig.getControllerPort());
			
			commands.add("-Dappdynamics.controller.ssl.enabled=" + controllerConfig.getControllerSslEnabled());
			buff.append(" -Dappdynamics.controller.ssl.enabled=" + controllerConfig.getControllerSslEnabled());
			
			commands.add("-Dappdynamics.agent.accountName=" + controllerConfig.getControllerAccount());
			buff.append(" -Dappdynamics.agent.accountName=" + controllerConfig.getControllerAccount());
			
			commands.add("-Dappdynamics.agent.accountAccessKey=" + controllerConfig.getControllerAccessKey());
			buff.append(" -Dappdynamics.agent.accountAccessKey=" + controllerConfig.getControllerAccessKey());
			
			commands.add("-Dappdynamics.agent.maxMetrics=300000");
			buff.append(" -Dappdynamics.agent.maxMetrics=300000");

			commands.add("-Dappdynamics.agent.uniqueHostId=" + uniqueHostId);
			buff.append(" -Dappdynamics.agent.uniqueHostId=" + uniqueHostId);
			
			commands.add("-Ddbagent.name=" + dbagentName);
			buff.append(" -Ddbagent.name=" + dbagentName);
			
			commands.add("-jar");
			buff.append(" -jar");
			
			commands.add("db-agent.jar");
			buff.append(" " + dbagentHomeDir + "/db-agent.jar");
			
			commands.add("&");
			//buff.append(" &");
			
			shellResults.dbAgentStartCommand = buff.toString();
			
			pb = new ProcessBuilder(commands);
			pb.directory(dir);
			//pb.inheritIO();
			p = pb.start();
			//p.waitFor();
			
			shellResults.dbAgentName = dbagentName;
			shellResults.dbAgentHomeDir = dbagentHomeDir;
			
			
			// now create the shell scripts for the DB agent start / stop when VM goes up / down
			
			String startShell = "#!/bin/bash" + StringUtils.getNewLine() + StringUtils.getNewLine() + shellResults.dbAgentStartCommand + StringUtils.getNewLine() + StringUtils.getNewLine();
			StringUtils.saveStringAsFile(dbagentHomeDir + "/.svc/start.sh", startShell);
			ProcessBuilder shpb1 = new ProcessBuilder("chmod", "+x", dbagentHomeDir + "/.svc/start.sh");
			shpb1.start();
			
			
			String stopShell = "#!/bin/bash" + StringUtils.getNewLine() + StringUtils.getNewLine() + "pkill -f db-agent.jar" + StringUtils.getNewLine() + StringUtils.getNewLine();
			StringUtils.saveStringAsFile(dbagentHomeDir + "/.svc/stop.sh", stopShell);
			ProcessBuilder shpb2 = new ProcessBuilder("chmod", "+x", dbagentHomeDir + "/.svc/stop.sh");
			shpb2.start();
			
			success = true;
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		return success;
	}

	public static void launchSIMAgent(ControllerConfig controllerConfig, String simAgentHomeDir, ShellTaskResults shellResults) throws Throwable {
		
		ProcessBuilder pb;
		Process p;
		File dir = new File(simAgentHomeDir);
		
		//logr.info(" - Starting server agent deployment");
		pb = new ProcessBuilder("unzip", "simagent.zip");
		pb.directory(dir);
		//pb.inheritIO();
		p = pb.start();
		p.waitFor();
        
		StringBuffer buff = new StringBuffer();
		
		List <String> commands = new ArrayList <String>();
		commands.add("nohup");
		buff.append("nohup");
		
		commands.add("java");
		buff.append(" java");
		
		commands.add("-Dappdynamics.controller.hostName=" + controllerConfig.getControllerHostName());
		buff.append(" -Dappdynamics.controller.hostName=" + controllerConfig.getControllerHostName());
		
		commands.add("-Dappdynamics.controller.port=" + controllerConfig.getControllerPort());
		buff.append(" -Dappdynamics.controller.port=" + controllerConfig.getControllerPort());
		
		commands.add("-Dappdynamics.controller.ssl.enabled=" + controllerConfig.getControllerSslEnabled());
		buff.append(" -Dappdynamics.controller.ssl.enabled=" + controllerConfig.getControllerSslEnabled());
		
		commands.add("-Dappdynamics.agent.accountName=" + controllerConfig.getControllerAccount());
		buff.append(" -Dappdynamics.agent.accountName=" + controllerConfig.getControllerAccount());
		
		commands.add("-Dappdynamics.agent.accountAccessKey=" + controllerConfig.getControllerAccessKey());
		buff.append(" -Dappdynamics.agent.accountAccessKey=" + controllerConfig.getControllerAccessKey());
				
		commands.add("-Dappdynamics.agent.maxMetrics=300000");
		buff.append(" -Dappdynamics.agent.maxMetrics=300000");

		commands.add("-Dappdynamics.sim.enabled=true");
		buff.append(" -Dappdynamics.sim.enabled=true");
		
		commands.add("-Dappdynamics.docker.enabled=true");
		buff.append(" -Dappdynamics.docker.enabled=true");

		commands.add("-Dappdynamics.docker.container.containerIdAsHostId.enabled=true");
		buff.append(" -Dappdynamics.docker.container.containerIdAsHostId.enabled=true");
		
		commands.add("-jar");
		buff.append(" -jar");
		
		commands.add("machineagent.jar");
		buff.append(" machineagent.jar");
		
		commands.add("&");
		buff.append(" &");
		
		//shellResults.simAgentStartCommand = buff.toString();
		//shellResults.simAgentHomeDir = simAgentHomeDir;
		
		
		pb = new ProcessBuilder(commands);
		pb.directory(dir);
		//pb.inheritIO();
		p = pb.start();
		//p.waitFor();
		
		
		
		
		
		
		//logr.info(" - Finished server agent deployment");
		
	}
	
	public static boolean expandEC2InstanceVolume(SetupConfig setupConfig, int newSizeInGB, ShellTaskResults shellResults) throws Throwable {
		
		boolean success = false;
		
		try {
			CloudHelper.initSetup(setupConfig.getCloudProvider());
			
			AmazonEC2 ec2;
			
			ec2 = AmazonEC2ClientBuilder.standard().withRegion(Regions.fromName(CloudHelper.getCurrentCloudRegion())).build();
			
			String instanceId = EC2MetadataUtils.getInstanceId();
			List<String> instIds = new ArrayList<String>();
			instIds.add(instanceId);
			
			DescribeInstancesRequest dirReq = new DescribeInstancesRequest();
			
			dirReq.setInstanceIds(instIds);
			DescribeInstancesResult diRes = ec2.describeInstances(dirReq);
			
			List<Reservation> reservs = diRes.getReservations();
			
			if (reservs != null && !reservs.isEmpty()) {
			
				for (Reservation res : reservs) {
				
					List<Instance> insts =  res.getInstances();
					
					if (insts != null && !insts.isEmpty()) {
						
						for (Instance inst : insts) {
							
							
							List<InstanceBlockDeviceMapping> ibdms = inst.getBlockDeviceMappings();
							
							if (ibdms != null && !ibdms.isEmpty()) {
								
								for (InstanceBlockDeviceMapping ibdm : ibdms) {
									
									String volumeId = ibdm.getEbs().getVolumeId();
																	
									ModifyVolumeRequest mvr = new ModifyVolumeRequest();
									mvr.setSize(newSizeInGB);
									
									mvr.setVolumeId(volumeId);
									
									ec2.modifyVolume(mvr);
							
									logr.info(" - Expanding Disk Space for Volume : " + volumeId);
									
									String msg = "     - Waiting for Volume Expansion ....................................";
	                                
									logr.info(msg);
									for (int i = 0; i < 32; i++) {
									
										msg = msg.substring(0, msg.length() -1);
										Thread.currentThread().sleep(1000);
										logr.info(msg);
									}
									
									
									logr.info(" - Finished Expanding Disk Space for Volume : " + volumeId);
									
									
									return true;
									
								}
								
							}
							
							
						}
					}	
				}
			}	
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		return success;
	}
	
	
	
	
	
}
