import java.io.File;

public class FileList {
	String path;
	File root;
	File[] list;
	
	public FileList(String path) {
		this.path = path;
		root = new File(path);
		list = root.listFiles();
	}
	
	// Display the content of the given directory
	// return all the files' name
	public String getList() {
		String files = "";
		for (File f : list) {
			if (f.isDirectory()) {
//				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
//				System.out.println("File:" + f.getAbsoluteFile());
				files += f.getName()+"\n";
			}
		}
		return files;
	}
	public String getList(String name) {
		String files = "";
		for (File f : list) {
			if (f.isDirectory()) {
//				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
//				System.out.println("File:" + f.getAbsoluteFile());
				if(f.getAbsolutePath().contains(name))
					files = f.getAbsolutePath();
			}
		}
		return files;
	}
	// return a full path to the file
	public String getFile(int index){
		int length = list.length;
		if(index <0 || index >=length){
			System.out.println("invalid index, please check input");
			return null;
		}
		if(list[index].isDirectory()){
			System.out.println("Folder is not supported, please choose a file");
			return null;
		}
		return list[index].getAbsolutePath();
	}
}
