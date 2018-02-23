package com.blibli.oss.template.properties;

import com.github.jknack.handlebars.io.TemplateLoader;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Eko Kurniawan Khannedy
 */
@Data
@ConfigurationProperties("template.plugin")
public class TemplateProperties {

  private String prefix = "/templates/";

  private String suffix = TemplateLoader.DEFAULT_SUFFIX;
}
