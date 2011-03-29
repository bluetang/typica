
import java.util.ArrayList;
import java.util.Properties;

import com.xerox.amazonws.sqs2.MessageQueue;
import com.xerox.amazonws.sqs2.QueueService;
import com.xerox.amazonws.sqs2.SQSException;

// measures load average on queues at 1, 5 and 15 minutes
public class ShowQueueLoadHistory {
	private static final int SAMPLES_PER_MINUTE = 20;

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(ShowQueueLoadHistory.class.getClassLoader().getResourceAsStream("aws.properties"));

		QueueService qs = new QueueService(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
		MessageQueue mq = qs.getOrCreateMessageQueue(args[0]);

		ArrayList<Integer> samples = new ArrayList<Integer>(SAMPLES_PER_MINUTE*15);
		while (true) {
			samples.add(0, mq.getApproximateNumberOfMessages());
			if (samples.size() > (SAMPLES_PER_MINUTE * 15)) {
				samples.remove(samples.size()-1);
			}
			float avg1 = 0;
			int count1;
			for (count1=0; count1<SAMPLES_PER_MINUTE && count1<samples.size(); count1++) {
				avg1 += samples.get(count1);
			}
			float avg2 = avg1;
			int count2;
			for (count2=SAMPLES_PER_MINUTE; count2<(SAMPLES_PER_MINUTE*5) && count2<samples.size(); count2++) {
				avg2 += samples.get(count2);
			}
			float avg3 = avg2;
			int count3;
			for (count3=(SAMPLES_PER_MINUTE*5); count3<(SAMPLES_PER_MINUTE*15) && count3<samples.size(); count3++) {
				avg3 += samples.get(count3);
			}
			avg1 = avg1 / count1;
			count2 = count1 + (count2-SAMPLES_PER_MINUTE);
			avg2 = avg2 / count2;
			count3 = count2 + (count3-(SAMPLES_PER_MINUTE*5));
			avg3 = avg3 / count3;
			System.out.printf("%d load average: %.1f, %.1f, %.1f\n", samples.get(0), avg1, avg2, avg3);
			try { Thread.sleep(60000 / SAMPLES_PER_MINUTE); } catch (InterruptedException ex) {}
		}
	}
}
