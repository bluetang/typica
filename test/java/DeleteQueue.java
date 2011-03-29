
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.sqs2.Message;
import com.xerox.amazonws.sqs2.MessageQueue;
import com.xerox.amazonws.sqs2.QueueService;
import com.xerox.amazonws.sqs2.SQSException;

public class DeleteQueue {
    private static Log logger = LogFactory.getLog(TestQueueService.class);

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(TestJec2.class.getClassLoader().getResourceAsStream("aws.properties"));

		try {
			QueueService qs = new QueueService(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
			MessageQueue msgQueue = null;
			if (args.length > 1) {
				msgQueue = qs.getOrCreateMessageQueue(args[0], Integer.parseInt(args[1]));
			}
			else {
				msgQueue = qs.getOrCreateMessageQueue(args[0]);
			}
			msgQueue.deleteQueue();
		} catch (SQSException ex) {
			ex.printStackTrace();
		}
	}






























        final static String AWSAccessKeyId = "1SEQ6QDW2YNW8T6K64R2";
        final static String SecretAccessKey = "7P1KY+a4FTtiVBuU935NHHOI19eYrbyWG7CDklmk";
}
