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

import java.util.Date;
import java.util.List;

public class DomainMetadata {
	private int itemCount;
	private int attributeValueCount;
	private int attributeNameCount;
	private long itemNamesSizeBytes;
	private long attributeValuesSizeBytes;
	private long attributeNamesSizeBytes;
	private Date timestamp;

	DomainMetadata(int itemCount, int attributeValueCount, int attributeNameCount,
			long itemNamesSizeBytes, long attributeValuesSizeBytes, long attributeNamesSizeBytes,
			Date timestamp) {
		this.itemCount = itemCount;
		this.attributeValueCount = attributeValueCount;
		this.attributeNameCount = attributeNameCount;
		this.itemNamesSizeBytes = itemNamesSizeBytes;
		this.attributeValuesSizeBytes = attributeValuesSizeBytes;
		this.attributeNamesSizeBytes = attributeNamesSizeBytes;
		this.timestamp = timestamp;
	}

	public int getItemCount() {
		return itemCount;
	}

	public int getAttributeValueCount() {
		return attributeValueCount;
	}

	public int getAttributeNameCount() {
		return attributeNameCount;
	}

	public long getItemNamesSizeBytes() {
		return itemNamesSizeBytes;
	}

	public long getAttributeValuesSizeBytes() {
		return attributeValuesSizeBytes;
	}

	public long getAttributeNamesSizeBytes() {
		return attributeNamesSizeBytes;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String toString() {
		return "DomainMetadata[itemCount:"+itemCount+
				",attrNameCount:"+attributeNameCount+
				",attrValueCount:"+attributeValueCount+
				",itemNameSize:"+itemNamesSizeBytes+
				",attrNameSize:"+attributeNamesSizeBytes+
				",attrValueSize:"+attributeValuesSizeBytes+
				",timestamp:"+timestamp.toString()+"]";
	}
}
