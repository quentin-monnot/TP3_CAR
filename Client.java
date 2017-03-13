import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	
	static Scanner sc = new Scanner (System.in);
	
	static FileStorageStub fss;
	static boolean close;
	
	public static void main(String[]args){
		try {
			fss = new FileStorageStub(InetAddress.getLocalHost(),4000);
			close = false;
			while (!close){
				String request = sc.nextLine();
				decode(request);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void decode(String request) {
		String[] array = request.split(" ");
		if (array.length != 0){
			switch (array[0]){
				case "GET":
					if(array.length != 2){
						System.out.println("Erreur de saisie");
					}else{
						System.out.println(fss.get(array[1]));
					}
					break;
				case "LIST":
					if(array.length != 1){
						System.out.println("Erreur de saisie");
					}else{
						System.out.println(fss.list());
					}
					break;
				case "CLOSE":
					if(array.length != 1){
						System.out.println("Erreur de saisie");
					}else{
						fss.close();
						close = true;
					}
					break;
				default:
					System.out.println("Erreur de saisie");
					
			}
		}
		
		
	}
}