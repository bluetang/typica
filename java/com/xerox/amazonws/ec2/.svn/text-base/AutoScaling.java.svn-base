//
// typica - A client library for Amazon Web Services
// Copyright (C) 2007,2008,2009 Xerox Corporation
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.xerox.amazonws.ec2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.monitoring.StandardUnit;
import com.xerox.amazonws.monitoring.Statistics;
import com.xerox.amazonws.common.AWSException;
import com.xerox.amazonws.common.AWSQueryConnection;
import com.xerox.amazonws.typica.autoscale.jaxb.CreateAutoScalingGroupResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DeleteAutoScalingGroupResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DeleteTriggerResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DescribeTriggersResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DescribeScalingActivitiesResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DescribeScalingActivitiesResult;
import com.xerox.amazonws.typica.autoscale.jaxb.DescribeAutoScalingGroupsResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.CreateOrUpdateScalingTriggerResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.CreateLaunchConfigurationResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DeleteLaunchConfigurationResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DescribeLaunchConfigurationsResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.DescribeLaunchConfigurationsResult;
import com.xerox.amazonws.typica.autoscale.jaxb.LaunchConfigurations;
import com.xerox.amazonws.typica.autoscale.jaxb.SetDesiredCapacityResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.TerminateInstanceInAutoScalingGroupResponse;
import com.xerox.amazonws.typica.autoscale.jaxb.TerminateInstanceInAutoScalingGroupResult;
import com.xerox.amazonws.typica.autoscale.jaxb.UpdateAutoScalingGroupResponse;

/**
 * A Java wrapper for the AutoScaling web services API
 */
public class AutoScaling extends AWSQueryConnection {

    private static Log logger = LogFactory.getLog(AutoScaling.class);

	/**
	 * Initializes the AutoScaling service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
	 */
    public AutoScaling(String awsAccessId, String awsSecretKey) {
        this(awsAccessId, awsSecretKey, true);
    }

	/**
	 * Initializes the AutoScaling service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from EC2.
	 */
    public AutoScaling(String awsAccessId, String awsSecretKey, boolean isSecure) {
        this(awsAccessId, awsSecretKey, isSecure, "autoscaling.amazonaws.com");
    }

	/**
	 * Initializes the AutoScaling service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from EC2.
     * @param server Which host to connect to.  Usually, this will be autoscaling.amazonaws.com
	 */
    public AutoScaling(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server)
    {
        this(awsAccessId, awsSecretKey, isSecure, server,
             isSecure ? 443 : 80);
    }

    /**
	 * Initializes the AutoScaling service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from EC2.
     * @param server Which host to connect to.  Usually, this will be autoscaling.amazonaws.com
     * @param port Which port to use.
     */
    public AutoScaling(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server, int port)
    {
		super(awsAccessId, awsSecretKey, isSecure, server, port);
		ArrayList<String> vals = new ArrayList<String>();
		vals.add("2009-05-15");
		super.headers.put("Version", vals);
    }

