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

package com.xerox.amazonws.sns;

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
import com.xerox.amazonws.common.ListResult;
import com.xerox.amazonws.common.Result;
import com.xerox.amazonws.typica.sns.jaxb.AddPermissionResponse;
import com.xerox.amazonws.typica.sns.jaxb.ConfirmSubscriptionResponse;
import com.xerox.amazonws.typica.sns.jaxb.CreateTopicResponse;
import com.xerox.amazonws.typica.sns.jaxb.DeleteTopicResponse;
import com.xerox.amazonws.typica.sns.jaxb.GetTopicAttributesResponse;
import com.xerox.amazonws.typica.sns.jaxb.ListSubscriptionsResponse;
import com.xerox.amazonws.typica.sns.jaxb.ListSubscriptionsResult;
import com.xerox.amazonws.typica.sns.jaxb.ListSubscriptionsByTopicResponse;
import com.xerox.amazonws.typica.sns.jaxb.ListSubscriptionsByTopicResult;
import com.xerox.amazonws.typica.sns.jaxb.ListTopicsResponse;
import com.xerox.amazonws.typica.sns.jaxb.ListTopicsResult;
import com.xerox.amazonws.typica.sns.jaxb.PublishResponse;
import com.xerox.amazonws.typica.sns.jaxb.RemovePermissionResponse;
import com.xerox.amazonws.typica.sns.jaxb.SetTopicAttributesResponse;
import com.xerox.amazonws.typica.sns.jaxb.Subscription;
import com.xerox.amazonws.typica.sns.jaxb.SubscribeResponse;
import com.xerox.amazonws.typica.sns.jaxb.Topic;
import com.xerox.amazonws.typica.sns.jaxb.TopicAttributesMap;
import com.xerox.amazonws.typica.sns.jaxb.TopicAttributesMapEntry;
import com.xerox.amazonws.typica.sns.jaxb.UnsubscribeResponse;

