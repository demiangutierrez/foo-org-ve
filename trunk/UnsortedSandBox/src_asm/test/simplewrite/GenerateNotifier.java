package test.simplewrite;

import java.io.FileOutputStream;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class GenerateNotifier implements Opcodes {

  public static void main(String[] args) throws Exception {
    ClassWriter cw = new ClassWriter(0);

    cw.visit( //
        V1_5, //
        ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, //
        "asm1/Notifier", //
        null, //
        "java/lang/Object", //
        null);

    cw.visitMethod( //
        ACC_PUBLIC + ACC_ABSTRACT, //
        "notify", //
        "(Ljava/lang/String;)V", //
        null, null);

    cw.visitMethod( //
        ACC_PUBLIC + ACC_ABSTRACT, //
        "addListener", //
        "(Ljava/lang/String;)V", //
        null, null);

    cw.visitEnd();

    byte[] bytecode = cw.toByteArray();

    FileOutputStream fos = new FileOutputStream("class/asm1/Notifier.class");
    fos.write(bytecode);
    fos.close();
  }
}
