package reference;

public class Main {

  public static void foo(TestObj testObj) {
    testObj.x = 20; // Works
  }

  public static void faa(TestObj testObj) {
    TestObj newTestObj = new TestObj();
    newTestObj.x = 20;

    testObj = newTestObj; // Does not work
  }

  public static void bar(ObjPtr<TestObj> testObjPtr) {
    testObjPtr.get().x = 20; // Works
  }

  public static void baz(ObjPtr<TestObj> testObjPtr) {
    ObjPtr<TestObj> newTestObjPtr = new ObjPtr<TestObj>(new TestObj());

    newTestObjPtr.get().x = 20;

    testObjPtr.set(newTestObjPtr.get()); // Works
  }

  public static void main(String[] args) {
    ObjPtr<TestObj> objPtrObjPtr;
    TestObj testObj;

    testObj = new TestObj();
    testObj.x = 10;

    foo(testObj);
    System.err.println(testObj.x); // Shows 20 (worked)

    faa(testObj);
    System.err.println(testObj.x); // Shows 10 (worked the Java way, not the C++ way)

    objPtrObjPtr = new ObjPtr<TestObj>(testObj);
    bar(objPtrObjPtr);
    testObj = objPtrObjPtr.get();
    System.err.println(testObj.x); // Shows 20 (worked but code is cluttered)

    objPtrObjPtr = new ObjPtr<TestObj>(testObj);
    bar(objPtrObjPtr);
    testObj = objPtrObjPtr.get();
    System.err.println(testObj.x); // Shows 20 (worked but code is cluttered)
  }
}
