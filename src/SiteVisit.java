
public class SiteVisit extends Event{
	public SiteVisit(){
		verb = "UPLOAD";
	}

	@Override
	boolean validateKeys(String line) {
		return true;
	}
}
