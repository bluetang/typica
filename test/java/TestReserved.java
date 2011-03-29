
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.ec2.ProductDescription;
import com.xerox.amazonws.ec2.ReservedInstances;
import com.xerox.amazonws.ec2.Jec2;

// args : 
// 0 - offering id
// 1 - number of instances

public class TestReserved {
    private static Log logger = LogFactory.getLog(TestReserved.class);

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(TestJec2.class.getClassLoader().getResourceAsStream("aws.properties"));

		Jec2 ec2 = new Jec2(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
		List<ProductDescription> offerings = ec2.describeReservedInstancesOfferings(null, null, null, null);
		for (ProductDescription prod : offerings) {
			logger.info(prod.toString());
		}
		if (args.length == 2) {
			int numInstances = 0;
			try {
				numInstances = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {
				logger.error("2nd argument must be an integer.", ex);
			}
			logger.info("About to purchase offering "+args[0]+", "+args[1]+" instances");
			List<String> ids = new ArrayList<String>();
			ids.add(args[0]);
			offerings = ec2.describeReservedInstancesOfferings(ids, null, null, null);
			ProductDescription theOffering = offerings.get(0);
			logger.info("NOTE: this is going to cost you : $"+(theOffering.getFixedPrice()*numInstances));
			logger.info("Going to pause for 10 seconds to give you time to cancel!!!");
			try { Thread.sleep(10000); } catch (InterruptedException ex) {}
			ec2.purchaseReservedInstancesOffering(args[0], numInstances);
			logger.info("Instances reserved. Check the list to confirm");
		}
		List<ReservedInstances> instances = ec2.describeReservedInstances(null);
		if (instances.size() == 0) {
			logger.info("No reservations");
		}
		for (ReservedInstances instance : instances) {
			logger.info(instance.toString());
		}
	}
}

