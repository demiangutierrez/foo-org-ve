package test.transform;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;

public class ClassAdapterA extends ClassAdapter {

  public ClassAdapterA(ClassVisitor cv) {
    super(cv);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    String[] intInterfaces = new String[interfaces.length + 1];

    for (int i = 0; i < interfaces.length; i++) {
      intInterfaces[i] = interfaces[i];
    }

    intInterfaces[intInterfaces.length - 1] = "test/transform/SomeDummyInterface";

    super.visit(version, access, name, signature, superName, intInterfaces);
  }
}
