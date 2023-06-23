package com.ngs.model.reportcheck.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import  com.fasterxml.jackson.xml.XmlMapper;

public class ReportCheckClient {
	
	public static void main2(String[] args) throws IOException {
		String Input_XML_String = new String(Files.readAllBytes(Paths.get("input.txt")));
		JSONObject json = new JSONObject(Input_XML_String);
		System.out.println("Json Req. :"+json);
		String xml = XML.toString(json);
		System.out.println("xml:="+xml);
		System.out.println("=================");
		JSONObject json2 = XML.toJSONObject(xml);
		System.out.println("resp : "+json2);
		
	}

	public static void main(String[] args) throws IOException {

		ReportCheckWSProxy checkWSProxy = new ReportCheckWSProxy();

		try {

			String Input_XML_String = new String(Files.readAllBytes(Paths.get("input.txt")));
			System.out.println(
					"---********************************---Input XML String---********************************");
			System.out.println(Input_XML_String);

			String Output_XML = checkWSProxy.processRequest(Input_XML_String);
			System.out.println(
					"---********************************---Output XML String---********************************");
			System.out.println(Output_XML);

			byte[] downloadPDFReport = checkWSProxy.downloadPDFReport(Input_XML_String);
			if (downloadPDFReport != null) {

				OutputStream out = new FileOutputStream("out.pdf");
				out.write(downloadPDFReport);
				System.out.println("downloadPDFReport=====" + downloadPDFReport);

			}
			System.out.println("Exit");
			
			JSONObject xmlJSONObj = XML.toJSONObject(Output_XML);
//	        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
	        System.out.println(xmlJSONObj);
	        
	        ObjectMapper jsonMapper = new ObjectMapper();
	        ReportCheckClient foo = jsonMapper.readValue(xmlJSONObj.toString(), ReportCheckClient.class);

	        XmlMapper xmlMapper = new XmlMapper();
	        System.out.println(xmlMapper.writeValueAsString(foo));
	        
			

			// Output_XML =
			// checkWSProxy.getColoredStonePearlResults(Input_XML_String);
			// System.out.println("Output_XML====" + Output_XML);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
