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

import java.util.Set;
import java.util.HashSet;

/**
 * The base class for all AMI list attributes.
 */
public abstract class ImageListAttribute extends ImageAttribute {
	private Set<ImageListAttributeItem> items;

	/**
	 * Enumerates image list attribute item types.
	 */
	public enum ImageListAttributeItemType {
		group,
		userId,
		productCode,
		kernel,
		ramdisk,
		blockDeviceMapping
	}

	public ImageListAttribute(ImageAttribute.ImageAttributeType _type) {
		super(_type);
		items = new HashSet<ImageListAttributeItem>();
	}

	/**
	 * Add an item to the attribute's list of key-value pairs.
	 * @param type
	 *         The type of list attribute item to add.
	 * @param value
	 *         The value of the item.
	 * @return True if the item was successfully added to the list, false if the operation failed.
	 */
	public boolean addImageListAttributeItem(ImageListAttributeItemType type, String value) {
		if (itemTypeCompatible(type))
		  return items.add(new ImageListAttributeItem(type, value));
		else
		  return false;
	}

	public Set<ImageListAttributeItem> getImageListAttributeItems() {
		return items;
	}
	
	/**
	 * Indicates if the list attribute may contain items of the given type.
	 * @param type
	 *         The list item type to test if membership is valid for this list attribute.
	 * @return true if the item type is admissable, false otherwise
	 * 
	 */
	public abstract boolean itemTypeCompatible(ImageListAttributeItemType type);
}

