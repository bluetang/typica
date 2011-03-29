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

package com.xerox.amazonws.sdb;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import com.xerox.amazonws.common.AWSException;
import com.xerox.amazonws.common.AWSQueryConnection;
import com.xerox.amazonws.typica.sdb.jaxb.Attribute;
import com.xerox.amazonws.typica.sdb.jaxb.BatchDeleteAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.BatchPutAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.DomainMetadataResponse;
import com.xerox.amazonws.typica.sdb.jaxb.SelectResponse;

/**
 * This class provides an interface with the Amazon SDB service. It provides methods for
 * listing and deleting items.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class Domain extends AWSQueryConnection {

    private static Log logger = LogFactory.getLog(Domain.class);

	private String domainName;
	private int maxThreads = 30;
	private ThreadPoolExecutor executor;

    protected Domain(String domainName, String awsAccessId,
							String awsSecretKey, boolean isSecure,
							String server, int port) throws SDBException {
        super(awsAccessId, awsSecretKey, isSecure, server, port);
		this.domainName = domainName;
		SimpleDB.setVersionHeader(this);
    }

	/**
	 * Gets the name of the domain represented by this object.
	 *
     * @return the name of the domain
	 */
	public String getName() {
		return domainName;
	}

	/**
	 * Gets the max number of threads to use for the threaded operations.
	 *
     * @return max number of threads being used
	 */
	public int getMaxThreads() {
		return maxThreads;
	}

	/**
	 * Sets the max number of threads to use for the threaded operations.
	 *
	 * @param threads the new max to set
	 */
	public void setMaxThreads(int threads) {
		maxThreads = threads;
	}

	/**
	 * Method for getting an Item object without getting a list of them.
	 *
	 * @param identifier id of the item
     * @return the object representing the item
	 * @throws SDBException wraps checked exceptions
	 */
	public Item getItem(String identifier) throws SDBException {
		Item ret = new Item(identifier, domainName, getAwsAccessKeyId(), getSecretAccessKey(),
										isSecure(), getPort(), getServer());
		ret.setSignatureVersion(getSignatureVersion());
		ret.setHttpClient(getHttpClient());
		return ret;
	}

	/**
	 * Deletes an item.
	 *
	 * @param identifier the name of the item to be deleted
	 * @throws SDBException wraps checked exceptions
	 */
	public void deleteItem(String identifier) throws SDBException {
		getItem(identifier).deleteAttributes(null);
	}

	/**
	 * Deletes an item, with conditions
	 *
	 * @param identifier the name of the item to be deleted
	 * @throws SDBException wraps checked exceptions
	 */
	public void deleteItem(String identifier, List<Condition> conditions) throws SDBException {
		getItem(identifier).deleteAttributes(null, conditions);
	}

	/**
	 * Returns information about the domain.
	 *
     * @return the object containing metadata about this domain
	 * @throws SDBException wraps checked exceptions
	 */
	public DomainMetadataResult getMetadata() throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		HttpGet method = new HttpGet();
		DomainMetadataResponse response =
					makeRequestInt(method, "DomainMetadata", params, DomainMetadataResponse.class);
		return new DomainMetadataResult(response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage(),
					Integer.parseInt(response.getDomainMetadataResult().getItemCount()),
					Integer.parseInt(response.getDomainMetadataResult().getAttributeValueCount()),
					Integer.parseInt(response.getDomainMetadataResult().getAttributeNameCount()),
					Long.parseLong(response.getDomainMetadataResult().getItemNamesSizeBytes()),
					Long.parseLong(response.getDomainMetadataResult().getAttributeValuesSizeBytes()),
					Long.parseLong(response.getDomainMetadataResult().getAttributeNamesSizeBytes()),
					new Date(Long.parseLong(response.getDomainMetadataResult().getTimestamp())*1000));
	}

	/**
	 * This method supports selecting items/attributers based on the select syntax
	 *
	 * @param selectExpression the select query
	 * @param nextToken the next token, for fetching more results from a previous query
     * @return an object containing query results and stats
	 * @throws SDBException wraps checked exceptions
	 */
	public QueryWithAttributesResult selectItems(String selectExpression, String nextToken) throws SDBException {
		return selectItems(selectExpression, nextToken, false);
	}

	/**
	 * This method supports selecting items/attributers based on the select syntax
	 *
	 * @param selectExpression the select query
	 * @param nextToken the next token, for fetching more results from a previous query
	 * @param consistent if true, consistency is assured
     * @return an object containing query results and stats
	 * @throws SDBException wraps checked exceptions
	 */
	public QueryWithAttributesResult selectItems(String selectExpression, String nextToken, boolean consistent) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("SelectExpression", selectExpression);
		if (nextToken != null) {
			params.put("NextToken", nextToken);
		}
		if (consistent) {
			params.put("ConsistentRead", "true");
		}
		HttpGet method = new HttpGet();
		SelectResponse response =
					makeRequestInt(method, "Select", params, SelectResponse.class);
		Map<String, List<ItemAttribute>> results = new LinkedHashMap<String, List<ItemAttribute>>();
		for (com.xerox.amazonws.typica.sdb.jaxb.Item i : response.getSelectResult().getItems()) {
			List<ItemAttribute> attrs = new ArrayList<ItemAttribute>();
			for (Attribute a : i.getAttributes()) {
				attrs.add(createAttribute(a));
			}
			String iName = i.getName().getValue();
			String encoding = i.getName().getEncoding();
			if (encoding != null && encoding.equals("base64")) {
				iName = new String(Base64.decodeBase64(iName.getBytes()));
			}
			results.put(iName, attrs);
		}

		return new QueryWithAttributesResult(
					response.getSelectResult().getNextToken(),
					response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage(),
					results);
	}

	/**
	 * Batch inserts multiple items w/ attributes
	 *
	 * @param attributes list of attributes to add
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult batchPutAttributes(Map<String, List<ItemAttribute>> items) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		int k=1;
		for (String item : items.keySet()) {
			params.put("Item."+k+".ItemName", item);
			int i=1;
			for (ItemAttribute attr : items.get(item)) {
				String val = attr.getValue();
				if (val != null) {
					params.put("Item."+k+".Attribute."+i+".Name", attr.getName());
					params.put("Item."+k+".Attribute."+i+".Value", val);
					if (attr.isReplace()) {
						params.put("Item."+k+".Attribute."+i+".Replace", "true");
					}
					i++;
				}
			}
			k++;
		}
		HttpPost method = new HttpPost();
		BatchPutAttributesResponse response =
			makeRequestInt(method, "BatchPutAttributes", params, BatchPutAttributesResponse.class);
		return new SDBResult(null, 
					response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}


	static List<Domain> createList(String [] domainNames, String awsAccessKeyId,
									String awsSecretAccessKey, boolean isSecure,
									String server, int port, int signatureVersion, HttpClient hc)
			throws SDBException {
		ArrayList<Domain> ret = new ArrayList<Domain>();
		for (int i=0; i<domainNames.length; i++) {
			Domain dom = new Domain(domainNames[i], awsAccessKeyId, awsSecretAccessKey, isSecure, server, port);
			dom.setSignatureVersion(signatureVersion);
			dom.setHttpClient(hc);
			ret.add(dom);
		}
		return ret;
	}

	/**
	 * Batch deletes multiple items w/ attributes
	 *
	 * @param attributes list of attributes to delete
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult batchDeleteAttributes(Map<String, List<ItemAttribute>> items) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		int k=1;
		for (String item : items.keySet()) {
			params.put("Item."+k+".ItemName", item);
			int i=1;
			for (ItemAttribute attr : items.get(item)) {
				String val = attr.getValue();
				if (val != null) {
					params.put("Item."+k+".Attribute."+i+".Name", attr.getName());
					params.put("Item."+k+".Attribute."+i+".Value", val);
					if (attr.isReplace()) {
						params.put("Item."+k+".Attribute."+i+".Replace", "true");
					}
					i++;
				}
			}
			k++;
		}
		HttpPost method = new HttpPost();
		BatchDeleteAttributesResponse response =
			makeRequestInt(method, "BatchDeleteAttributes", params, BatchDeleteAttributesResponse.class);
		return new SDBResult(null, 
					response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		if (executor != null) {
			return executor;
		}
		else {
			return new ThreadPoolExecutor(maxThreads, maxThreads, 5,
							TimeUnit.SECONDS, new ArrayBlockingQueue(maxThreads));
		}
	}
	
	public void setThreadPoolExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	protected class RejectionHandler implements RejectedExecutionHandler {
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			// ok, on the rare occasion, just run it here!
			r.run();
		}
	}

	protected <T> T makeRequestInt(HttpRequestBase method, String action, Map<String, String> params, Class<T> respType)
		throws SDBException {
		try {
			return makeRequest(method, action, params, respType);
		} catch (AWSException ex) {
			throw new SDBException(ex);
		} catch (JAXBException ex) {
			throw new SDBException("Problem parsing returned message.", ex);
		} catch (SAXException ex) {
			throw new SDBException("Problem parsing returned message.", ex);
		} catch (HttpException ex) {
			throw new SDBException(ex.getMessage(), ex);
		} catch (IOException ex) {
			throw new SDBException(ex.getMessage(), ex);
		}
	}

	private ItemAttribute createAttribute(Attribute a) {
		String name = a.getName().getValue();
		String encoding = a.getName().getEncoding();
		if (encoding != null && encoding.equals("base64")) {
			name = new String(Base64.decodeBase64(name.getBytes()));
		}
		String value = a.getValue().getValue();
		encoding = a.getValue().getEncoding();
		if (encoding != null && encoding.equals("base64")) {
			value = new String(Base64.decodeBase64(value.getBytes()));
		}
		return new ItemAttribute(name, value, false);
	}
}
