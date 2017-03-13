import java.io.File;
import java.util.ArrayList;


public class FileServer {

	private File file;
	private ArrayList<String> tags;
	private String type;
	
	public FileServer(String fileName, ArrayList<String> tags){
		file = new File (fileName);
		this.tags = tags;
		this.type = fileName.substring(fileName.length()-3);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
