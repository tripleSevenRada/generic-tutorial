package cz.etn.etnshop.custom_editors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.beans.PropertyEditorSupport;

@Configuration
@PropertySource(value="classpath:dirty_words.properties")
public class BaseCustomEditor extends PropertyEditorSupport{

	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public CustomStringEditor dirtyWordsEditor() {
		return new DirtyWordsEditor();
	}
}