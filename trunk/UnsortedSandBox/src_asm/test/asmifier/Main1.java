package test.asmifier;

import org.objectweb.asm.util.ASMifierClassVisitor;

public class Main1 {

  public static void main(String[] args) throws Exception {
    ASMifierClassVisitor.main(new String[] {"test.asmifier.Test1"});
  }
}
