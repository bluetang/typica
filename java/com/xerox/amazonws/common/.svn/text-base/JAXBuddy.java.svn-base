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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * This class implements some helpful methods to marshal and unmarshal xml.
 *
 * @author D. Kavanagh
 * @author developer@dotech.com
 */
public class JAXBuddy {
	public final static Hashtable<String, JAXBContext> contextCache = new Hashtable<String, JAXBContext>();

	private static final DocumentBuilderFactory domFactory =
	    DocumentBuilderFactory.newInstance();
	
	private static ThreadLocal<DocumentBuilder> builder = null;
	
	public static DocumentBuilder createDocumentBuilder() throws Exception
	{
		return domFactory.newDocumentBuilder() ;
	}
	
	/** Initialize DocumentBuilder **/
	static {
		builder = new ThreadLocal<DocumentBuilder>() {
			@Override
			protected synchronized DocumentBuilder initialValue() {
				DocumentBuilder b = null;
				try {
					domFactory.setNamespaceAware(true);
					b= createDocumentBuilder();
				} catch(JAXBException e) {
					throw new ExceptionInInitializerError(e);
				} catch (Exception e) {						
					e.printStackTrace();
				}
				return b;
			}
		};
	}

	/**
	 * A convenience method to turn an object into a stream of XML.
	 *
	 * @param c the class you're serializing
	 * @param object the object you're serializing
	 * @return an input stream to read the XML from
	 */
    public static <T> InputStream serializeXMLFile(Class<T> c, Object object) 
            throws JAXBException, IOException {
        Marshaller m = getMarshaller(c);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        m.marshal(object, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;
    }

	/**
	 * A convenience method to turn an object into a string of XML.
	 *
	 * @param c the class you're serializing
	 * @param object the object you're serializing
	 * @return a string containing the XML
	 */
    public static <T> String serializeXMLString(Class<T> c, Object object) 
            throws JAXBException, IOException {
        Marshaller m = getMarshaller(c);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        m.marshal(object, baos);
		return new String(baos.toByteArray());
    }
 
	/**
	 * A convenience method to turn XML in a stream into an object.
	 *
	 * @param c the class you're deserializing
	 * @param is the stream to read the XMl from 
	 * @return an object representing the data from the stream
	 */
    public static <T> T deserializeXMLStream(Class<T> c, InputStream is)
			throws JAXBException, IOException, SAXException {
        Unmarshaller u = getUnmarshaller(c);
        //T result = c.cast(u.unmarshal(is));
		Document doc = builder.get().parse(is);
		if (doc == null) {
			throw new IOException("XML parser returned no document");
		}
		Node root = doc.getDocumentElement();
		T result = c.cast(u.unmarshal(doc));         
        return result;
    }

	/**
	 * This method will clear the internal cache we use to speed up these util functions.
	 *
	 * Do we think anybody will need this method? no...
	 * Are we providing it to be nice to the anal retentive amongst us? yes.
	 */
	public static void clearCache() {
		contextCache.clear();
	}
    
    private static Marshaller getMarshaller(Class<?> c) throws JAXBException {
       	String typePackage = c.getPackage().getName();
		JAXBContext jc = contextCache.get(typePackage);
		if (jc == null) {
        	jc = JAXBContext.newInstance(typePackage);
			contextCache.put(typePackage, jc);
		}
		Marshaller m = jc.createMarshaller();
        return m;
    }

    private static Unmarshaller getUnmarshaller(Class<?> c) throws JAXBException {
		String typePackage = c.getPackage().getName();
		JAXBContext jc = contextCache.get(typePackage);
		if (jc == null) {
			jc = JAXBContext.newInstance(typePackage, c.getClassLoader());
			contextCache.put(typePackage, jc);
		}
		Unmarshaller u = jc.createUnmarshaller();
        return u;
    }
}
