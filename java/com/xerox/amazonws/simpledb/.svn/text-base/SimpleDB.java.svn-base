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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpGet;

import com.xerox.amazonws.common.AWSException;
import com.xerox.amazonws.common.AWSQueryConnection;
import com.xerox.amazonws.typica.sdb.jaxb.CreateDomainResponse;
import com.xerox.amazonws.typica.sdb.jaxb.DeleteDomainResponse;
import com.xerox.amazonws.typica.sdb.jaxb.ListDomainsResponse;

/**
 * This class provides an interface with the Amazon SDB service. It provides high level
 * methods for listing and creating and deleting domains.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class SimpleDB {

    private static Log logger = LogFactory.getLog(SimpleDB.class);

	private AWSQueryConnection connection;	// connection delegate

	/**
	 * Initializes the sdb service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
	 */
    public SimpleDB(String awsAccessId, String awsSecretKey) {
        this(awsAccessId, awsSecretKey, true);
    }

	/**
	 * Initializes the sdb service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from SDB.
	 */
    public SimpleDB(String awsAccessId, String awsSecretKey, boolean isSecure) {
        this(awsAccessId, awsSecretKey, isSecure, "sdb.amazonaws.com");
    }

	/**
	 * Initializes the sdb service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from SDB.
     * @param server Which host to connect to.  Usually, this will be sdb.amazonaws.com
	 */
    public SimpleDB(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server)
    {
        this(awsAccessId, awsSecretKey, isSecure, server,
             isSecure ? 443 : 80);
    }

    /**
	 * Initializes the sdb service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from SDB.
     * @param server Which host to connect to.  Usually, this will be sdb.amazonaws.com
     * @param port Which port to use.
     */
    public SimpleDB(String awsAccessId, String awsSecretKey, boolean isSecure,
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
	 * This method sets the endpoint (based on EU or US endpoint)
	 *
	 * @param endpointHost the name of the enpoint server
	 */
	public void setEndpoint(String endpointHost) {
		connection.setServer(endpointHost);
	}

	/**
	 * Creates a domain. If domain already exists, no error is thrown.
	 *
	 * @param name name of the new domain
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult<Domain> createDomain(String name) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", name);
		HttpGet method = new HttpGet();
		CreateDomainResponse response =
					makeRequestInt(method, "CreateDomain", params, CreateDomainResponse.class);
		Domain ret = new Domain(name, connection);
		return new SDBResult<Domain>(response.getResponseMetadata().getRequestId(),
								response.getResponseMetadata().getBoxUsage(), ret);
	}

	/**
	 * Deletes a domain.
	 *
	 * @param domain the domain to be deleted
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult deleteDomain(Domain domain) throws SDBException {
		return deleteDomain(domain.getName());
	}

	/**
	 * Deletes a domain.
	 *
	 * @param name the name of the domain to be deleted
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBResult deleteDomain(String name) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("DomainName", name);
		HttpGet method = new HttpGet();
		DeleteDomainResponse response =
				makeRequestInt(method, "DeleteDomain", params, DeleteDomainResponse.class);
		return new SDBResult<Domain>(response.getResponseMetadata().getRequestId(),
								response.getResponseMetadata().getBoxUsage());
	}

	/**
	 * Method for getting a Domain object without getting a list of them.
	 *
	 * @param domainName the name of the domain to be returned
	 * @throws SDBException wraps checked exceptions
	 */
	public Domain getDomain(String domainName) throws SDBException {
		return new Domain(domainName, connection);
	}

	/**
	 * Gets a list of domains
	 * 
	 * @return A list of {@link Domain} instances.
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBListResult<Domain> listDomains() throws SDBException {
		return this.listDomains(null);
	}

	/**
	 * Gets a list of domains
	 * 
	 * @param nextToken token to use when retrieving next results
	 * @return A list of {@link Domain} instances.
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBListResult<Domain> listDomains(String nextToken) throws SDBException {
		return this.listDomains(nextToken, 0);
	}

	/**
	 * Gets a list of domains
	 * 
	 * @param nextToken token to use when retrieving next results
	 * @param maxResults the max number of results to return (0 means no max defined)
	 * @throws SDBException wraps checked exceptions
	 */
	public SDBListResult<Domain> listDomains(String nextToken, int maxResults) throws SDBException {
		Map<String, String> params = new HashMap<String, String>();
		if (nextToken != null) {
			params.put("NextToken", nextToken);
		}
		if (maxResults > 0) {
			params.put("MaxNumberOfDomains", ""+maxResults);
		}
		HttpGet method = new HttpGet();
		ListDomainsResponse response =
					makeRequestInt(method, "ListDomains", params, ListDomainsResponse.class);

		SDBListResult<Domain> ret = new SDBListResult<Domain>(
								response.getListDomainsResult().getNextToken(),
								response.getResponseMetadata().getRequestId(),
								response.getResponseMetadata().getBoxUsage());
		List<Domain> results = ret.getItems();
		results.addAll(Domain.createList(response.getListDomainsResult().getDomainNames().toArray(new String[] {}),
							connection));
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

	static void setVersionHeader(AWSQueryConnection connection) {
		ArrayList vals = new ArrayList();
		vals.add("2009-04-15");
		connection.getHeaders().put("Version", vals);
	}
}
