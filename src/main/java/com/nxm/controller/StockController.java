package com.nxm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nxm.model.Brand;
import com.nxm.model.Product;
import com.nxm.model.ProductType;
import com.nxm.repository.ProductRepository;
import com.nxm.service.BrandService;
import com.nxm.service.ProductService;
import com.nxm.service.ProductTypeService;

@Controller
public class StockController {
	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductTypeService proTypeRepository;

	@Autowired
	private ProductTypeService protypeService;

	@Autowired
	public ProductService service;

	@GetMapping("/stock")
	public String stock(Model model) {
		System.out.println("dsdasdas");
		model.addAttribute("brand", brandService.getAll());
		model.addAttribute("protype", proTypeRepository.getAll());
		model.addAttribute("product", new Product());
		return "stock";
	}

	@PostMapping("/stock")
	public String addproduct(@ModelAttribute Product product, @RequestParam("name") String name,
			@RequestParam("brand") String brand, @RequestParam("price") int price,
			@RequestParam("productType") String productType, @RequestParam("packageType") String packageType,Model model) {
		Long longId = Long.parseLong(brand);
		Brand brand1 = brandService.findBrandById(longId);
		
		Long longIdpro = Long.parseLong(productType);
		ProductType productType2 = protypeService.findProductById(longIdpro);

		product.setName(name);
		product.setPackageType(packageType);
		product.setPrice(price);
		product.setBrand(brand1);
		product.setProductType(productType2);

		
		if(service.create(product)==true) {
			model.addAttribute("mgs", "Thêm mới thành công sản phẩm");
			return "stock";
		}else {
			model.addAttribute("mgs", "Thêm mới không thàng công");
			return "stock";
		}
	
	}

}
