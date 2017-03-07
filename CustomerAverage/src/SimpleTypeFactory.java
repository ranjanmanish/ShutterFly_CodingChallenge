
public class SimpleTypeFactory {
	public Event checkEvent(String type ){
		Event event = null;
		if(type.equals("Customer")){
			event = new Customer();
		}
		if(type.equals("SiteVisit")){
			event = new SiteVisit();
		}
		if(type.equals("Image")){
			event = new Image();
		}
		if(type.equals("Order")){
			event = new Order();
		}
		
		return event;
		
	}
}
