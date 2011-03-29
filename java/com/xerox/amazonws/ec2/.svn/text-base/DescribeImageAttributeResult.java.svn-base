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
 * The results of a call to describe image attributes. 
 */
public class DescribeImageAttributeResult {
	private String imageId;
	private ImageListAttribute imageListAttribute;
	private List<String> productCodes;
	private String kernelId;
	private String ramdiskId;
	private List<BlockDeviceMapping> blockDeviceMappings;
	
	public DescribeImageAttributeResult(String imageId, ImageListAttribute imageListAttribute,
					List<String> productCodes, String kernelId, String ramdiskId,
					List<BlockDeviceMapping> blockDeviceMappings) {
		this.imageId = imageId;
		this.imageListAttribute = imageListAttribute;
		this.productCodes = productCodes;
		this.kernelId = kernelId;
		this.ramdiskId = ramdiskId;
		this.blockDeviceMappings = blockDeviceMappings;
	}

	public String getImageId() {
		return imageId;
	}

	public ImageListAttribute getImageListAttribute() {
		return imageListAttribute;
	}

	public List<String> getProductCodes() {
		return productCodes;
	}

	public String getKernelId() {
		return kernelId;
	}

	public String getRamdiskId() {
		return ramdiskId;
	}

	public List<BlockDeviceMapping> getBlockDeviceMappings() {
		return blockDeviceMappings;
	}
}

