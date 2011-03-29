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

package com.xerox.amazonws.sdb;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a thread-safe, persistent counter, backed by SimpleDB.
 *
 * @author D. Kavanagh
 */
public class Counter {
	private Domain domain;
	private String name;

	/**
	 * Constructs a counter, where a value may already be stored. If no value is
	 * assigned, the counter is initialized to zero (the first counter value will be 1).
	 *
	 * @param domain the domain to use for the counter
	 * @param counterName the name of this counter, must be unique within this domain
	 * @throws SDBException wraps checked exceptions
	 */
	public Counter(Domain domain, String counterName) throws SDBException {
		this.domain = domain;
		this.name = counterName;
		// check for value, if none, default to 0
		Item i = domain.getItem(name);
		List<ItemAttribute> attrs = i.getAttributes();
		if (attrs == null) {
			attrs = new ArrayList<ItemAttribute>();
		}
		ItemAttribute attr = null;
		if (attrs.size() > 0) {
			attr = attrs.get(0);
		}
		if (attr != null) {
			String val = attr.getValue();
			if (val != null) {
				// good, there's a value
				return;
			}
		}
		else {
			attrs.add(new ItemAttribute("Value", "0", true));
		}
		i.putAttributes(attrs);
	}

	/**
	 * Constructs a counter with a specified initial value
	 *
	 * @param domain the domain to use for the counter
	 * @param counterName the name of this counter, must be unique within this domain
	 * @param initValue the initial value for the counter
	 * @throws SDBException wraps checked exceptions
	 */
	public Counter(Domain domain, String counterName, long initValue) throws SDBException {
		this(domain, counterName);
		// initialize counter
		Item i = domain.getItem(name);
		ItemAttribute attr = new ItemAttribute("Value", ""+initValue, true);
		ArrayList<ItemAttribute> attrs = new ArrayList<ItemAttribute>();
		attrs.add(attr);
		i.putAttributes(attrs);
	}

	/**
	 * This method returns the counter name
	 *
	 * @return the name of the counter
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the next value, period.
	 *
	 * @return the next counter value
	 */
	public long nextValue() throws SDBException {
		Item i = domain.getItem(name);
		List<ItemAttribute> attrs = i.getAttributes();
		ItemAttribute attr = attrs.get(0);
		String val = attr.getValue();
		long value = Long.parseLong(val);
		boolean done = false;
		while (!done) {
			try {
				attr = new ItemAttribute("Value", ""+(value+1), true);
				attrs.clear();
				attrs.add(attr);
				ArrayList<Condition> conds = new ArrayList<Condition>();
				conds.add(new Condition("Value", ""+value));
				i.putAttributes(attrs, conds);
				done = true;
			} catch (SDBException ex) {
				String msg = ex.getErrors().get(0).getCode();
				if (msg.equals("ConditionalCheckFailed")) {
					// increment, pause and try again
					value++;
					try { Thread.sleep(500); } catch (InterruptedException ie) {}
				}
				else {
					throw ex;
				}
			}
		}
		return value+1;
	}
}
