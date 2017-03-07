enum  Cus_Type{NEW, UPDATE}

public class Customer extends Event{
	
	public Customer(){
		verb = "NEW";
	}

	/**
	 * This method will validate against all the necessary fields for ORDER
	 */
	boolean validateKeys(String line) {
		// TODO Auto-generated method stub
		return true;
	}
}
