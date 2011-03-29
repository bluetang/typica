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

/**
 * The base class for all AMI attributes.
 */
public abstract class ImageAttribute {
	private ImageAttributeType type;

	/**
	 * Enumerates image attribute types.
	 */
	public enum ImageAttributeType {
		launchPermission,
		productCodes
	}

	public ImageAttribute(ImageAttributeType _type) {
		type = _type;
	}		

	public ImageAttributeType getType() {
		return type;
	}
}

