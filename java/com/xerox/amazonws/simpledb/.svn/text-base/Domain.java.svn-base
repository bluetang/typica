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

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpGet;

import com.xerox.amazonws.common.AWSException;
import com.xerox.amazonws.common.AWSQueryConnection;
import com.xerox.amazonws.common.ListResult;
import com.xerox.amazonws.common.Result;
import com.xerox.amazonws.typica.sdb.jaxb.Attribute;
import com.xerox.amazonws.typica.sdb.jaxb.BatchDeleteAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.BatchPutAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.DeleteAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.DomainMetadataResponse;
import com.xerox.amazonws.typica.sdb.jaxb.GetAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.PutAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.SelectResponse;

/**
 * This class provides an interface with the Amazon SDB service. It provides methods for
 * listing and deleting items.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class Domain {

    private static Log logger = LogFactory.getLog(Domain.class);

	private AWSQueryConnection connection;	// connection delegate

	private String domainName;
	private ItemCache cache;

    protected Domain(String domainName, AWSQueryConnection connection) throws SDBException {
		this.domainName = domainName;
		this.connection = connection;
    }

	/**
	 * Returns connection object, so connection params can be tweaked
	 */
	public AWSQueryConnection getConnectionDelegate() {
		return connection;
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
	 * Returns information about the domain.
	 *
     * @return the object containing metadata about this domain
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult<DomainMetadata> getMetadata() throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		HttpGet method = new HttpGet();
		DomainMetadataResponse response =
					makeRequestInt(method, "DomainMetadata", params, DomainMetadataResponse.class);
		com.xerox.amazonws.typica.sdb.jaxb.DomainMetadataResult result =
								response.getDomainMetadataResult();
		return new SDBResult<DomainMetadata>(response.getResponseMetadata().getRequestId(),
											response.getResponseMetadata().getBoxUsage(),
				new DomainMetadata(Integer.parseInt(result.getItemCount()),
					Integer.parseInt(result.getAttributeValueCount()),
					Integer.parseInt(result.getAttributeNameCount()),
					Long.parseLong(result.getItemNamesSizeBytes()),
					Long.parseLong(result.getAttributeValuesSizeBytes()),
					Long.parseLong(result.getAttributeNamesSizeBytes()),
					new Date(Long.parseLong(result.getTimestamp())*1000)));
	}

	/**
	 * Adds an item.
	 *
	 * @param item the item to add to this domain
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult addItem(Item item) throws SDBException {
		return addItem(item.getIdentifier(), item.getAttributes(), null);
	}

	/**
	 * Adds an item. This method also works to add attributes to an existing item.
	 *
	 * @param identifier the name of the item to be added
	 * @param attributes the attributes to associate with this item
	 * @param conditions the conditions under which attributes should be put
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult addItem(String identifier, Map<String, Set<String>> attributes, List<Condition> conditions) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		int i=1;
		for (String key : attributes.keySet()) {
			Set<String> vals = attributes.get(key);
			if (vals != null && vals.size() > 0) {
				Iterator<String> iter = vals.iterator();
				while (iter.hasNext()) {
					String val = iter.next();
					params.put("Attribute."+i+".Name", key);
					params.put("Attribute."+i+".Value", val);
					i++;
				}
			}
		}
		if (conditions != null) {
			i=1;
			for (Condition cond : conditions) {
				params.put("Expected."+i+".Name", cond.getName());
				String value = cond.getValue();
				if (value != null) {
					params.put("Expected."+i+".Value", value);
				}
				else {
					params.put("Expected."+i+".Exists", cond.isExists()?"true":"false");
				}
				i++;
			}
		}
		HttpGet method = new HttpGet();
		PutAttributesResponse response =
			makeRequestInt(method, "PutAttributes", params, PutAttributesResponse.class);
		if (cache != null) {
			// create new item object
			Item newItem = new ItemVO(identifier);
			Map<String, Set<String>> attrs = newItem.getAttributes();
			// throw attrs into it
			attrs.putAll(attributes);
			Item old = cache.getItem(identifier);
			if (old != null) {
				// merge cached attrs with those just set
				for (String key: old.getAttributes().keySet()) {
					Set<String> oldAttrs = old.getAttributes().get(key);
					Set<String> newAttrs = attrs.get(key);
					if (newAttrs != null) {
						newAttrs.addAll(oldAttrs);
					}
					else {
						attrs.put(key, oldAttrs);
					}
				}
			}
			// place/replace item in cache
			cache.putItem(newItem);
		}
		return new SDBResult(response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * Batch inserts multiple items w/ attributes
	 *
	 * @param attributes list of attributes to add
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult batchPutAttributes(List<Item> items) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		int k=1;
		for (Item item : items) {
			params.put("Item."+k+".ItemName", item.getIdentifier());
			int i=1;
			for (String attr : item.getAttributes().keySet()) {
				Set<String> vals = item.getAttributeValues(attr);
				if (vals != null && vals.size() > 0) {
					for (String val : vals) {
						params.put("Item."+k+".Attribute."+i+".Name", attr);
						params.put("Item."+k+".Attribute."+i+".Value", val);
						i++;
//						if (attr.isReplace()) {
//							params.put("Item."+k+".Attribute."+i+".Replace", "true");
//						}
					}
				}
			}
			k++;
		}
		HttpGet method = new HttpGet();
		BatchPutAttributesResponse response =
			makeRequestInt(method, "BatchPutAttributes", params, BatchPutAttributesResponse.class);
		return new SDBResult(response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * Replace attributes on an item. Using this call will force attribute values to be
	 * with the new ones supplied.
	 *
	 * @param identifier the name of the item to be added
	 * @param attributes the attributes to associate with this item
	 * @param conditions the conditions under which attributes should be put
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult replaceAttributes(String identifier, Map<String, Set<String>> attributes, List<Condition> conditions) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		int i=1;
		for (String key : attributes.keySet()) {
			Set<String> vals = attributes.get(key);
			if (vals != null && vals.size() > 0) {
				Iterator<String> iter = vals.iterator();
				while (iter.hasNext()) {
					String val = iter.next();
					params.put("Attribute."+i+".Name", key);
					params.put("Attribute."+i+".Value", val);
					params.put("Attribute."+i+".Replace", "true");
					i++;
				}
			}
		}
		if (conditions != null) {
			i=1;
			for (Condition cond : conditions) {
				params.put("Expected."+i+".Name", cond.getName());
				String value = cond.getValue();
				if (value != null) {
					params.put("Expected."+i+".Value", value);
				}
				else {
					params.put("Expected."+i+".Exists", cond.isExists()?"true":"false");
				}
				i++;
			}
		}
		HttpGet method = new HttpGet();
		PutAttributesResponse response =
			makeRequestInt(method, "PutAttributes", params, PutAttributesResponse.class);
		if (cache != null) {
			// create new item object
			Item newItem = new ItemVO(identifier);
			Map<String, Set<String>> attrs = newItem.getAttributes();
			// throw attrs into it
			attrs.putAll(attributes);
			Item old = cache.getItem(identifier);
			if (old != null) {
				// merge cached attrs
				attrs.putAll(old.getAttributes());
			}
			// place/replace item in cache
			cache.putItem(newItem);
		}
		return new SDBResult(response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * Deletes attributes from an item
	 *
	 * @param identifier the name of the item
	 * @param attributes the names of the attributes to be deleted
	 * @param conditions the conditions under which attributes should be put
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult deleteAttributes(String identifier, Set<String> attributes, List<Condition> conditions) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		int i=1;
		if (attributes != null && attributes.size() > 0) {
			Iterator<String> iter = attributes.iterator();
			while (iter.hasNext()) {
				String val = iter.next();
				params.put("Attribute."+i+".Name", val);
				i++;
			}
		}
		if (conditions != null) {
			i=1;
			for (Condition cond : conditions) {
				params.put("Expected."+i+".Name", cond.getName());
				String value = cond.getValue();
				if (value != null) {
					params.put("Expected."+i+".Value", value);
				}
				else {
					params.put("Expected."+i+".Exists", cond.isExists()?"true":"false");
				}
				i++;
			}
		}
		HttpGet method = new HttpGet();
		DeleteAttributesResponse response =
			makeRequestInt(method, "DeleteAttributes", params, DeleteAttributesResponse.class);
		if (cache != null) {
		//	cache.removeItem(identifier);
		}
		return new SDBResult(response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * Batch deletes multiple items w/ attributes
	 *
	 * @param attributes list of attributes to delete
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult batchDeleteAttributes(List<Item> items) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		int k=1;
		for (Item item : items) {
			params.put("Item."+k+".ItemName", item.getIdentifier());
			int i=1;
			for (String attr : item.getAttributes().keySet()) {
				Set<String> vals = item.getAttributeValues(attr);
				if (vals != null && vals.size() > 0) {
					for (String val : vals) {
						params.put("Item."+k+".Attribute."+i+".Name", attr);
						params.put("Item."+k+".Attribute."+i+".Value", val);
						i++;
//						if (attr.isReplace()) {
//							params.put("Item."+k+".Attribute."+i+".Replace", "true");
//						}
					}
				}
			}
			k++;
		}
		HttpGet method = new HttpGet();
		BatchDeleteAttributesResponse response =
			makeRequestInt(method, "BatchDeleteAttributes", params, BatchDeleteAttributesResponse.class);
		return new SDBResult(response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * Deletes an item.
	 *
	 * @param identifier the name of the item to be deleted
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult deleteItem(String identifier) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		HttpGet method = new HttpGet();
		DeleteAttributesResponse response =
			makeRequestInt(method, "DeleteAttributes", params, DeleteAttributesResponse.class);
		if (cache != null) {
			cache.removeItem(identifier);
		}
		return new SDBResult(response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * This method returns an item object, which exists locally only.
	 * It must be persisted with addItem, batchPutAttributes or replaceAttributes
	 *
	 * @param identifier the name of the item to be deleted
	 * @return item object
	 * @throws SDBException wraps checked exceptions
	 */
	public Item createItem(String identifier) {
		return new ItemVO(identifier);
	}

	/**
	 * Returns an named item.
	 *
	 * @param identifier the name of the item to be deleted
	 * @throws SDBException wraps checked exceptions
	 */
	public Result<Item> getItem(String identifier) throws SDBException {
		if (cache != null) {
			Item cached = cache.getItem(identifier);
			if (cached != null) {
				return new Result<Item>(null, cached);
			}
			// else, go fetch it anyway
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		HttpGet method = new HttpGet();
		GetAttributesResponse response =
					makeRequestInt(method, "GetAttributes", params, GetAttributesResponse.class);
		Item newItem = new ItemVO(identifier);
		Map<String, Set<String>> attrs = newItem.getAttributes();
		for (Attribute a : response.getGetAttributesResult().getAttributes()) {
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
			Set<String> vals = attrs.get(name);
			if (vals == null) {
				vals = Collections.synchronizedSet(new HashSet<String>());
				attrs.put(name, vals);
			}
			vals.add(value);
		}
		if (cache != null) {
			cache.putItem(newItem);
		}
		return new Result<Item>(null, newItem);
	}

	/**
	 * Performs a query against this domain. A set of items is returned which may not be
	 * complete. If the nextToken in the result is set, this method can be called again
	 * with the same query and the supplied nextToken.
	 *
	 * @param selectExpression the query to be run
	 * @param nextToken an optional token to get more results
	 * @param consistent if true, consistency is assured
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBListResult<Item> selectItems(String selectExpression, String nextToken, boolean consistent) throws SDBException {
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
		SDBListResult<Item> ret = new SDBListResult<Item>(
								response.getSelectResult().getNextToken(),
								response.getResponseMetadata().getRequestId(),
								response.getResponseMetadata().getBoxUsage());
		List<Item> results = ret.getItems();
		for (com.xerox.amazonws.typica.sdb.jaxb.Item i : response.getSelectResult().getItems()) {
			String iName = i.getName().getValue();
			String encoding = i.getName().getEncoding();
			if (encoding != null && encoding.equals("base64")) {
				iName = new String(Base64.decodeBase64(iName.getBytes()));
			}
			Item newItem = new ItemVO(iName);
			Map<String, Set<String>> attrs = newItem.getAttributes();
			for (Attribute a : i.getAttributes()) {
				String name = a.getName().getValue();
				encoding = a.getName().getEncoding();
				if (encoding != null && encoding.equals("base64")) {
					name = new String(Base64.decodeBase64(name.getBytes()));
				}
				String value = a.getValue().getValue();
				encoding = a.getValue().getEncoding();
				if (encoding != null && encoding.equals("base64")) {
					value = new String(Base64.decodeBase64(value.getBytes()));
				}
				Set<String> vals = attrs.get(name);
				if (vals == null) {
					vals = Collections.synchronizedSet(new HashSet<String>());
					attrs.put(name, vals);
				}
				vals.add(value);
			}
			results.add(newItem);
		}

		return ret;
	}

	public ItemCache getItemCache() {
		return this.cache;
	}

	public void setCacheProvider(ItemCache cache) {
		this.cache = cache;
	}

	static List<Domain> createList(String [] domainNames, AWSQueryConnection connection)
			throws SDBException {
		ArrayList<Domain> ret = new ArrayList<Domain>();
		for (int i=0; i<domainNames.length; i++) {
			Domain dom = new Domain(domainNames[i], connection);
			ret.add(dom);
		}
		return ret;
	}

	protected <T> T makeRequestInt(HttpRequestBase method, String action, Map<String, String> params, Class<T> respType)
		throws SDBException {
		try {
			return connection.makeRequest(method, action, params, respType);
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
}
