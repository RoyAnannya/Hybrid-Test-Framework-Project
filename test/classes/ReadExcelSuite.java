package classes;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcelSuite {
	static ReadConfigProperty config = new ReadConfigProperty();
	public static void main(String[] args) {
	ReadExcelSuite read = new ReadExcelSuite();
	read.getNumberOfSheetsinSuite();
	}
	public List<String> getNumberOfSheetsinSuite() {
	List<String> listOfSheets = new ArrayList<String>();
	try {
		Workbook workbook = WorkbookFactory.create(new File(config.getConfigValues("TestSuiteName")));
		} 
	catch (InvalidFormatException | IOException e) {
		System.out.println(e.getMessage());
		}
		return listOfSheets;
		}

	public static String readCell(int rowNum, int ColNum) 
	{
		Workbook workbook;
		String cellValue = null;
	try 
	{
		workbook = WorkbookFactory.create(new File(config.getConfigValues("TestCasePath")));
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(ColNum);
		cellValue = cell.getStringCellValue();
		} 
	catch(InvalidFormatException | IOException e) 
		{
		System.out.println("InvalidFormatException");
		}
		return cellValue;
		}
	}
