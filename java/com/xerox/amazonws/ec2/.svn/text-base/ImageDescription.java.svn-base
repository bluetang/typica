//
// typica - A client library for Amazon Web Services
// Copyright (C) 2007 Xerox Corporation
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

import java.util.List;

/**
 * An instance of this class represents an AMI description.
 * <p>
 * Instances are returned by calls to {@link com.xerox.amazonws.ec2.Jec2#describeImages(List)} or
 * {@link com.xerox.amazonws.ec2.Jec2#describeImages(String[])}.
 */
public class ImageDescription {
	private String imageId;
	private String imageLocation;
	private String imageOwnerId;
	private String imageState;
	private boolean isPublic;
	private List<String> productCodes;
	private String architecture;
	private String imageType;
	private String kernelId;
	private String ramdiskId;
	private String platform;

	private String reason;
	private String imageOwnerAlias;
	private String name;
	private String description;
	private String rootDeviceType;
	private String rootDeviceName;
	private List<BlockDeviceMapping> blockDeviceMapping;
	private String virtualizationType;

	public ImageDescription(String id, String loc, String owner,
			String state, Boolean isPublic, List<String> productCodes,
			String architecture, String imageType, String kernelId, String ramdiskId,
			String platform, String reason, String imageOwnerAlias, String name,
			String description, String rootDeviceType, String rootDeviceName,
			List<BlockDeviceMapping> blockDeviceMapping, String virtualizationType) {
		this.imageId = id;
		this.imageLocation = loc;
		this.imageOwnerId = owner;
		this.imageState = state;
		this.isPublic = isPublic;
		this.productCodes = productCodes;
		this.architecture = architecture;
		this.imageType = imageType;
		this.kernelId = kernelId;
		this.ramdiskId = ramdiskId;
		this.platform = platform;
		this.reason = reason;
		this.imageOwnerAlias = imageOwnerAlias;
		this.name = name;
		this.description = description;
		this.rootDeviceType = rootDeviceType;
		this.rootDeviceName = rootDeviceName;
		this.blockDeviceMapping = blockDeviceMapping;
		this.virtualizationType = virtualizationType;
	}

	public String getImageId() {
		return imageId;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public String getImageOwnerId() {
		return imageOwnerId;
	}

	public String getImageState() {
		return imageState;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public List<String> getProductCodes() {
		return productCodes;
	}

	public String getArchitecture() {
		return architecture;
	}

	public String getImageType() {
		return imageType;
	}

	public String getKernelId() {
		return kernelId;
	}

	public String getRamdiskId() {
		return ramdiskId;
	}

	public String getPlatform() {
		return platform;
	}

	public String getReason() {
		return reason;
	}

	public String getImageOwnerAlias() {
		return imageOwnerAlias;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getRootDeviceType() {
		return rootDeviceType;
	}

	public String getRootDeviceName() {
		return rootDeviceName;
	}

	public List<BlockDeviceMapping> getBlockDeviceMapping() {
		return blockDeviceMapping;
	}

	public String getVirtualizationType() {
		return virtualizationType;
	}

	public String toString() {
		return "Image[ID=" + imageId + ", Loc=" + imageLocation + ", own="
				+ imageOwnerId + ", state=" + imageState + " isPublic="
				+ isPublic + ", arch=" + architecture + ", imgTyp="
				+ imageType + ", kernelId=" + kernelId + ", ramdiskId="
				+ ramdiskId + ", platform=" + platform + ", reason="
				+ reason + ", imgOwnrAlias=" + imageOwnerAlias + ", name="
				+ name + ", descrip=" + description + ", rootDevType="
				+ rootDeviceType + ", rootDevName=" + rootDeviceName + "]";
	}
}

