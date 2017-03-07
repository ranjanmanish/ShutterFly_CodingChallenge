
public class CustomerVisit {
	public String cust_id;
	public int visit;
	public CustomerVisit(String id, int visit){
		this.cust_id = id;
		this.visit = visit;
	}
	
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public int getVisit() {
		return visit;
	}
	public void setVisit(int visit) {
		this.visit = visit;
	}
	
}
