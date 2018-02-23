package SetUp;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Report Reader created to ease reading an .xls files, 
 * by specifing simple methods to receive data from more complex structure
 * 
 * @author Piotr Rapacz
 * 
 * 
 * <pre>
 * <code>
 * Example:
 *	String filePath = "C:\\tmp\\report.xls";
 *	String sheetName = "Cars";
 *	String dataPath1 = "Brand->Model->Price,Color";
 *	String dataPath2 = "Brand->Model";
 *
 *	try {
 *		ReportReader report = new ReportReader(filePath, sheetName, dataPath);
 *		
 *		String result = report.getSingle("Furd->Scatter->Color");
 *		System.out.println("Result: "+result);
 *		
 *		Set<String> resultSet = report.getSet("Furd->Model");
 *		System.out.println("Result: "+resultSet);
 *		
 *	} catch (IOException e) {
 *		e.printStackTrace();
 *	}
 * </code>
 * 
 * See also {@link ReportReader#getSingle(String) or {@link ReportReader#getSet(String)} for usage
 * See also {@link ReportReader#ReportReader(String, String, String)} for constructor dataPath details</pre>
 * </pre>
 */
public class ReportReader {

	
	public final String MAP_SEPARATOR = "->";
	public final String DATA_SEPARATOR = ",";
	
	private final String NO_DATA_FOUND = "No data found. Collected data don't contains requested data. Please compare ReportReader dataPath with get method dataPath.";
	private final String ILLEGAL_DATAPATH = "Constructor dataPath contains headers that don't exists in report";
	
	private Object report;
	
	/**
	 * 
	 * @param filePath - system path to .xls file
	 * @param sheetName - the name of the sheet that data comes from
	 * @param dataPath - Map oriented path to data. 
	 * 	<pre>	Consists of name of headers splited by '->' separator.
	 *	Last section can have multiple data headers specified, separated by ',' separator, 
	 *	used to receive multiple data with one ReportReader instance
	 *	'*' symbol is used to map every column 
	 * 	
	 *  Example:
	 *  	Report:
	 *  		Brand	Model	Color	Production Year	Price
	 *  		Furd	Siesta	Blue	2002		10000
	 *  		Furd	Scatter	Black	2008		33000
	 *  	dataPath:
	 *  		Brand->Model->Production Year->Price
	 *  		Brand->Model->Price,Color
	 *  		Brand->&#42; (Brand,Model,Color,Production Year,Price)
	 *  		Brand
	 *
	 *  
	 *  See also {@link ReportReader#getSingle(String) or {@link ReportReader#getSet(String)} for usage
	 *  </pre>	
	 * @throws IOException - Thrown by File opening
	 * 
	 */
	public ReportReader(String filePath, String sheetName, String dataPath) throws IOException{
		
		initializeReport();
		
		HSSFSheet sheet = getSheet(filePath, sheetName);
		dataPath = translateAsterix(dataPath, sheet);
		Map<String, Integer> columnIndexes = initializeColumnIndex(dataPath, sheet);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for(int i = 1 ; i < rowCount; i++ ){ //Skipping first row - its header row
			HSSFRow row = sheet.getRow(i);
			loadRowToReport(dataPath, row, report, columnIndexes);
		}
		sheet.getWorkbook().close();
	}


	/**
	 * Report Initialization
	 */
	private void initializeReport() {
		report = new HashMap<String, Object>();
	}

	/**
	 * Creates HSSFSheet from filePath and sheetName
	 * @param filePath - path to .xls file
	 * @param sheetName - name of sheet to be opened
	 * @return opened sheet
	 * @throws IOException on bad filePath and sheetName
	 */
	private HSSFSheet getSheet(String filePath, String sheetName) throws IOException{
	
		FileInputStream file = new FileInputStream(filePath);
		@SuppressWarnings("resource")
        HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		
		return sheet;
	}
	

	private String translateAsterix(String dataPath, HSSFSheet sheet) {
		if(dataPath.contains("*")){
			HSSFRow row = sheet.getRow(0);//Header row
			
			String columns = "";
			
			for(Cell cell : row){
				String cellValue = cell.getStringCellValue();
				columns = columns+","+cellValue;
			}
			columns = columns.substring(1);
			
			String result = dataPath.replaceAll("\\*", columns);
			return result;
		}else{
			return dataPath;
		}
	}
	
	/**
	 * Creates header name to column index map
	 * @param dataPath - Map oriented path to data
	 * @param sheet - opened sheet
	 * @return header name to column index map
	 */
	private Map<String, Integer> initializeColumnIndex(String dataPath, HSSFSheet sheet) {
		Map<String, Integer> columnIndexes = new HashMap<String, Integer>();
		HSSFRow row = sheet.getRow(0);//Header row
		
		Set<String> columns = new HashSet<String>(Arrays.asList(dataPath.split(MAP_SEPARATOR+"|"+DATA_SEPARATOR)));
		
		for(Cell cell : row){
			String cellValue = cell.getStringCellValue();
			if(columns.contains(cellValue)){
				columnIndexes.put(cellValue, cell.getColumnIndex());
			}
		}
		if(columnIndexes.keySet().size() != columns.size()){
			throw new RuntimeException(new IllegalArgumentException(ILLEGAL_DATAPATH));
		}
		
		return columnIndexes;		
	}
	
	/**
	 * Recursive algorithm to populate reportObject
	 * @param dataPath - Map oriented path to data
	 * @param row - row which is to be added to report
	 * @param reportObject - Map to be populated by this method.
	 * @param columnIndexes - header name to column index map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadRowToReport(String dataPath, HSSFRow row, Object reportObject, Map<String, Integer> columnIndexes) {
		String[] columns = dataPath.split(MAP_SEPARATOR);
		if(columns.length > 1){ //Create Map - dataPath contains column headers to be maped
			String rowData = getCellToStringValue( row.getCell( columnIndexes.get(columns[0]) ) );//Get data at row for column
			dataPath = dataPath.substring( (columns[0]+MAP_SEPARATOR).length() );//Cut off data that was parsed

			if( !((Map)reportObject).containsKey(rowData) ){
				((Map)reportObject).put(rowData, new HashMap<String, Object>() );
			}

			loadRowToReport(dataPath, row, ((Map)reportObject).get(rowData), columnIndexes );

		}else{ //Create List - dataPath contains only requested data headers
			String[] dataColumns = columns[0].split(DATA_SEPARATOR);
			Map<String, List<String>> data = (Map<String, List<String>>)reportObject;
			for(String dataColumn : dataColumns){
				String rowData = getCellToStringValue( row.getCell( columnIndexes.get(dataColumn) ) );//Get data at row for column
				if( !data.containsKey(dataColumn) ){
					List<String> s = new ArrayList<String>();
					data.put(dataColumn,s);
				}
				data.get(dataColumn).add(rowData);
			}
			
		}
	}
	
	/**
	 * 
	 * @param cell - cell to be parsed
	 * @return string value of cell
	 */
	private String getCellToStringValue(Cell cell){
		switch(cell.getCellType()){
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			double val =cell.getNumericCellValue();
			if(val == Math.floor(val) && !Double.isInfinite(val)){
				return String.valueOf((int) val);
			}
			
			return String.valueOf(val);
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_ERROR:
			return "cellError";
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() == true ? "true" : "false";
		case Cell.CELL_TYPE_BLANK:
			return "";
		}
		return null;
	}

	/**
	 * Used to read data set
	 * @param dataPath - Map oriented path to data.
	 * <pre>	Consists of name of headers splited by '->' separator.
	 *	For constructor dataPath: 'Brand'
	 *	this dataPath can look like: 
	 *		'Brand' to receive set o Brands[Furd, Furd]
	 *
	 *
	 *	See also {@link ReportReader#ReportReader(String, String, String)} for constructor dataPath details</pre>
	 * @return List of String with value of column specified by dataPath
	 * @throws IllegalArgumentException when dataPath is incorrect
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getList(String dataPath){
		
		Object data =  new HashMap<String, Object>();
		((Map)data).putAll( (Map)this.report );
		
		String[] columns = dataPath.split(MAP_SEPARATOR);
		try{
			for( String column : columns ){
				data = ((Map)data).get(column);
			}
	
			return new ArrayList<String>((List)data);
		}catch(NullPointerException e){
			throw new RuntimeException(new IllegalArgumentException(NO_DATA_FOUND));
		}
	}
	
	/**
	 * Used to read data set
	 * @param dataPath - Map oriented path to data.
	 * <pre>	Consists of name of headers splited by '->' separator.
	 *	For constructor dataPath: 'Brand'
	 *	this dataPath can look like: 
	 *		'Brand' to receive set o Brands[Furd]
	 *
	 *	For constructor dataPath: 'Brand->Model'
	 *	this dataPath can look like: 
	 *		'Furd->Model' to receive set of Models for Brand[Siesta,Scatter]	
	 *
	 *	See also {@link ReportReader#ReportReader(String, String, String)} for constructor dataPath details</pre>
	 * @return Set of String with value of column specified by dataPath
	 * @throws IllegalArgumentException when dataPath is incorrect
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<String> getSet(String dataPath){
			return new HashSet<String>( getList(dataPath) );
	}
	
	/**
	 * Used to read only single data
	 * @param dataPath - Map oriented path to data.
	 * <pre>	Consists of name of headers splited by '->' separator.
	 *	For constructor dataPath: 'Brand->Model->Price,Color'
	 *	this dataPath can look like: 
	 *		'Furd->Siesta->Price' to receive Price[10000] for Brand[Furd] and Model[Siesta]
	 *		'Furd->Scatter->Color' to receive Color[Black] for Brand[Furd] and Model[Scatter]
	 *
	 *	See also {@link ReportReader#ReportReader(String, String, String)} for constructor dataPath details</pre>
	 * @return String with value of cell specified by dataPath
	 * @throws IllegalArgumentException when dataPath is incorrect
	 */
	public String getSingle(String dataPath){	
		return getList(dataPath).get(0);
	}
	
}
