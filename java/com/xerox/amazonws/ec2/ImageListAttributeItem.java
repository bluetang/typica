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
 * An type-value item for a list attribute.
 * <p>
 * ImageListAttributeItems are keyed on type and value. 
 */
public class ImageListAttributeItem {
	private ImageListAttribute.ImageListAttributeItemType type;
	private String value;

	public ImageListAttributeItem(ImageListAttribute.ImageListAttributeItemType _type, String _value) {
		type = _type;
		value = _value;
	}

	public ImageListAttribute.ImageListAttributeItemType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public boolean equals(Object other) {
		if (this == other) return true;
		if (null == other) return false;
		ImageListAttributeItem item = (ImageListAttributeItem) other;
		return type.equals(item.type) && value.equals(item.value);
	}
	
	public int hashCode() {
		return (type == null ? 17 : type.hashCode()) ^
			   (value == null ? 31 : value.hashCode());
	}
}

