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
 * An instance of this class represents a Datapoint for some metrics
 */
public class Datapoint {
	private Calendar timestamp;
	private double samples;
	private Double average;
	private Double sum;
	private Double minimum;
	private Double maximum;
	private String unit;
	private String customUnit;

	public Datapoint(Calendar timestamp, double samples, Double average, Double sum, Double minimum,
					Double maximum, String unit, String customUnit) {
		this.timestamp = timestamp;
		this.samples = samples;
		this.average = average;
		this.sum = sum;
		this.minimum = minimum;
		this.maximum = maximum;
		this.unit = unit;
		this.customUnit = customUnit;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public double getSamples() {
		return samples;
	}

	public Double getAverage() {
		return average;
	}

	public Double getSum() {
		return sum;
	}

	public Double getMinimum() {
		return minimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public String getUnit() {
		return unit;
	}

	public String getCustomUnit() {
		return customUnit;
	}

	public String toString() {
		return "Datapoint[timestamp=" + this.timestamp.toString() + ", Samples=" + this.samples + "]";
	}
}

