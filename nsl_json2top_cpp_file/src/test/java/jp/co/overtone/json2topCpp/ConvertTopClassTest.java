package jp.co.overtone.json2topCpp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConvertTopClassTest {
	CnvertTopFromJson tmp ;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testReadTest(){

		tmp = new CnvertTopFromJson("src/test/resources/test.json");
		tmp.allRead();
	}
	
	@Test
	public void testTest_run_alt_test_run() {
		tmp = new CnvertTopFromJson("src/test/resources/test.json");
		try {
			ArrayList<NSLElement> array = tmp.createNSLElementArray();
			System.out.println(array);
		} catch (ConvertTopClassException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateCppHaderIncludeSection() {

		ConvertTopClass tmp = new ConvertTopClass("src/test/resources/test.json");
		String str = tmp.createCppHaderIncludeSection();
		System.out.println("---out\n"+str+"\n---end");
	}

	@Test
	public void testCreateCppHaderMethodSection() {

		ConvertTopClass tmp = new ConvertTopClass("src/test/resources/test.json");
		String str = tmp.createCppHaderMethodSection();
		System.out.println("---out\n"+str+"\n---end");
	}
}
