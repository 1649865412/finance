package com.utils.excel.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.model.InternalSheet;
import org.apache.poi.hssf.record.DVRecord;
import org.apache.poi.hssf.record.aggregates.DataValidityTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadTest {
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		String templatepath = "d:/ymx.xlsm";
		String ft = "d:/read.txt";
		File file = new File(templatepath);
		
		try {
			File filet = new File(ft);
			 
			InputStream inputStream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			XSSFSheet sheetHidden = workbook.getSheet("Hidden");
			int length = sheetHidden.getPhysicalNumberOfRows();
			List<KeyValue> kvs = new ArrayList<KeyValue>();
			for (int k = 0; k < length; k++) {

				Row rz = sheetHidden.getRow(k);
				Cell key1 = rz.getCell(0);
				Cell v1 = rz.getCell(1);
				KeyValue kv = new KeyValue(key1.getStringCellValue(), v1
						.getStringCellValue(), null);
				kvs.add(kv);
			}
			Map<String, String> mp = mapList();
			Set<String> sets = mp.keySet();
			FileOutputStream fo = new FileOutputStream(filet);
			for (String st : sets) {
				for (KeyValue kv : kvs) {
					if (kv.getKey().equals(st)) {
						String pro = mp.get(st);
						String out = "INSERT INTO  submit_report_amazon_fieldvalue ";
						out += "(field_value, ";
						out += "field_id";
						out += ")";
						out += "VALUES";
						out += "(  ";
						out += "'" + kv.getValue() + "', ";
						out += "'" + pro + "'  ";
						out += ");";
						fo.write(out.getBytes());
						System.out.println(out);
					}
				}
			}
			fo.close();
			
			/***
			List<XSSFDataValidation> list = sheet1.getDataValidations();
			for (XSSFDataValidation xssf : list) {
				DataValidationConstraint dvc = xssf.getValidationConstraint();
				String[] sv = dvc.getExplicitListValues();
				if (sv != null && sv.length > 0) {
					for (int i = 0; i < sv.length; i++) {
						if (sv[i] != null && sv[i].startsWith("Hidden")) {
							String tmpid = sv[i].replace("Hidden", "");
							String[] sarr = tmpid.split("_");
							for (String sk : sarr) {
								if (sk == null) {
									continue;
								}
								for (KeyValue kv : kvs) {
									if (kv.getKey().equals(sk)) {
										System.out
												.println("k:" + kv.getValue());
									}
								}
							}
						}

					}
				}
				**/
				// System.out.println(":"+dvc.getFormula1()+":"+dvc.getFormula2()+","+dvc.getExplicitListValues());
				/***
				 * CellRangeAddressList crl = xssf.getRegions();
				 * CellRangeAddress[] crs = crl.getCellRangeAddresses();
				 * for(CellRangeAddress cl : crs){
				 * 
				 * }
				 ***/

			//}
			inputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Map<String, String> mapList() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("84550", "purchaserManager");
		map.put("53050", "supplierNameCode");
		map.put("53052", "brandName");
		map.put("61561", "brandType");
		map.put("53066", "scanBarCode");

		map.put("53067", "barCodeType");
		map.put("53078", "productType");
		map.put("53061", "productSecondCategory");
		map.put("62583", "internalPhysicalState");

		map.put("65578", "pcRelationshipType");
		map.put("53089", "producingAreaForeign");
		map.put("53572", "producingAreaChina");
		map.put("61565", "suitablePerson");
		map.put("62054", "suitableSkin");
		map.put("62055", "suitableHair");
		map.put("61566", "limitDate");

		map.put("61567", "expirationDate");
		map.put("53573", "searchKeyFunction");
		map.put("53574", "searchKeyMin");
		map.put("61572", "somePackageNum");
		map.put("62058", "somePackageUnit");
		map.put("64066", "deleveryPackage");
		return map;

	}
}

class KeyValue {
	private String key;
	private String value;
	private String parent;

	public KeyValue(String key, String value, String parent) {

		this.key = key;
		this.value = value;
		this.parent = parent;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
