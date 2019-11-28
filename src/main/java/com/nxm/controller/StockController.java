package com.nxm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nxm.model.Brand;
import com.nxm.model.PalletPoisitionVO;
import com.nxm.model.PalletPosition;
import com.nxm.model.Product;
import com.nxm.model.ProductType;
import com.nxm.model.StockChange;
import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;
import com.nxm.model.StockTotalDetailVO;
import com.nxm.repository.PalletPoisitionRepository;
import com.nxm.repository.ProductRepository;
import com.nxm.repository.StockChangeRepository;
import com.nxm.repository.StockTotalDetailRepository;
import com.nxm.repository.StockTotalRepository;
import com.nxm.service.BrandService;
import com.nxm.service.PalletPoisitionService;
import com.nxm.service.ProductService;
import com.nxm.service.ProductTypeService;
import com.nxm.service.StockTotalDetailService;
import com.nxm.service.StockTotalService;

@Controller
public class StockController {
	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductTypeService proTypeRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StockTotalService stockTotalService;

	@Autowired
	private StockChangeRepository stockChangeRepository;

	@Autowired
	private StockTotalDetailService stockTotalDetailService;
	
	@Autowired
	private StockTotalRepository stockTotalRepository;
	
	@Autowired
	private StockTotalDetailRepository stockTotalDetailRepository;

	@Autowired
	private PalletPoisitionService palletPoisitionService;

	@Autowired
	private ProductService service;

	@Autowired
	private ProductTypeService protypeService;

	@GetMapping("/stock")
	public String stock(Model model, Pageable pageable) {
		model.addAttribute("brand", brandService.getAll());
		model.addAttribute("protype", proTypeRepository.getAll());
		model.addAttribute("product", new Product());
		Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
		PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
		model.addAttribute("palletpositions", page.getContent());
		model.addAttribute("page", page);
		return "stock";
	}

