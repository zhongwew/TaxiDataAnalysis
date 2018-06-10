package Conn;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.naming.NamingException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONObject;


@ServerEndpoint("/websocket")

public class WebSocketTest {
	//define session which is used to send message to users
	Session session;
	Vector<String> types;
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        // Print the client message for testing purposes
        System.out.println("Received: " + message);
		types = new Vector<String>();
        JSONObject js = new JSONObject(message);
        JSONArray jarr= js.getJSONArray("district");
        for(int i=0; i< jarr.length(); i++)
        		types.add(jarr.getString(i));
		if(!SocketsPool.contains(this))
			SocketsPool.addSocket(this);
    }

    @OnOpen
    public void onOpen(Session session) throws RemoteException, NamingException {
    		if(!DHTServer.server_status)
    			DHTServer.run();//open the server when first connection comes
    		this.session = session;
        System.out.println("Client connected");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
        
    }
    
    public void sendMessage(String message) throws IOException {
    		System.out.println("message send: "+message);
    		session.getBasicRemote().sendText(message);
    }
    
}
