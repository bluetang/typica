
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
import com.xerox.amazonws.ec2.SnapshotInfo;
import com.xerox.amazonws.ec2.VolumeInfo;
import com.xerox.amazonws.ec2.AttachmentInfo;

public class TestEBS {
    private static Log logger = LogFactory.getLog(TestEBS.class);

	public static void main(String [] args) throws Exception {
		Properties props = new Properties();
		props.load(TestEBS.class.getClassLoader().getResourceAsStream("aws.properties"));

		Jec2 ec2 = new Jec2(props.getProperty("aws.accessId"), props.getProperty("aws.secretKey"));
		describeVolumes(ec2, new String [] {});
		describeSnapshots(ec2, new String [] {});
		VolumeInfo vol = ec2.createVolume("512", null, "merlot");
		describeVolumes(ec2, new String [] {vol.getVolumeId()});

		if (args[0] != null) {
			ec2.attachVolume(vol.getVolumeId(), args[0], "/dev/sdi");
			try { Thread.sleep(4000); } catch (InterruptedException ex) {}
			describeVolumes(ec2, new String [] {vol.getVolumeId()});
			try { Thread.sleep(4000); } catch (InterruptedException ex) {}
			ec2.detachVolume(vol.getVolumeId(), null, null, true);
		}
		SnapshotInfo snap = ec2.createSnapshot(vol.getVolumeId(), "");
		try { Thread.sleep(4000); } catch (InterruptedException ex) {}
		describeSnapshots(ec2, new String [] {snap.getSnapshotId()});
		try { Thread.sleep(4000); } catch (InterruptedException ex) {}
		ec2.deleteSnapshot(snap.getSnapshotId());
		ec2.deleteVolume(vol.getVolumeId());
		describeVolumes(ec2, new String [] {vol.getVolumeId()});
	}

	public static void describeVolumes(Jec2 ec2, String [] ids) throws Exception {
		logger.info("Volumes");
		List<VolumeInfo> vols = ec2.describeVolumes(ids);
		for (VolumeInfo info : vols) {
			logger.info(info.getVolumeId()+"\t"+info.getSize()+"\t"+info.getStatus());
			List<AttachmentInfo> set = info.getAttachmentInfo();
			for (AttachmentInfo att : set) {
				logger.info("  "+att.getInstanceId()+"\t"+att.getDevice()+"\t"+att.getStatus());
			}
		}
	}

	public static void describeSnapshots(Jec2 ec2, String [] ids) throws Exception {
		logger.info("Snapshots");
		List<SnapshotInfo> snaps = ec2.describeSnapshots(ids);
		for (SnapshotInfo info : snaps) {
			logger.info(info.getSnapshotId()+"\t"+info.getVolumeId()+"\t"+info.getStatus()+"\t"+info.getProgress());
		}
	}
}

