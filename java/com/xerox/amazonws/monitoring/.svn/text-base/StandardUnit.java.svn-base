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
public enum StandardUnit {
	SECONDS ("Seconds"),
	PERCENT ("Percent"),
	BYTES ("Bytes"),
	BITS ("Bits"),
	COUNT ("Count"),
	BYTES_SEC ("Bytes/Second"),
	BITS_SEC ("Bits/Second"),
	COUNT_SEC ("Count/Second"),
	NONE ("None");

	private final String unitId;

	StandardUnit(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitId() {
		return unitId;
	}

	public static StandardUnit getTypeFromString(String val) {
		for (StandardUnit t : StandardUnit.values()) {
			if (t.getUnitId().equals(val)) {
				return t;
			}
		}
		return null;
	}
}
