import com.fh.shop.api.util.MyConfig;
import com.github.wxpay.sdk.WXPay;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WxTest {
    @Test
    public void test1() throws Exception {
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "QQ会员充值");
        data.put("out_trade_no", "2016090910595900013");
        //data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        //data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        //data.put("product_id", "12");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        MyConfig config = new MyConfig();
        try {
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900013");
           // Map<String, String> resp = wxpay.orderQuery(data);
            //System.out.println(resp+"11111");
            int count=0;
            while(true){
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp+"11111");
                count++;
                String returnCode = resp.get("return_code");
                String returnMsg = resp.get("return_msg");
                if(!returnCode.equalsIgnoreCase("SUCCESS")){
                    System.out.println(returnMsg);
                    return;
                }
                String resultCode = resp.get("result_code");
                String errCodeDes = resp.get("err_code_des");
                if(!resultCode.equalsIgnoreCase("SUCCESS")){
                    System.out.println(errCodeDes);
                    return;
                }

                String tradeState = resp.get("trade_state");
                if(tradeState.equalsIgnoreCase("SUCCESS")){
                    System.out.println("-------");
                }
                if(count>100){
                    break;
                }
                new Thread().sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
