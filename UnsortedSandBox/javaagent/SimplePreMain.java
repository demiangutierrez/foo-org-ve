import java.lang.instrument.Instrumentation;


public class SimplePreMain {

	public static void premain(String agentArguments, Instrumentation instrumentation) {	
		instrumentation.addTransformer(new SimpleTransformer());
	}	
}
