package client;

import java.io.IOException;
import java.rmi.Remote;

public interface DHTService extends Remote {
	public void sendMessage(String type, String data) throws IOException;
}
