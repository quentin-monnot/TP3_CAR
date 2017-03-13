import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class FileStorageStub implements FileStorageInterface{
	
	public Protocol prot;
	
	public FileStorageStub (InetAddress addr,int port) throws IOException{
		Socket sock = new Socket(addr,port);
		prot = new Protocol(sock);
	}
	
	public String get(String fileName) {
		
		prot.writeResquestHeader("GET");
		prot.writeParamString(fileName);
		prot.endMessage();
		int code = prot.readResponseHeader();
		if (code == 0){
			File download = new File(fileName);
			try {
				download.createNewFile();
				String result =  prot.readResponseBody();
				prot.downloadFile(download);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return "ERR 400 \nFile does not exist";
		}
	}

	public String list() {
		prot.writeResquestHeader("LIST");
		prot.endMessage();
		prot.readResponseHeader();
		return prot.readResponseBody();
	}
	
	public void close(){
		prot.writeResquestHeader("CLOSE");
		prot.endMessage();
	}

}