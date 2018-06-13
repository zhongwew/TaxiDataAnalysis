package Conn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;

import Conn.DHTService;

public class DHTable {
	//used to store latest published data
	String loPath;//the location file path
	String couPath; //the count file path
	String DType;
	DHTable(String type){
		DType = type;
	}
	Vector<Double> getCoordinate(int disNum) throws IOException{
		Vector<Double> vec = null;
		BufferedReader br = new BufferedReader(new FileReader(loPath));
		String tempstr = null;
		int counter = 0;
		while((tempstr = br.readLine()) != null) {
			if(counter == disNum)
				break;
		}
		double lot = Double.parseDouble(tempstr.substring(1, tempstr.indexOf(',')));
		double lat = Double.parseDouble(tempstr.substring(tempstr.indexOf(',')+1,tempstr.length()));
		vec.add(lot);
		vec.add(lat);
		return vec;
	}
	
	String parseData() throws IOException {
		//the output json
		JSONObject js = new JSONObject();
		JSONArray jarr = new JSONArray();
		//open the coordinate file
		BufferedReader br = new BufferedReader(new FileReader(couPath));
		String tempstr = null;
		while((tempstr = br.readLine()) != null) {
			JSONObject tempjs = new JSONObject(tempstr);
			if(tempjs.getInt("count") < 100) {
				int tempint = tempjs.getInt("count")*100;
				tempjs.remove("count");
				tempjs.put("count", tempint);
			}
			tempjs.put("coordinates", getCoordinate(tempjs.getInt("prediction")));
			jarr.put(tempjs);
		}
		js.put("type", "TaxiData");
		js.put("district", jarr);
		System.out.println(js.toString());
		return js.toString();
		
	}
	static public void main(String[] args) throws NamingException, IOException, InterruptedException {
		
		String url = "rmi://54.202.179.219:1099/";
		Context ncontext = new InitialContext();
		DHTService ser = (DHTService)ncontext.lookup(url+"DHTService");
		System.out.println("client created");
		String filePath = "/home/ubuntu/TaxiDataAnalysis/spark/";
		System.out.println("success");
		ser.sendMessage("Manhattan", "hahaha");
//		DHTable dt = new DHTable(args[1]);
//		int month_counter = 4;
//		int tag = 1;
//		switch(args[1]) {
//		case "Manhattan":
//			tag = 1; break;
//		case "Queen":
//			tag =4; break;
//		case "The Bronx":
//			tag = 2; break;
//		case "Brooklyn":
//			tag = 3; break;
//		}
//		while(true) {
//			//build the data path
//			dt.couPath = filePath+"KmResult"+tag+month_counter+"/"+"km"+tag+month_counter+".json";
//			dt.loPath = dt.couPath = filePath+"bz"+tag+month_counter+"/"+"bz"+tag+month_counter+".json";
//			//
//			ser.sendMessage(dt.DType, dt.parseData());
//			//parseData after every 100 seconds
//			Thread.sleep(10000);
//		}
	}
}
