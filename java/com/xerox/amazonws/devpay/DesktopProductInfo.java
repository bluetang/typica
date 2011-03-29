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

package com.xerox.amazonws.devpay;

public class DesktopProductInfo {
	private String accessId;
	private String secretKey;
	private String userToken;

	public DesktopProductInfo(String accessId, String secretKey, String userToken) {
		this.accessId = accessId;
		this.secretKey = secretKey;
		this.userToken = userToken;
	}

	public String getAccessId() {
		return accessId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String getUserToken() {
		return userToken;
	}

	public String toString() {
		return "DesktopProductInfo[accessId=" + accessId +
			", secretKey=" + secretKey + ", userToken=" + userToken + "]";
	}
}
