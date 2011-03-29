//
// typica - A client library for Amazon Web Services
// Copyright (C) 2007,2008 Xerox Corporation
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

import java.util.Calendar;
import java.util.Map;

import com.xerox.amazonws.monitoring.StandardUnit;
import com.xerox.amazonws.monitoring.Statistics;

/**
 * This is a container class for scaling triggers
 */
public class ScalingTrigger {
	private String name;
	private String autoScalingGroupName;
	private String measureName;
	private Statistics statistic;
	private Map<String, String> dimensions;
	private int period;
	private StandardUnit unit;
	private String customUnit;
	private double lowerThreshold;
	private String lowerBreachScaleIncrement;
	private double upperThreshold;
	private String upperBreachScaleIncrement;
	private int breachDuration;
	private String status;
	private Calendar createdTime;

	public ScalingTrigger(String name, String autoScalingGroupName, String measureName,
						Statistics statistic, Map<String, String> dimensions, int period,
						StandardUnit unit, String customUnit,
						double lowerThreshold, String lowerBreachScaleIncrement,
						double upperThreshold, String upperBreachScaleIncrement,
						int breachDuration, String status, Calendar createdTime) {
		this.name = name;
		this.autoScalingGroupName = autoScalingGroupName;
		this.measureName = measureName;
		this.statistic = statistic;
		this.dimensions = dimensions;
		this.period = period;
		this.unit = unit;
		this.customUnit = customUnit;
		this.lowerThreshold = lowerThreshold;
		this.lowerBreachScaleIncrement = lowerBreachScaleIncrement;
		this.upperThreshold = upperThreshold;
		this.upperBreachScaleIncrement = upperBreachScaleIncrement;
		this.breachDuration = breachDuration;
		this.status = status;
		this.createdTime = createdTime;
	}

	public String getName() {
		return name;
	}

	public String getAutoScalingGroupName() {
		return autoScalingGroupName;
	}

	public String getMeasureName() {
		return measureName;
	}

	public Statistics getStatistic() {
		return statistic;
	}

	public Map<String, String> getDimensions() {
		return dimensions;
	}

	public int getPeriod() {
		return period;
	}

	public StandardUnit getUnit() {
		return unit;
	}

	public String getCustomUnit() {
		return customUnit;
	}

	public double getLowerThreshold() {
		return lowerThreshold;
	}

	public String getLowerBreachScaleIncrement() {
		return lowerBreachScaleIncrement;
	}

	public double getUpperThreshold() {
		return upperThreshold;
	}

	public String getUpperBreachScaleIncrement() {
		return upperBreachScaleIncrement;
	}

	public int getBreachDuration() {
		return breachDuration;
	}

	public String getStatus() {
		return status;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public String toString() {
		return "ScalingTrigger[name=" + name + ", autoScalingGroupName=" + autoScalingGroupName +
			", measureName=" + measureName + ", statistic=" + statistic.getStatId() +
			", period=" + period + ", unit=" + unit.getUnitId() + ", customUnit=" + customUnit +
			", lowerThreshold=" + lowerThreshold + ", lowerBreachScaleIncrement=" + lowerBreachScaleIncrement +
			", upperThreshold=" + upperThreshold + ", upperBreachScaleIncrement=" + upperBreachScaleIncrement +
			", breachDuration=" + breachDuration + ", status=" + status + "]";
	}
}
