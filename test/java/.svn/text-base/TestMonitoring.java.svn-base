
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.monitoring.Datapoint;
import com.xerox.amazonws.monitoring.Metric;
import com.xerox.amazonws.monitoring.Metric.Dimension;
import com.xerox.amazonws.monitoring.MetricStatisticsResult;
import com.xerox.amazonws.monitoring.Monitoring;
import com.xerox.amazonws.monitoring.StandardUnit;
import com.xerox.amazonws.monitoring.Statistics;

// args : 

public class TestMonitoring {
    private static Log logger = LogFactory.getLog(TestMonitoring.class);

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(TestMonitoring.class.getClassLoader().getResourceAsStream("aws.properties"));

		Monitoring mon = new Monitoring(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
		List<Metric> metrix = mon.listMetrics();
		for (Metric m : metrix) {
			logger.info("name = "+m.getName()+":"+m.getNamespace());
			for (Dimension dim : m.getDimensions()) {
				logger.info("   "+dim.getName()+": "+dim.getValue());
			}
		}
		List<Statistics> stats = new ArrayList<Statistics>();
		stats.add(Statistics.AVERAGE);
		Map<String, String> dimensions = new HashMap<String, String>();
		// can be InstanceId, InstanceType, ImageId
		dimensions.put("InstanceId", "i-1de3a674");
		//dimensions.put("ImageId", "ami-85d037ec");
		Date end = new Date();	// that means today
		// need to adjust these for GMT??
		end = new Date(end.getTime() + 3600000*5);
		Date start = new Date(end.getTime() - 3600000*24);	// 1 days ago
		MetricStatisticsResult result = mon.getMetricStatistics(
						60,	// must be multiple of 60
						stats,	// see above
						"AWS/EC2",
						dimensions,
						start,	// start of interval
						end,	// end of interval
							// can be NetworkIn, NetworkOut, DiskReadOps,
							// DiskWriteOps, DiskReadBytes, DiskWriteBytes,
							// CPUUtilization
						"CPUUtilization",
						StandardUnit.PERCENT,
						null);
		logger.info("metrics label = "+result.getLabel());
		for (Datapoint dp : result.getDatapoints()) {
			logger.info(dp.getTimestamp().getTime().toString()+" samples:"+dp.getSamples()+" "+dp.getAverage()+" "+dp.getUnit()+"("+dp.getMinimum()+"/"+dp.getMaximum()+")");
		}
	}
}

