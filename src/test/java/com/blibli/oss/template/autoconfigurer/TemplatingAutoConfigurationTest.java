package com.blibli.oss.template.autoconfigurer;

import com.blibli.oss.template.properties.TemplateProperties;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.cache.ConcurrentMapTemplateCache;
import com.github.jknack.handlebars.cache.TemplateCache;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Eko Kurniawan Khannedy
 */
public class TemplatingAutoConfigurationTest {

  private TemplatingAutoConfiguration configuration = new TemplatingAutoConfiguration();

  @Test
  public void testTemplateLoader() throws Exception {
    TemplateProperties templateProperties = new TemplateProperties();
    TemplateLoader templateLoader = configuration.templateLoader(templateProperties);

    assertEquals("/templates/", templateLoader.getPrefix());
    assertEquals(".hbs", templateLoader.getSuffix());

    ClassPathTemplateLoader loader = (ClassPathTemplateLoader) templateLoader;
    TemplateSource hello = loader.sourceAt("hello");

    assertEquals("Hello {{name}}", hello.content());
    assertEquals("/templates/hello.hbs", hello.filename());
  }

  @Test
  public void testHandlebars() throws Exception {
    TemplateProperties templateProperties = new TemplateProperties();
    TemplateCache templateCache = configuration.templateCache();
    TemplateLoader templateLoader = configuration.templateLoader(templateProperties);
    Handlebars handlebars = configuration.handlebars(templateLoader, templateCache);

    assertNotNull(handlebars);

    assertTrue(handlebars.getCache() instanceof ConcurrentMapTemplateCache);
    assertEquals(templateLoader, handlebars.getLoader());
  }

}