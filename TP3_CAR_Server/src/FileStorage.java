import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class FileStorage implements FileStorageInterface {

	HashMap<String, FileServer> stored;

	public FileStorage() {

		stored = new HashMap<String, FileServer>();

		// File 1
		ArrayList<String> tagsF1 = new ArrayList<String>();
		tagsF1.add("Informatique");
		tagsF1.add("Anglais");
		tagsF1.add("Ordinateur");
		FileServer file1 = new FileServer("file1.txt", tagsF1);
		stored.put("file1.txt", file1);

		// File 2
		ArrayList<String> tagsF2 = new ArrayList<String>();
		tagsF2.add("Sport");
		tagsF2.add("Football");
		tagsF2.add("Zidane");
		FileServer file2 = new FileServer("file2.txt", tagsF2);
		stored.put("file2.txt", file2);

		// File 3
		ArrayList<String> tagsF3 = new ArrayList<String>();
		tagsF3.add("Recette");
		tagsF3.add("Cuisine");
		tagsF3.add("Tajine");
		FileServer file3 = new FileServer("file3.jpg", tagsF3);
		stored.put("file3.jpg", file3);

		// File 4
		ArrayList<String> tagsF4 = new ArrayList<String>();
		tagsF4.add("Film");
		tagsF4.add("Suspense");
		tagsF4.add("James Bond");
		FileServer file4 = new FileServer("file4.jpg", tagsF4);
		stored.put("file4.jpg", file4);

	}

	public String get(String fileName) {
		if (stored.containsKey(fileName)) {
			FileServer s = stored.get(fileName);
			StringBuilder sb = new StringBuilder();
			sb.append("FIC " + s.getFile().getName() + "\n");
			sb.append("TAGS: ");
			for (String tag : s.getTags()) {
				sb.append(tag);
				if (s.getTags().size() - 1 != s.getTags().indexOf(tag)) {
					sb.append(", ");
				} else {
					sb.append("\n");
				}
			}
			sb.append("TYPE: " + s.getType() + "\n");
			if (s.getType().equals("txt")) {
				BufferedReader br;
				try {
					br = new BufferedReader(new FileReader(s.getFile()));
					String line = null;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
						sb.append(line+"\n");
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}

			} else {

				byte[] bytes;
				try {
					bytes = loadFile(s.getFile());
					byte[] encoded = Base64.getEncoder().encode(bytes);
					String encodedString = new String(encoded);
					
					sb.append(encodedString+"\n");
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	public String list() {
		StringBuilder sb = new StringBuilder();
		sb.append("NUM " + stored.size() + " \n");
		for (String fileName : stored.keySet()) {
			sb.append(fileName + " \n");
			FileServer s = stored.get(fileName);
			for (String tag : s.getTags()) {
				sb.append(tag);
				if (s.getTags().size() - 1 != s.getTags().indexOf(tag)) {
					sb.append(" , ");
				} else {
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}
	

	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	}

}