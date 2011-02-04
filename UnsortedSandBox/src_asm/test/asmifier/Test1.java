package test.asmifier;

public class Test1 {

	private String xxx;

	public void setXxx(String xxx) {
		this.xxx = xxx;
	}

	public String getXxx() {
		return xxx;
	}

	public void foo() {
		int a = 10;
		if (a < 10) {
			System.err.println("XXX");
		} else {
			System.err.println("YYY");
		}
	}
}
