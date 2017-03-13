import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerSkel {
	private static ServerSocket ss;

	public static void main(String[]args){
		FileStorage fileS = new FileStorage();
		try {
			ss = new ServerSocket(4000);
			while(true){
				Socket s = ss.accept();
				System.out.println("Connection established");
				boolean close = false;
				while (!close){
					Protocol p = new Protocol(s);
					String fonc = p.readRequestHeader();
					switch (fonc){
						case "GET":
							String param = p.readParamString();
							System.err.println(param);
							String get = fileS.get(param);
							if (get == null){
								p.writeResponseHeader(-1);
								p.endMessage();
							}else{
								p.writeResponseHeader(0);
								p.writeResponseBody(get);
								p.endMessage();
								p.uploadFile(fileS.stored.get(param).getFile());
							}
							
							break;
						case "LIST":
							String list = fileS.list();
							p.writeResponseHeader(0);
							p.writeResponseBody(list);
							p.endMessage();
							break;
						case "CLOSE":
							close = true;
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}