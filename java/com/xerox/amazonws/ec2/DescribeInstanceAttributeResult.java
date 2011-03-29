//
// typica - A client library for Amazon Web Services
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

import java.util.ArrayList;
import java.util.List;

import com.xerox.amazonws.typica.jaxb.DescribeInstanceAttributeResponse;
import com.xerox.amazonws.typica.jaxb.NullableAttributeValueType;
import com.xerox.amazonws.typica.jaxb.NullableAttributeBooleanValueType;
import com.xerox.amazonws.typica.jaxb.InstanceBlockDeviceMappingResponseType;
import com.xerox.amazonws.typica.jaxb.InstanceBlockDeviceMappingResponseItemType;

/**
 * The results of a call to describe snapshot attributes. 
 */
public class DescribeInstanceAttributeResult {
	private String requestId;
	private String instanceId;
	private InstanceType instanceType;
	private String kernelId;
	private String ramdiskId;
	private String userData;
	private boolean disableApiTermination;
	private String instanceInitiatedShutdownBehavior;
	private String rootDeviceName;
	private List<BlockDeviceMapping> blockDeviceMappings;

	public DescribeInstanceAttributeResult(String requestId, String instanceId) {
		this.requestId = requestId;
		this.instanceId = instanceId;
		blockDeviceMappings = new ArrayList<BlockDeviceMapping>();
	}

	DescribeInstanceAttributeResult(DescribeInstanceAttributeResponse response) {
		requestId = response.getRequestId();
		instanceId = response.getInstanceId();
		
		NullableAttributeValueType val = response.getInstanceType();
		if (val != null) instanceType = InstanceType.getTypeFromString(val.getValue());

		val = response.getKernel();
		if (val != null) kernelId = val.getValue();

		val = response.getRamdisk();
		if (val != null) ramdiskId = val.getValue();

		val = response.getUserData();
		if (val != null) userData = val.getValue();

		NullableAttributeBooleanValueType bool = response.getDisableApiTermination();
		if (bool != null) disableApiTermination = bool.isValue();

		val = response.getInstanceInitiatedShutdownBehavior();
		if (val != null) instanceInitiatedShutdownBehavior = val.getValue();

		val = response.getRootDeviceName();
		if (val != null) rootDeviceName = val.getValue();

		blockDeviceMappings = new ArrayList<BlockDeviceMapping>();
		InstanceBlockDeviceMappingResponseType bdmSet = response.getBlockDeviceMapping();
		if (bdmSet != null) {
			for (InstanceBlockDeviceMappingResponseItemType mapping : bdmSet.getItems()) {
//				blockDeviceMappings.add(new BlockDeviceMapping(mapping.getVirtualName(), mapping.getDeviceName()));
			}
		}
	}

	public String getRequestId() {
		return requestId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public InstanceType getInstanceType() {
		return this.instanceType;
	}

	public String getKernelId() {
		return kernelId;
	}

	public String getRamdiskId() {
		return ramdiskId;
	}

	public String getUserData() {
		return userData;
	}

	public boolean getDisableApiTermination() {
		return disableApiTermination;
	}

	public String getInstanceInitiatedShutdownBehavior() {
		return instanceInitiatedShutdownBehavior;
	}

	public String getRootDeviceName() {
		return rootDeviceName;
	}

	public List<BlockDeviceMapping> getBlockDeviceMappings() {
		return blockDeviceMappings;
	}
}