	/**
	 * Create a launch configuration
	 * 
	 * @param config the launch configuration
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void createLaunchConfiguration(LaunchConfiguration config) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("LaunchConfigurationName", config.getConfigName());
		params.put("ImageId", config.getImageId());
		//params.put("MinCount", "" + config.getMinCount());
		//params.put("MaxCount", "" + config.getMaxCount());

		byte[] userData = config.getUserData();
		if (userData != null && userData.length > 0) {
			params.put("UserData",
			new String(Base64.encodeBase64(userData)));
		}
		params.put("AddressingType", "public");
		String keyName = config.getKeyName();
		if (keyName != null && !keyName.trim().equals("")) {
			params.put("KeyName", keyName);
		}

		if (config.getSecurityGroup() != null) {
			for(int i = 0; i < config.getSecurityGroup().size(); i++) {
				params.put("SecurityGroup." + (i + 1), config.getSecurityGroup().get(i));
			}
		}
		params.put("InstanceType", config.getInstanceType().getTypeId());
		if (config.getAvailabilityZone() != null && !config.getAvailabilityZone().trim().equals("")) {
			params.put("Placement.AvailabilityZone", config.getAvailabilityZone());
		}
		if (config.getKernelId() != null && !config.getKernelId().trim().equals("")) {
			params.put("KernelId", config.getKernelId());
		}
		if (config.getRamdiskId() != null && !config.getRamdiskId().trim().equals("")) {
			params.put("RamdiskId", config.getRamdiskId());
		}
		if (config.getBlockDevicemappings() != null) {
			for(int i = 0; i < config.getBlockDevicemappings().size(); i++) {
				BlockDeviceMapping bdm = config.getBlockDevicemappings().get(i);
				params.put("BlockDeviceMapping." + (i + 1) + ".VirtualName",
				bdm.getVirtualName());
				params.put("BlockDeviceMapping." + (i + 1) + ".DeviceName",
				bdm.getDeviceName());
			}
		}

		HttpGet method = new HttpGet();
	//	CreateLaunchConfigurationResponse response =
			makeRequestInt(method, "CreateLaunchConfiguration", params, CreateLaunchConfigurationResponse.class);
	}

	/**
	 * Delete a launch configuration
	 * 
	 * @param configName the name of the configuration to delete
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void deleteLaunchConfiguration(String configName) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("LaunchConfigurationName", configName);
		HttpGet method = new HttpGet();
	//	DeleteLaunchConfigurationResponse response =
			makeRequestInt(method, "DeleteLaunchConfiguration", params, DeleteLaunchConfigurationResponse.class);
	}

	/**
	 * Describe the launch configurations that have been created
	 * 
	 * @param configNames the names of the configurations to show, null for all
	 * @return A list of {@link LaunchConfiguration} configs
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public List<LaunchConfiguration> describeLaunchConfigurations(List<String> configNames) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		if (configNames != null && configNames.size() > 0) {
			int i = 0;
			for (String name : configNames) {
				params.put("LaunchConfigurationNames.member."+(i+1), name);
				i++;
			}
		}
		HttpGet method = new HttpGet();
		DescribeLaunchConfigurationsResponse response =
				makeRequestInt(method, "DescribeLaunchConfigurations", params, DescribeLaunchConfigurationsResponse.class);
		List<com.xerox.amazonws.typica.autoscale.jaxb.LaunchConfiguration> result =
					response.getDescribeLaunchConfigurationsResult().getLaunchConfigurations().getMembers();
		List<LaunchConfiguration> ret = new ArrayList<LaunchConfiguration>();
		for (com.xerox.amazonws.typica.autoscale.jaxb.LaunchConfiguration config : result) {
			LaunchConfiguration newConfig = new LaunchConfiguration(config.getLaunchConfigurationName(),
											config.getImageId(), 1, 1);
			newConfig.setKeyName(config.getKeyName());
			newConfig.setSecurityGroup(config.getSecurityGroups().getMembers());
			newConfig.setUserData(config.getUserData().getBytes());
			newConfig.setInstanceType(InstanceType.getTypeFromString(config.getInstanceType()));
			newConfig.setKernelId(config.getKernelId());
			newConfig.setRamdiskId(config.getRamdiskId());
			List<BlockDeviceMapping> mappings = new ArrayList<BlockDeviceMapping>();
			for (com.xerox.amazonws.typica.autoscale.jaxb.BlockDeviceMapping mapping : config.getBlockDeviceMappings().getMembers()) {
				mappings.add(new BlockDeviceMapping(mapping.getVirtualName(), mapping.getDeviceName()));
			}
			newConfig.setBlockDevicemappings(mappings);
			ret.add(newConfig);
		}
		return ret;
	}

	/**
	 * Terminates a running instance.
	 * 
	 * @param instanceId An instance id
	 * @param shouldDecrement true of desired capacity should be decremented at the same time
	 * @return activity description
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public Activity terminateInstancesInAutoScalingGroup(String instanceId, boolean shouldDecrement) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("InstanceId", instanceId);
		params.put("ShouldDecrementDesiredCapacity", shouldDecrement?"true":"false");
		HttpGet method = new HttpGet();
		TerminateInstanceInAutoScalingGroupResponse response =
				makeRequestInt(method, "TerminateInstanceInAutoScalingGroup", params, TerminateInstanceInAutoScalingGroupResponse.class);
		TerminateInstanceInAutoScalingGroupResult result = response.getTerminateInstanceInAutoScalingGroupResult();
		com.xerox.amazonws.typica.autoscale.jaxb.Activity activity = result.getActivity();
		Activity ret = new Activity(activity.getActivityId(), activity.getDescription(),
						activity.getCause(), activity.getStartTime().toString(),
						activity.getEndTime().toString(), activity.getStatusCode(),
						activity.getStatusMessage(),
						activity.getProgress().intValue());
		return ret;
	}

	/**
	 * Describes the autoScaling activities for a given group.
	 * 
	 * @param activityIds activity ids used to filter the list, null for all
	 * @param autoScalingGroupName an auto scaling group name
	 * @return activity descriptions
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public List<Activity> describeScalingActivities(List<String> activityIds, String autoScalingGroupName) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		if (activityIds != null && activityIds.size() > 0) {
			int i = 0;
			for (String id : activityIds) {
				params.put("ActivityIds.member."+(i+1), id);
				i++;
			}
		}
		params.put("AutoScalingGroupName", autoScalingGroupName);
		HttpGet method = new HttpGet();
		List<Activity> ret = new ArrayList<Activity>();
		String nextToken = null;
		do {
			if (nextToken != null) {
				params.put("NextToken", nextToken);
			}
			DescribeScalingActivitiesResponse response =
					makeRequestInt(method, "DescribeScalingActivities", params, DescribeScalingActivitiesResponse.class);

			DescribeScalingActivitiesResult result = response.getDescribeScalingActivitiesResult();
			List<com.xerox.amazonws.typica.autoscale.jaxb.Activity> activities =
													result.getActivities().getMembers();
			for (com.xerox.amazonws.typica.autoscale.jaxb.Activity activity : activities) {
				Activity newActivity = new Activity(activity.getActivityId(), activity.getDescription(),
								activity.getCause(), activity.getStartTime().toString(),
								activity.getEndTime().toString(), activity.getStatusCode(),
								activity.getStatusMessage(),
								activity.getProgress().intValue());
				ret.add(newActivity);
			}
			nextToken = result.getNextToken();
		} while (nextToken != null);
		return ret;
	}

	/**
	 * Creates a scaling trigger, or updates an existing one
	 * 
	 * @param trigger the information about the trigger
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void createOrUpdateScalingTrigger(ScalingTrigger trigger) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TriggerName", trigger.getName());
		params.put("AutoScalingGroupName", trigger.getAutoScalingGroupName());
		params.put("MeasureName", trigger.getMeasureName());
		params.put("Statistic", trigger.getStatistic().getStatId());
		Map<String, String> dimensions = trigger.getDimensions();
		if (dimensions != null && dimensions.size() > 0) {
			int i=0;
			for (String key : dimensions.keySet()) {
				String value = dimensions.get(key);
				params.put("Dimensions.member."+(i+1)+".Name", key);
				params.put("Dimensions.member."+(i+1)+".Value", value);
				i++;
			}
		}
		params.put("Period", ""+trigger.getPeriod());
		if (trigger.getUnit() != null) {
			params.put("Unit", trigger.getUnit().getUnitId());
		}
		String tmp = trigger.getCustomUnit();
		if (tmp != null && !tmp.equals("")) {
			params.put("CustomUnit", tmp);
		}
		params.put("LowerThreshold", ""+trigger.getLowerThreshold());
		params.put("LowerBreachScaleIncrement", trigger.getLowerBreachScaleIncrement());
		params.put("UpperThreshold", ""+trigger.getUpperThreshold());
		params.put("UpperBreachScaleIncrement", trigger.getUpperBreachScaleIncrement());
		params.put("BreachDuration", ""+trigger.getBreachDuration());
		HttpGet method = new HttpGet();
	//	CreateOrUpdateScalingTriggerReponse response =
			makeRequestInt(method, "CreateOrUpdateScalingTrigger", params, CreateOrUpdateScalingTriggerResponse.class);
	}

	/**
	 * Deletes a trigger
	 * 
	 * @param triggerName An trigger name
	 * @param autoScalingGroupName a autoScaling group name
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void deleteTrigger(String triggerName, String autoScalingGroupName) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TriggerName", triggerName);
		params.put("AutoScalingGroupName", autoScalingGroupName);
		HttpGet method = new HttpGet();
	//  DeleteTriggerResponse response =
			makeRequestInt(method, "DeleteTrigger", params, DeleteTriggerResponse.class);
	}

	/**
	 * Describes the scaling triggers for a given group.
	 * 
	 * @param autoScalingGroupName a autoScaling group name
	 * @return activity descriptions
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public List<ScalingTrigger> describeTriggers(String autoScalingGroupName) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("AutoScalingGroupName", autoScalingGroupName);
		HttpGet method = new HttpGet();
		DescribeTriggersResponse response =
				makeRequestInt(method, "DescribeTriggers", params, DescribeTriggersResponse.class);

		List<com.xerox.amazonws.typica.autoscale.jaxb.Trigger> result =
					response.getDescribeTriggersResult().getTriggers().getMembers();
		List<ScalingTrigger> ret = new ArrayList<ScalingTrigger>();
		for (com.xerox.amazonws.typica.autoscale.jaxb.Trigger trigger : result) {
			Map<String, String> dimensions = new HashMap<String, String>();
			List<com.xerox.amazonws.typica.autoscale.jaxb.Dimension> dims = trigger.getDimensions().getMembers();
			for (com.xerox.amazonws.typica.autoscale.jaxb.Dimension dim : dims) {
				dimensions.put(dim.getName(), dim.getValue());
			}
			ScalingTrigger newTrigger = new ScalingTrigger(trigger.getTriggerName(), trigger.getAutoScalingGroupName(),
							trigger.getMeasureName(), Statistics.getTypeFromString(trigger.getStatistic()),
							dimensions, trigger.getPeriod().intValue(),
							StandardUnit.getTypeFromString(trigger.getUnit()), trigger.getCustomUnit(),
							trigger.getLowerThreshold(),
							trigger.getLowerBreachScaleIncrement(),
							trigger.getUpperThreshold(),
							trigger.getUpperBreachScaleIncrement(),
							trigger.getBreachDuration().intValue(),
							trigger.getStatus(), trigger.getCreatedTime().toGregorianCalendar());
			ret.add(newTrigger);
		}
		return ret;
	}

	/**
	 * Creates a new auto scaling group
	 * 
	 * @param autoScalingGroupName a autoScaling group name
	 * @param launchConfigurationName name of launch configuration for this group
	 * @param minSize min number of servers in this group
	 * @param maxSize max number of servers in this group (must be &lt; 1000)
	 * @param cooldown number of seconds to wait before adjusting capacity
	 * @param availabilityZones zones for this group
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void createAutoScalingGroup(String autoScalingGroupName, String launchConfigurationName, int minSize, int maxSize,
							int cooldown, List<String> availabilityZones) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("AutoScalingGroupName", autoScalingGroupName);
		params.put("LaunchConfigurationName", launchConfigurationName);
		params.put("MinSize", ""+minSize);
		params.put("MaxSize", ""+maxSize);
		params.put("Cooldown", ""+cooldown);
		int i=0;
		for (String zone : availabilityZones) {
			params.put("AvailabilityZones.member."+(i+1), zone);
			i++;
		}
		HttpGet method = new HttpGet();
	//  CreateAutoScalingGroupResponse response =
			makeRequestInt(method, "CreateAutoScalingGroup", params, CreateAutoScalingGroupResponse.class);
	}

	/**
	 * Deletes a auto scaling group
	 * 
	 * @param autoScalingGroupName a autoScaling group name
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void deleteAutoScalingGroup(String autoScalingGroupName) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("AutoScalingGroupName", autoScalingGroupName);
		HttpGet method = new HttpGet();
	//  DeleteAutoScalingGroupResponse response =
			makeRequestInt(method, "DeleteAutoScalingGroup", params, DeleteAutoScalingGroupResponse.class);
	}

	/**
	 * Describes one or more auto scaling groups
	 * 
	 * @param autoScalingGroupNames a auto scaling group name
	 * @return autoScaling group descriptions
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public List<AutoScalingGroup> describeAutoScalingGroups(List<String> autoScalingGroupNames) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		if (autoScalingGroupNames != null && autoScalingGroupNames.size() > 0) {
			int i=0;
			for (String name : autoScalingGroupNames) {
				params.put("AutoScalingGroupNames.member."+(i+1), name);
				i++;
			}
		}
		HttpGet method = new HttpGet();
		DescribeAutoScalingGroupsResponse response =
				makeRequestInt(method, "DescribeAutoScalingGroups", params, DescribeAutoScalingGroupsResponse.class);

		List<com.xerox.amazonws.typica.autoscale.jaxb.AutoScalingGroup> result =
					response.getDescribeAutoScalingGroupsResult().getAutoScalingGroups().getMembers();
		List<AutoScalingGroup> ret = new ArrayList<AutoScalingGroup>();
		for (com.xerox.amazonws.typica.autoscale.jaxb.AutoScalingGroup group : result) {
			List<String> zones = new ArrayList<String>();
			for (String zone : group.getAvailabilityZones().getMembers()) {
				zones.add(zone);
			}
			AutoScalingGroup newGroup = new AutoScalingGroup(group.getAutoScalingGroupName(),
							group.getLaunchConfigurationName(), group.getMinSize().intValue(),
							group.getMaxSize().intValue(),
							group.getDesiredCapacity().intValue(),
							group.getCooldown().intValue(),
							zones, group.getCreatedTime().toGregorianCalendar());
			List<com.xerox.amazonws.typica.autoscale.jaxb.Instance> instList = group.getInstances().getMembers();
			for (com.xerox.amazonws.typica.autoscale.jaxb.Instance inst : instList) {
				newGroup.addInstance(inst.getInstanceId(), inst.getLifecycleState());
			}
			ret.add(newGroup);
		}
		return ret;
	}

	/**
	 * Adjusts a auto scaling groups' capacity
	 * 
	 * @param autoScalingGroupName a autoScaling group name
	 * @param desiredCapacity the new capacity setting
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void setDesiredCapacity(String autoScalingGroupName, int desiredCapacity) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("AutoScalingGroupName", autoScalingGroupName);
		params.put("DesiredCapacity", ""+desiredCapacity);
		HttpGet method = new HttpGet();
	//  SetDesiredCapacityResponse response =
			makeRequestInt(method, "SetDesiredCapacity", params, SetDesiredCapacityResponse.class);
	}

	/**
	 * Update a auto scaling group
	 * 
	 * @param autoScalingGroupName a autoScaling group name
	 * @param launchConfigurationName name of launch configuration for this group
	 * @param minSize min number of servers in this group
	 * @param maxSize max number of servers in this group (must be &lt; 1000)
	 * @param defaultCooldown number of seconds to wait before adjusting capacity
	 * @throws AutoScalingException wraps checked exceptions
	 */
	public void updateAutoScalingGroup(String autoScalingGroupName, String launchConfigurationName, int minSize, int maxSize,
							int defaultCooldown) throws AutoScalingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("AutoScalingGroupName", autoScalingGroupName);
		params.put("LaunchConfigurationName", launchConfigurationName);
		params.put("MinSize", ""+minSize);
		params.put("MaxSize", ""+maxSize);
		params.put("DefaultCooldown", ""+defaultCooldown);
		HttpGet method = new HttpGet();
	//  UpdateAutoScalingGroupResponse response =
			makeRequestInt(method, "UpdateAutoScalingGroup", params, UpdateAutoScalingGroupResponse.class);
	}

	protected <T> T makeRequestInt(HttpRequestBase method, String action, Map<String, String> params, Class<T> respType)
		throws AutoScalingException {
		try {
			return makeRequest(method, action, params, respType);
		} catch (AWSException ex) {
			throw new AutoScalingException(ex);
		} catch (JAXBException ex) {
			throw new AutoScalingException("Problem parsing returned message.", ex);
		} catch (SAXException ex) {
			throw new AutoScalingException("Problem parsing returned message.", ex);
		} catch (MalformedURLException ex) {
			throw new AutoScalingException(ex.getMessage(), ex);
		} catch (IOException ex) {
			throw new AutoScalingException(ex.getMessage(), ex);
		} catch (HttpException ex) {
			throw new AutoScalingException(ex.getMessage(), ex);
		}
	}
}
