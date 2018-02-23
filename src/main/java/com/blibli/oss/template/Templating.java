package com.blibli.oss.template;

/**
 * @author Eko Kurniawan Khannedy
 */
public interface Templating {

  String render(String templateName, Object value) throws TemplatingException;

  String renderInline(String template, Object value) throws TemplatingException;

}
