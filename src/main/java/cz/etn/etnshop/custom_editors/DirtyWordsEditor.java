package 
 
import java.beans.PropertyEditorSupport;
import org.springframework.util.StringUtils;

@PropertySource(value="classpath:dirty_words.properties")
public class DirtyWordsEditor extends BaseCustomEditor {

    private static final String LOG_TAG = "DirtyWordsEditor: "

    @Value("#{'${dirty_words}'.split(',')}")
    private List<Integer> dirtyWords;
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println(LOG_TAG + "set : " + text);
        if (StringUtils.hasText(text)) {
            setValue(text.trim());
        } else {
            setValue(null);
        }
    }
 
    @Override
    public String getAsText() {
        String value = (String) getValue();
        System.out.println(LOG_TAG + "get : " + value);
        for(String s: dirtyWords){
            System.out.println(LOG_TAG + "test print dirty: " + s);
        }
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }
}