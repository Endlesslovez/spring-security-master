package com.nxm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.nxm.model.User;
import com.nxm.repository.UserRepository;
import com.nxm.service.SendMail;

@Controller
public class SendMailController {

	@Autowired
	private UserRepository respository;

	@Autowired
	private SendMail sendmail;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/sendmail")
	public String SendMail(@RequestParam("email") String email, ModelMap model,HttpServletRequest request) {
		User user = respository.findByEmail(email);
		if (user == null) {
			model.addAttribute("sendmail", "Không tồn tại tài khoản trong hệ thống");
		}
		int[] password = new int[5];

		for (int i = 0; i < password.length; i++) {
			password[i] = (int) (Math.random() * 0 + 9);

		}
		System.out.println(password.toString());
		user.setPassword(passwordEncoder.encode(password.toString()));
		respository.save(user);
		String text = "Mật khẩu mới của bạn là \n " + password;
		sendmail.sendMail(email, text);
		if (sendmail.sendMail(email, text) == true) {
			model.addAttribute("sendmail", "Mời bạn vào mail xác nhận mật khẩu");
		} else {
			model.addAttribute("sendmail", "Lỗi. Mời bạn thử lại");
		}

	return "quenmatkhau";

}
	
}
