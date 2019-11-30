package com.nxm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
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
				stockTotalDetailVO.setProductId(Math.toIntExact(stockChange.getProduct().getId()));
				stockTotalDetailVO.setProductName(stockChange.getProduct().getName());
				stockTotalDetailVO.setTypeOfProduct(Math.toIntExact(stockChange.getProduct().getProductType().getId()));
				stockTotalDetailVO.setQuantity(stockChange.getQuantityChange());
				StockTotalDetail stockTotalDetail = stockTotalDetailService
						.findOne(stockChange.getStockTotalDetailId());
				if (stockTotalDetail != null) {
					LocalDate localDate = stockTotalDetail.getExpiredDate();// For reference
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
					String formattedString = localDate.format(formatter);
					stockTotalDetailVO.setExpiredDate(formattedString);
					stockTotalDetailVO.setPalletPositionId(stockTotalDetail.getPalletPosition().getId());
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

		String fileName = "F:/Spring framework/qlphanmem/template/Bieu_mau_chot_kho.xlsx";
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
								LocalDate localDate = stockTotalDetail.getExpiredDate();// For reference
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
								String formattedString = localDate.format(formatter);
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
				fileOutName = "F:/Spring framework/qlphanmem/Excel/Bieu_mau_chot_kho1" + dtf.format(now) + ".xlsx";
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
	public String mapReapExcelDatatoDB(HttpServletRequest httpServletRequest, HttpServletResponse response,
			@RequestParam("file") MultipartFile reapExcelDataFile, Model model, Pageable pageable)
			throws IOException, IllegalStateException {
		StockTotal stockTotalNow = stockTotalService.findNow();
		if (stockTotalNow == null) {
			String chotkho = "Kho đang hoạt động! Vui lòng liên hệ quản lý để kiểm kê kho";
			model.addAttribute("chotkho", chotkho);
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
			PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
			model.addAttribute("palletpositions", page.getContent());
			model.addAttribute("page", page);
			return "stock";
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
				if (stockTotalDetail != null) {
					if (stockTotalDetail.getProduct().getId() != stockTotalDetailVO.getProductId()) {
						stockTotalDetailVO.setError("Bản ghi tồn kho không tồn tại. Vui lòng xem lại Product_id");
						lstFail.add(stockTotalDetailVO);
						countFail++;
						continue;
					}
					LocalDate localDate = stockTotalDetail.getExpiredDate();// For reference
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
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
						long quantityChange = stockTotalDetailVO.getQuantity() - stockTotalDetail.getQuantity();
						stockChange.setQuantityChange(quantityChange);
						Product product = productRepository.findOne((long) stockTotalDetailVO.getProductId());
						stockChange.setProduct(product);
						stockChange.setStockTotalDetailId(stockTotalDetail.getId());
						stockChange.setStockTotalId(stockTotalNow.getId());
						stockChangeRepository.save(stockChange);
						stockTotalDetail.setQuantity(stockTotalDetailVO.getQuantity() + quantityChange);
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
		List<StockTotalDetail> lstCount = stockTotalDetailService.findByStockTotalId(stockTotalNow.getId());
		if (lstCount != null && lstCount.size() >= stockTotalNow.getTotalCount() && lstFail.size() == 0) {
			if (lstCompare.size() != 0) {
				model.addAttribute("compare", "Chênh lệch: " + lstCompare.size());
			}
			chotkho = "Hoàn thành chốt kho";
			model.addAttribute("chotkho", chotkho);
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
			PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
			model.addAttribute("palletpositions", page.getContent());
			model.addAttribute("page", page);
		} else {
			chotkho = "Chưa hoàn thành chốt kho";
			exportFile(httpServletRequest, response, "Bieu_mau_chot_kho_loi", lstFail);
		}

		return "stock";
	}

	public void exportFile(HttpServletRequest httpServletRequest, HttpServletResponse response, String filetype,
			List<StockTotalDetailVO> list) throws IllegalStateException {
		if (list.size() == 0) {
			return;
		}
		String fileName = "F:/Spring framework/qlphanmem/template/" + filetype + ".xlsx";
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
								if (stockTotalDetailVO.getExpiredDate() != null&&stockTotalDetailVO.getExpiredDate().equals("")) {
									cell.setCellValue(stockTotalDetailVO.getExpiredDate());
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 6) {
								if (stockTotalDetailVO.getPalletPositionId()!=null&&stockTotalDetailVO.getPalletPositionId() >0) {
									long id = stockTotalDetailVO.getPalletPositionId();
									cell.setCellValue(id);
								
								}
								cell.setCellStyle(style);
							} else if (cellIndex == 7) {
								if (stockTotalDetailVO.getStockTotalDetailId()!=null&&stockTotalDetailVO.getStockTotalDetailId() >0) {
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
				fileOutName = "F:/Spring framework/qlphanmem/Excel/" + filetype + dtf.format(now) + ".xlsx";
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
		return "stock";
	}

	private static final Logger logger = LoggerFactory.getLogger(StockController.class);

	@PostMapping("/startCheckStock")
	public String startCheckStock(Model model, Pageable pageable) {
		// Vô hiệu hóa chốt kho cũ
		StockTotal stockTotal = stockTotalService.findAvaiableRecord();
		if (stockTotal != null) {
			stockTotal.setStatus(0);
			stockTotalRepository.save(stockTotal);

			// Tạo bản ghi chốt kho mới
			stockTotal = new StockTotal();
			stockTotal.setStatus(2);
			stockTotal.setUserCreateId(1);
			stockTotal.setTotalCount(0);
			stockTotalRepository.save(stockTotal);

			model.addAttribute("chotkho", "Bắt đầu kiểm kê kho");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
			PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
			model.addAttribute("palletpositions", page.getContent());
			model.addAttribute("page", page);
			return "stock";
		} else {
			model.addAttribute("chotkho", "Hiện tại đã kiểm kê kho.");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
			PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
			model.addAttribute("palletpositions", page.getContent());
			model.addAttribute("page", page);
			return "stock";
		}
	}

	@PostMapping("/acceptCheckStock")
	public String acceptCheckStock(Model model, Pageable pageable) {

		StockTotal stockTotal = stockTotalService.findNow();
		if (stockTotal != null) {
			List<StockTotalDetail> lstCount = stockTotalDetailService.findByStockTotalId(stockTotal.getId());
			if (lstCount != null && lstCount.size() >= stockTotal.getTotalCount()) {
				stockTotal.setStatus(1);
				stockTotalRepository.save(stockTotal);

				model.addAttribute("chotkho", "Hoàn thành kiểm kê kho");
				model.addAttribute("brand", brandService.getAll());
				model.addAttribute("protype", proTypeRepository.getAll());
				model.addAttribute("product", new Product());
				Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
				PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
				model.addAttribute("palletpositions", page.getContent());
				model.addAttribute("page", page);
				return "stock";
				
			}else {
				model.addAttribute("chotkho", "Chưa hoàn thành kiểm kê kho");
				model.addAttribute("brand", brandService.getAll());
				model.addAttribute("protype", proTypeRepository.getAll());
				model.addAttribute("product", new Product());
				Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
				PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
				model.addAttribute("palletpositions", page.getContent());
				model.addAttribute("page", page);
				return "stock";
			}
			
		} else {
			model.addAttribute("chotkho", "Hiện chưa bắt đầu kiểm kê kho");
			model.addAttribute("brand", brandService.getAll());
			model.addAttribute("protype", proTypeRepository.getAll());
			model.addAttribute("product", new Product());
			Page<PalletPosition> palletPoisitionPage = palletPoisitionService.getAllPalletPoisitions(pageable);
			PageWrapper<PalletPosition> page = new PageWrapper<>(palletPoisitionPage, "/stock");
			model.addAttribute("palletpositions", page.getContent());
			model.addAttribute("page", page);
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
