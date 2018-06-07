package cz.etn.etnshop.custom_editors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:access_test.properties", "classpath:dirty_words.properties"})
public class CustomEditorsConfig {

	// add support to resolve ${...} properties
	// NOTE: THIS IS ONLY REQUIRED FOR OLD VERSIONS OF SPRING: v4.2 and lower
	// This is NOT required if using Spring v4.3+

	@Bean
	public static PropertySourcesPlaceholderConfigurer
					propertySourcesPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public StringEditor dirtyWordsEditor() {
		return new DirtyWordsEditor();
	}
}
