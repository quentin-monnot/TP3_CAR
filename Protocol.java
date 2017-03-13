import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;


public class Protocol{
	
	public Socket sock;
	public BufferedReader in;
	public DataOutputStream out;
	
	public Protocol (Socket s) throws IOException{
		sock = s;
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new DataOutputStream(s.getOutputStream());
	}
	
	public void writeResquestHeader(String fonctionName){
		try {
			out.writeBytes(fonctionName+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeResponseHeader(int code){
		try {
			if (code == 0){
				out.writeBytes("OK:\n");
			}else{
				out.writeBytes("ERR:\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readRequestHeader(){
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int readResponseHeader(){
		try {
			String responseHeader = in.readLine();
			System.out.println(responseHeader);
			if (responseHeader.equals("OK:")){
				return 0;
			}else{
				return -1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public void writeParamString(String param){
		try {
			out.writeBytes(param+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readParamString(){
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String readResponseBody(){
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			while (!(line = in.readLine()).equals("")){
				sb.append(line+"\n");
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeResponseBody(String body){
		try {
			out.writeBytes(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endMessage(){
		try {
			out.writeBytes("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadFile(File f){
		try {
			 ObjectInputStream inF = new ObjectInputStream(sock.getInputStream());
			 byte[] content = (byte[]) inF.readObject();
			 Files.write(f.toPath(), content);
			 
		} catch (IOException e) {
			System.out.println("ServeurFichier : Erreur de réception du fichier ----> " + e);
		} catch (ClassNotFoundException e) {
			System.out.println("Une erreur est survenue avec le fichier ----> " + e);
		}
	}
	
	public void uploadFile(File f){
		try{	
			ObjectOutputStream outF = new ObjectOutputStream(sock.getOutputStream());             
        	byte content[] = Files.readAllBytes(f.toPath());
        	outF.writeObject(content);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}