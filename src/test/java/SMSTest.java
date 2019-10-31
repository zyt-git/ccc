import com.fh.shop.api.util.SMSUtil;
import org.junit.Test;

public class SMSTest {
    @Test
    public  void sendSMS(){
        String s = SMSUtil.sendMsg("18338782300");
        System.out.println(s);
    }
}
