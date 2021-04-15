package com.davidlozzi.calculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Math {
  static ScriptEngineManager scriptMngr = new ScriptEngineManager();
  static ScriptEngine scriptEngine = scriptMngr.getEngineByName("JavaScript");

  public static double calculate(String calculation) throws ScriptException {
    return Double.parseDouble(scriptEngine.eval(calculation).toString());
  }
}
