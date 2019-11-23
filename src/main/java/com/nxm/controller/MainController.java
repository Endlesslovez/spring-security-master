package com.nxm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nxm.model.Brand;
import com.nxm.model.Product;
import com.nxm.model.StockTotalDetail;
import com.nxm.model.User;
import com.nxm.repository.BrandRepositoty;
import com.nxm.repository.ProductRepository;
import com.nxm.repository.UserRepository;
import com.nxm.service.BrandService;
import com.nxm.service.ProductTypeService;
import com.nxm.service.StockTotalDetailService;

import antlr.collections.List;

@Controller
public class MainController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductTypeService proTypeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEndcode;

	@Autowired
	private StockTotalDetailService stockTotalDetailService;

	@Autowired
	private ProductRepository productRepository;
	
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

	@GetMapping("/stock")
	public String stock(Model model) {
		model.addAttribute("brand", brandService.getAll());
		model.addAttribute("protype", proTypeRepository.getAll());
		model.addAttribute("product", new Product());
		return "stock";
	}

	@Value("${image.upload-dir}")
	private String localtion;
	
	@PostMapping("/addproduct")
	public String addproduct(Model model,Product product,MultipartFile file) {
		  
	    try {
	    
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	      File file1 = new File(localtion, fileName);
	      product.setImage(fileName);
	     if(file1.exists()) {
	    	 model.addAttribute("file", "File đã tồn tại");
	     }
	     productRepository.save(product);
	    } catch (Exception e) {
	      e.printStackTrace();
	      model.addAttribute("message", "upload failed");
	    }
	    return "stock";
	  }

	}

