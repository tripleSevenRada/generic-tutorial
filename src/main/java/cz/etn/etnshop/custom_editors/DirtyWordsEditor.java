package 
 
import java.beans.PropertyEditorSupport;
import org.springframework.util.StringUtils;
 
public class DirtyWordsEditor extends PropertyEditorSupport {

    private static final String LOG_TAG = "DirtyWordsEditor: "

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
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }
}