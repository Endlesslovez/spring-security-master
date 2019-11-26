package com.nxm.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nxm.model.Product;
import com.nxm.model.UploadFileResponse;
import com.nxm.repository.ProductRepository;
import com.nxm.service.BrandService;
import com.nxm.service.FileStorageService;
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
	private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
