import java.util.Comparator;

public class CustomerVisitComparator implements Comparator<CustomerVisit>{

	@Override
	public int compare(CustomerVisit o1, CustomerVisit o2) {
		int visit1 = o1.getVisit();
		int visit2 = o2.getVisit();
		if(visit1> visit2){
			return 1;
		}
		if(visit2 > visit1){
			return -1;
		}
		else {
			return 0;
		}
	}

}
