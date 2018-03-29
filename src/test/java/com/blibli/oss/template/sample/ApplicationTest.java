package com.blibli.oss.template.sample;

import com.blibli.oss.template.Templating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.Application.class)
public class ApplicationTest {

  @Autowired
  private Templating templating;

  @Test
  public void testHello() {
    String result = templating.render("hello", Collections.singletonMap("name", "Eko"));
    assertEquals("Hello Eko", result);
  }

  @Test
  public void testRenderInline() {
    String result = templating.renderInline("Hello {{name}}", Collections.singletonMap("name", "Eko"));
    assertEquals("Hello Eko", result);
  }

  @Test
  public void testStringHelpers_capitalize() {
    String result = templating.renderInline("hello {{capitalize name}}", Collections.singletonMap("name", "eko khannedy"));
    assertEquals("hello Eko Khannedy", result);
  }

  @Test
  public void testStringHelpers_percent() {
    String result = templating.renderInline("hello {{numberFormat name 'percent'}}", Collections.singletonMap("name", 500000));
    assertEquals("hello 50,000,000%", result);
  }


  @Test
  public void testStringHelpers_currency() {
    String result = templating.renderInline("hello {{numberFormat name 'currency'}}", Collections.singletonMap("name", 500000));
    assertEquals("hello $500,000.00", result);
  }

  @Test
  public void testStringHelpers_currencyID() {
    String result = templating.renderInline("hello Rp {{numberFormat name 'integer' maximumFractionDigits=0}}", Collections.singletonMap("name", 500000));
    assertEquals("hello Rp 500,000", result);
  }

  @SpringBootApplication
  public static class Application {

  }

}
