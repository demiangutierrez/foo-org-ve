package reference;

public class ObjPtr<T> {

	private T obj;

	// ----------------------------------------

	public ObjPtr(T obj) {
		this.obj = obj;
	}

	// ----------------------------------------

	public T/**/get(/* */) {
		return obj;
	}

	public void set(T obj) {
		this.obj = obj;
	}
}
