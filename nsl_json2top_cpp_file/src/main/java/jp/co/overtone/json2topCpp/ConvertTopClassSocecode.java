package jp.co.overtone.json2topCpp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ConvertTopClassSocecode extends CnvertTopFromJson implements
		NSLConvertorConst {

	public ConvertTopClassSocecode(String fileName) {
		super(fileName);
		try {
			file = new FileReader(fileName);
			path = new File("./");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String createCppSourceSection() {
		String ret = NSL_CPP_INCLUDE_FILES;
		ret += NSL_CPP_TOP;
		ret += "void " + className + "::nextStep()"
				+ "{\n\tsc_start(100, SC_NS);\n}\n";

		for (int i = 0; i < NSLElementArray.size(); i++) {
			NSLElement element = NSLElementArray.get(i);
			if (FUNCIN == element.getType()) {
				ret += "void " + className + "::" + element.getName() + "()\n"
						+ "{\n\t" + "_target_" + element.getName() + " = 1;\n" + "}\n";
			} else if (FUNCOUT == element.getType()) {
				ret += "void " + className + "::" + element.getName()
						+ "(){}\n";
			}
		}
		return ret;
	}

	public void createNSLTopSourceFile() throws IOException {
		createNSLTopSourceFile(path);
	}

	public void createNSLTopSourceFile(File path) throws IOException {
		File file;
		if (path.isDirectory()) {
			file = new File(path.getPath() + "/" + className + ".cpp");
		} else {
			file = path;
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(file)));
		pw.print(createCppSourceSection());
		pw.close();

	}

}
