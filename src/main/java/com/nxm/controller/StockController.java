package com.nxm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nxm.model.Brand;
import com.nxm.model.Pallet;
import com.nxm.model.PalletPoisitonVo;
import com.nxm.model.PalletPosition;
import com.nxm.model.Product;
import com.nxm.model.ProductType;
import com.nxm.model.StockChange;
import com.nxm.model.StockTotal;
import com.nxm.model.StockTotalDetail;
import com.nxm.model.StockTotalDetailVO;
import com.nxm.repository.ProductRepository;
import com.nxm.repository.StockChangeRepository;
import com.nxm.repository.StockTotalDetailRepository;
import com.nxm.repository.StockTotalRepository;
import com.nxm.service.BrandService;
import com.nxm.service.PalletPoisitionService;
import com.nxm.service.PalletService;
import com.nxm.service.ProductService;
import com.nxm.service.ProductTypeService;
import com.nxm.service.StockChangeService;
import com.nxm.service.StockTotalDetailService;
import com.nxm.service.StockTotalService;
import com.nxm.utils.ReadingFromExcelSheet;

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
	private StockChangeService stockChangeService;

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
	private PalletService palletService;

	@Autowired
	private ProductService service;

	@Autowired
	private ProductTypeService protypeService;

	@GetMapping("/stock")
	public String stock(Model model, @PageableDefault(size = 10) Pageable pageable,
			@RequestParam(value = "nameproduct", required = false) String nameproduct,
			@RequestParam(value = "namebrand", required = false) String namebrand,
			@RequestParam(value = "typename", required = false) String typename,
			@RequestParam(value = "expireddate", required = false) String expireddate, HttpServletRequest request) {
		// String areaId = httpServletRequest.getParameter("username");
		model.addAttribute("brand", brandService.getAll());
		List<ProductType> lst = proTypeRepository.getAll();
		model.addAttribute("protype", lst);
		model.addAttribute("product", new Product());
		model.addAttribute("productList", productRepository.findAll());
		model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
		StockTotal stockTotalNow = stockTotalService.findAvaiableRecord();
		int status = 1;
		if (stockTotalNow != null) {
			model.addAttribute("status", status);
		}
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("productId")) {
					nameproduct = cookie.getValue();
					System.out.println(nameproduct);
				} else if (cookie.getName().equals("brandId")) {
					namebrand = cookie.getValue();
					System.out.println(namebrand);
				} else if (cookie.getName().equals("typeProductId")) {
					typename = cookie.getValue();
					System.out.println(typename);
				} else if (cookie.getName().equals("expriedDate")) {
					expireddate = cookie.getValue();
					System.out.println(expireddate);
				}

			}
		}
		List<StockTotalDetail> impiantos = new ArrayList<>();
		if (nameproduct != null && namebrand != null && typename != null && expireddate != null) {
			long productId = Long.parseLong(nameproduct);
			long brandId = Long.parseLong(namebrand);
			long typeProductId = Long.parseLong(typename);
			Product product = productRepository.findOne(productId);
			Brand brand = brandService.findBrandById(brandId);
			ProductType productType = proTypeRepository.findProductById(typeProductId);
			if (product != null && brand != null && productType != null) {
				impiantos = stockTotalDetailService.findByQuery(product.getName(), brand.getName(),
						productType.getTypeName());
			}
		} else {
			impiantos = stockTotalDetailService.findAllNow();
		}

		int page1 = pageable.getPageNumber();
		int count = 10;

		if (impiantos != null && impiantos.size() > 0) {
			int min = page1 * count;

			int max = (page1 + 1) * count;
			if (max > impiantos.size()) {
				max = impiantos.size();
			}
			long total = (long) impiantos.size();

			Page<StockTotalDetail> pageImpianto = new PageImpl<StockTotalDetail>(impiantos.subList(min, max), pageable,
					total);
			model.addAttribute("page", pageImpianto);
			return "stock";

		} else {
			Page<StockTotalDetail> pageImpianto = new PageImpl<StockTotalDetail>(impiantos, pageable, (long) 0);
			model.addAttribute("page", pageImpianto);
			String chotkho = "Không tìm thấy tồn kho sản phẩm bạn tìm kiếm";
			model.addAttribute("chotkho", chotkho);
			return "stock";
		}
	}

	@RequestMapping("/search")
	public String search(@RequestParam(value = "nameproduct", required = false) String nameproduct,
			@RequestParam(value = "namebrand", required = false) String namebrand,
			@RequestParam(value = "typename", required = false) String typename,
			@RequestParam(value = "expireddate", required = false) String expireddate, Model model,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request, HttpServletResponse response) {

		model.addAttribute("brand", brandService.getAll());
		model.addAttribute("protype", proTypeRepository.getAll());
		model.addAttribute("product", new Product());
		model.addAttribute("productList", productRepository.findAll());

		StockTotal stockTotalNow = stockTotalService.findAvaiableRecord();
		int status = 1;
		if (stockTotalNow != null) {
			model.addAttribute("status", status);
		}
		if (nameproduct == null && namebrand == null && typename == null && expireddate == null) {
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("productId")) {
						cookie.setMaxAge(0);
					} else if (cookie.getName().equals("brandId")) {
						cookie.setMaxAge(0);
					} else if (cookie.getName().equals("typeProductId")) {
						cookie.setMaxAge(0);
					} else if (cookie.getName().equals("expriedDate")) {
						cookie.setMaxAge(0);
					}

				}
			}
		} else {
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("productId")) {
						cookie.setValue(nameproduct);
						cookie.setMaxAge(0);
						Cookie cookie1 = new Cookie("productId", nameproduct);
				        //set the expiration time
				        //1 hour = 60 seconds x 60 minutes
				        cookie1.setMaxAge(60 * 60);
				        //add the cookie to the  response
				        response.addCookie(cookie1);
						System.out.println(nameproduct);
					} else if (cookie.getName().equals("brandId")) {
						cookie.setValue(namebrand);
						cookie.setMaxAge(0);
						Cookie cookie1 = new Cookie("brandId", namebrand);
				        //set the expiration time
				        //1 hour = 60 seconds x 60 minutes
				        cookie1.setMaxAge(60 * 60);
				        //add the cookie to the  response
				        response.addCookie(cookie1);
						System.out.println(namebrand);
					} else if (cookie.getName().equals("typeProductId")) {
						cookie.setValue(typename);
						cookie.setMaxAge(0);
						Cookie cookie1 = new Cookie("typeProductId", typename);
				        //set the expiration time
				        //1 hour = 60 seconds x 60 minutes
				        cookie1.setMaxAge(60 * 60);
				        //add the cookie to the  response
				        response.addCookie(cookie1);
						System.out.println(typename);
					} else if (cookie.getName().equals("expriedDate")) {
						cookie.setValue(expireddate);
						cookie.setMaxAge(0);
						Cookie cookie1 = new Cookie("expriedDate", expireddate);
				        //set the expiration time
				        //1 hour = 60 seconds x 60 minutes
				        cookie1.setMaxAge(60 * 60);
				        //add the cookie to the  response
				        response.addCookie(cookie1);
						System.out.println(expireddate);
					}

				}
			}
		}

		List<StockTotalDetail> impiantos = new ArrayList<>();
		if (nameproduct != null && namebrand != null && typename != null && expireddate != null) {
			long productId = Long.parseLong(nameproduct);
			long brandId = Long.parseLong(namebrand);
			long typeProductId = Long.parseLong(typename);
			Product product = productRepository.findOne(productId);
			Brand brand = brandService.findBrandById(brandId);
			ProductType productType = proTypeRepository.findProductById(typeProductId);
			if (product != null && brand != null && productType != null) {
				impiantos = stockTotalDetailService.findByQuery(product.getName(), brand.getName(),
						productType.getTypeName());
			}
		} else {
			impiantos = stockTotalDetailService.findAllNow();
		}

		int page1 = pageable.getPageNumber();
		int count = 10;

		if (impiantos != null && impiantos.size() > 0) {
			int min = page1 * count;

			int max = (page1 + 1) * count;
			if (max > impiantos.size()) {
				max = impiantos.size();
			}
			long total = (long) impiantos.size();

			Page<StockTotalDetail> pageImpianto = new PageImpl<StockTotalDetail>(impiantos.subList(min, max), pageable,
					total);
			model.addAttribute("page", pageImpianto);
			String chotkho = "Thông tin tồn kho sản phẩm bạn tìm kiếm như sau";
			model.addAttribute("chotkho", chotkho);
			return "stock";

		} else {
			Page<StockTotalDetail> pageImpianto = new PageImpl<StockTotalDetail>(impiantos, pageable, (long) 0);
			model.addAttribute("page", pageImpianto);
			String chotkho = "Không tìm thấy tồn kho sản phẩm bạn tìm kiếm";
			model.addAttribute("chotkho", chotkho);
			return "stock";
		}
	}

	@RequestMapping(value = "/findPoisition/{id}", method = RequestMethod.GET)
	public String findPoisition(Model model, @PageableDefault(size = 10) Pageable pageable, @PathVariable("id") long id,
			@RequestParam(value = "areaId", required = false) String areaId,
			@RequestParam(value = "percent", required = false) String percent,
			@RequestParam(value = "product", required = false) String product,
			@RequestParam(value = "paletPosition", required = false) String paletPosition) {
		int page1 = pageable.getPageNumber();
		int count = 10;

		List<PalletPosition> temp = palletPoisitionService.findRecord();
		List<PalletPoisitonVo> impiantos = this.filterByParam(temp, areaId, percent, product, paletPosition); // returned
		model.addAttribute("id", id); // 30
		if (impiantos != null && impiantos.size() > 0) {
			int min = page1 * count;

			int max = (page1 + 1) * count;
			if (max > impiantos.size()) {
				max = impiantos.size();
			}
			long total = (long) impiantos.size();

			Page<PalletPoisitonVo> pageImpianto = new PageImpl<PalletPoisitonVo>(impiantos.subList(min, max), pageable,
					total);
			model.addAttribute("page", pageImpianto);
			return "findPoisition";
		} else { // objects
			return "findPoisition";
		}

	}

	private List<PalletPoisitonVo> filterByParam(final List<PalletPosition> palletPositions, String areaId,
			String percent, String ProductId, String paletPoisiton) {
		final List<PalletPoisitonVo> palletPoisitonVos = new ArrayList<>();
		long percent1 = -1;
		long productId = -1;
		long palletpositionId = -1;
		if (percent != null && !percent.equals("")) {
			percent1 = Long.parseLong(percent);
		}
		if (ProductId != null && !ProductId.equals("")) {
			productId = Long.parseLong(ProductId);
		}
		if (paletPoisiton != null && !paletPoisiton.equals("")) {
			palletpositionId = Long.parseLong(paletPoisiton);
		}
		for (PalletPosition palletPosition : palletPositions) {
			PalletPoisitonVo palletPoisitonVo = new PalletPoisitonVo();
			if (areaId != null && !areaId.equals("")) {
				if (areaId.equals(palletPosition.getPallet().getAreaId()) && percent1 >= 0
						&& palletPosition.getEmptyPercent() == percent1) {
					if (palletpositionId > 0 && palletPosition.getPallet().getPalletNumber() == palletpositionId) {
						palletPoisitonVo.setAreaId(palletPosition.getPallet().getAreaId());
						palletPoisitonVo.setEmptyPercent(palletPosition.getEmptyPercent());
						palletPoisitonVo.setId(palletPosition.getId());
						palletPoisitonVo.setPalletNumber(palletPosition.getPallet().getPalletNumber());
						// get values from contact entity and set them in contactDto
						// e.g. contactDto.setContactId(contact.getContactId());

						List<StockTotalDetail> temp = stockTotalDetailService.findRecord();
						String product = "";
						for (StockTotalDetail stockTotalDetail : temp) {
							if (stockTotalDetail.getPalletPosition().getId() == palletPosition.getId()) {
								product += stockTotalDetail.getProduct().getName() + "; ";
							}
						}
						palletPoisitonVo.setProduct(product);
						palletPoisitonVos.add(palletPoisitonVo);
					} else {
						palletPoisitonVo.setAreaId(palletPosition.getPallet().getAreaId());
						palletPoisitonVo.setEmptyPercent(palletPosition.getEmptyPercent());
						palletPoisitonVo.setId(palletPosition.getId());
						palletPoisitonVo.setPalletNumber(palletPosition.getPallet().getPalletNumber());
						// get values from contact entity and set them in contactDto
						// e.g. contactDto.setContactId(contact.getContactId());

						List<StockTotalDetail> temp = stockTotalDetailService.findRecord();
						String product = "";
						for (StockTotalDetail stockTotalDetail : temp) {
							if (stockTotalDetail.getPalletPosition().getId() == palletPosition.getId()) {
								product += stockTotalDetail.getProduct().getName() + "; ";
							}
						}
						palletPoisitonVo.setProduct(product);
						palletPoisitonVos.add(palletPoisitonVo);
					}

				} else if (areaId.equals(palletPosition.getPallet().getAreaId()) && percent1 < 0) {
					if (palletpositionId > 0 && palletPosition.getPallet().getPalletNumber() == palletpositionId) {
						palletPoisitonVo.setAreaId(palletPosition.getPallet().getAreaId());
						palletPoisitonVo.setEmptyPercent(palletPosition.getEmptyPercent());
						palletPoisitonVo.setId(palletPosition.getId());
						palletPoisitonVo.setPalletNumber(palletPosition.getPallet().getPalletNumber());
						// get values from contact entity and set them in contactDto
						// e.g. contactDto.setContactId(contact.getContactId());

						List<StockTotalDetail> temp = stockTotalDetailService.findRecord();
						String product = "";
						for (StockTotalDetail stockTotalDetail : temp) {
							if (stockTotalDetail.getPalletPosition().getId() == palletPosition.getId()) {
								product += stockTotalDetail.getProduct().getName() + "; ";
							}
						}
						palletPoisitonVo.setProduct(product);
						palletPoisitonVos.add(palletPoisitonVo);
					} else {
						palletPoisitonVo.setAreaId(palletPosition.getPallet().getAreaId());
						palletPoisitonVo.setEmptyPercent(palletPosition.getEmptyPercent());
						palletPoisitonVo.setId(palletPosition.getId());
						palletPoisitonVo.setPalletNumber(palletPosition.getPallet().getPalletNumber());
						// get values from contact entity and set them in contactDto
						// e.g. contactDto.setContactId(contact.getContactId());

						List<StockTotalDetail> temp = stockTotalDetailService.findRecord();
						String product = "";
						for (StockTotalDetail stockTotalDetail : temp) {
							if (stockTotalDetail.getPalletPosition().getId() == palletPosition.getId()) {
								product += stockTotalDetail.getProduct().getName() + "; ";
							}
						}
						palletPoisitonVo.setProduct(product);
						palletPoisitonVos.add(palletPoisitonVo);
					}
				}
			} else {
				if ("A".equals(palletPosition.getPallet().getAreaId())) {
					palletPoisitonVo.setAreaId(palletPosition.getPallet().getAreaId());
					palletPoisitonVo.setEmptyPercent(palletPosition.getEmptyPercent());
					palletPoisitonVo.setId(palletPosition.getId());
					palletPoisitonVo.setPalletNumber(palletPosition.getPallet().getPalletNumber());
					// get values from contact entity and set them in contactDto
					// e.g. contactDto.setContactId(contact.getContactId());

					List<StockTotalDetail> temp = stockTotalDetailService.findRecord();
					String product = "";
					for (StockTotalDetail stockTotalDetail : temp) {
						if (stockTotalDetail.getPalletPosition().getId() == palletPosition.getId()) {
							product += stockTotalDetail.getProduct().getName() + "; ";
						}
					}
					palletPoisitonVo.setProduct(product);
					palletPoisitonVos.add(palletPoisitonVo);
				}
			}
		}
		return palletPoisitonVos;
	}

	private PalletPoisitonVo convertToContactDto(final PalletPosition palletPosition) {
		final PalletPoisitonVo palletPoisitonVo = new PalletPoisitonVo();
		palletPoisitonVo.setAreaId(palletPosition.getPallet().getAreaId());
		palletPoisitonVo.setEmptyPercent(palletPosition.getEmptyPercent());
		palletPoisitonVo.setId(palletPosition.getId());
		palletPoisitonVo.setPalletNumber(palletPosition.getPallet().getPalletNumber());
		// get values from contact entity and set them in contactDto
		// e.g. contactDto.setContactId(contact.getContactId());

		List<StockTotalDetail> temp = stockTotalDetailService.findRecord();
		String product = "";
		for (StockTotalDetail stockTotalDetail : temp) {
			if (stockTotalDetail.getPalletPosition().getId() == palletPosition.getId()) {
				product += stockTotalDetail.getProduct().getName() + "; ";
			}
		}
		palletPoisitonVo.setProduct(product);
		return palletPoisitonVo;
	}

	private List<PalletPoisitonVo> convertToContactDtos(final List<PalletPosition> palletPositions) {
		final List<PalletPoisitonVo> palletPoisitonVos = new ArrayList<>();
		for (PalletPosition palletPosition : palletPositions) {
			PalletPoisitonVo palletPoisitonVo = new PalletPoisitonVo();
			palletPoisitonVo.setAreaId(palletPosition.getPallet().getAreaId());
			palletPoisitonVo.setEmptyPercent(palletPosition.getEmptyPercent());
			palletPoisitonVo.setId(palletPosition.getId());
			palletPoisitonVo.setPalletNumber(palletPosition.getPallet().getPalletNumber());
			// get values from contact entity and set them in contactDto
			// e.g. contactDto.setContactId(contact.getContactId());

			List<StockTotalDetail> temp = stockTotalDetailService.findRecord();
			String product = "";
			for (StockTotalDetail stockTotalDetail : temp) {
				if (stockTotalDetail.getPalletPosition().getId() == palletPosition.getId()) {
					product += stockTotalDetail.getProduct().getName() + "; ";
				}
			}
			palletPoisitonVo.setProduct(product);
			palletPoisitonVos.add(palletPoisitonVo);
		}

		return palletPoisitonVos;
	}

	@GetMapping("/exportExcelCompare")
	public void downloadCompareExcel(HttpServletRequest httpServletRequest, HttpServletResponse response) {

		StockTotal stockTotal = stockTotalService.findNow();
		if (stockTotal == null) {
			return;
		}
		List<StockChange> lststockChange = stockChangeRepository.findByStockTotalId(stockTotal.getId());
		if (lststockChange != null) {

			List<StockTotalDetailVO> lst = new ArrayList<>();

			for (StockChange stockChange : lststockChange) {
				StockTotalDetailVO stockTotalDetailVO = new StockTotalDetailVO();
				stockTotalDetailVO.setBrandId(Math.toIntExact(stockChange.getProduct().getBrand().getId()));
				stockTotalDetailVO.setBrandName(stockChange.getProduct().getBrand().getName());
				stockTotalDetailVO.setProductId(Math.toIntExact(stockChange.getProduct().getId()));
				stockTotalDetailVO.setProductName(stockChange.getProduct().getName());
				stockTotalDetailVO.setTypeOfProduct(Math.toIntExact(stockChange.getProduct().getProductType().getId()));
				stockTotalDetailVO.setQuantity(stockChange.getQuantityChange());
				StockTotalDetail stockTotalDetail = stockTotalDetailService
						.findOne(stockChange.getStockTotalDetailId());
				if (stockTotalDetail != null) {
//					LocalDate localDate = stockTotalDetail.getExpiredDate();// For reference
//					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
					String formattedString = stockTotalDetail.getExpiredDate();
					stockTotalDetailVO.setExpiredDate(formattedString);
					stockTotalDetailVO.setPalletPositionId((int) stockTotalDetail.getPalletPosition().getId());
					stockTotalDetailVO.setStockTotalDetailId(stockTotalDetail.getId());
				} else {
					stockTotalDetailVO.setError("Bản ghi lỗi. Vui lòng báo lại quản trị viên");
				}
				lst.add(stockTotalDetailVO);
			}
			if (lst.size() > 0) {
				exportFile(httpServletRequest, response, "Bieu_mau_chenh_lech_ton_kho", lst);
			}
		}
	}

	@GetMapping("/exportExcelCK")
	public void downloadFile(HttpServletRequest httpServletRequest, HttpServletResponse response) {

		String fileName = "src/main/resources/excel/Bieu_mau_chot_kho.xlsx";
		String fileOutName = "";
		try {
			// Code to download
			File fileToDownload1 = new File(fileName);
			InputStream in1 = new FileInputStream(fileToDownload1);
			try {
				XSSFWorkbook workbook = new XSSFWorkbook(in1);
				XSSFSheet worksheet = workbook.getSheetAt(0);
				XSSFCellStyle style = workbook.createCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderTop(BorderStyle.THIN);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderRight(BorderStyle.THIN);
				style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderLeft(BorderStyle.THIN);
				style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				Iterable<StockTotalDetail> lst = stockTotalDetailRepository.findAll();
				if (lst != null) {
					int rowNum = 3;
					for (StockTotalDetail stockTotalDetail : lst) {
						Row row = worksheet.createRow(rowNum);
						Product product = productRepository.findOne(stockTotalDetail.getProduct().getId());
						for (int cellIndex = 0; cellIndex < 10; cellIndex++) {
							Cell cell = row.createCell(cellIndex);
							if (cellIndex == 0) {
								cell.setCellValue(stockTotalDetail.getProduct().getId());
								cell.setCellStyle(style);
							} else if (cellIndex == 1) {
								cell.setCellValue(product.getName());
								cell.setCellStyle(style);
							} else if (cellIndex == 2) {
								cell.setCellValue(product.getBrand().getName());
								cell.setCellStyle(style);
							} else if (cellIndex == 3) {
								cell.setCellValue(product.getProductType().getId());
								cell.setCellStyle(style);
							} else if (cellIndex == 4) {
								cell.setCellValue(stockTotalDetail.getQuantity());
								if (stockTotalDetail.getQuantity() <= 0) {
									Font font = workbook.createFont();
									font.setColor(IndexedColors.RED.getIndex());
									style.setFont(font);
								} else {
									Font font = workbook.createFont();
									font.setColor(IndexedColors.BLACK.getIndex());
									style.setFont(font);
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 5) {
//								LocalDate localDate = stockTotalDetail.getExpiredDate();// For reference
//								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
								String formattedString = stockTotalDetail.getExpiredDate();
								cell.setCellValue(formattedString);
								cell.setCellStyle(style);
							} else if (cellIndex == 6) {
								if (stockTotalDetail.getPalletPosition() != null) {
									long id = stockTotalDetail.getPalletPosition().getId();
									cell.setCellValue(id);
									cell.setCellStyle(style);
								}
							} else if (cellIndex == 7) {
								if (stockTotalDetail.getId() != 0) {
									long id = stockTotalDetail.getId();
									cell.setCellValue(id);
									cell.setCellStyle(style);
								}

							}

						}
						rowNum++;
					}
				}
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
				LocalDateTime now = LocalDateTime.now();
				fileOutName = "src/main/resources/excel/Bieu_mau_chot_kho1" + dtf.format(now) + ".xlsx";
				FileOutputStream out = new FileOutputStream(new File(fileOutName));
				workbook.write(out);
				out.close();
				System.out.println("Successfully saved Selenium WebDriver TestNG result to Excel File!!!");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File fileToDownload = new File(fileOutName);
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

			/**/

			System.out.println("File downloaded at client successfully");

		} catch (NullPointerException ex1) {
			System.out.println(ex1);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Value("${image.upload-dir}")
	private String localtion;

	@PostMapping("/stock")
	public String addproduct(@ModelAttribute Product product, @RequestParam("name") String name,
			@RequestParam("brand") String brand, @RequestParam("price") Integer price,
			@RequestParam("productType") String productType, @RequestParam("packageType") String packageType,
			Model model, @PageableDefault(size = 10) Pageable pageable) {
		Long longId = Long.parseLong(brand);
		Brand brand1 = brandService.findBrandById(longId);

		Long longIdpro = Long.parseLong(productType);
		ProductType productType2 = protypeService.findProductById(longIdpro);
		product.setName(name);
		product.setPackageType(packageType);
		product.setPrice(price);
		String proceString = price.toString();
		product.setBrand(brand1);
		product.setProductType(productType2);

		if (name == null || brand == null || proceString == null || productType == null || packageType == null) {
			model.addAttribute("mgs", "Thêm mới thật bại");
		}
		if (service.create(product) == true) {

			model.addAttribute("mgs", "Thêm mới thành công sản phẩm");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
			StockTotal stockTotalNow = stockTotalService.findAvaiableRecord();
			int status = 1;
			if (stockTotalNow != null) {
				model.addAttribute("status", status);
			}
			return "stock";
		} else {
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
			StockTotal stockTotalNow = stockTotalService.findAvaiableRecord();
			int status = 1;
			if (stockTotalNow != null) {
				model.addAttribute("status", status);
			}
			model.addAttribute("mgs", "Thêm mới không thàng công");

			return "stock";
		}

	}

	@PostMapping("/importExcel")
	public String mapReapExcelDatatoDB(HttpServletRequest httpServletRequest, HttpServletResponse response,
			@RequestParam("file") MultipartFile reapExcelDataFile, Model model,
			@PageableDefault(size = 10) Pageable pageable) throws IOException, IllegalStateException {
		StockTotal stockTotalNow = stockTotalService.findNow();
		if (stockTotalNow == null) {
			String chotkho = "Kho đang hoạt động! Vui lòng liên hệ quản lý để kiểm kê kho";
			model.addAttribute("chotkho", chotkho);
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
			int status = 1;
			model.addAttribute("status", status);
			return "stock";
		}
		if (reapExcelDataFile.getInputStream() == null) {
			String chotkho = "Vui lòng kiểm tra nhập file Excel! ";
			model.addAttribute("chotkho", chotkho);
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
			return "stock";
		}
		int status = 1;
		if (stockTotalNow != null) {
			model.addAttribute("status", status);
		}
		List<StockChange> lstDelete = stockChangeRepository.findByStockTotalId(stockTotalNow.getId());
		for (StockChange stockChange : lstDelete) {
			stockChangeRepository.delete(stockChange);
		}
		ReadingFromExcelSheet readingFromExcelSheet = new ReadingFromExcelSheet();
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int count = 0;
		int countFail = 0;
		int productCount = 0;
		List<StockTotalDetailVO> lstFail = new ArrayList<>();
		List<StockTotalDetailVO> lstCompare = new ArrayList<>();
		int totalRow = worksheet.getPhysicalNumberOfRows();

		for (int i = 3; i < totalRow + 1; i++) {
			int checkData = 0;
			String msg = "";
			XSSFRow row = worksheet.getRow(i);
			StockTotalDetailVO stockTotalDetailVO = new StockTotalDetailVO();

			for (int cellIndex = 0; cellIndex < 8; cellIndex++) {

				String value = "";
				Cell cell = row.getCell(cellIndex);
				if (cell != null && cellIndex == 0) {

					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						try {
							stockTotalDetailVO.setProductId((int) Math.round(Double.parseDouble(value)));
						} catch (NumberFormatException e) {
							e.printStackTrace();
							msg += "Mã sản phẩm không hợp lệ";
							checkData++;
							continue;
						}

					} else {
						msg += "Không để trống mã sản phẩm";
						checkData++;
						continue;
					}

				}
				if (cell != null && cellIndex == 1) {
					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						stockTotalDetailVO.setProductName(value);
					}

				}
				if (cell != null && cellIndex == 2) {
					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						stockTotalDetailVO.setBrandName(value);

					} else {
						msg += "Không để trống Nhãn hiệu";
						checkData++;
						continue;
					}

				}
				if (cell != null && cellIndex == 3) {
					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						try {
							stockTotalDetailVO.setTypeOfProduct((int) Math.round(Double.parseDouble(value)));
						} catch (NumberFormatException e) {
							e.printStackTrace();
							msg += "Loại sản phẩm không hợp lệ";
							checkData++;
							continue;
						}

					} else {
						msg += "Không để trống loại sản phẩm";
						checkData++;
						continue;
					}

				}

				if (cell != null && cellIndex == 4) {
					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						try {
							stockTotalDetailVO.setQuantity((int) Math.round(Double.parseDouble(value)));
						} catch (NumberFormatException e) {
							e.printStackTrace();
							msg += "Số lượng không hợp lệ";
							checkData++;
							continue;
						}
					} else {
						msg += "Không để trống số lượng";
						checkData++;
						continue;
					}
				}
				if (cell != null && cellIndex == 5) {
					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						stockTotalDetailVO.setExpiredDate(value);
					} else {
						msg += "Không để trống ngày hết hạn";
						checkData++;
					}
				}

				if (cell != null && cellIndex == 6) {
					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						try {
							stockTotalDetailVO.setPalletPositionId((int) Math.round(Double.parseDouble(value)));
						} catch (NumberFormatException e) {
							e.printStackTrace();
							msg += "Vị trí không hợp lệ";
							checkData++;
							continue;
						}
					} else {
						msg += "Không để trống vị trí tồn kho";
						checkData++;
						continue;
					}

				}
				if (cell != null && cellIndex == 7) {
					value = readingFromExcelSheet.getCellValue(cell);
					if (!value.equals("")) {
						try {
							stockTotalDetailVO.setStockTotalDetailId((int) Math.round(Double.parseDouble(value)));
						} catch (NumberFormatException e) {
							e.printStackTrace();
							msg += "Bản ghi tồn kho không hợp lệ";
							checkData++;
							continue;
						}
					} else {
						msg += "Không để trống bản ghi tồn kho";
						checkData++;
					}
				}
				if (!msg.equals("") && checkData != 7) {
					stockTotalDetailVO.setError(msg);
					if (!lstFail.contains(stockTotalDetailVO)) {
						lstFail.add(stockTotalDetailVO);
					}
					countFail++;
				}

			}
			if (msg.equals("")) {
				StockTotalDetail stockTotalDetail = stockTotalDetailService
						.findOne(stockTotalDetailVO.getStockTotalDetailId());
				if (stockTotalDetail != null && stockTotalDetail.getProductStatus() == 1) {
					if (stockTotalDetail.getProduct().getId() != stockTotalDetailVO.getProductId()) {
						stockTotalDetailVO.setError("Bản ghi tồn kho không tồn tại. Vui lòng xem lại Product_id");
						lstFail.add(stockTotalDetailVO);
						countFail++;
						continue;
					}
//					LocalDate localDate = stockTotalDetail.getExpiredDate();// For reference
//					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
					String formattedString = stockTotalDetail.getExpiredDate();
					if (!formattedString.equals(stockTotalDetailVO.getExpiredDate())) {
						stockTotalDetailVO.setError("Bản ghi tồn kho không tồn tại. Vui lòng xem lại Ngày hết hạn");
						lstFail.add(stockTotalDetailVO);
						countFail++;
						continue;
					}
					if (stockTotalDetail.getQuantity() != stockTotalDetailVO.getQuantity()) {
						StockChange stockChange = new StockChange();
						stockChange.setStockTotalId(stockTotalNow.getId());
						long quantityChange = stockTotalDetailVO.getQuantity() - stockTotalDetail.getQuantity();
						stockChange.setQuantityChange(quantityChange);
						Product product = productRepository.findOne((long) stockTotalDetailVO.getProductId());
						stockChange.setProduct(product);
						stockChange.setStockTotalDetailId(stockTotalDetail.getId());
						stockChange.setStockTotalId(stockTotalNow.getId());
						stockChangeRepository.save(stockChange);
						stockTotalDetail.setQuantity(stockTotalDetail.getQuantity() + quantityChange);
						stockTotalDetail.setAvaiableQuantity(stockTotalDetail.getAvaiableQuantity() + quantityChange);
						stockTotalDetailVO.setError("Số lượng thay đổi: " + quantityChange);
						lstCompare.add(stockTotalDetailVO);
						LocalDate formattedString1 = LocalDate.now();
						DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
						String updateDate = formattedString1.format(formatter1);
						stockTotalDetail.setUpdateDate(updateDate);
						stockTotalDetail.setStockTotal(stockTotalNow);
						stockTotalDetailRepository.save(stockTotalDetail);
					} else {
						LocalDate formattedString1 = LocalDate.now();
						DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
						String updateDate = formattedString1.format(formatter1);
						stockTotalDetail.setUpdateDate(updateDate);
						stockTotalDetail.setStockTotal(stockTotalNow);
						stockTotalDetailRepository.save(stockTotalDetail);
						count++;
					}

				} else if (stockTotalDetail != null && stockTotalDetail.getProductStatus() == 0) {
					StockChange stockChange = new StockChange();
					stockChange.setStockTotalId(stockTotalNow.getId());
					long quantityChange = stockTotalDetailVO.getQuantity() - stockTotalDetail.getQuantity();
					stockChange.setQuantityChange(quantityChange);
					Product product = productRepository.findOne((long) stockTotalDetailVO.getProductId());
					stockChange.setProduct(product);
					stockChange.setStockTotalDetailId(stockTotalDetail.getId());
					stockChange.setStockTotalId(stockTotalNow.getId());
					stockChangeRepository.save(stockChange);
					stockTotalDetail.setQuantity(stockTotalDetail.getQuantity() + quantityChange);
					stockTotalDetail.setAvaiableQuantity(stockTotalDetail.getAvaiableQuantity() + quantityChange);
					stockTotalDetailVO.setError("Số lượng thay đổi: " + quantityChange);
					lstCompare.add(stockTotalDetailVO);
					LocalDate formattedString1 = LocalDate.now();
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
					String updateDate = formattedString1.format(formatter1);
					stockTotalDetail.setUpdateDate(updateDate);
					stockTotalDetail.setStockTotal(stockTotalNow);
					stockTotalDetail.setProductStatus(1);
					stockTotalDetailRepository.save(stockTotalDetail);
				} else {
					stockTotalDetailVO.setError("Bản ghi tồn kho không tồn tại. Vui lòng xem lại Stotal_detail_id");
					lstFail.add(stockTotalDetailVO);
					countFail++;
				}

			}
			if (checkData == 7) {
				break;
			}
			productCount++;
		}
		if (stockTotalNow != null && stockTotalNow.getTotalCount() < productCount) {
			stockTotalNow.setTotalCount(productCount);
			stockTotalRepository.save(stockTotalNow);
		}
		String chotkho = "";
		List<StockTotalDetail> lstCount = stockTotalDetailService.findByStockTotal(stockTotalNow);
		if (lstCount != null && lstCount.size() >= stockTotalNow.getTotalCount() && lstFail.size() == 0) {
			if (lstCompare.size() != 0) {
				model.addAttribute("compare", "Chênh lệch: " + lstCompare.size());
			}
			chotkho = "Hoàn thành chốt kho. Vui lòng duyệt kiểm kê để kho tiếp tục hoạt động";
			model.addAttribute("chotkho", chotkho);
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
			return "stock";
		} else {
			chotkho = "Chưa hoàn thành chốt kho. Vui lòng kiểm tra file Excel chốt kho";
			model.addAttribute("chotkho", chotkho);
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));

			exportFile(httpServletRequest, response, "Bieu_mau_chot_kho_loi", lstFail);
		}
		return "stock";
	}

	public void exportFile(HttpServletRequest httpServletRequest, HttpServletResponse response, String filetype,
			List<StockTotalDetailVO> list) throws IllegalStateException {
		if (list.size() == 0) {
			return;
		}
		String fileName = "src/main/resources/excel/" + filetype + ".xlsx";
		String fileOutName = "";
		try {
			// Code to download
			File fileToDownload1 = new File(fileName);
			InputStream in1 = new FileInputStream(fileToDownload1);
			try {
				XSSFWorkbook workbook = new XSSFWorkbook(in1);
				XSSFSheet worksheet = workbook.getSheetAt(0);
				XSSFCellStyle style = workbook.createCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderTop(BorderStyle.THIN);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderRight(BorderStyle.THIN);
				style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderLeft(BorderStyle.THIN);
				style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				if (list != null) {
					int rowNum = 3;
					for (StockTotalDetailVO stockTotalDetailVO : list) {
						Row row = worksheet.createRow(rowNum);
						for (int cellIndex = 0; cellIndex < 10; cellIndex++) {
							Cell cell = row.createCell(cellIndex);
							if (cellIndex == 0) {
								if (stockTotalDetailVO.getProductId() != null) {
									cell.setCellValue(stockTotalDetailVO.getProductId());
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 1) {
								if (stockTotalDetailVO.getProductName() != null) {
									cell.setCellValue(stockTotalDetailVO.getProductName());
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 2) {
								if (stockTotalDetailVO.getBrandName() != null) {
									cell.setCellValue(stockTotalDetailVO.getBrandName());
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 3) {
								if (stockTotalDetailVO.getTypeOfProduct() != null) {
									cell.setCellValue(stockTotalDetailVO.getTypeOfProduct());
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 4) {
								cell.setCellValue(stockTotalDetailVO.getQuantity());
								if (stockTotalDetailVO.getQuantity() <= 0) {
									Font font = workbook.createFont();
									font.setColor(IndexedColors.RED.getIndex());
									style.setFont(font);
								} else {
									Font font = workbook.createFont();
									font.setColor(IndexedColors.BLACK.getIndex());
									style.setFont(font);
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 5) {
								if (stockTotalDetailVO.getExpiredDate() != null
										&& stockTotalDetailVO.getExpiredDate().equals("")) {
									cell.setCellValue(stockTotalDetailVO.getExpiredDate());
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 6) {
								if (stockTotalDetailVO.getPalletPositionId() != null
										&& stockTotalDetailVO.getPalletPositionId() > 0) {
									long id = stockTotalDetailVO.getPalletPositionId();
									cell.setCellValue(id);

								}
								cell.setCellStyle(style);
							} else if (cellIndex == 7) {
								if (stockTotalDetailVO.getStockTotalDetailId() != null
										&& stockTotalDetailVO.getStockTotalDetailId() > 0) {
									long id = stockTotalDetailVO.getStockTotalDetailId();
									cell.setCellValue(id);

								}
								cell.setCellStyle(style);
							} else if (cellIndex == 8) {
								if (stockTotalDetailVO.getError() != null) {
									cell.setCellValue(stockTotalDetailVO.getError());
									Font font = workbook.createFont();
									font.setColor(IndexedColors.RED.getIndex());
									style.setFont(font);

								}
								cell.setCellStyle(style);
							}

						}
						rowNum++;
					}
				}
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
				LocalDateTime now = LocalDateTime.now();
				fileOutName = "F://" + filetype + dtf.format(now) + ".xlsx";
				FileOutputStream out = new FileOutputStream(new File(fileOutName));
				workbook.write(out);
				out.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File fileToDownload = new File(fileOutName);
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

			// in.close();
			// outStream.close();

			System.out.println("File downloaded at client successfully");

			/**/

			System.out.println("File downloaded at client successfully");

		} catch (NullPointerException ex1) {
			System.out.println(ex1);
		} catch (IllegalStateException ex4) {
			System.out.println(ex4);
			throw new IllegalStateException();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		try {
			response.sendRedirect("/stock");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		StockTotal stockTotalNow = stockTotalService.findAvaiableRecord();
		int status = 1;
		if (stockTotalNow != null) {
			model.addAttribute("status", status);
		}
		return "stock";
	}

	private static final Logger logger = LoggerFactory.getLogger(StockController.class);

	@PostMapping("/startCheckStock")
	public String startCheckStock(Model model, @PageableDefault(size = 10) Pageable pageable) {
		// Vô hiệu hóa chốt kho cũ
		StockTotal stockTotal = stockTotalService.findAvaiableRecord();
		if (stockTotal != null) {

			createNewStockTotal(stockTotal);
			model.addAttribute("chotkho", "Bắt đầu kiểm kê kho");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
			return "stock";
		} else {
			model.addAttribute("chotkho", "Hiện tại đã kiểm kê kho.");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));

			return "stock";
		}
	}

	@Transactional
	public void createNewStockTotal(StockTotal stockTotal) {
		long stock = stockTotal.getTotalCount();
		stockTotal.setStatus(0);
		stockTotalRepository.save(stockTotal);

		// Tạo bản ghi chốt kho mới
		stockTotal = new StockTotal();
		stockTotal.setStatus(2);
		stockTotal.setUserCreateId(1);
		stockTotal.setTotalCount(stock);
		stockTotalRepository.save(stockTotal);
	}

	@PostMapping("/acceptCheckStock")
	public String acceptCheckStock(Model model, @PageableDefault(size = 10) Pageable pageable) {

		StockTotal stockTotal = stockTotalService.findNow();
		if (stockTotal != null) {
			List<StockTotalDetail> lstCount = stockTotalDetailService.findByStockTotal(stockTotal);
			if (lstCount != null && lstCount.size() >= stockTotal.getTotalCount()) {
				stockTotal.setStatus(1);
				stockTotalRepository.save(stockTotal);

				model.addAttribute("chotkho", "Hoàn thành kiểm kê kho");
				model.addAttribute("brand", brandService.getAll());
				model.addAttribute("protype", proTypeRepository.getAll());
				model.addAttribute("product", new Product());
				model.addAttribute("productList", productRepository.findAll());
				model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
				StockTotal stockTotalNow = stockTotalService.findAvaiableRecord();
				int status = 1;
				if (stockTotalNow != null) {
					model.addAttribute("status", status);
				}
				return "stock";

			} else {
				model.addAttribute("chotkho", "Chưa hoàn thành chốt kho");
				model.addAttribute("brand", brandService.getAll());
				model.addAttribute("protype", proTypeRepository.getAll());
				model.addAttribute("product", new Product());
				model.addAttribute("productList", productRepository.findAll());
				model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));

				return "stock";
			}

		} else {
			model.addAttribute("chotkho", "Hiện chưa bắt đầu kiểm kê kho");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			model.addAttribute("productList", productRepository.findAll());
			model.addAttribute("stockTotalDetail", stockTotalDetailService.findAll(pageable));
			StockTotal stockTotalNow = stockTotalService.findAvaiableRecord();
			int status = 1;
			if (stockTotalNow != null) {
				model.addAttribute("status", status);
			}
			return "stock";
		}

	}

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
