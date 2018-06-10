package Conn;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;

public class SocketsPool {
	private static CopyOnWriteArraySet<WebSocketTest> sockets= new CopyOnWriteArraySet<WebSocketTest>();
	public static void addSocket(WebSocketTest w) {
		sockets.add(w);
	}
	public static void eraseSocket(WebSocketTest w) {
		sockets.remove(w);
	}
	public static Vector<WebSocketTest> getSockets(String type) {
		Vector<WebSocketTest> vec = new Vector<WebSocketTest>();
		System.out.println(sockets.size());
		for(WebSocketTest w : sockets) {
			for(String t:w.types)
				if(t.equals(type))
					vec.add(w);
		}
		return vec;
	}
	public static boolean contains(WebSocketTest w) {
		return sockets.contains(w);
	}
}
