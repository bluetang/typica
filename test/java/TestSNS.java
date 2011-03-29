
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.common.Result;
import com.xerox.amazonws.sns.NotificationService;
import com.xerox.amazonws.sns.SubscriptionInfo;

// args : 

public class TestSNS {
    private static Log logger = LogFactory.getLog(TestSNS.class);
	private static String TEST_MSG = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(TestSNS.class.getClassLoader().getResourceAsStream("aws.properties"));

		NotificationService sns = new NotificationService(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
		Result<String> ret = sns.createTopic("TestTopic");
		String topicArn = ret.getResult();
		System.err.println("topicArn: "+topicArn);

		sns.subscribe(topicArn, "email", "dkavanagh@gmail.com");
		System.out.println("Waiting till subscription is confirmed.");
		System.out.println("Check your e-mail, confirm, then press <return>");
		System.in.read();

		List<SubscriptionInfo> subs = sns.listSubscriptionsByTopic(topicArn, null).getItems();
		String subArn = subs.get(0).getSubscriptionArn();
		System.err.println("subscriptionArn: "+subArn);
		sns.publish(topicArn, TEST_MSG, "[SNS] testing...");

		sns.unsubscribe(subArn);
		sns.deleteTopic(topicArn);
	}
}

