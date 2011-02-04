package test.tracer;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassAdapterA extends ClassAdapter {

  public ClassAdapterA(ClassVisitor cv) {
    super(cv);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    return new MethodVisitorA(super.visitMethod(access, name, desc, signature, exceptions));
  }
}
