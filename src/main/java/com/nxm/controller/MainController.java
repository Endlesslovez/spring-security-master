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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nxm.model.StockTotalDetail;
import com.nxm.repository.UserRepository;
import com.nxm.service.StockTotalDetailService;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEndcode;

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
	public String getLogin() {
		return "login";
	}
//
//	@GetMapping("/login")
//	public String getLoginError(@RequestParam("username") String username, @RequestParam("password") String password,
//			ModelMap modelMap) {
//		User user = userRepository.findByEmail(username);
//		if (username.length() == 0) {
//			modelMap.addAttribute("username", "Tai Khoan trong");
//		}
//		if (password.length() == 0) {
//			modelMap.addAttribute("password", "Mat khau de trong");
//		}
//		if (user == null) {
//			modelMap.addAttribute("login", "Dang nhap that bai");
//		}
//
//		return "login";
//	}

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

	@GetMapping("/exportExcel")
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
         rowhead.createCell(0).setCellValue("");
         rowhead.createCell(1).setCellValue("");
         rowhead.createCell(2).setCellValue("Danh");
         rowhead.createCell(3).setCellValue("Email");
         
         HSSFRow row1 = sheet.createRow((short) 1);
         row1.createCell(0).setCellValue("No.");
         row1.createCell(1).setCellValue("Mã sản phẩm");
         row1.createCell(2).setCellValue("Tên sản phẩm");
         row1.createCell(3).setCellValue("Số lượng");
         row1.createCell(4).setCellValue("Ngày hết hạn");
         row1.createCell(5).setCellValue("Mã vị trí");
         HSSFRow row = sheet.createRow((short) 2);
         row.createCell(0).setCellValue("1");
         row.createCell(1).setCellValue("SP001");
         row.createCell(2).setCellValue("Costa Rica");
         row.createCell(3).setCellValue("20");
         row.createCell(4).setCellValue("22/12/2019");
         row.createCell(5).setCellValue("VT0012");
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
