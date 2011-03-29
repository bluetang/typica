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

package com.xerox.amazonws.sqs2;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a wrapper for a message received from a queue.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class Message {
	private String messageId;
	private String receiptHandle;
	private String messageBody;
	private String bodyMD5;
	private Map<String, String> attributes;

	protected Message(String messageId, String receiptHandle, String messageBody, String bodyMD5) {
		this.messageId = messageId;
		this.receiptHandle = receiptHandle;
		this.messageBody = messageBody;
		this.bodyMD5 = bodyMD5;
		this.attributes = new HashMap<String, String>();
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getReceiptHandle() {
		return receiptHandle;
	}

	public void setReceiptHandle(String receiptHandle) {
		this.receiptHandle = receiptHandle;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getBodyMD5() {
		return bodyMD5;
	}

	public void setBodyMD5(String bodyMD5) {
		this.bodyMD5 = bodyMD5;
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public String getAttribute(String name) {
		return this.attributes.get(name);
	}

	public void setAttribute(String name, String value) {
		this.attributes.put(name, value);
	}

	public String toString() {
		return "id: "+messageId+" body: "+messageBody;
	}
}
