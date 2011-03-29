
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.sqs2.QueueService;
import com.xerox.amazonws.sqs2.Message;
import com.xerox.amazonws.sqs2.MessageQueue;

public class TestVisibility {
    private static Log logger = LogFactory.getLog(TestVisibility.class);

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(TestJec2.class.getClassLoader().getResourceAsStream("aws.properties"));

		QueueService qs = new QueueService(props.getProperty("aws.accessId"),
								props.getProperty("aws.secretKey"), false);
		qs.setSignatureVersion(2);
		MessageQueue mq = qs.getOrCreateMessageQueue(args[0]);
		mq.setSignatureVersion(2);
		int timeout = mq.getVisibilityTimeout();
		logger.info("Queue timeout = "+timeout);
		mq.sendMessage("Testing 1, 2, 3");
		try { Thread.sleep(5000); } catch (InterruptedException ex) {}
		Message msg = mq.receiveMessage();
		logger.info("Message = "+msg.getMessageBody());
		int i=0;
		long start = System.currentTimeMillis();
		while ((msg = mq.receiveMessage()) == null) {
			logger.info(".");
			try { Thread.sleep(1000); } catch (InterruptedException ex) {}
			i++;
		}
		long end = System.currentTimeMillis();
		logger.info("Message was invisible for "+(end-start)/1000.0+" seconds");
		mq.deleteMessage(msg);

		// test queue visibility
		logger.info("setting timeout to 10 seconds.");
		mq.setVisibilityTimeout(10);
		mq.sendMessage("Testing 1, 2, 3");
		try { Thread.sleep(5000); } catch (InterruptedException ex) {}
		msg = mq.receiveMessage();
		logger.info("Message = "+msg.getMessageBody());
		i=0;
		start = System.currentTimeMillis();
		while ((msg = mq.receiveMessage()) == null) {
			logger.info(".");
			try { Thread.sleep(1000); } catch (InterruptedException ex) {}
			i++;
		}
		end = System.currentTimeMillis();
		logger.info("Message was invisible for "+(end-start)/1000.0+" seconds");
		mq.deleteMessage(msg);

		// test change message visibility
		logger.info("setting timeout to 10 seconds.");
		mq.setVisibilityTimeout(10);
		mq.sendMessage("Testing 1, 2, 3");
		try { Thread.sleep(5000); } catch (InterruptedException ex) {}
		msg = mq.receiveMessage();
		logger.info("Message = "+msg.getMessageBody());
		i=0;
		String receiptHandle = msg.getReceiptHandle();
		start = System.currentTimeMillis();
		while ((msg = mq.receiveMessage()) == null) {
			logger.info(".");
			if (i == 4) {
				logger.info("change timeout to 60 seconds.");
				mq.setMessageVisibilityTimeout(receiptHandle, 60);
			}
			try { Thread.sleep(1000); } catch (InterruptedException ex) {}
			i++;
		}
		end = System.currentTimeMillis();
		logger.info("Message was invisible for "+(end-start)/1000.0+" seconds");
		mq.deleteMessage(msg);

		// test receive visibility
		mq.sendMessage("Testing 1, 2, 3");
		try { Thread.sleep(5000); } catch (InterruptedException ex) {}
		msg = mq.receiveMessage(30);
		logger.info("Message = "+msg.getMessageBody());
		i=0;
		start = System.currentTimeMillis();
		while ((msg = mq.receiveMessage()) == null) {
			logger.info(".");
			try { Thread.sleep(1000); } catch (InterruptedException ex) {}
			i++;
		}
		end = System.currentTimeMillis();
		logger.info("Message was invisible for "+(end-start)/1000.0+" seconds");
		mq.deleteMessage(msg);
		// reset queue timeout
		mq.setVisibilityTimeout(timeout);
	}
}
