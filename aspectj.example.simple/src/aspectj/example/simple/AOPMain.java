package aspectj.example.simple;

public class AOPMain {

	public static void main(String[] args) {
		new AOPMain().methodOne(2);
	}

	public void methodOne(final Integer num) {
		System.out.println("methodOne");
	}
}
