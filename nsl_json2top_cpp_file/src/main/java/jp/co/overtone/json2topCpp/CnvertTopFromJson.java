package jp.co.overtone.json2topCpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

public class CnvertTopFromJson {

	protected String className = "TestTopClass";
	protected FileReader file;
	protected ArrayList<NSLElement> NSLElementArray = new ArrayList<NSLElement>();
	protected File path;

	public CnvertTopFromJson() {
		super();
	}

	public CnvertTopFromJson(String fileName) {
		try {
			file = new FileReader(fileName);
			createNSLElementArray();
			file = new FileReader(fileName);
			path = new File("./");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConvertTopClassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<NSLElement> createNSLElementArray() throws ConvertTopClassException {
		ArrayList test;
		try {
	
			test = (ArrayList) JSON.decode(file, Object.class);
		} catch (JSONException e) {
			throw new ConvertTopClassException(
					"ExpectedsFileがJsonのフォーマットではありません\n" + e.getMessage(),
					false);
		} catch (FileNotFoundException e) {
			throw new ConvertTopClassException("ExpectedsFileがファイルがありません\n"
					+ e.getMessage(), false);
		} catch (IOException e) {
			throw new ConvertTopClassException("ExpectedsFileがファイルが読み込めません\n"
					+ e.getMessage(), false);
		}
	
		for (int i = 0; i < test.size(); i++) {
			LinkedHashMap true_data = (LinkedHashMap) test.get(i);
	
			String name = (String) true_data.get("name");
	
			if (!name.isEmpty()) {
				BigDecimal k = (BigDecimal) true_data.get("type");
				BigDecimal j = (BigDecimal) true_data.get("width");
				NSLElement tmp = new NSLElement(name, k.intValue(),
						j.intValue());
	
				NSLElementArray.add(tmp);
			}
	
		}
		return NSLElementArray;
	}

	public void allRead() {
		BufferedReader reader = new BufferedReader(file);
		try {
			while (reader.ready()) {
				System.out.println(reader.readLine());
				// file.reset();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}