/**
 * This class provides an interface with the Amazon Simple Notification Service.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class NotificationService {

    private static Log logger = LogFactory.getLog(NotificationService.class);

	private AWSQueryConnection connection;	// connection delegate

	/**
	 * Initializes the Simple Notification service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
	 */
    public NotificationService(String awsAccessId, String awsSecretKey) {
        this(awsAccessId, awsSecretKey, true);
    }

	/**
	 * Initializes the Simple Notification service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from LS.
	 */
    public NotificationService(String awsAccessId, String awsSecretKey, boolean isSecure) {
        this(awsAccessId, awsSecretKey, isSecure, "sns.us-east-1.amazonaws.com");
    }

	/**
	 * Initializes the Simple Notification service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from LS.
     * @param server Which host to connect to.  Usually, this will be sns.amazonaws.com
	 */
    public NotificationService(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server)
    {
        this(awsAccessId, awsSecretKey, isSecure, server, isSecure ? 443 : 80);
    }

    /**
	 * Initializes the Simple Notification service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from LS.
     * @param server Which host to connect to.  Usually, this will be sns.amazonaws.com
     * @param port Which port to use.
     */
    public NotificationService(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server, int port)
    {
		connection = new AWSQueryConnection(awsAccessId, awsSecretKey, isSecure, server, port);
		setVersionHeader(connection);
    }

	/**
	 * Returns connection object, so connection params can be tweaked
	 */
	public AWSQueryConnection getConnectionDelegate() {
		return connection;
	}

	/**
	 * Adds to the topic's access control policy.
	 *
	 * @param topicArn the ARN for the topic
	 * @param label the unique identifier for the new policy statement
	 * @param accountIds users being given access
	 * @param actionNames actions you are allowing
	 * @throws SNSException wraps checked exceptions
	 */
	public void addPermission(String topicArn, String label, List<String> accountIds, List<String> actionNames) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		params.put("Label", label);
		for (int i=0 ; i<accountIds.size(); i++) {
			params.put("AWSAccountId.member."+(i+1), accountIds.get(i));
		}
		for (int i=0 ; i<actionNames.size(); i++) {
			params.put("ActionName.member."+(i+1), actionNames.get(i));
		}
		HttpGet method = new HttpGet();
		AddPermissionResponse response =
					makeRequestInt(method, "AddPermission", params, AddPermissionResponse.class);
	}

	/**
	 * Verifies an endpoint intends to receive messages
	 *
	 * @param topicArn the ARN for the topic
	 * @param token subscription token
	 * @param authenticateOnUnsubscribe requires authenticated unsubscribe from the topic
	 * @return the subscription ARN
	 * @throws SNSException wraps checked exceptions
	 */
	public Result<String> confirmSubscription(String topicArn, String token, boolean authenticateOnUnsubscribe) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		params.put("Token", token);
		if (authenticateOnUnsubscribe) {
			params.put("AuthenticateOnUnsubscribe", "true");
		}
		HttpGet method = new HttpGet();
		ConfirmSubscriptionResponse response =
					makeRequestInt(method, "ConfirmSubscription", params, ConfirmSubscriptionResponse.class);
		return new Result<String>(response.getResponseMetadata().getRequestId(),
					response.getConfirmSubscriptionResult().getSubscriptionArn());
	}

	/**
	 * Creates a topic
	 *
	 * @param name name of the new topic
	 * @return the topic ARN
	 * @throws SNSException wraps checked exceptions
	 */
	public Result<String> createTopic(String name) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Name", name);
		HttpGet method = new HttpGet();
		CreateTopicResponse response =
					makeRequestInt(method, "CreateTopic", params, CreateTopicResponse.class);

		return new Result<String>(response.getResponseMetadata().getRequestId(),
					response.getCreateTopicResult().getTopicArn());
	}

	/**
	 * Deletes a topic
	 *
	 * @param topicArn the ARN for the topic
	 * @throws SNSException wraps checked exceptions
	 */
	public void deleteTopic(String topicArn) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		HttpGet method = new HttpGet();
		makeRequestInt(method, "DeleteTopic", params, DeleteTopicResponse.class);
	}

	/**
	 * Gets attributes for the topic
	 *
	 * @param topicArn the ARN for the topic
	 * @return a map of attributes
	 * @throws SNSException wraps checked exceptions
	 */
	public Map<String, String> getTopicAttributes(String topicArn) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		HttpGet method = new HttpGet();
		GetTopicAttributesResponse response =
					makeRequestInt(method, "GetTopicAttributes", params, GetTopicAttributesResponse.class);

		HashMap<String, String> ret = new HashMap<String, String>();
		TopicAttributesMap attrs = response.getGetTopicAttributesResult().getAttributes();
		for (TopicAttributesMapEntry e : attrs.getEntries()) {
			ret.put(e.getKey(), e.getValue());
		}
		return ret;
	}

	/**
	 * Lists the subscriptions
	 *
	 * @param nextToken the user token
	 * @return the list of subscriptions 
	 * @throws SNSException wraps checked exceptions
	 */
	public ListResult<SubscriptionInfo> listSubscriptions(String nextToken) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		if (nextToken != null) {
			params.put("NextToken", nextToken);
		}
		HttpGet method = new HttpGet();
		ListSubscriptionsResponse response =
					makeRequestInt(method, "ListSubscriptions", params, ListSubscriptionsResponse.class);
		ListSubscriptionsResult result = response.getListSubscriptionsResult();

		ListResult<SubscriptionInfo> ret = new ListResult<SubscriptionInfo>(
							result.getNextToken(),
							response.getResponseMetadata().getRequestId());
		for (Subscription s : result.getSubscriptions().getMembers()) {
			ret.getItems().add(new SubscriptionInfo(s.getTopicArn(), s.getProtocol(),
								s.getSubscriptionArn(), s.getOwner(), s.getEndpoint()));
		}
		return ret;
	}

	/**
	 * Lists subscriptions for a topic
	 *
	 * @param nextToken the user token
	 * @return the list of subscriptions 
	 * @throws SNSException wraps checked exceptions
	 */
	public ListResult<SubscriptionInfo> listSubscriptionsByTopic(String topicArn, String nextToken) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		if (nextToken != null) {
			params.put("NextToken", nextToken);
		}
		HttpGet method = new HttpGet();
		ListSubscriptionsByTopicResponse response =
					makeRequestInt(method, "ListSubscriptionsByTopic", params, ListSubscriptionsByTopicResponse.class);
		ListSubscriptionsByTopicResult result = response.getListSubscriptionsByTopicResult();

		ListResult<SubscriptionInfo> ret = new ListResult<SubscriptionInfo>(
							result.getNextToken(),
							response.getResponseMetadata().getRequestId());
		for (Subscription s : result.getSubscriptions().getMembers()) {
			ret.getItems().add(new SubscriptionInfo(s.getTopicArn(), s.getProtocol(),
								s.getSubscriptionArn(), s.getOwner(), s.getEndpoint()));
		}
		return ret;
	}

	/**
	 * Lists topics for this account
	 *
	 * @param nextToken the user token
	 * @return the list of topics
	 * @throws SNSException wraps checked exceptions
	 */
	public ListResult<String> listTopics(String nextToken) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		if (nextToken != null) {
			params.put("NextToken", nextToken);
		}
		HttpGet method = new HttpGet();
		ListTopicsResponse response =
					makeRequestInt(method, "ListTopics", params, ListTopicsResponse.class);
		ListTopicsResult result = response.getListTopicsResult();

		ListResult<String> ret = new ListResult<String>(
							result.getNextToken(),
							response.getResponseMetadata().getRequestId());
		for (Topic t : result.getTopics().getMembers()) {
			ret.getItems().add(t.getTopicArn());
		}
		return ret;
	}

	/**
	 * Publishes a message to a topic's subscribed endpoints
	 *
	 * @param topicArn the ARN for the topic
	 * @param message the message to be sent
	 * @param subject the optional subject for the message
	 * @return true if product is subscribed
	 * @throws SNSException wraps checked exceptions
	 */
	public Result<String> publish(String topicArn, String message, String subject) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		params.put("Message", message);
		if (subject != null) {
			params.put("Subject", subject);
		}
		HttpGet method = new HttpGet();
		PublishResponse response =
					makeRequestInt(method, "Publish", params, PublishResponse.class);

		return new Result<String>(response.getResponseMetadata().getRequestId(),
						response.getPublishResult().getMessageId());
	}

	/**
	 * Removes permissions from a topic
	 *
	 * @param topicArn the ARN for the topic
	 * @param label the label for the permission statement
	 * @throws SNSException wraps checked exceptions
	 */
	public void removePermission(String topicArn, String label) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		params.put("Label", label);
		HttpGet method = new HttpGet();
		makeRequestInt(method, "RemovePermission", params, RemovePermissionResponse.class);
	}

	/**
	 * Set a topic attribute.
	 *
	 * @param topicArn the ARN for the topic
	 * @param name name of the attribute
	 * @param value value of the attribute
	 * @throws SNSException wraps checked exceptions
	 */
	public void setTopicAttributes(String topicArn, String name, String value) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		params.put("AttributeName", name);
		params.put("AttributeValue", value);
		HttpGet method = new HttpGet();
		makeRequestInt(method, "SetTopicAttributes", params, SetTopicAttributesResponse.class);
	}

	/**
	 * Subscribe this account to a topic
	 *
	 * @param topicArn the ARN for the topic
	 * @return subscription ARN
	 * @throws SNSException wraps checked exceptions
	 */
	public Result<String> subscribe(String topicArn, String protocol, String endpoint) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TopicArn", topicArn);
		params.put("Protocol", protocol);
		params.put("Endpoint", endpoint);
		HttpGet method = new HttpGet();
		SubscribeResponse response =
					makeRequestInt(method, "Subscribe", params, SubscribeResponse.class);

		return new Result<String>(response.getResponseMetadata().getRequestId(),
						response.getSubscribeResult().getSubscriptionArn());
	}

	/**
	 * Unsubscribe this account from a topic
	 *
	 * @param topicArn the ARN for the topic
	 * @throws SNSException wraps checked exceptions
	 */
	public void unsubscribe(String subscriptionArn) throws SNSException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("SubscriptionArn", subscriptionArn);
		HttpGet method = new HttpGet();
		makeRequestInt(method, "Unsubscribe", params, UnsubscribeResponse.class);
	}

	protected <T> T makeRequestInt(HttpRequestBase method, String action, Map<String, String> params, Class<T> respType)
		throws SNSException {
		try {
			return connection.makeRequest(method, action, params, respType);
		} catch (AWSException ex) {
			throw new SNSException(ex);
		} catch (JAXBException ex) {
			throw new SNSException("Problem parsing returned message.", ex);
		} catch (SAXException ex) {
			throw new SNSException("Problem parsing returned message.", ex);
		} catch (HttpException ex) {
			throw new SNSException(ex.getMessage(), ex);
		} catch (IOException ex) {
			throw new SNSException(ex.getMessage(), ex);
		}
	}

	static void setVersionHeader(AWSQueryConnection connection) {
		ArrayList<String> vals = new ArrayList<String>();
		vals.add("2010-03-31");
		connection.getHeaders().put("Version", vals);
	}
}
