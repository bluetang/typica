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

package com.xerox.amazonws.simpledb;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents an item in SimpleDB. To modify this item, use the interfaces in Domain.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class ItemVO implements Item {
    private static Log logger = LogFactory.getLog(Item.class);

	private String identifier;
	private Map<String, Set<String>> attributes;

    public ItemVO(String identifier) {
		this.identifier = identifier;
		attributes = Collections.synchronizedMap(new HashMap<String, Set<String>>());
	}

	/**
	 * Gets the name of the identifier that is unique to this Item
	 *
     * @return the id
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Gets a map of all attributes for this item
	 *
     * @return the map of attributes
	 */
	public Map<String, Set<String>> getAttributes() {
		return this.attributes;
	}

	/**
	 * Gets a single attribute value. If multiple values exist, only the first
	 * will be returned. Calling getAttributeValues(String) should be used instead.
	 *
     * @return the attribute value
	 */
	public String getAttribute(String name) {
		Set vals = this.attributes.get(name);
		if (vals == null || vals.size() == 0) {
			return null;
		}
		else {
			return ((Iterator<String>)vals.iterator()).next();
		}
	}

	/**
	 * Gets a Set of values for the named attribute.
	 *
     * @return the set of attribute values
	 */
	public Set<String> getAttributeValues(String name) {
		return this.attributes.get(name);
	}
}
