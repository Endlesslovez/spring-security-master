package com.nxm.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.nxm.model.Product;
import com.nxm.repository.ProductRepository;
import com.nxm.service.BrandService;
import com.nxm.service.ProductTypeService;

@Controller
public class StockController {
	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductTypeService proTypeRepository;
	@Autowired
	private ProductRepository productRepository;
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
