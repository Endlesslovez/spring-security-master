package com.nxm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping("/exportExcel")
    public void downloadFile(HttpServletRequest httpServletRequest, HttpServletResponse response) {             

		String typeOflst = httpServletRequest.getParameter("typeOflst");
		 String fileName ;
		if (typeOflst.equals("1")) {
			fileName = "F:/download/QLPM/DsSanPhamHethan.xls";
		} else {
			fileName = "F:/download/QLPM/DsSanPhamHetHang.xls";
		}
    try {

        
         HSSFWorkbook workbook = new HSSFWorkbook();
         HSSFSheet sheet = workbook.createSheet("Danh Sách Nhân Viên");

         HSSFRow rowhead = sheet.createRow((short) 0);
         rowhead.createCell(0).setCellValue("No.");
         rowhead.createCell(1).setCellValue("Name");
         rowhead.createCell(2).setCellValue("Address");
         rowhead.createCell(3).setCellValue("Email");

         HSSFRow row = sheet.createRow((short) 1);
         row.createCell(0).setCellValue("1");
         row.createCell(1).setCellValue("Carlos");
         row.createCell(2).setCellValue("Costa Rica");
         row.createCell(3).setCellValue("myNameh@gmail.com");

         FileOutputStream fileOut = new FileOutputStream(fileName);
         workbook.write(fileOut);
         fileOut.close();
         System.out.println("Your excel file has been generated!");

         //Code to download
         File fileToDownload = new File(fileName);
         InputStream in = new FileInputStream(fileToDownload);

         // Gets MIME type of the file
         String mimeType = new MimetypesFileTypeMap().getContentType(fileName);

         if (mimeType == null) {
             // Set to binary type if MIME mapping not found
             mimeType = "application/octet-stream";
         }
         System.out.println("MIME type: " + mimeType);

         // Modifies response
         response.setContentType(mimeType);
         response.setContentLength((int) fileToDownload.length());

         // Forces download
         String headerKey = "Content-Disposition";
         String headerValue = String.format("attachment; filename=\"%s\"", fileToDownload.getName());
         response.setHeader(headerKey, headerValue);

         // obtains response's output stream
         OutputStream outStream = response.getOutputStream();

         byte[] buffer = new byte[4096];
         int bytesRead = -1;

         while ((bytesRead = in.read(buffer)) != -1) {
             outStream.write(buffer, 0, bytesRead);
         }

         in.close();
         outStream.close();

         System.out.println("File downloaded at client successfully");


     } catch (Exception ex) {
         System.out.println(ex);
     }
 }
}
