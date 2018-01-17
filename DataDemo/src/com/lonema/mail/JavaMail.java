package com.lonema.mail;

import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * @DESC mial工具类
 * @AUTHOR JIANGCW
 * @DATE 2018-1-16下午08:05:57
 */
public class JavaMail {
	private static JavaMail javaMail;
	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	private static String username = ""; // smtp认证用户名和密码
	private static String password = "";
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	
	public JavaMail(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * @param hostName
	 *            String
	 */
	public void setSmtpHost(String hostName) {
		System.out.println("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
			props.put("mail.smtp.host", hostName); // 设置SMTP主机
			props.setProperty("mail.smtp.starttls.enable","true");//开启SSL传输
	}

	/**
	 * @return boolean
	 */
	public boolean createMimeMessage() {
		try {
			System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			System.out.println("获取邮件会话对象时发生错误！" + e);
			return false;
		}

		System.out.println("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			System.out.println("创建MIME邮件对象失败！" + e);
			return false;
		}
	}

	/**
	 * @param need
	 *            boolean
	 */
	public void setNeedAuth(boolean need) {
		System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();

		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * @param mailSubject
	 *            String
	 * @return boolean
	 */
	public boolean setSubject(String mailSubject) {
		//log.info("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			System.out.println("设置邮件主题发生错误！");
			return false;
		}
	}

	/**
	 * @param mailBody
	 *            String
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=gb2312>"
							+ mailBody, "text/html;charset=GB2312");
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			System.out.println("设置邮件正文时发生错误！" + e);
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean addFileAffix(String filename) {
		System.out.println("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			System.out.println("增加邮件附件：" + filename + "发生错误！" + e);
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setFrom(String from) {
		System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setTo(String to) {
		if (to == null)
			return false;

		try {
			mimeMsg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean sendout(String subject,String mailbody,String sendTo) {
		setSubject(subject);
		setBody(mailbody);
		setTo(sendTo);
		setFrom(username);
		setNamePass(username, password);
		setNeedAuth(true);
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			System.out.println("正在发送邮件....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			transport.sendMessage(mimeMsg,
					mimeMsg.getRecipients(Message.RecipientType.TO));
			System.out.println("发送邮件成功！");
			transport.close();

			return true;
		} catch (Exception e) {
			System.out.println("邮件发送失败！" + e);
			return false;
		}
	}
	
	
	public static void init(){
		Map<String,ServiceParam> smap=(Map<String,ServiceParam>)OSCacheManage.getInstance().getCache("serviceParam");
		ServiceParam sparam=smap.get(ServiceType.MAIL);
		if(sparam!=null){
			username=sparam.getServiceUser();
			password=sparam.getServicePassword();
			javaMail=new JavaMail(sparam.getServiceUrl());
		}
	}
	
	
	public static JavaMail getInstance(){
		if(null==javaMail){
			init();
		}
		return javaMail;
	}
	/**
	 * Just do it as this
	*/
	public static void main(String[] args) {

		String mailbody = "用java代码发送的邮件";

		JavaMail themail = new JavaMail("smtp.qq.com");
		themail.setNeedAuth(true);
		if (themail.setSubject("标题") == false)
			return;
		if (themail.setBody(mailbody) == false)
			return;
		if (themail.setTo("17904@qq.com") == false)
			return;
		if (themail.setFrom("538042@qq.com") == false)
			return;
		//if (themail.addFileAffix("c:\\boot.ini") == false)
		//	return;
		themail.setNamePass("393532@qq.com", "");//邮箱登录授权码（16位目前）

		if (themail.sendout("test", mailbody, "7904@qq.com") == false)
			return;
	} 
}