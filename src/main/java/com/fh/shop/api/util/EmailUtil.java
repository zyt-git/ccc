package com.fh.shop.api.util;


import com.fh.shop.api.common.SystemConstant;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailUtil {



    public  static  void sendMail(String toMail,String subject,String content) {
        Properties prop = new Properties();
        prop.setProperty("mail.host", SystemConstant.FMTP_DUANKOU);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        //session.setDebug(true);
        Transport ts =null;
        try {
             ts = session.getTransport();
            ts.connect(SystemConstant.FMTP_DUANKOU, SystemConstant.FROM_MMAIL, SystemConstant.FROM_MMAIL_PASSWORD);

            //创建邮件对象
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(SystemConstant.FROM_MMAIL,"dtfygujh"));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            //邮件的标题
            message.setSubject(subject);
            //邮件的文本内容
            message.setContent(content, "text/html;charset=UTF-8");


            //4、创建邮件
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if(ts!=null){
                try {
                    ts.close();
                    ts=null;
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
