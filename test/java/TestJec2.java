
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.ec2.AddressInfo;
import com.xerox.amazonws.ec2.AvailabilityZone;
import com.xerox.amazonws.ec2.ConsoleOutput;
import com.xerox.amazonws.ec2.DescribeImageAttributeResult;
import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.Jec2.ImageListAttributeOperationType;
import com.xerox.amazonws.ec2.ImageAttribute.ImageAttributeType;
import com.xerox.amazonws.ec2.ImageDescription;
import com.xerox.amazonws.ec2.ImageListAttributeItem;
import com.xerox.amazonws.ec2.ImageListAttribute.ImageListAttributeItemType;
import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.LaunchConfiguration;
import com.xerox.amazonws.ec2.LaunchPermissionAttribute;
import com.xerox.amazonws.ec2.ProductCodesAttribute;
import com.xerox.amazonws.ec2.ProductInstanceInfo;
import com.xerox.amazonws.ec2.RegionInfo;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

public class TestJec2 {
    private static Log logger = LogFactory.getLog(TestJec2.class);

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(TestJec2.class.getClassLoader().getResourceAsStream("aws.properties"));

		Jec2 ec2 = new Jec2(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
		List<String> params = new ArrayList<String>();
	
/*
*/
		//params.add("291944132575");
		params.add("ami-bd9d78d4");
		List<ImageDescription> images = ec2.describeImages(params);
		logger.info("Available Images");
		for (ImageDescription img : images) {
			if (img.getImageState().equals("available")) {
				logger.info(img.getImageId()+"\t"+img.getImageLocation()+"\t"+img.getImageOwnerId());
				if (img.getProductCodes() != null) {
					logger.info("          product code : "+img.getProductCodes().get(0));
				}
			}
		}

		List<AvailabilityZone> zones = ec2.describeAvailabilityZones(null);
		for (AvailabilityZone zone : zones) {
			logger.info("zone : "+zone.getName()+" state : "+zone.getState());
		}
		List<AddressInfo> addrs = ec2.describeAddresses(null);
		for (AddressInfo info : addrs) {
			logger.info("address : "+info.getPublicIp()+" instance : "+info.getInstanceId());
		}
//		String publicIp = ec2.allocateAddress();
//		logger.info("Address allocated : "+publicIp);

//		ReservationDescription runInst = ec2.runInstances(new LaunchConfiguration("ami-20b65349", 1, 1));
//		ReservationDescription runInst = ec2.runInstances("ami-36ff1a5f", 1, 1, new ArrayList<String>(), null, "dak-keypair", true, InstanceType.LARGE, "us-east-1c", null, null, null);

/*
*/
		params = new ArrayList<String>();
		List<ReservationDescription> instances = ec2.describeInstances(params);
		logger.info("Instances");
		String instanceId = "";
		for (ReservationDescription res : instances) {
			logger.info(res.getOwner()+"\t"+res.getReservationId());
			if (res.getInstances() != null) {
				for (Instance inst : res.getInstances()) {
					logger.info("\t"+inst.getImageId()+"\t"+inst.getDnsName()+"\t"+inst.getState()+"\t"+inst.getKeyName()+"\t"+inst.getInstanceType().getTypeId());
					instanceId = inst.getInstanceId();
				}
			}
		}

//		ec2.associateAddress(runInst.getInstances().get(0).getInstanceId(), publicIp);

//		addrs = ec2.describeAddresses(null);
//		for (AddressInfo info : addrs) {
//			logger.info("address : "+info.getPublicIp()+" instance : "+info.getInstanceId());
//		}
//		ec2.disassociateAddress(publicIp);
//		ec2.releaseAddress(publicIp);

//		ec2.terminateInstances(new String [] {runInst.getInstances().get(0).getInstanceId()});

		// confirm product instance
/*
		ReservationDescription res = ec2.runInstances("ami-45997c2c", 1, 1, new ArrayList<String>(), null, "dak-keypair");
		ProductInstanceInfo pinfo = ec2.confirmProductInstance(res.getInstances().get(0).getInstanceId(), "BA7154BF");
		if (pinfo == null) {
			logger.info("no relationship here");
		}
		else {
			logger.info("relationship confirmed. owner = "+pinfo.getOwnerId());
		}
*/

		// test console output
/*
		ConsoleOutput consOutput = ec2.getConsoleOutput(instanceId);
		logger.info("Console Output:");
		logger.info(consOutput.getOutput());
*/

		// test keypair methods
/*
		List<KeyPairInfo> info = ec2.describeKeyPairs(new String [] {});
		logger.info("keypair list");
		for (KeyPairInfo i : info) {
			logger.info("keypair : "+i.getKeyName()+", "+i.getKeyFingerprint());
		}
		ec2.createKeyPair("test-keypair");
		info = ec2.describeKeyPairs(new String [] {});
		logger.info("keypair list");
		for (KeyPairInfo i : info) {
			logger.info("keypair : "+i.getKeyName()+", "+i.getKeyFingerprint());
		}
		ec2.deleteKeyPair("test-keypair");
		info = ec2.describeKeyPairs(new String [] {});
		logger.info("keypair list");
		for (KeyPairInfo i : info) {
			logger.info("keypair : "+i.getKeyName()+", "+i.getKeyFingerprint());
		}
*/

		// test security group methods
/*
		List<GroupDescription> info = ec2.describeSecurityGroups(new String [] {});
		logger.info("SecurityGroup list");
		for (GroupDescription i : info) {
			logger.info("group : "+i.getName()+", "+i.getDescription()+", "+i.getOwner());
		}
		ec2.createSecurityGroup("test-group", "My test security group");
		info = ec2.describeSecurityGroups(new String [] {});
		logger.info("SecurityGroup list");
		for (GroupDescription i : info) {
			logger.info("group : "+i.getName()+", "+i.getDescription());
		}
		ec2.authorizeSecurityGroupIngress("default", "tcp", 1000, 1001, "0.0.0.0/0");
		ec2.revokeSecurityGroupIngress("default", "tcp", 1000, 1001, "0.0.0.0/0");
		ec2.authorizeSecurityGroupIngress("default", "tcp", 1000, 1001, "0.0.0.0/0");
		ec2.revokeSecurityGroupIngress("default", "tcp", 1000, 1001, "0.0.0.0/0");

		ec2.authorizeSecurityGroupIngress("default", "test-group", "291944132575");
		ec2.revokeSecurityGroupIngress("default", "test-group", "291944132575");

		ec2.deleteSecurityGroup("test-group");
		info = ec2.describeSecurityGroups(new String [] {});
		logger.info("GroupDescription list");
		for (GroupDescription i : info) {
			logger.info("group : "+i.getName()+", "+i.getDescription());
		}
*/

		// test image attribute methods
		DescribeImageAttributeResult res = ec2.describeImageAttribute("ami-45997c2c", ImageAttributeType.launchPermission);
		Iterator<ImageListAttributeItem> iter = res.getImageListAttribute().getImageListAttributeItems().iterator();
		logger.info("image attrs");
		while (iter.hasNext()) {
			ImageListAttributeItem item = iter.next();
			logger.info("image : "+res.getImageId()+", "+item.getType()+"="+item.getValue());
		}
/*
		LaunchPermissionAttribute attr = new LaunchPermissionAttribute();
		attr.getImageListAttributeItems().add(new ImageListAttributeItem(ImageListAttributeItemType.userId, "291944132575"));
		ec2.modifyImageAttribute("ami-11816478", attr, ImageListAttributeOperationType.add);
		res = ec2.describeImageAttribute("ami-11816478", ImageAttributeType.launchPermission);
		iter = res.getImageListAttribute().getImageListAttributeItems().iterator();
		logger.info("image attrs");
		while (iter.hasNext()) {
			ImageListAttributeItem item = iter.next();
			logger.info("image : "+res.getImageId()+", "+item.getValue());
		}
		ec2.resetImageAttribute("ami-11816478", ImageAttributeType.launchPermission);
		res = ec2.describeImageAttribute("ami-11816478", ImageAttributeType.launchPermission);
		iter = res.getImageListAttribute().getImageListAttributeItems().iterator();
		logger.info("image attrs");
		while (iter.hasNext()) {
			ImageListAttributeItem item = iter.next();
			logger.info("image : "+res.getImageId()+", "+item.getValue());
		}
*/
		// test image attribute methods for product codes
/*
		DescribeImageAttributeResult res = ec2.describeImageAttribute("ami-45997c2c", ImageAttributeType.productCodes);
		Iterator<ImageListAttributeItem> iter = res.getImageListAttribute().getImageListAttributeItems().iterator();
		logger.info("image attrs");
		while (iter.hasNext()) {
			ImageListAttributeItem item = iter.next();
			logger.info("image : "+res.getImageId()+", "+item.getValue());
		}
		ProductCodesAttribute attr = new ProductCodesAttribute();
		attr.getImageListAttributeItems().add(new ImageListAttributeItem(ImageListAttributeItemType.productCode, "BA7154BF"));
		res = ec2.describeImageAttribute("ami-45997c2c", ImageAttributeType.productCodes);
		iter = res.getImageListAttribute().getImageListAttributeItems().iterator();
		logger.info("image attrs");
		while (iter.hasNext()) {
			ImageListAttributeItem item = iter.next();
			logger.info("image : "+res.getImageId()+", "+item.getValue());
		}
*/
		List<RegionInfo> rInfo = ec2.describeRegions(null);
		for (RegionInfo r : rInfo) {
			logger.info("region : "+r.getName()+" url : "+r.getUrl());
		}
	}
}

