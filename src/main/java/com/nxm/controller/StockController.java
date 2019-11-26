package com.nxm.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	private ProductRepository productRepository;


    
	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductTypeService protypeService;
	
	@GetMapping("/stock")
	public String stock(Model model) {
		model.addAttribute("brand", brandService.getAll());
		model.addAttribute("protype", proTypeRepository.getAll());
		model.addAttribute("product", new Product());
		return "stock";
	}

	@Value("${image.upload-dir}")
	private String localtion;
	

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

	public String addproduct1(Model model,Product product,MultipartFile file) {
		  
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
	private static final Logger logger = LoggerFactory.getLogger(StockController.class);

//    
//    @PostMapping("/uploadFile")
//    public UploadFileResponse uploadFile1(@RequestParam("file") MultipartFile file) {
// 
//        String fileName = fileStorageService.storeFile(file);
//
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//
//	
//	
//	
//        return new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
//}
//
//
//
//   
//    @GetMapping("/downloadFile/{fileName:.+}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//        // Load file as Resource
//        Resource resource = fileStorageService.loadFileAsResource(fileName);
//
//        // Try to determine file's content type
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
}
