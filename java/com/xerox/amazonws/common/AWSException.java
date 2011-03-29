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

import com.xerox.amazonws.fps.FPSError;
import java.util.List;

/**
 * A wrapper exception to simplify catching errors related to AWS activity.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class AWSException extends Exception {
	private String requestId;
	private List<? extends AWSError> errors;

    public AWSException(String s) {
        super(s);
    }

    public AWSException(String s, Throwable cause) {
        super(s, cause);
    }

    public AWSException(String s, String requestId) {
        this(s, requestId, null);
    }

    public AWSException(String s, String requestId, List<? extends AWSError> errors) {
        super(s);
		this.requestId = requestId;
        this.errors = errors;
    }

    protected AWSException(AWSException ex) {
		// copy constructor
		super(ex.getMessage(), ex.getCause());
		this.requestId = ex.getRequestId();
		this.errors = ex.getErrors();
	}

	public String getRequestId() {
		return requestId;
	}

	public List<? extends AWSError> getErrors() {
		return errors;
	}

    protected static String concatenateErrors(List<FPSError> errors) {
        StringBuffer buffer = new StringBuffer();
        for (FPSError error : errors)
            buffer.append(error.getCode()).append(": ").append(error.getMessage()).append('.');
        return buffer.toString();
    }
}

