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

package com.xerox.amazonws.devpay;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpGet;


import com.xerox.amazonws.common.AWSException;
import com.xerox.amazonws.common.AWSQueryConnection;
import com.xerox.amazonws.typica.jaxb.ActivateDesktopProductResponse;
import com.xerox.amazonws.typica.jaxb.ActivateDesktopProductResult;
import com.xerox.amazonws.typica.jaxb.ActivateHostedProductResponse;
import com.xerox.amazonws.typica.jaxb.ActivateHostedProductResult;
import com.xerox.amazonws.typica.jaxb.GetActiveSubscriptionsByPidResponse;
import com.xerox.amazonws.typica.jaxb.GetActiveSubscriptionsByPidResult;
import com.xerox.amazonws.typica.jaxb.RefreshUserTokenResponse;
import com.xerox.amazonws.typica.jaxb.RefreshUserTokenResult;
import com.xerox.amazonws.typica.jaxb.VerifyProductSubscriptionByPidResponse;
import com.xerox.amazonws.typica.jaxb.VerifyProductSubscriptionByPidResult;
import com.xerox.amazonws.typica.jaxb.VerifyProductSubscriptionByTokensResponse;
import com.xerox.amazonws.typica.jaxb.VerifyProductSubscriptionByTokensResult;

/**
 * This class provides an interface with the Amazon DevPay LS service. It provides high level
 * methods for listing and creating and deleting domains.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class DevPayLS extends AWSQueryConnection {

    private static Log logger = LogFactory.getLog(DevPayLS.class);

	/**
	 * Initializes the devpay service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
	 */
    public DevPayLS(String awsAccessId, String awsSecretKey) {
        this(awsAccessId, awsSecretKey, true);
    }

	/**
	 * Initializes the devpay service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from LS.
	 */
    public DevPayLS(String awsAccessId, String awsSecretKey, boolean isSecure) {
        this(awsAccessId, awsSecretKey, isSecure, "ls.amazonaws.com");
    }

	/**
	 * Initializes the devpay service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from LS.
     * @param server Which host to connect to.  Usually, this will be ls.amazonaws.com
	 */
    public DevPayLS(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server)
    {
        this(awsAccessId, awsSecretKey, isSecure, server,
             isSecure ? 443 : 80);
    }

    /**
	 * Initializes the devpay service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from LS.
     * @param server Which host to connect to.  Usually, this will be ls.amazonaws.com
     * @param port Which port to use.
     */
    public DevPayLS(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server, int port)
    {
		super(awsAccessId, awsSecretKey, isSecure, server, port);
		setVersionHeader(this);
    }

	/**
	 * This method returns the signature version
	 *
	 * @return the version
	 */
	public int getSignatureVersion() {
		return super.getSignatureVersion();
	}

	/**
	 * This method sets the signature version used to sign requests (0 or 1).
	 *
	 * @param version signature version
	 */
	public void setSignatureVersion(int version) {
		super.setSignatureVersion(version);
	}

	/**
	 * Activates a desktop product.
	 *
	 * @param activationKey key obtained from the customer
	 * @param productToken token for your product
	 * @return the product info
	 * @throws DevPayException wraps checked exceptions
	 */
	public DesktopProductInfo activateDesktopProduct(String activationKey, String productToken) throws DevPayException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ActivationKey", activationKey);
		params.put("ProductToken", productToken);
		HttpGet method = new HttpGet();
		ActivateDesktopProductResponse response =
					makeRequestInt(method, "ActivateDesktopProduct", params, ActivateDesktopProductResponse.class);

		ActivateDesktopProductResult result = response.getActivateDesktopProductResult();
		return new DesktopProductInfo(result.getAWSAccessKeyId(), result.getSecretAccessKey(), result.getUserToken());
	}

	/**
	 * Activates a hosted product.
	 *
	 * @param activationKey key obtained from the customer
	 * @param productToken token for your product
	 * @return the product info
	 * @throws DevPayException wraps checked exceptions
	 */
	public HostedProductInfo activateHostedProduct(String activationKey, String productToken) throws DevPayException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ActivationKey", activationKey);
		params.put("ProductToken", productToken);
		HttpGet method = new HttpGet();
		ActivateHostedProductResponse response =
					makeRequestInt(method, "ActivateHostedProduct", params, ActivateHostedProductResponse.class);

		ActivateHostedProductResult result = response.getActivateHostedProductResult();
		return new HostedProductInfo(result.getPersistentIdentifier(), result.getUserToken());
	}

	/**
	 * Gets list of active subscriptions by persistent identifier
	 *
	 * @param persistentIdentifier customers's PID
	 * @return true if product is subscribed
	 * @throws DevPayException wraps checked exceptions
	 */
	public List<String> getActiveSubscriptionsByPid(String persistentIdentifier) throws DevPayException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("PersistentIdentifier", persistentIdentifier);
		HttpGet method = new HttpGet();
		GetActiveSubscriptionsByPidResponse response =
					makeRequestInt(method, "GetActiveSubscriptionsByPid", params, GetActiveSubscriptionsByPidResponse.class);

		GetActiveSubscriptionsByPidResult result = response.getGetActiveSubscriptionsByPidResult();
		return result.getProductCodes();
	}

	/**
	 * Verifies that a specified product is subscribed to by a customer.
	 *
	 * @param persistentIdentifier customers's PID
	 * @param productCode the product code
	 * @return true if product is subscribed
	 * @throws DevPayException wraps checked exceptions
	 */
	public boolean isProductSubscribedByPid(String persistentIdentifier, String productCode) throws DevPayException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("PersistentIdentifier", persistentIdentifier);
		params.put("ProductCode", productCode);
		HttpGet method = new HttpGet();
		VerifyProductSubscriptionByPidResponse response =
					makeRequestInt(method, "VerifyProductSubscriptionByPid", params, VerifyProductSubscriptionByPidResponse.class);

		VerifyProductSubscriptionByPidResult result = response.getVerifyProductSubscriptionByPidResult();
		return result.isSubscribed();
	}

	/**
	 * Verifies that a specified product is subscribed to by a customer.
	 *
	 * @param productToken the product token
	 * @param userToken the user token
	 * @return the list of product codes 
	 * @throws DevPayException wraps checked exceptions
	 */
	public boolean isProductSubscribedByTokens(String productToken, String userToken) throws DevPayException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ProductToken", productToken);
		params.put("UserToken", userToken);
		HttpGet method = new HttpGet();
		VerifyProductSubscriptionByTokensResponse response =
					makeRequestInt(method, "VerifyProductSubscriptionByTokens", params, VerifyProductSubscriptionByTokensResponse.class);

		VerifyProductSubscriptionByTokensResult result = response.getVerifyProductSubscriptionByTokensResult();
		return result.isSubscribed();
	}

	/**
	 * Gets the most up-to-date version of the user token.
	 *
	 * @param userToken the user token
	 * @param additionalTokens optional token (see dev guide), null if not used
	 * @return the list of product codes 
	 * @throws DevPayException wraps checked exceptions
	 */
	public String refreshUserToken(String userToken, String additionalTokens) throws DevPayException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UserToken", userToken);
		if (additionalTokens != null) {
			params.put("AdditionalTokens", additionalTokens);
		}
		HttpGet method = new HttpGet();
		RefreshUserTokenResponse response =
					makeRequestInt(method, "RefreshUserToken", params, RefreshUserTokenResponse.class);

		RefreshUserTokenResult result = response.getRefreshUserTokenResult();
		return result.getUserToken();
	}

	protected <T> T makeRequestInt(HttpRequestBase method, String action, Map<String, String> params, Class<T> respType)
		throws DevPayException {
		try {
			return makeRequest(method, action, params, respType);
		} catch (AWSException ex) {
			throw new DevPayException(ex);
		} catch (JAXBException ex) {
			throw new DevPayException("Problem parsing returned message.", ex);
		} catch (SAXException ex) {
			throw new DevPayException("Problem parsing returned message.", ex);
		} catch (HttpException ex) {
			throw new DevPayException(ex.getMessage(), ex);
		} catch (IOException ex) {
			throw new DevPayException(ex.getMessage(), ex);
		}
	}

	static void setVersionHeader(AWSQueryConnection connection) {
		ArrayList<String> vals = new ArrayList<String>();
		vals.add("2008-04-28");
		connection.getHeaders().put("Version", vals);
	}
}
