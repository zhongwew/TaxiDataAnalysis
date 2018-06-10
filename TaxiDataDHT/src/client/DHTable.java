package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;

import client.DHTService;

public class DHTable {
	//used to store latest published data
	String dataPath;
	String DType;
	DHTable(String type, String path){
		dataPath = path;
		DType = type;
	}
	String parseData() throws IOException {
		JSONObject js = new JSONObject();
		JSONArray jarr = new JSONArray();
		BufferedReader br = new BufferedReader(new FileReader(dataPath));
		String tempstr = br.readLine();//skip the first line
		while((tempstr = br.readLine()) != null) {
			String[] props = tempstr.split("\t");
			JSONObject tempjs = new JSONObject();
			tempjs.put("id", Integer.parseInt(props[0]));
			tempjs.put("count", Integer.parseInt(props[1]));
		}
		js.put("type", "TaxiData");
		js.put("district", jarr);
		return js.toString();
		
	}
	static public void main(String[] args) throws NamingException, IOException, InterruptedException {
		
		String url = "rmi://localhost:1099/";
		Context ncontext = new InitialContext();
		DHTService ser = (DHTService)ncontext.lookup(url+"DHTService");
		System.out.println("client created");
		String filePath = "input file path here";
		DHTable dt = new DHTable(args[1],filePath);
		while(true) {
			//parseData after every 100 seconds
			ser.sendMessage(dt.DType, dt.parseData());
			Thread.sleep(10000);
		}
	}
}
