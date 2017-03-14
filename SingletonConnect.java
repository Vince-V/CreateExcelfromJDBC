
public class SingletonConnect {

	private static SingletonConnect sc = new SingletonConnect();

	private SingletonConnect() {

	}

	public static SingletonConnect myConnection() {
		return sc;
	}

}
