package com.nxm.utils;



/**
 *
 * @author Win
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.swing.JFileChooser;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadingFromExcelSheet {

	public  Object[][] getDataObject(String fileName, String testCase) throws EncryptedDocumentException, IOException, Exception {
		String[][] tabArray = null;
		
			try {
				InputStream is = new FileInputStream(fileName);

				Workbook wb = WorkbookFactory.create(is);

				Sheet sheet = wb.getSheet(testCase);
				// Iterate through each rows from first sheet
				Iterator<Row> rowIterator = sheet.iterator();
				int j = 0;
				int testData = 0;
				int expectResult = 0;
				tabArray = new String[sheet.getLastRowNum()][2];
				int countArray = 0;
				int tableRow=0;
				boolean checkData=false;
				boolean checkExpect=false;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					String input = "";
					String expect = "";
					// For each row, iterate through each columns
					Iterator<Cell> cellIterator = row.cellIterator();

					int i = 0;
					while (cellIterator.hasNext()) {
						
						Cell cell = cellIterator.next();
						if (getCellValue(cell).equalsIgnoreCase("Dữ liệu test")) {
							testData = i;
							tableRow=j;
							checkData=true;
							tabArray = new String[sheet.getLastRowNum()-tableRow][2];
						}
						if (getCellValue(cell).equalsIgnoreCase("Kết quả mong đợi")) {
							expectResult = i;
							checkExpect=true;
						}
						if (j > tableRow && i == testData&&checkData==true) {
							System.out.println(getCellValue(cell) + "\n");
							input = getCellValue(cell);
						} else if (j > tableRow && i == expectResult&&checkExpect==true) {
							System.out.println(getCellValue(cell) + "\n");
							expect = getCellValue(cell);
						}
						if (testData > 0 && expectResult > 0) {
							if (input != null && !input.equals("") && expect != null && !expect.equals("")) {
								tabArray[countArray][0] = input;
								tabArray[countArray][1] = expect;
								countArray++;
								input="";
								expect="";
							}
							
						}

						i++;
					}
					j++;
				}
				System.out.println("\n");

				is.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		return tabArray;

	}

	public String getCellValue(Cell cell) {
		switch (cell.getCellTypeEnum()) {

		case BLANK:
			return "";

		case STRING:
			return cell.getStringCellValue();

		case NUMERIC:
			return Double.toString(cell.getNumericCellValue());

		case FORMULA:
			return cell.getCellFormula().toString();

		case BOOLEAN:
			return Boolean.toString(cell.getBooleanCellValue());

		default:
			throw new IllegalArgumentException("Cannot read the column : ");
		}
	}
}