	@GetMapping("/exportExcelCK")
	public void downloadFile(HttpServletRequest httpServletRequest, HttpServletResponse response) {

		String fileName = "F:/Spring framework/qlphanmem/Bieu_mau_chot_kho.xls";

		try {
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

	@Value("${image.upload-dir}")
	private String localtion;

	@PostMapping("/stock")
	public String addproduct(@ModelAttribute Product product, @RequestParam("name") String name,
			@RequestParam("brand") String brand, @RequestParam("price") int price,
			@RequestParam("productType") String productType, @RequestParam("packageType") String packageType,
			Model model, Pageable pageable) {
		Long longId = Long.parseLong(brand);
		Brand brand1 = brandService.findBrandById(longId);


		Long longIdpro = Long.parseLong(productType);
		ProductType productType2 = protypeService.findProductById(longIdpro);
		product.setName(name);
		product.setPackageType(packageType);
		product.setPrice(price);
		product.setBrand(brand1);
		product.setProductType(productType2);
		if (service.create(product) == true) {
	
			model.addAttribute("mgs", "Thêm mới thành công sản phẩm");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
			PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
			model.addAttribute("palletpositions", page.getContent());
			model.addAttribute("page", page);
			return "stock";
		} else {
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
			PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
			model.addAttribute("palletpositions", page.getContent());
			model.addAttribute("page", page);
			model.addAttribute("mgs", "Thêm mới không thàng công");
			return "stock";
		}

		
	}

	


	@PostMapping("/importExcel")
	public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, Model model,
			Pageable pageable) throws IOException {
		StockTotal stockTotalNow = stockTotalService.findNow();
		List<Test> tempStudentList = new ArrayList<Test>();
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int count = 0;
		int countFail = 0;
		int productCount = 1;
		List<StockTotalDetailVO> lstFail = new ArrayList<>();
		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			String msg = "";
			XSSFRow row = worksheet.getRow(i);
			StockTotalDetailVO stockTotalDetailVO = new StockTotalDetailVO();
			for (int cellIndex = 1; cellIndex < 8; cellIndex++) {

				String value = "";

				if (row.getCell(cellIndex) != null && cellIndex == 1) {
					productCount++;
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setProductId(Integer.parseInt(value));
				} else {
					msg += "Không để trống mã sản phẩm";
				}
				if (row.getCell(cellIndex) != null && cellIndex == 2) {
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setProductName(value);
				}
				if (row.getCell(cellIndex) != null && cellIndex == 3) {
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setBrandId(Integer.parseInt(value));
				} else {
					msg += "Không để trống Nhãn hiệu";
				}
				if (row.getCell(cellIndex) != null && cellIndex == 4) {
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setTypeOfProduct(Integer.parseInt(value));
				} else {
					msg += "Không để trống loại sản phẩm";
				}

				if (row.getCell(cellIndex) != null && cellIndex == 5) {
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setTypeOfProduct(Integer.parseInt(value));
				} else {
					msg += "Không để trống số lượng";
				}

				if (row.getCell(cellIndex) != null && cellIndex == 6) {
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setExpiredDate(value);
				} else {
					msg += "Không để trống ngày hết hạn";
				}

				if (row.getCell(cellIndex) != null && cellIndex == 7) {
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setExpiredDate(value);
				} else {
					msg += "Không để trống ngày hết hạn";
				}
				if (row.getCell(cellIndex) != null && cellIndex == 8) {
					value = row.getCell(cellIndex).toString();
					stockTotalDetailVO.setStockTotalDetailId(Integer.parseInt(value));
				} else {
					msg += "Không để trống bản ghi tồn kho";
				}
				if (!msg.equals("")) {
					stockTotalDetailVO.setError(msg);
					lstFail.add(stockTotalDetailVO);
					countFail++;
				}

			}
			if (msg.equals("")) {
				StockTotalDetail stockTotalDetail = stockTotalDetailService.findOne(stockTotalDetailVO.getStockTotalDetailId());
				if (stockTotalDetail != null) {
					if (stockTotalDetail.getProduct().getId() != stockTotalDetailVO.getProductId()) {
						stockTotalDetailVO.setError("Bản ghi tồn kho không tồn tại. Vui lòng xem lại Product_id");
						lstFail.add(stockTotalDetailVO);
						countFail++;
						continue;
					}
					LocalDate localDate = stockTotalDetail.getExpiredDate();// For reference
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
					String formattedString = localDate.format(formatter);
					if (!formattedString.equals(stockTotalDetailVO.getExpiredDate())) {
						stockTotalDetailVO.setError("Bản ghi tồn kho không tồn tại. Vui lòng xem lại Ngày hết hạn");
						lstFail.add(stockTotalDetailVO);
						countFail++;
						continue;
					}
					if (stockTotalDetail.getQuantity() != stockTotalDetailVO.getQuantity()) {
						StockChange stockChange = new StockChange();
						stockChange.setStockTotalId(stockTotalNow.getId());
						stockChange
								.setQuantityChange(stockTotalDetail.getQuantity() - stockTotalDetailVO.getQuantity());
						stockChange.setStockTotalDetailId(stockTotalDetail.getId());
						stockChange.setStockTotalId(stockTotalNow.getId());
						stockChangeRepository.save(stockChange);
					} else {
						LocalDate formattedString1 = LocalDate.now();
						DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						String updateDate = formattedString1.format(formatter1);
						stockTotalDetail.setUpdateDate(updateDate);
						stockTotalDetailRepository.save(stockTotalDetail);
					}
					count++;

				} else {
					stockTotalDetailVO.setError("Bản ghi tồn kho không tồn tại. Vui lòng xem lại Stotal_detail_id");
					lstFail.add(stockTotalDetailVO);
					countFail++;
				}

			}
			System.out.println(row.getCell(1));
		}
		if (stockTotalNow != null && stockTotalNow.getTotalCount() < productCount) {
			stockTotalNow.setTotalCount(productCount);
			stockTotalRepository.save(stockTotalNow);
		}
		String chotkho = "";
		List<StockTotalDetail> lstCount = stockTotalDetailService.findByStockTotalId(stockTotalNow.getId());
		if (lstCount != null && lstCount.size() == stockTotalNow.getTotalCount()) {
			chotkho = "Hoàn thành chốt kho";
		} else {
			chotkho = "Chưa hoàn thành chốt kho";
		}
		model.addAttribute("chotkho",chotkho);
		model.addAttribute("brand", brandService.getAll());
		model.addAttribute("protype", proTypeRepository.getAll());
		model.addAttribute("product", new Product());
		Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
		PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
		model.addAttribute("palletpositions", page.getContent());
		model.addAttribute("page", page);
		return "stock";
	}

	public String addproduct1(Model model, Product product, MultipartFile file) {

		try {

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			File file1 = new File(localtion, fileName);
			product.setImage(fileName);
			if (file1.exists()) {
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
