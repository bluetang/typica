//
// typica - A client library for Amazon Web Services
// Copyright (C) 2008 Xerox Corporation
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

package com.xerox.amazonws.common;

import java.io.Serializable;

/**
 * A wrapper exception to simplify catching errors related to AWS activity.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class AWSError implements Serializable {
	private final ErrorType type;
	private final String code;
	private final String message;

	public AWSError(ErrorType type, String code, String message) {
		this.type = type;
		this.code = code;
		this.message = message;
	}

	public ErrorType getErrorType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String toString() {
		return "AWSError [type="+type.getTypeId()+",code="+code+",message="+message+"]";
	}

	public enum ErrorType implements Serializable {
		RECEIVER("Receiver"),
		SENDER("Sender");

		private final String typeId;

		ErrorType(String typeId) {
			this.typeId = typeId;
		}

		public String getTypeId() {
			return typeId;
		}

		public static ErrorType getTypeFromString(String val) {
			for (ErrorType t : ErrorType.values()) {
				if (t.getTypeId().equals(val)) {
					return t;
				}
			}
			return null;
		}
	}
}

