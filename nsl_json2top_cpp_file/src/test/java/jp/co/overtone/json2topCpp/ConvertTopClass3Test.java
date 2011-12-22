package jp.co.overtone.json2topCpp;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConvertTopClass3Test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	private File path;

	@Before
	public void setUp() throws Exception {

		path = new File("target/output/");
	}

	@Test
	public void testCreateNSLTopHeaderFile() {
		ConvertTopClass tmp = new ConvertTopClass("src/test/resources/test.json");
		try {
			tmp.createNSLTopHeaderFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateNSLTopSourceFile() {

		ConvertTopClassSocecode tmp = new ConvertTopClassSocecode("src/test/resources/test.json");
		try {
			tmp.createNSLTopSourceFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateCppSourceSection() {
		ConvertTopClassSocecode tmp = new ConvertTopClassSocecode("src/test/resources/test.json");
		String str = tmp.createCppSourceSection();
		System.out.println("---out\n"+str+"\n---end");	}

}
