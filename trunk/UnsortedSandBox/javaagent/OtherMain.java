public class OtherMain {

	public static void main(String[] args) {
		DemoLoadedClass dlc = new DemoLoadedClass();

		dlc.setFoo(10);
		System.err.println(dlc.getFoo());
	}
}
