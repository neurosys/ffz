
import java.util.ArrayList;

class SplittedText {
	String original;
	ArrayList<String> words;
	
	public SplittedText(String s) {
		this.original = s;
		words = new ArrayList<String>();
	}
	
	public void SplitText(String separator) {
		String tmp = original;

		int begining_of_extn = 0;
		// Remove extension
		try {
			begining_of_extn = tmp.lastIndexOf('.');
			if (begining_of_extn != -1) {
				tmp = tmp.substring(0, begining_of_extn);
			}
		}
		catch (Exception e) {
			System.out.println(original);
			System.out.println(begining_of_extn);
			throw e;
		}
		
		tmp = tmp.replaceAll("[-_,.:;'\"+&$`~=()]+", " ");
		tmp = tmp.replaceAll(" +", " ");
		String[] tmp_words = tmp.toLowerCase().split(separator);
		
		for (int i = 0; i < tmp_words.length; i++) {
			boolean found = false;
			for (int j = 0; j < tmp_words.length && found == false; j++) {
				if (i != j && tmp_words[i].equals(tmp_words[j])) {
					found = true;
				}
			}
			
			if (!found) {
				words.add(tmp_words[i]);
			}

		}
	}
	
	public void Print() {
		System.out.println("Original = " + original + "'");
		for(String word : words) {
			System.out.println("    " + word);
		}
	}
	
	public int Find(String s) {
		int occurences = 0;
		for (String word : words) {
			if (word.equalsIgnoreCase(s) == true) {
				occurences++;
			}
		}
		return occurences;
	}
	
	public float Compare(SplittedText st) {
		int charaters_matching = 0;
		int total_characters_in_words = 0;
		for (String s : words) {
			total_characters_in_words += s.length();
			int nr_of_finds = st.Find(s);
			if (nr_of_finds > 0) {
				charaters_matching += s.length();
			}
			/*
			if (original.equals("Hesse, Hermann - Gertrud.epub")) {
				System.out.println("DEXTRACE:>> " + s);
				System.out.println("DEXTRACE:>> characters_matching = " + charaters_matching + " total_caracters_in_worrds = " + total_characters_in_words);
			}
			*/
		}
		return charaters_matching / (float)total_characters_in_words;
	}
	
	public float TwoWayCompare(SplittedText a, SplittedText b) {
		float a_val = a.Compare(b);
		float b_val = b.Compare(a);

		if (a_val > b_val) {
			return b_val;
		}
		return a_val;
	}
}

public class FuzzyMatch {

	public static void main(String[] args) {
		SplittedText st = new SplittedText("Ana are mere mari si frumoase.jpg");
		st.SplitText(" ");
		st.Print();

		SplittedText st2 = new SplittedText("Dana are  pere medii dar frumoase.pdf");
		st2.SplitText(" ");
		st2.Print();
		
		float procent = st2.Compare(st);
		System.out.println("Procent echivalenta " + procent + "%");
	}

}
