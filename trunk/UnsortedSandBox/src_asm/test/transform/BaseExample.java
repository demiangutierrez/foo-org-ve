package test.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class BaseExample {

  public static void main(String[] args) throws Exception {

    File f = new File("bin/test/transform/SimpleTest.class");
    byte[] b1 = new byte[(int) f.length()];
    FileInputStream fis = new FileInputStream(f);
    fis.read(b1);
    fis.close();

    ClassWriter cw = new ClassWriter(0);

//    ClassAdapter ca = new ClassAdapter(cw); // ca forwards all events to cw
    ClassAdapterB ca = new ClassAdapterB(cw);


    ClassReader cr = new ClassReader(b1);
    cr.accept(ca, 0);

    byte[] b2 = cw.toByteArray(); // b2 represents the same class as b1  }

    FileOutputStream fos = new FileOutputStream(f);
    fos.write(b2);
    fos.close();
  }
}
