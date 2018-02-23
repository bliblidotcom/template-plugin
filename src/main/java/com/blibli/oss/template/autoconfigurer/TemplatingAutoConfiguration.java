package com.blibli.oss.template.autoconfigurer;

import com.blibli.oss.template.Templating;
import com.blibli.oss.template.impl.TemplatingImpl;
import com.blibli.oss.template.properties.TemplateProperties;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.cache.ConcurrentMapTemplateCache;
import com.github.jknack.handlebars.cache.TemplateCache;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    return new Handlebars()
        .with(templateLoader)
        .with(templateCache);
  }

  @Bean
  public Templating templating(Handlebars handlebars) {
    return new TemplatingImpl(handlebars);
  }

}
