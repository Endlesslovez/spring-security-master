package com.nxm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nxm.model.StockTotalDetail;
import com.nxm.repository.UserRepository;
import com.nxm.service.StockTotalDetailService;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StockTotalDetailService stockTotalDetailService;

	@GetMapping("/")
	public String index(Model model, Pageable pageable) {
		Page<StockTotalDetail> stockTotalDetailPage = stockTotalDetailService.findAll(pageable);
		PageWrapper<StockTotalDetail> page = new PageWrapper<>(stockTotalDetailPage, "/routes");
		model.addAttribute("routes", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message", "");
		return "index";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}

	@GetMapping("/login")
	public String getLogin( ) {
//		if (userRepository.findByEmail(username) == null) {
//			model.addAttribute("login", "Đăng nhập thất bại mời thử lại");
//		}
//		if (username.isEmpty()) {
//			model.addAttribute("username", "Username trống");
//		}
//		if (password.isEmpty()) {
//			model.addAttribute("password", "Password trống");
//		}
		
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}

	@GetMapping("/quenmatkhau")
	public String quenMatKhau() {
		return "quenmatkhau";
	}

}
