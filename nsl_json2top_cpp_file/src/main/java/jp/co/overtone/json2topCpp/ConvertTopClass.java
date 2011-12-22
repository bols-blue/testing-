package jp.co.overtone.json2topCpp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ConvertTopClass extends CnvertTopFromJson implements
		NSLConvertorConst {
	private String memberVariable = null;
	private String memberMethod = null;

	public String createCppHaderIncludeSection() {
		String cppHaderIncludeSection = "#ifndef __" + className.toUpperCase()
				+ "_H__\n" + "#define __" + className.toUpperCase() + "_H__\n"
				+ "#include <systemc.h>\n" + "#include <stdio.h>\n"
				+ "#include <stdlib.h>\n" + "#include <stdint.h>\n\n";

		return cppHaderIncludeSection;
	}

	public String createCppHaderMethodSection() {
		if (memberVariable == null) {
			this.setMemberMethod();
		}
		if (memberMethod == null) {
			this.setMemberVariable();
		}
		String cppHaderMethodSection = "class " + className
				+ "{\n//base mathods\n" + "public :\n" + memberVariable + "\t"
				+ "sc_trace_file *tf;\n\t" + className + "();\n\t" + "~"
				+ className + "();\n" + memberMethod +
				/*
				 * sc_signal<sc_uint<8> > send_data; sc_signal<sc_uint<8> >
				 * resv_data; sc_signal<sc_uint<1> > MOSI; sc_signal<sc_uint<1>
				 * > MISO; sc_signal<sc_uint<1> > SS; sc_signal<sc_uint<1> >
				 * SCLK; sc_signal<sc_uint<1> > send_w; sc_signal<sc_uint<1> >
				 * read_MISO_w; sc_signal<sc_uint<1> > write_MOSI_w; void
				 * nextStep(); void send(); char read_MISO(); void reset(); void
				 * write_MOSI(char send_data);
				 */

				"};\n" + "#endif /* __" + className.toUpperCase() + "_H__ */";
		return cppHaderMethodSection;
	}

	public void setMemberVariable() {
		String tmpout = "";
		for (int i = 0; i < NSLElementArray.size(); i++) {
			NSLElement element = NSLElementArray.get(i);
			tmpout += "\tsc_signal<sc_uint<" + element.getWidth() + "> > "
					+ "_target_" + element.getName() + ";\n";
		}
		this.memberVariable = tmpout;
	}

	public void setMemberMethod() {
		String tmpout = "";
		for (int i = 0; i < NSLElementArray.size(); i++) {
			NSLElement element = NSLElementArray.get(i);
			if (FUNCIN == element.getType()) {
				tmpout += "\tvoid " + element.getName() + ";\n";
			} else if (FUNCOUT == element.getType()) {
				tmpout += "\tvoid " + element.getName() + ";\n";
			}
		}
		this.memberMethod = tmpout;
	}

	public void createNSLTopHeaderFile() throws IOException {
		createNSLTopHeaderFile(path);
	}

	public void createNSLTopHeaderFile(File path) throws IOException {
		File file;
		if (path.isDirectory()) {
			file = new File(path.getPath() + "/" + className + ".h");
		} else {
			file = path;
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(file)));
		pw.print(createCppHaderMethodSection());
		pw.close();
	}

	public ConvertTopClass(String fileName) {
		super(fileName);
		try {
			file = new FileReader(fileName);
			setMemberMethod();
			setMemberVariable();
			path = new File("./");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}