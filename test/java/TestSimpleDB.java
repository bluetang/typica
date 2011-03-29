
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.sdb.Domain;
import com.xerox.amazonws.sdb.Item;
import com.xerox.amazonws.sdb.ItemAttribute;
import com.xerox.amazonws.sdb.ListDomainsResult;
import com.xerox.amazonws.sdb.QueryWithAttributesResult;
import com.xerox.amazonws.sdb.SimpleDB;
import com.xerox.amazonws.sdb.SDBException;

public class TestSimpleDB {
    private static Log logger = LogFactory.getLog(TestSimpleDB.class);

	public static void main(String [] args) throws Exception {
		try {
		Properties props = new Properties();
		props.load(TestSimpleDB.class.getClassLoader().getResourceAsStream("aws.properties"));

		SimpleDB sdb = new SimpleDB(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));

		logger.info("domains:");
		String nextToken = "";
		while (nextToken != null) {
			ListDomainsResult result = sdb.listDomains(nextToken, 10);
			List<Domain> domains = result.getDomainList();
			for (Domain dom : domains) {
				logger.info(dom.getName());
			}
			nextToken = result.getNextToken();
		}
		Domain dom = sdb.createDomain(args[0]);
		QueryWithAttributesResult qr = dom.selectItems("select * from "+args[0], null);
		Map<String, List<ItemAttribute>> iList = qr.getItems();
		for (String id : iList.keySet()) {
			logger.info("item : "+id);
		}
		Map<String, List<ItemAttribute>> items = new HashMap<String, List<ItemAttribute>>();
		List<ItemAttribute> list = new ArrayList<ItemAttribute>();
		list.add(new ItemAttribute("test1", "value1", false));
		list.add(new ItemAttribute("t\0u000dst1", "value2", false));
		list.add(new ItemAttribute("test1", "Jérôme", false));
		list.add(new ItemAttribute("test1", "\0u000dvalue4&gt;", false));
		list.add(new ItemAttribute("test1", "value5", false));
		list.add(new ItemAttribute("test1", "value6", false));
		items.put("00001", list);

		list = new ArrayList<ItemAttribute>();
		list.add(new ItemAttribute("test1", "value1", false));
		list.add(new ItemAttribute("t\0u000dst1", "value2", false));
		list.add(new ItemAttribute("test1", "Jérôme", false));
		list.add(new ItemAttribute("test1", "\0u000dvalue4&gt;", false));
		list.add(new ItemAttribute("test1", "value5", false));
		list.add(new ItemAttribute("test1", "value6", false));
		items.put("00002", list);

		list = new ArrayList<ItemAttribute>();
		list.add(new ItemAttribute("test1", "value1", false));
		list.add(new ItemAttribute("t\0u000dst1", "value2", false));
		list.add(new ItemAttribute("test1", "Jérôme", false));
		list.add(new ItemAttribute("test1", "\0u000dvalue4&gt;", false));
		list.add(new ItemAttribute("test1", "value5", false));
		list.add(new ItemAttribute("test1", "value6", false));
		items.put("00003", list);

		list = new ArrayList<ItemAttribute>();
		list.add(new ItemAttribute("test1", "value1", false));
		list.add(new ItemAttribute("t\0u000dst1", "value2", false));
		list.add(new ItemAttribute("test1", "Jérôme", false));
		list.add(new ItemAttribute("test1", "\0u000dvalue4&gt;", false));
		list.add(new ItemAttribute("test1", "value5", false));
		list.add(new ItemAttribute("test1", "value6", false));
		items.put("00004", list);

		dom.batchPutAttributes(items);

		logger.info("items after batch put");
		qr = dom.selectItems("select * from "+args[0], null);
		iList = qr.getItems();
		for (String id : iList.keySet()) {
			logger.info("item : "+id);
		}

		dom.batchDeleteAttributes(items);

		logger.info("items after batch delete");
		qr = dom.selectItems("select * from "+args[0], null);
		iList = qr.getItems();
		for (String id : iList.keySet()) {
			logger.info("item : "+id);
		}


/*		List<ItemAttribute> attrs = i.getAttributes();
		logger.info("all");
		for (ItemAttribute attr : attrs) {
			logger.info("Item:"+attr.getName()+" Value:"+attr.getValue());
		}
		attrs = i.getAttributes("test1");
		logger.info("test1");
		for (ItemAttribute attr : attrs) {
			logger.info("Item:"+attr.getName()+" Value:"+attr.getValue());
		}
		ArrayList<String> some = new ArrayList<String>();
		some.add("test1");
		some.add("test2");
		attrs = i.getAttributes(some);
		logger.info("test1,test2");
		for (ItemAttribute attr : attrs) {
			logger.info("Item:"+attr.getName()+" Value:"+attr.getValue());
		}
		Map<String, List<String>> attrMap = i.getAttributesMap(some);
		logger.info("test1,test2 - map");
		for (String key : attrMap.keySet()) {
			for (String value : attrMap.get(key)) {
				logger.info("Item:"+key+" Value:"+value);
			}
		}
*/
		} catch (SDBException ex) {
			System.err.println("message : "+ex.getMessage());
			System.err.println("requestID : "+ex.getRequestId());
		}
	}
}

