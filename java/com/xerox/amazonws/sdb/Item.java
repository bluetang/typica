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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xerox.amazonws.typica.sdb.jaxb.DeleteAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.GetAttributesResponse;
import com.xerox.amazonws.typica.sdb.jaxb.PutAttributesResponse;

/**
 * This class provides an interface with the Amazon SDB service. It provides methods for
 * listing items and adding/removing attributes.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class Item extends AWSQueryConnection {
    private static Log logger = LogFactory.getLog(Item.class);

	private String domainName;
	private String identifier;

    protected Item(String identifier, String domainName, String awsAccessId,
							String awsSecretKey, boolean isSecure, int port,
							String server) throws SDBException {
        super(awsAccessId, awsSecretKey, isSecure, server, port);
		this.domainName = domainName;
		this.identifier = identifier;
		SimpleDB.setVersionHeader(this);
	}

	/**
	 * Gets the name of the identifier that is unique to this Item
	 *
     * @return the id
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Gets a map of all attributes for this item
	 *
     * @return the map of attributes
	 * @throws SDBException wraps checked exceptions
	 */
	public List<ItemAttribute> getAttributes() throws SDBException {
		return getAttributes((String)null);
	}

	/**
	 * Gets attributes of a given name. The parameter limits the results to those of
	 * the name given.
	 *
	 * @param attributeName a name that limits the results
     * @return the list of attributes
	 * @throws SDBException wraps checked exceptions
	 * @deprecated this didn't work, so I don't expect anyone was using it anyway!
	 */
	public List<ItemAttribute> getAttributes(String attributeName) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		if (attributeName != null) {
			params.put("AttributeName.1", attributeName);
		}
		HttpGet method = new HttpGet();
		try {
			GetAttributesResponse response =
						makeRequestInt(method, "GetAttributes", params, GetAttributesResponse.class);
			List<ItemAttribute> ret = new ArrayList<ItemAttribute>();
			List<Attribute> attrs = response.getGetAttributesResult().getAttributes();
			for (Attribute attr : attrs) {
				ret.add(createAttribute(attr));
			}
			return ret;
		} catch (UnsupportedEncodingException ex) {
			throw new SDBException(ex.getMessage(), ex);
		}
	}

	/**
	 * Gets selected attributes. The parameter limits the results to those of
	 * the name(s) given.
	 *
	 * @param attributes name(s) that limits the results
     * @return the list of attributes
	 * @throws SDBException wraps checked exceptions
	 */
	public List<ItemAttribute> getAttributes(List<String> attributes) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		int idx = 1;
		if (attributes != null) {
			for (String attr : attributes) {
				params.put("AttributeName."+idx, attr);
				idx++;
			}
		}
		HttpGet method = new HttpGet();
		try {
			GetAttributesResponse response =
						makeRequestInt(method, "GetAttributes", params, GetAttributesResponse.class);
			List<ItemAttribute> ret = new ArrayList<ItemAttribute>();
			List<Attribute> attrs = response.getGetAttributesResult().getAttributes();
			for (Attribute attr : attrs) {
				ret.add(createAttribute(attr));
			}
			return ret;
		} catch (UnsupportedEncodingException ex) {
			throw new SDBException(ex.getMessage(), ex);
		}
	}

	/**
	 * Gets selected attributes. The parameter limits the results to those of
	 * the name(s) given.
	 *
	 * @param attributes name(s) that limits the results
     * @return the list of attributes
	 * @throws SDBException wraps checked exceptions
	 */
	public Map<String, List<String>> getAttributesMap(List<String> attributes) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		int idx = 1;
		if (attributes != null) {
			for (String attr : attributes) {
				params.put("AttributeName."+idx, attr);
				idx++;
			}
		}
		HttpGet method = new HttpGet();
		try {
			GetAttributesResponse response =
						makeRequestInt(method, "GetAttributes", params, GetAttributesResponse.class);
			Map<String, List<String>> ret = new HashMap<String, List<String>>();
			List<Attribute> attrs = response.getGetAttributesResult().getAttributes();
			for (Attribute attr : attrs) {
				String name = attr.getName().getValue();
				String encoding = attr.getName().getEncoding();
				if (encoding != null && encoding.equals("base64")) {
					name = new String(Base64.decodeBase64(name.getBytes()), "UTF-8");
				}
				List<String> vals = ret.get(name);
				if (vals == null) {
					vals = new ArrayList<String>();
					ret.put(name, vals);
				}
				String value = attr.getValue().getValue();
				encoding = attr.getValue().getEncoding();
				if (encoding != null && encoding.equals("base64")) {
					value = new String(Base64.decodeBase64(value.getBytes()), "UTF-8");
				}
				vals.add(value);
			}
			return ret;
		} catch (UnsupportedEncodingException ex) {
			throw new SDBException(ex.getMessage(), ex);
		}
	}

	/**
	 * Creates attributes for this item. Each item can have "replace" specified which
	 * indicates to replace the Attribute/Value or ad a new Attribute/Value.
	 * NOTE: if an attribute value is null, that attribute will be ignored.
	 *
	 * @param attributes list of attributes to add
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult putAttributes(List<ItemAttribute> attributes) throws SDBException {
		return putAttributes(attributes, null);
	}

	/**
	 * Creates attributes for this item. Each item can have "replace" specified which
	 * indicates to replace the Attribute/Value or ad a new Attribute/Value.
	 * NOTE: if an attribute value is null, that attribute will be ignored.
	 *
	 * @param attributes list of attributes to add
	 * @param conditions the conditions under which attributes should be put
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult putAttributes(List<ItemAttribute> attributes, List<Condition> conditions) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		int i=1;
		for (ItemAttribute attr : attributes) {
			String val = attr.getValue();
			if (val != null) {
				params.put("Attribute."+i+".Name", attr.getName());
				params.put("Attribute."+i+".Value", val);
				if (attr.isReplace()) {
					params.put("Attribute."+i+".Replace", "true");
				}
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
		PutAttributesResponse response =
			makeRequestInt(method, "PutAttributes", params, PutAttributesResponse.class);
		return new SDBResult(null, 
					response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * Deletes one or more attributes.
	 *
	 * @param attributes the names of the attributes to be deleted
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult deleteAttributes(List<ItemAttribute> attributes) throws SDBException {
		return deleteAttributes(attributes, null);
	}

	/**
	 * Deletes one or more attributes.
	 *
	 * @param attributes the names of the attributes to be deleted
	 * @param conditions the conditions under which the delete should happen
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult deleteAttributes(List<ItemAttribute> attributes, List<Condition> conditions) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", domainName);
		params.put("ItemName", identifier);
		if (attributes != null) {
			int i=1;
			for (ItemAttribute attr : attributes) {
				params.put("Attribute."+i+".Name", attr.getName());
				String value = attr.getValue();
				if (value != null) {
					params.put("Attribute."+i+".Value", value);
				}
				i++;
			}
		}
		if (conditions != null) {
			int i=1;
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
		return new SDBResult(null, 
					response.getResponseMetadata().getRequestId(),
					response.getResponseMetadata().getBoxUsage());
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

	private ItemAttribute createAttribute(Attribute a) throws UnsupportedEncodingException {
		String name = a.getName().getValue();
		String encoding = a.getName().getEncoding();
		if (encoding != null && encoding.equals("base64")) {
			name = new String(Base64.decodeBase64(name.getBytes()), "UTF-8");
		}
		String value = a.getValue().getValue();
		encoding = a.getValue().getEncoding();
		if (encoding != null && encoding.equals("base64")) {
			value = new String(Base64.decodeBase64(value.getBytes()), "UTF-8");
		}
		return new ItemAttribute(name, value, false);
	}
}
