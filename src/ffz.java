import java.io.File;
import java.util.*;

class DirTree {
	public enum FileType {FileEntry, DirEntry};
	FileType type;
	String name;
	public ArrayList<DirTree> children;
	public DirTree(FileType type, String name) {
		this.type = type;
		this.name = name;
		this.children = new ArrayList<DirTree>();
	}
	
	public DirTree AddChild(FileType t, String name) {
		DirTree new_leaf = new DirTree(t, name);
		children.add(new_leaf);
		return new_leaf;
	}

	public DirTree AddChild(DirTree t) {
		children.add(t);
		return t;
	}
	
	private void PrintLineStart(int indentation) {
		for (int i = 0; i < indentation; i++) {
			System.out.print("    ");
		}
	}
	
	public void Print() {
		Print(0);
	}

	public void Print(int indentation) {
		if (type == FileType.DirEntry) {
			System.out.println(name + "/");
		}
		else {
			System.out.println(name);
		}
		indentation++;
		for (DirTree dt : children) {
			if (dt.type == FileType.DirEntry) {
				PrintLineStart(indentation);
				dt.Print(indentation);
			}
		}
		
		for (DirTree dt : children) {
			if (dt.type == FileType.FileEntry) {
				PrintLineStart(indentation);
				System.out.println(dt.name);
			}
		}
	}
	
	public ArrayList<String> Flatten() {
		ArrayList<String> x = new ArrayList<String>();
		for (DirTree dt : children) {
			if (dt.type == FileType.FileEntry) {
				x.add(dt.name);
			}
			else {
				x.addAll(dt.Flatten());
			}
		}
		return x;
	}
}

public class ffz {
	
	public static void ListDir(File dir, DirTree tree) {
		for (File f : dir.listFiles()) {
			//System.out.println(f.getName());
			if (f.isDirectory()) {
				DirTree current_dir = tree.AddChild(DirTree.FileType.DirEntry, f.getName()); 
				ListDir(f, current_dir);
			}
			else {
				tree.AddChild(DirTree.FileType.FileEntry, f.getName());
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Salutare");
		
		File directory;
		float treshold = 20f;
		if (args.length == 0) {
			//directory = new File("./");
			directory = new File("/mnt/hdd/Downloads/Finished/Hesse, Herman");
		}
		else {
			directory = new File(args[0]);
			treshold = Float.parseFloat(args[1]);
		}
		
		treshold /= 100;
		DirTree dt = new DirTree(DirTree.FileType.DirEntry, ".");
		ListDir(directory, dt);
		System.out.println("================================================================");
		System.out.println("Folder = " + directory + "/ treshold = " + treshold);
		System.out.println("================================================================");
		ArrayList<String> files = dt.Flatten();
		ArrayList<SplittedText> splits = new ArrayList<SplittedText>();
		for(String s : files) {
			//System.out.println(s);
			SplittedText tmp = new SplittedText(s);
			tmp.SplitText(" ");
			splits.add(tmp);
		}
		
		for (SplittedText x : splits) {
			System.out.println(x.original);
			for (SplittedText y : splits) {
				float match_degree = x.TwoWayCompare(x, y);
				//System.out.println("    " + "(" + match_degree + "%" + ")" + " " + y.original);
				if (match_degree >= treshold) {
					if (! x.original.equals(y.original) ) { 
						System.out.println("    " + "(" + (match_degree * 100) + "%" + ")" + " " + y.original);
					}
				}
			}
		}
	}
}




















