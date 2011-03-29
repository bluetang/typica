package com.xerox.amazonws.ec2;

import com.xerox.amazonws.typica.jaxb.SpotPriceHistorySetItemType;

import java.util.Calendar;

public class SpotPriceHistoryItem implements Comparable<SpotPriceHistoryItem> {
	private InstanceType instanceType;
	private double price;
	private Calendar timestamp;
	private String productDescription;

	public SpotPriceHistoryItem() {
	}

	SpotPriceHistoryItem(SpotPriceHistorySetItemType item) {
		instanceType = InstanceType.getTypeFromString(item.getInstanceType());
		price = Double.parseDouble(item.getSpotPrice());
		timestamp = item.getTimestamp().toGregorianCalendar();
		productDescription = item.getProductDescription();
	}

	public InstanceType getInstanceType() {
		return instanceType;
	}

	public double getPrice() {
		return price;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public int compareTo(SpotPriceHistoryItem o) {
		return timestamp.compareTo(o.timestamp);
	}

	@Override
	public String toString() {
		return "SpotPriceHistoryItem[" +
				"instanceType=" + instanceType +
				", price=" + price +
				", timestamp=" + timestamp.getTime().toString() +
				", productDescription='" + productDescription + '\'' +
				']';
	}
}
