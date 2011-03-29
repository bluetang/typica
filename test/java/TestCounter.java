
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.sdb.Counter;
import com.xerox.amazonws.sdb.Domain;
import com.xerox.amazonws.sdb.SDBException;
import com.xerox.amazonws.sdb.SimpleDB;

/**
 * This tests the SimplDB Counter. Due to eventual consistency,
 * a multi-threaded tester isn't required.
 */
public class TestCounter {
    private static Log logger = LogFactory.getLog(TestCounter.class);

	public static void main(String [] args) throws Exception {
		try {
			Properties props = new Properties();
			props.load(TestSimpleDB.class.getClassLoader().getResourceAsStream("aws.properties"));

			SimpleDB sdb = new SimpleDB(props.getProperty("aws.accessId"),
									props.getProperty("aws.secretKey"));

			Domain dom = sdb.createDomain(args[0]);
			Counter c = new Counter(dom, "counter1");
			for (int i=0; i<20; i++) {
				System.err.println("next val = "+c.nextValue());
			}
		} catch (SDBException ex) {
			System.err.println("message : "+ex.getMessage());
			System.err.println("requestID : "+ex.getRequestId());
			ex.printStackTrace();
		}
	}
}

