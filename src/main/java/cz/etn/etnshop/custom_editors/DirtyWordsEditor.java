package cz.etn.etnshop.custom_editors;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value="classpath:dirty_words.properties")
public class DirtyWordsEditor extends BaseCustomEditor{

	@Value("#{'${dirty_words}'.split(',')}")
	private List<Integer> dirtyWords;
	
	private String value;

	@Override
	public void setAsText(String text) {
		value = text;
	}
 
	@Override
	public String getAsText() {
		return value;
	}

}
