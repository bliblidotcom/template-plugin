package com.blibli.oss.template.autoconfigurer;

import com.blibli.oss.template.Templating;
import com.blibli.oss.template.impl.TemplatingImpl;
import com.blibli.oss.template.properties.TemplateProperties;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.cache.ConcurrentMapTemplateCache;
import com.github.jknack.handlebars.cache.TemplateCache;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * @author Eko Kurniawan Khannedy
 */
@Configuration
@EnableConfigurationProperties({
    TemplateProperties.class
})
@ConditionalOnClass({Handlebars.class})
public class TemplatingAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public TemplateCache templateCache() {
    return new ConcurrentMapTemplateCache();
  }

  @Bean
  @ConditionalOnMissingBean
  public TemplateLoader templateLoader(TemplateProperties templateProperties) {
    return new ClassPathTemplateLoader(templateProperties.getPrefix(), templateProperties.getSuffix());
  }

  @Bean
  @ConditionalOnMissingBean
  public Handlebars handlebars(TemplateLoader templateLoader, TemplateCache templateCache) {
    Handlebars handlebars = new Handlebars()
            .with(templateLoader)
            .with(templateCache);
    handlebars.registerHelper("customSeparator", (context, options) -> customSeparator((Double) context, options));
    StringHelpers.register(handlebars);

    return handlebars;
  }

  public String customSeparator(Double data, Options options) {
    String separator = (String) options.hash.getOrDefault("separator", ".");
    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
    decimalFormatSymbols.setGroupingSeparator(separator.charAt(0));
    DecimalFormat decimalFormat = new DecimalFormat("###,###", decimalFormatSymbols);
    return decimalFormat.format(data);
  }

  @Bean
  public Templating templating(Handlebars handlebars) {
    return new TemplatingImpl(handlebars);
  }

}
