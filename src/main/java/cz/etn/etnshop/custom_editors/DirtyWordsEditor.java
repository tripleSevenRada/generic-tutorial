package cz.etn.etnshop.custom_editors;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

public class DirtyWordsEditor extends BaseCustomEditor implements CustomStringEditor{

    private static final String LOG_TAG = "DirtyWordsEditor: ";

    @Value("#{'${dirty_words}'.split(',')}")
    private List <String> dirtyWords;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println(LOG_TAG + "set : " + text);
        if (StringUtils.hasText(text)) {
            setValue(edit(text.trim()));
        } else {
            setValue(null);
        }
    }
 
    @Override
    public String getAsText() {
		Object value = getValue();
		return (value != null ? value.toString() : "");
    }
    
	@Override
	public String edit(String text) {
		// TODO 
		// FOR EACH singleDirtyWord : dirtyWords
		//     sunout uvnitr textu substring o delce singleDirtyWord
		//     IF (edit distance substring >> singleDirtyWord) < delka singleDirtyWord / KONST : replace substring hezkym slovem  
		return text + "-edit";
	}
	
    public void testPrint() {
        System.out.println(LOG_TAG + "dirty words test print");
        for(String s: dirtyWords){
            System.out.println(LOG_TAG + "test print dirty: " + s);
        }
    }
}