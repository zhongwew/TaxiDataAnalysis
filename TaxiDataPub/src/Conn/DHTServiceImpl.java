package Conn;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class DHTServiceImpl extends UnicastRemoteObject implements DHTService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DHTServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public void sendMessage(String type, String data) {
		Vector<WebSocketTest> vec = SocketsPool.getSockets(type);
		System.out.println(vec.size());
		for(WebSocketTest w : vec)
			try {
				w.sendMessage(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
