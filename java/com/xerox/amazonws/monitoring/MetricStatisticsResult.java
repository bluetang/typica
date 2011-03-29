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
import java.util.List;

/**
 * An instance of this class represents a staticics result
 */
public class MetricStatisticsResult {
	private String label;
	private List<Datapoint> points;

	public MetricStatisticsResult(String label) {
		this.label = label;
		this.points = new ArrayList<Datapoint>();
	}

	public String getLabel() {
		return label;
	}

	public List<Datapoint> getDatapoints() {
		return points;
	}

	public String toString() {
		return "MetricStatsResult[label=" + this.label + ", numPoints=" + this.points.size() + "]";
	}
}

