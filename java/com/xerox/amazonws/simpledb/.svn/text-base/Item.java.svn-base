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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents an item in SimpleDB. To modify this item,
 * use the interfaces in Domain.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public interface Item {

	/**
	 * Gets the name of the identifier that is unique to this Item
	 *
     * @return the id
	 */
	public String getIdentifier();

	/**
	 * Gets a map of all attributes for this item
	 *
     * @return the map of attributes
	 */
	public Map<String, Set<String>> getAttributes();

	/**
	 * Gets a single attribute value. If multiple values exist, only the first
	 * will be returned. Calling getAttributeValues(String) should be used instead.
	 *
     * @return the attribute value
	 */
	public String getAttribute(String name);

	/**
	 * Gets a Set of values for the named attribute.
	 *
     * @return the set of attribute values
	 */
	public Set<String> getAttributeValues(String name);
}
