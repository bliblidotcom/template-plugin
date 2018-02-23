package com.blibli.oss.template.impl;


import com.blibli.oss.template.Templating;
import com.blibli.oss.template.TemplatingException;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

/**
 * @author Eko Kurniawan Khannedy
 */
public class TemplatingImpl implements Templating {

  private Handlebars handlebars;

  public TemplatingImpl(Handlebars handlebars) {
    this.handlebars = handlebars;
  }

  @Override
  public String render(String templateName, Object value) throws TemplatingException {
    Template template = getTemplate(templateName);
    return renderTemplate(template, value);
  }

  @Override
  public String renderInline(String template, Object value) throws TemplatingException {
    Template templateResult = getTemplateInline(template);
    return renderTemplate(templateResult, value);
  }

  private String renderTemplate(Template template, Object value) {
    try {
      return template.apply(value);
    } catch (Throwable e) {
      throw new TemplatingException(e);
    }
  }

  private Template getTemplate(String templateName) {
    try {
      return handlebars.compile(templateName);
    } catch (Throwable e) {
      throw new TemplatingException(e);
    }
  }

  private Template getTemplateInline(String template) {
    try {
      return handlebars.compileInline(template);
    } catch (Throwable e) {
      throw new TemplatingException(e);
    }
  }
}
