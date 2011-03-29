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

/**
 * This enumeration represents different image types that can be launched.
 */
public enum Statistics {
	AVERAGE ("Average"),
	SUM ("Sum"),
	MINIMUM ("Minimum"),
	MAXIMUM ("Maximum");

	private final String statId;

	Statistics(String statId) {
		this.statId = statId;
	}

	public String getStatId() {
		return statId;
	}

	public static Statistics getTypeFromString(String val) {
		for (Statistics t : Statistics.values()) {
			if (t.getStatId().equals(val)) {
				return t;
			}
		}
		return null;
	}
}
