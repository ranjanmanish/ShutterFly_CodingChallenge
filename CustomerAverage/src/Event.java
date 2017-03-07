
public abstract class Event {
	String verb;
	abstract boolean validateKeys(String line);
}
