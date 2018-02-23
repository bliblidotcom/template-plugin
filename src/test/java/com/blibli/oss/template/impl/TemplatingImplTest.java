package com.blibli.oss.template.impl;

import com.blibli.oss.template.Templating;
import com.blibli.oss.template.TemplatingException;
import com.blibli.oss.template.autoconfigurer.TemplatingAutoConfiguration;
import com.blibli.oss.template.properties.TemplateProperties;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.TemplateCache;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Eko Kurniawan Khannedy
 */
public class TemplatingImplTest {

  private TemplateProperties templateProperties = new TemplateProperties();

  private TemplatingAutoConfiguration configuration = new TemplatingAutoConfiguration();

  private TemplateLoader templateLoader = configuration.templateLoader(templateProperties);

  private TemplateCache templateCache = configuration.templateCache();

  private Handlebars handlebars = configuration.handlebars(templateLoader, templateCache);

  private TemplatingImpl templating = new TemplatingImpl(handlebars);

  @Test(expected = TemplatingException.class)
  public void testCompileError() throws Exception {
    templating.render("notfound", "ups");
  }

  @Test(expected = TemplatingException.class)
  public void testCompileErrorInline() throws Exception {
    templating.renderInline("{{{{{{", "ups");
  }

  @Test(expected = TemplatingException.class)
  public void testRenderError() throws Exception {
    Handlebars handlebars = mock(Handlebars.class);
    Template template = mock(Template.class);

    when(handlebars.compile("test")).thenReturn(template);
    when(template.apply("eh")).thenThrow(new IOException());

    Templating templating = new TemplatingImpl(handlebars);
    templating.render("test", "eh");
  }

  @Test
  public void testSuccess() throws Exception {
    Map<String, String> sampleData = Collections.singletonMap("name", "Eko");
    String result = templating.render("hello", sampleData);

    assertEquals("Hello Eko", result);
  }

  @Test
  public void testSuccessInline() throws Exception {
    Map<String, String> sampleData = Collections.singletonMap("name", "Eko");
    String result = templating.renderInline("Hello {{name}}", sampleData);

    assertEquals("Hello Eko", result);
  }

}