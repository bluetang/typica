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

package com.xerox.amazonws.common;

import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * This class provides an interface with the Amazon SQS service. It provides high level
 * methods for listing and creating message queues.
 *
 * Http authentication code borrowed from Amazon S3 AWSAuthConnection code
 * (see amazon copyright below).
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class AWSAuthConnection extends AWSConnection {

    /**
	 * Initializes the queue service with your AWS login information.
	 *
     * @param awsAccessId The your user key into AWS
     * @param awsSecretKey The secret string used to generate signatures for authentication.
     * @param isSecure True if the data should be encrypted on the wire on the way to or from SQS.
     * @param server Which host to connect to.  Usually, this will be s3.amazonaws.com
     * @param port Which port to use.
     */
    public AWSAuthConnection(String awsAccessId, String awsSecretKey, boolean isSecure,
                             String server, int port)
    {
		super(awsAccessId, awsSecretKey, isSecure, server, port);
    }

    /**
     * Make a new HttpURLConnection.
     * @param method The HTTP method to use (GET, PUT, DELETE)
     * @param resource The resource name (bucketName + "/" + key).
     * @param headers A Map of String to List of Strings representing the http
     * headers to pass (can be null).
     */
    protected HttpURLConnection makeRequest(String method, String resource, Map headers)
        throws MalformedURLException, IOException
    {
        URL url = makeURL(resource);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod(method);

        addHeaders(connection, headers);
        addAuthHeader(connection, method, resource);

        return connection;
    }

    /**
     * Add the given headers to the HttpURLConnection.
     * @param connection The HttpURLConnection to which the headers will be added.
     * @param headers A Map of String to List of Strings representing the http
     * headers to pass (can be null).
     */
    private void addHeaders(HttpURLConnection connection, Map headers) {
        addHeaders(connection, headers, "");
    }

    /**
     * Add the given metadata fields to the HttpURLConnection.
     * @param connection The HttpURLConnection to which the headers will be added.
     * @param metadata A Map of String to List of Strings representing the s3
     * metadata for this resource.
     */
    private void addMetadataHeaders(HttpURLConnection connection, Map metadata) {
        addHeaders(connection, metadata, "x-amz-meta-");
    }

    /**
     * Add the given headers to the HttpURLConnection with a prefix before the keys.
     * @param connection The HttpURLConnection to which the headers will be added.
     * @param headers A Map of String to List of Strings representing the http
     * headers to pass (can be null).
     * @param prefix The string to prepend to each key before adding it to the connection.
     */
    private void addHeaders(HttpURLConnection connection, Map headers, String prefix) {
        if (headers != null) {
            for (Iterator i = headers.keySet().iterator(); i.hasNext(); ) {
                String key = (String)i.next();
                for (Iterator j = ((List)headers.get(key)).iterator(); j.hasNext(); ) {
                    String value = (String)j.next();
                    connection.addRequestProperty(prefix + key, value);
                }
            }
        }
    }

    /**
     * Add the appropriate Authorization header to the HttpURLConnection.
     * @param connection The HttpURLConnection to which the header will be added.
     * @param method The HTTP method to use (GET, PUT, DELETE)
     * @param resource The resource name (bucketName + "/" + key).
     */
    private void addAuthHeader(HttpURLConnection connection, String method, String resource) {
        if (connection.getRequestProperty("Date") == null) {
            connection.setRequestProperty("Date", httpDate());
        }
        if (connection.getRequestProperty("Content-Type") == null) {
            connection.setRequestProperty("Content-Type", "");
        }

        String canonicalString =
            makeCanonicalString(method, resource, connection.getRequestProperties());
        String encodedCanonical = encode(getSecretAccessKey(), canonicalString, false);
        connection.setRequestProperty("Authorization",
                                      "AWS " + getAwsAccessKeyId() + ":" + encodedCanonical);
    }

    private String makeCanonicalString(String method, String resource, Map headers) {
        return makeCanonicalString(method, resource, headers, null);
    }

    /**
     * Calculate the canonical string.  When expires is non-null, it will be
     * used instead of the Date header.
     */
    private String makeCanonicalString(String method, String resource,
                                             Map headers, String expires)
    {
        StringBuffer buf = new StringBuffer();
        buf.append(method + "\n");

        // Add all interesting headers to a list, then sort them.  "Interesting"
        // is defined as Content-MD5, Content-Type, Date, and x-amz-
        SortedMap interestingHeaders = new TreeMap();
        if (headers != null) {
            for (Iterator i = headers.keySet().iterator(); i.hasNext(); ) {
                String key = (String)i.next();
                if (key == null) continue;
                String lk = key.toLowerCase();

                // Ignore any headers that are not particularly interesting.
                if (lk.equals("content-type") || lk.equals("content-md5") || lk.equals("date") ||
                    lk.startsWith("x-amz-"))
                {
                    List s = (List)headers.get(key);
                    interestingHeaders.put(lk, concatenateList(s));
                }
            }
        }

        if (interestingHeaders.containsKey("x-amz-date")) {
            interestingHeaders.put("date", "");
        }

        // if the expires is non-null, use that for the date field.  this
        // trumps the x-amz-date behavior.
        if (expires != null) {
            interestingHeaders.put("date", expires);
        }

        // these headers require that we still put a new line in after them,
        // even if they don't exist.
        if (! interestingHeaders.containsKey("content-type")) {
            interestingHeaders.put("content-type", "");
        }
        if (! interestingHeaders.containsKey("content-md5")) {
            interestingHeaders.put("content-md5", "");
        }

        // Finally, add all the interesting headers (i.e.: all that startwith x-amz- ;-))
        for (Iterator i = interestingHeaders.keySet().iterator(); i.hasNext(); ) {
            String key = (String)i.next();
            if (key.startsWith("x-amz-")) {
                buf.append(key).append(':').append(interestingHeaders.get(key));
            } else {
                buf.append(interestingHeaders.get(key));
            }
            buf.append("\n");
        }

        // don't include the query parameters...
        int queryIndex = resource.indexOf('?');
        if (queryIndex == -1) {
            buf.append("/" + resource);
        } else {
            buf.append("/" + resource.substring(0, queryIndex));
        }

        // ...unless there is an acl or torrent parameter
        if (resource.matches(".*[&?]acl($|=|&).*")) {
            buf.append("?acl");
        } else if (resource.matches(".*[&?]torrent($|=|&).*")) {
            buf.append("?torrent");
        }

        return buf.toString();
    }

    /**
     * Concatenates a bunch of header values, seperating them with a comma.
     * @param values List of header values.
     * @return String of all headers, with commas.
     */
    private String concatenateList(List values) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0, size = values.size(); i < size; ++ i) {
            buf.append(((String)values.get(i)).replaceAll("\n", "").trim());
            if (i != (size - 1)) {
                buf.append(",");
            }
        }
        return buf.toString();
    }

    /**
     * Generate an rfc822 date for use in the Date HTTP header.
     */
    private static String httpDate() {
        final String DateFormat = "EEE, dd MMM yyyy HH:mm:ss ";
        SimpleDateFormat format = new SimpleDateFormat( DateFormat, Locale.US );
        format.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
        return format.format( new Date() ) + "GMT";
    }
}
