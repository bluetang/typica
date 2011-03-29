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

package com.xerox.amazonws.monitoring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An instance of this class represents a Metric
 */
public class Metric {
	private String name;
	private List<Dimension> dimensions = new ArrayList<Dimension>();
	private String namespace;

	public Metric(String name, String namespace) {
		this.name = name;
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public List<Dimension> getDimensions() {
		return dimensions;
	}

	public String getNamespace() {
		return namespace;
	}

	public Dimension addDimension(String name, String value) {
		Dimension dim = new Dimension(name, value);
		dimensions.add(dim);
		return dim;
	}

	public String toString() {
		return "Metric[name=" + this.name + ", namespace=" + this.namespace + "]";
	}

	public class Dimension {
		String name;
		String value;

		public Dimension(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
		
		public String toString() {
			return "Dimension[name=" + this.name + ", value=" + this.value + "]";
		}
	}
}

