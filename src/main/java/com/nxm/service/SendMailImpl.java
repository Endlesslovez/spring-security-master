package com.nxm.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.stereotype.Service;

import com.nxm.constant.Constant;
import com.nxm.utils.EmailUtil;

@Service
public class SendMailImpl implements SendMail {

	@Override
	public boolean sendMail(String toMail,String body) {
	Properties props= new Properties();
	props.put("mail.smtp.host", Constant.HOST);
	props.put("mail.smtp.post", Constant.POST);
	props.put("mail.smtp.auth","true");
	props.put("mail.smtp.starttls.enable", "true");
		
	Authenticator auth = new Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(Constant.MAILFROM, Constant.PASSWORD);
		}
	};
	Session session= Session.getInstance(props, auth);
	EmailUtil.sendEmail(session, "asd", body, Constant.MAILFROM, toMail);
	return true;
	}

}
