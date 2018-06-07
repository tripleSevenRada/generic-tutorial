package cz.etn.etnshop.custom_editors;

import java.beans.PropertyEditorSupport;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public class DirtyWordsEditor extends PropertyEditorSupport implements StringEditor{

	@Value("${test.key}")
	String testProp;
	
	@Value("#{'${dirty_words}'.split(',')}")
	private List<String> dirtyWords;
		
	private String value;

	@Override
	public void setAsText(String text) {
		value = text;
	}
 
	// TODO pouziju otestovene edit distance problem solution
	@Override
	public String getAsText() {
		return processString(value) + "_dirty_words?";
	}
	
	public void testOutputDirtyWords() {
		System.out.println("test key value access: " + testProp);
		for(String s: dirtyWords) {
			System.out.println("dirtyWords: " + s);
		}
	}

	@Override
	public String processString(String value) {
		// TODO Auto-generated method stub
		return value;
	}

}
