package com.nxm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nxm.model.Brand;
import com.nxm.model.Product;
import com.nxm.model.ProductType;
import com.nxm.model.StockTotalDetail;
import com.nxm.model.User;
import com.nxm.repository.ProductRepository;
import com.nxm.repository.UserRepository;
import com.nxm.service.BrandService;
import com.nxm.service.ProductService;
import com.nxm.service.ProductTypeService;
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

	@GetMapping("/index")
	public String index2() {
		return "index2";
	}

	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}

	@GetMapping("/login")
	public String getLogin(Model model,HttpServletResponse response) {
		List<User> list = userRepository.findAllUser();
		int i=0;
		for(User user : list) {
			Cookie cookie = new Cookie("user"+i, user.getEmail());
	        //set the expiration time
	        //1 hour = 60 seconds x 60 minutes
	        cookie.setMaxAge(60 * 60);
	        //add the cookie to the  response
	        response.addCookie(cookie);
	        i++;
		}
		
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

	@GetMapping("/exportExcel")
	public void downloadFile(HttpServletRequest httpServletRequest, HttpServletResponse response) {

		String typeOflst = httpServletRequest.getParameter("typeOflst");
		String fileName;
		if (typeOflst.equals("1")) {
			fileName = "src/main/resources/excel/DsSanPhamHethan.xls";
		} else {
			fileName = "src/main/resources/excel/DsSanPhamHetHang.xls";
		}
		try {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Danh Sách Nhân Viên");
			HSSFFont font = workbook.createFont();
			font.setBold(true);
			// font.setItalic(true);

			// Font Height
			font.setFontHeightInPoints((short) 10);

			// Font Color
			font.setColor(IndexedColors.WHITE.index);
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setFont(font);
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("");
			rowhead.createCell(1).setCellValue("");
			rowhead.createCell(2).setCellValue("Danh sách sản phẩm");

			rowhead.createCell(3).setCellValue("");

			HSSFRow row1 = sheet.createRow((short) 1);
			row1.createCell(0).setCellValue("No.");
			row1.createCell(1).setCellValue("Mã sản phẩm");
			row1.createCell(2).setCellValue("Tên sản phẩm");
			row1.createCell(3).setCellValue("Số lượng");
			row1.createCell(4).setCellValue("Ngày hết hạn");
			row1.createCell(5).setCellValue("Mã vị trí");
			row1.getCell(0).setCellStyle(style);
			row1.getCell(1).setCellStyle(style);
			row1.getCell(2).setCellStyle(style);
			row1.getCell(3).setCellStyle(style);
			row1.getCell(4).setCellStyle(style);
			row1.getCell(5).setCellStyle(style);
			HSSFRow row = sheet.createRow((short) 2);
			row.createCell(0).setCellValue("1");
			row.createCell(1).setCellValue("SP001");
			row.createCell(2).setCellValue("Costa Rica");
			row.createCell(3).setCellValue("20");
			row.createCell(4).setCellValue("22/12/2019");
			row.createCell(5).setCellValue("VT0012");

			// Code to download
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
