/*
 * Created on 10/10/2007
 */
package com.minotauro.state.generator;

import org.apache.commons.lang.SystemUtils;

/**
 * @author Demián Gutierrez
 */
public class BaseSMMain {

  private String template;

  // --------------------------------------------------------------------------------

  public BaseSMMain(String template) {
    this.template = template;
  }

  // --------------------------------------------------------------------------------

  protected String getBasePath() {
    StringBuffer ret = new StringBuffer();

    ret.append(SystemUtils.USER_DIR);
    ret.append("/src/");

    return ret.toString();
  }

  // --------------------------------------------------------------------------------

  protected String getBasePack() {
    return getClass().getPackage().getName();
  }

  // --------------------------------------------------------------------------------

  public void stateMachine(String name) throws Exception {
    CodeGenerator codeGenerator = new CodeGenerator( //
        getBasePath(), getBasePack(), name, template);
    codeGenerator.run();
  }
}
