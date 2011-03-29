
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.xerox.amazonws.sqs.Grant;
import com.xerox.amazonws.sqs2.Message;
import com.xerox.amazonws.sqs2.MessageQueue;
import com.xerox.amazonws.sqs2.QueueService;
import com.xerox.amazonws.sqs2.SQSException;

public class TestQueueService {
    private static Log logger = LogFactory.getLog(TestQueueService.class);

	public static void main(String [] args) throws Exception {
		try {
		Properties props = new Properties();
		props.load(TestQueueService.class.getClassLoader().getResourceAsStream("aws.properties"));

		QueueService qs = new QueueService(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
/*
	*/
		List<MessageQueue> queues = qs.listMessageQueues(null);
		for (MessageQueue queue : queues) {
			logger.info("Queue : "+queue.getUrl().toString());
			// delete queues that contain a certain phrase
			if (queue.getUrl().toString().indexOf("dak")>-1) {
				try {
					queue.deleteQueue();
				} catch (SQSException ex) {
					ex.printStackTrace();
					logger.info("request id : "+ex.getRequestId());
				}
			}
		}
		for (int i=0; i<args.length; i++) {
			MessageQueue mq = qs.getOrCreateMessageQueue(args[i]);
			ArrayList<String> msgids = new ArrayList<String>();
/* test send/receive
*/
			for (int j=0; j<5; j++) {
				String msgid = mq.sendMessage("Testing 1, 2, 3");
				msgids.add(msgid);
				logger.info("send message, id = "+msgid);
			}
			ArrayList<String> attrs = new ArrayList<String>();
			attrs.add("SenderId");
			attrs.add("SentTimestamp");
			for (int j=0; j<5; j++) {
				Message [] msg = mq.receiveMessages(1, 30, attrs);
				if (msg.length == 0) { continue; }
				msgids.remove(msg[0].getMessageId());
				logger.info("Message "+(j+1)+" = "+msg[0].getMessageBody());
				Map<String, String> rcvdAttrs = msg[0].getAttributes();
				for (String name : rcvdAttrs.keySet()) {
					String value = rcvdAttrs.get(name);
					logger.info("Attr: "+name+"="+value);
				}
				mq.deleteMessage(msg[0]);
			}
			logger.info("messages not received : "+msgids.size());
/* test grants
			logger.info("Grants for "+mq.getUrl());
			Grant [] grants = mq.listGrants(null, null);
			for (Grant g : grants) {
				logger.info("grant : "+g.getGrantee()+" perm : "+g.getPermission());
			}
			logger.info("Adding Grant");
			mq.addGrantByEmailAddress("dak@directthought.com", "ReceiveMessage");
			logger.info("Grants for "+mq.getUrl());
			grants = mq.listGrants(null, null);
			for (Grant g : grants) {
				logger.info("grant : "+g.getGrantee()+" perm : "+g.getPermission());
			}
			logger.info("Removing Grant");
			mq.removeGrantByEmailAddress("xrxs33@gmail.com", "ReceiveMessage");
			logger.info("Grants for "+mq.getUrl());
			grants = mq.listGrants(null, null);
			for (Grant g : grants) {
				logger.info("grant : "+g.getGrantee()+" perm : "+g.getPermission());
			}
*/
		}
		/* test forced deletion
		MessageQueue mq = qs.getOrCreateMessageQueue("deleteTest-12345");
		for (int j=0; j<10; j++) {	// throw 10 messages in the queue
			mq.sendMessage("Testing 1, 2, 3");
		}
		logger.info("visibility timeout = "+mq.getVisibilityTimeout());
		logger.info("approximate queue size = "+mq.getApproximateNumberOfMessages());
		try {
			mq.deleteQueue();
			logger.error("Queue deletion was allowed, even with messages and force=false !!");
		} catch (SQSException ex) {
			logger.info("queue can't be deleted (this is exptected)");
		}
		try {
			mq.deleteQueue(true);
			logger.info("queue deleted with force=true (this is exptected)");
		} catch (SQSException ex) {
			logger.error("Queue deletion failed (this is not exptected) !!");
		}
		*/
		} catch (SQSException ex) {
			logger.info("message : "+ex.getMessage());
			logger.info("request id : "+ex.getRequestId());
			ex.printStackTrace();
			logger.info("first error : "+ex.getErrors().get(0).toString());
		}
	}
}
