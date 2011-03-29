
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import com.xerox.amazonws.ec2.AttachmentInfo;
import com.xerox.amazonws.ec2.EC2Exception;
import com.xerox.amazonws.ec2.EC2Utils;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.VolumeInfo;

public class AttachVolume {

	public static void main(String [] args) {
		try {
			String userData = EC2Utils.getInstanceUserdata();
			StringTokenizer st = new StringTokenizer(userData);
			String accessId = st.nextToken();
			String secretKey = st.nextToken();
			String prefix = st.nextToken();
			String bucket = st.nextToken();
			String app = st.nextToken();
			String volumeOrSnapId = st.nextToken();

			Jec2 ec2 = new Jec2(accessId, secretKey);
			String volumeId = null;
			if (volumeOrSnapId.startsWith("snap-")) {
				String zone = EC2Utils.getInstanceMetadata("placement/availability-zone");
				// create volume from snapshot and wait
				VolumeInfo vinf = ec2.createVolume(null, volumeOrSnapId, zone);
				volumeId = vinf.getVolumeId();
				List<VolumeInfo> vols = ec2.describeVolumes(new String [] {volumeId});
				while (!vols.get(0).getStatus().equals("available")) {
					System.out.println(vols.get(0).getStatus());
					try { Thread.sleep(2); } catch (InterruptedException ex) {}
					vols = ec2.describeVolumes(new String [] {volumeId});
				}
			}
			if (volumeOrSnapId.startsWith("vol-")) {
				volumeId = volumeOrSnapId;
			}
			// attach volume and wait
			String instanceId = EC2Utils.getInstanceMetadata("instance-id");
			ec2.attachVolume(volumeId, instanceId, "/dev/sdh");
			List<VolumeInfo> vols = ec2.describeVolumes(new String [] {volumeId});
			while (!vols.get(0).getAttachmentInfo().get(0).getStatus().equals("attached")) {
				System.out.println(vols.get(0).getAttachmentInfo().get(0).getStatus());
				try { Thread.sleep(2); } catch (InterruptedException ex) {}
				vols = ec2.describeVolumes(new String [] {volumeId});
			}
		} catch (Exception ex) {
			System.err.println("Couldn't complete the attach : "+ex.getMessage());
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
