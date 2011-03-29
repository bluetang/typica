
package com.xerox.amazonws.common;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;

import org.apache.http.HttpConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.ProtocolException;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;


public class TypicaHttpClient extends DefaultHttpClient {
	public TypicaHttpClient(
            final ClientConnectionManager conman,
            final HttpParams params) {
        super(conman, params);
    }

	protected BasicHttpProcessor createHttpProcessor() {
		BasicHttpProcessor httpProc = super.createHttpProcessor();
		httpProc.removeRequestInterceptorByClass(RequestTargetHost.class);
		httpProc.removeRequestInterceptorByClass(RequestUserAgent.class);
		httpProc.addInterceptor(new TypicaTargetHost());
		return httpProc;
	}

	class TypicaTargetHost implements HttpRequestInterceptor {
		public TypicaTargetHost() {
			super();
		}

		public void process(final HttpRequest request, final HttpContext context) 
				throws HttpException, IOException {
			if (request == null) {
				throw new IllegalArgumentException("HTTP request may not be null");
			}
			if (context == null) {
				throw new IllegalArgumentException("HTTP context may not be null");
			}
			
			ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
			String method = request.getRequestLine().getMethod();
			if (method.equalsIgnoreCase("CONNECT") && ver.lessEquals(HttpVersion.HTTP_1_0)) {
				return;
			}
			
			if (!request.containsHeader(HTTP.TARGET_HOST)) {
				HttpHost targethost = (HttpHost) context
					.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				if (targethost == null) {
					HttpConnection conn = (HttpConnection) context
						.getAttribute(ExecutionContext.HTTP_CONNECTION);
					if (conn instanceof HttpInetConnection) {
						// Populate the context with a default HTTP host based on the 
						// inet address of the target host
						InetAddress address = ((HttpInetConnection) conn).getRemoteAddress();
//						int port = ((HttpInetConnection) conn).getRemotePort();
						if (address != null) {
							targethost = new HttpHost(address.getHostName());//, port);
						}
					}
					if (targethost == null) {
						if (ver.lessEquals(HttpVersion.HTTP_1_0)) {
							return;
						} else {
							throw new ProtocolException("Target host missing");
						}
					}
				}
				request.addHeader(HTTP.TARGET_HOST, targethost.getHostName());
			}
		}
	
	}
}
