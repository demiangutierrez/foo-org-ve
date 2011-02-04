package test.tracer;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodVisitorA extends MethodAdapter implements Opcodes {

  int line;
  
  public MethodVisitorA(MethodVisitor mv) {
    super(mv);
  }

  @Override
  public void visitLineNumber(int line, Label start) {
    this.line = line;
    super.visitLineNumber(line, start);
  }
  
  @Override
  public void visitLabel(Label label) {
    super.visitLabel(label);
    
    mv.visitLdcInsn("test/tracer/Test1WT");
    mv.visitLdcInsn(line);
    mv.visitMethodInsn(INVOKESTATIC, "test/tracer/TraceLogger", "begTrace", "(Ljava/lang/String;I)V");
//    Label l4 = new Label();
//    mv.visitLabel(l4);
  }
  
  
  @Override
  public void visitMaxs(int maxStack, int maxLocals) {
    super.visitMaxs(maxStack+4, maxLocals);
  }
}
