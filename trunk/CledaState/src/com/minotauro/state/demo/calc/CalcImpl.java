package com.minotauro.state.demo.calc;

import java.text.NumberFormat;
import java.util.Map;

import javax.swing.JTextField;

public class CalcImpl extends Calc {

  public static final String PARAMETER_KEY = "parameter_key";

  // --------------------------------------------------------------------------------

  private JTextField txtDisplay;

  private double ope1;
  private double ope2;

  private String oper;

  // --------------------------------------------------------------------------------

  public CalcImpl(JTextField txtDisplay) {
    this.txtDisplay = txtDisplay;
  }

  // --------------------------------------------------------------------------------
  // OPI0 ->
  // --------------------------------------------------------------------------------

  @Override
  protected void OPI0_numb_OPE1(Map<String, Object> ctx, Map<String, Object> par) {
    addNumb(true, ctx, par);
  }

  @Override
  protected void OPI0_decp_OPD1(Map<String, Object> ctx, Map<String, Object> par) {
    addDecp(true, ctx, par);
  }

  // --------------------------------------------------------------------------------
  // OPE1 ->
  // --------------------------------------------------------------------------------

  @Override
  protected void OPE1_numb_OPE1(Map<String, Object> ctx, Map<String, Object> par) {
    addNumb(false, ctx, par);
  }

  @Override
  protected void OPE1_decp_OPD1(Map<String, Object> ctx, Map<String, Object> par) {
    addDecp(false, ctx, par);
  }

  @Override
  protected void OPE1_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    ope1 = Double.parseDouble(txtDisplay.getText());
    oper = (String) par.get(PARAMETER_KEY);
  }

  @Override
  protected void OPE1_clea_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    clearDisplay();
  }

  // --------------------------------------------------------------------------------
  // OPD1 ->
  // --------------------------------------------------------------------------------

  @Override
  protected void OPD1_numb_OPD1(Map<String, Object> ctx, Map<String, Object> par) {
    addNumb(false, ctx, par);
  }

  @Override
  protected void OPD1_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    enterOperator(ctx, par);
  }

  @Override
  protected void OPD1_clea_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    clearDisplay();
  }

  // --------------------------------------------------------------------------------
  // OPI1 ->
  // --------------------------------------------------------------------------------

  @Override
  protected void OPI1_numb_OPE2(Map<String, Object> ctx, Map<String, Object> par) {
    addNumb(true, ctx, par);
  }

  @Override
  protected void OPI1_decp_OPD2(Map<String, Object> ctx, Map<String, Object> par) {
    addDecp(true, ctx, par);
  }

  // --------------------------------------------------------------------------------
  // OPE2 ->
  // --------------------------------------------------------------------------------

  @Override
  protected void OPE2_numb_OPE2(Map<String, Object> ctx, Map<String, Object> par) {
    addNumb(false, ctx, par);
  }

  @Override
  protected void OPE2_decp_OPD2(Map<String, Object> ctx, Map<String, Object> par) {
    addDecp(false, ctx, par);
  }

  @Override
  protected void OPE2_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    execOperation(ctx, par);
  }

  @Override
  protected void OPE2_equa_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    execOperation(ctx, par);
  }

  @Override
  protected void OPE2_clea_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    clearDisplay();
  }

  // --------------------------------------------------------------------------------
  // OPD2 ->
  // --------------------------------------------------------------------------------

  @Override
  protected void OPD2_numb_OPD2(Map<String, Object> ctx, Map<String, Object> par) {
    addNumb(false, ctx, par);
  }

  @Override
  protected void OPD2_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    execOperation(ctx, par);
  }

  @Override
  protected void OPD2_equa_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    execOperation(ctx, par);
  }

  @Override
  protected void OPD2_clea_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    clearDisplay();
  }

  // --------------------------------------------------------------------------------

  private void enterOperator(Map<String, Object> ctx, Map<String, Object> par) {
    enterOperator(ctx, par);
  }

  // --------------------------------------------------------------------------------

  private void clearDisplay() {
    txtDisplay.setText("0");
  }

  // --------------------------------------------------------------------------------

  private void addNumb(boolean clearDisplay, //
      Map<String, Object> ctx, Map<String, Object> par) {

    if (clearDisplay) {
      clearDisplay();
    }

    if (!txtDisplay.getText().equals("0")) {
      txtDisplay.setText(txtDisplay.getText() + par.get(PARAMETER_KEY));
    } else {
      txtDisplay.setText(par.get(PARAMETER_KEY).toString());
    }
  }

  // --------------------------------------------------------------------------------

  private void addDecp(boolean clearDisplay, //
      Map<String, Object> ctx, Map<String, Object> par) {

    if (clearDisplay) {
      clearDisplay();
    }

    txtDisplay.setText(txtDisplay.getText() + ".");
  }

  // --------------------------------------------------------------------------------

  private void execOperation(Map<String, Object> ctx, Map<String, Object> par) {
    ope2 = Double.parseDouble(txtDisplay.getText());

    /*   */if (oper.equals("/")) {
      ope1 = ope1 / ope2;
    } else if (oper.equals("*")) {
      ope1 = ope1 * ope2;
    } else if (oper.equals("-")) {
      ope1 = ope1 - ope2;
    } else if (oper.equals("+")) {
      ope1 = ope1 + ope2;
    }

    oper = (String) par.get(PARAMETER_KEY);

    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setGroupingUsed(false);

    txtDisplay.setText(nf.format(ope1));
  }
}
