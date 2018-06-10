package Conn;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DHTServer {
	static boolean server_status = false;
	public static void run() throws RemoteException, NamingException {
		LocateRegistry.createRegistry(1099);
		DHTService dser = new DHTServiceImpl();
		Context namingcontext = new InitialContext();
		namingcontext.rebind("rmi://localhost:1099/DHTService", dser);
		System.out.println("regist sucessful");
	}
}
