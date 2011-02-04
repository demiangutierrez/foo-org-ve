package test.transform;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class ClassAdapterB extends ClassAdapter implements Opcodes {

  public ClassAdapterB(ClassVisitor cv) {
    super(cv);
  }

  //  @Override
  //  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
  //    String[] intInterfaces = new String[interfaces.length + 1];
  //
  //    for (int i = 0; i < interfaces.length; i++) {
  //      intInterfaces[i] = interfaces[i];
  //    }
  //
  //    intInterfaces[intInterfaces.length - 1] = "test/transform/SomeDummyInterface";
  //
  //    super.visit(version, access, name, signature, superName, intInterfaces);
  //  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    return super.visitAnnotation(desc, visible);
  }

  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {

    System.err.println(name);
    System.err.println("Bef: " + Integer.toBinaryString(access));
    System.err.println("APU: " + Integer.toBinaryString(ACC_PUBLIC));
    System.err.println("APR: " + Integer.toBinaryString(ACC_PRIVATE));

    access &= ~ACC_PUBLIC;
    access |= ACC_PRIVATE;

    System.err.println("Aft: " + Integer.toBinaryString(access));

    return new FieldVisitor() {
      @Override
      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
      }

      @Override
      public void visitAttribute(Attribute attr) {
        // TODO Auto-generated method stub

      }

      @Override
      public void visitEnd() {
      }
    };
    //    return super.visitField(access, name, desc, signature, value);
  }

}
