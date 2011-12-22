package jp.co.overtone.json2topCpp;

public interface NSLConvertorConst {
	static final int OUTPUT = 2;
	static final int FUNCIN = 4;
	static final int FUNCOUT = 5;
//	#include "SPI_controler.sc"
	static final String NSL_CPP_INCLUDE_FILES = "#include <stdio.h>\n#include <stdlib.h>\n#include <stdint.h>\n";
	static final String NSL_CPP_TOP = "template<typename T> int _nsl_readmem(T array[], const char *file, int start, int end, int sft) ;\n" +
	"sc_clock m_clock(\"m_clock\",10, SC_NS, 0.5,0, SC_NS, false);\n" +
	"sc_signal<bool> p_reset;\n" +
//	sc_signal<sc_uint<8> > send_data;
//	sc_signal<sc_uint<8> > resv_data;

//	spi_controler spi_controler("spi_controler");
// 
	"static int ctrl_clock=0;\n"+
	"SC_MODULE (c_clock) {\n"+
	"\t"+"sc_in<bool> m_clock;\n"+
	"\t"+"void do_reset() {\n"+
	"\t\t"+"ctrl_clock++;\n"+
	"\t\t"+"if(ctrl_clock<2) p_reset=1;\n"+
	"\t\t"+"else p_reset=0;\n"+
	"\t"+"}\n"+
"\t"+"SC_CTOR(c_clock) {\n"+
	"\t\t"+"SC_METHOD(do_reset);\n"+
	"\t\t"+"sensitive << m_clock.pos();\n"+
	"\t"+"}\n"+
	"};\n"+

		"template<typename T> int _nsl_readmem(T array[], const char *file, int start, int end, int sft) {\n"+
		"\tFILE *txt;\n"+
		"\tuint64_t addr,data;\n"+
		"\tint i,bit,c,lastch,incomm;\n"+
		"\tif((txt=fopen(file,\"r\"))==NULL) {\n"+
		"\t\treturn(1);\n"+
		"\t}\n"+
		"\taddr = start;\n"+
		"\tdata =  0;\n"+
		"\tlastch=0;\n"+
		"\tincomm=0;\n"+
		"\twhile((c=fgetc(txt))!=EOF) {\n"+
		"\t\tunsigned int ch;\n"+
		"\t\tif(incomm) {\n"+
		"\t\t\tif(c=='\n') incomm=0;\n"+
		"\t\t\tcontinue;\n"+
		"\t\t}\n"+
		"\t\tif(c=='#') {incomm++; continue;}\n"+
		"\t\tif(c=='_' || c=='\r' ) continue;\n"+
		"\t\tif(!lastch && (c==' ' || c=='\t' || c=='\n') ) continue;\n"+
		"\t\tlastch=c;\n"+
		"\t\tch = c - 97;\n"+
		"\t\tif(sft==4 && ch<6) {\n"+
		"\t\t\tdata=(data << sft);\n"+
		"\t\t\tdata = (data + ch + 10);\n"+
		"\t\t}else if(sft==4 && ch+32 < 6) {\n"+
		"\t\t\tdata=(data << sft);\n"+
		"\t\t\tdata = (data + ch + 32 + 10);\n"+
		"\t\t}else if(sft==4 && ch+49 < 10) {\n"+
		"\t\t\tdata=(data << sft);\n"+
		"\t\t\tdata = (data + ch + 49);\n"+
		"\t\t}else if(sft==1 && ch+49 < 2) {\n"+
		"\t\t\tdata=(data << sft);\n"+
		"\t\t\tdata = (data + ch + 49);\n"+
		"\t\t}else {\n"+
		"\t\t\tarray[addr] =  data;\n"+
		"\t\t\taddr++;\n"+
		"\t\t\tif(addr>=end) break;\n"+
		"\t\t\tlastch=0;\n"+
		"\t\t\tdata=0;\n"+
		"\t\t\tcontinue;\n"+
		"\t\t}\n"+
		"\t}\n"+
		"\tfclose(txt);\n"+
		"\treturn 0;\n"+
		"}\n"+
		"void _nsl_hook(char *file, int line, int attrib, const char *name) {}\n"+
		"\n";
//		"#include "SPI_controler_top.h""+

	
}