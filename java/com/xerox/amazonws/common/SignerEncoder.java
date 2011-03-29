
package com.xerox.amazonws.common;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.security.AccessController;
import sun.security.action.GetPropertyAction;

/**
 * This encoder is to be used instead of the URLEncoder when encoding params for version 2 signing
 */
public class SignerEncoder {
	private static BitSet dontEncode;
	private static String defaultEncoding;

	static {
		// encode everything except what is included in the bitset
		dontEncode = new BitSet(256);
		for (int i='a'; i<='z'; i++) {
			dontEncode.set(i);
		}
		for (int i='A'; i<='Z'; i++) {
			dontEncode.set(i);
		}
		for (int i='0'; i<='9'; i++) {
			dontEncode.set(i);
		}
		dontEncode.set('-');
		dontEncode.set('_');
		dontEncode.set('.');
		dontEncode.set('~');

		defaultEncoding = (String)AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
	}

	public static String encode(String str) throws UnsupportedEncodingException {
		int lowerDiff = 'a' - 'A';
		StringBuffer ret = new StringBuffer(str.length());
		CharArrayWriter charsToEscape = new CharArrayWriter();
		try {
			for (int pos = 0; pos < str.length(); pos++) {
				int chr = (int)str.charAt(pos);
				if (dontEncode.get(chr)) {
					ret.append((char)chr);
				}
				else {
					do {
						charsToEscape.write(chr);
						if (chr >= 0xD800 && chr <= 0xDBFF) {
							if (pos < (str.length()-1)) {
								int lower = (int)str.charAt(pos+1);
								if (lower >= 0xDC00 && lower <= 0xDFFF) {
									charsToEscape.write(lower);
									pos++;
								}
							}
						}
						pos++;
					} while (pos < str.length() && !dontEncode.get((chr = (int)str.charAt(pos))));
					charsToEscape.flush();
					String tmp = new String(charsToEscape.toCharArray());
					byte [] bytes = tmp.getBytes("UTF-8");
					for (int i=0; i<bytes.length; i++) {
						ret.append("%");
						char ch = Character.forDigit((bytes[i] >> 4) & 0xf, 16);
						if (Character.isLetter(ch)) { ch -= lowerDiff; }
						ret.append(ch);
						ch = Character.forDigit(bytes[i] & 0xf, 16);
						if (Character.isLetter(ch)) { ch -= lowerDiff; }
						ret.append(ch);
					}
					charsToEscape.reset();
				}
			}
		} catch (UnsupportedEncodingException ex) { }
		return ret.toString();
	}

	public static void main(String [] args) throws Exception {
		String test1 = "abcdef ABCDEF -_.~";
		System.out.println("'"+test1+"' signer encoded as '"+SignerEncoder.encode(test1)+"'");
		String test2 = "hi\u2022there\077";
		System.out.println("'"+test2+"' signer encoded as '"+SignerEncoder.encode(test2)+"'");
		System.out.println("'"+test2+"' url encoded as '"+java.net.URLEncoder.encode(test2, "UTF-8")+"'");
	}
}
