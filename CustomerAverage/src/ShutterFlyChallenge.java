import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * This class takes input for a given text file
 * Ingest(e, D)
		Given event e, update data D

   TopXSimpleLTVCustomers(x, D)

		Return the top x customers with the highest Simple Lifetime Value from data D.

 * @author manishranjan
 *
 */
enum  Type{CUSTOMER, SITE_VISIT, IMAGE, ORDER}
public class ShutterFlyChallenge {
	private static String filePath= "./sample_input/events.txt";
	private static final String encoding = "UTF-8";

	static List<String> D  = new ArrayList<String>();
	public ShutterFlyChallenge(String filePath){
		this.filePath = filePath;
	}

	public static void main(String[] args) throws IOException {
		StringBuilder jsonData = new StringBuilder();
		if(fileIsValid(filePath)){
			// Buffered reader for performance on large file size
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filePath), encoding));
			String line;
			while((line = in.readLine()) != null) {
				// to  put a basic check if empty but not null line is not making its way inside json Data
				if(line.length() > 1){
					jsonData.append(line);
				}
			}
			/**
			 * this method will take the read JSON data and read it through to validate if it belongs to one of the types
			 * additionally if it has keys present
			 */
			if (checkIfJsonDataIsOneOfValidType(jsonData)){

				if(checkIfDataHasNecessaryKeys(jsonData)){
					topXSimpleLTVCustomers(10, jsonData);
				}
			}

		}

	}

	/**
	 * This factory method invokes respective type class to get the data validity 
	 * i.e Customer class needs to have key(customer_id) * event_time *
	 * SiteVisit class needs to have key(page_id) * event_time *customer_id *
	 * for now I return true, but iterating through the passed string and looking for relative type is present or not is  trivial case
	 * @param jsonData
	 * @return
	 */
	private static boolean checkIfDataHasNecessaryKeys(StringBuilder jsonData) {
		SimpleTypeFactory factory = new SimpleTypeFactory();
		List<String> listOfData = new ArrayList<String>(Arrays.asList(jsonData.toString().split(",")));
		for(String str: listOfData){
			System.out.println(str);
			JSONObject obj = new JSONObject(jsonData);
			String type = obj.getString("type");
			Event event;
			if(type!=null){
				event = factory.checkEvent(type);
				return event.validateKeys(str);
			}
		}
		return false;
	}

	/**
	 * checks Of Json is valid also filters out rows which do not have necessary keys
	 * @param jsonData
	 * @return 
	 */
	private static boolean checkIfJsonDataIsOneOfValidType(StringBuilder jsonData) {
		List<String> listOfData = new ArrayList<String>(Arrays.asList(jsonData.toString().split(",")));
		for(String str: listOfData){
			System.out.println(str);
			JSONObject obj = new JSONObject(str);
			String type = obj.getString("type");
			if(type.equals(Type.CUSTOMER) || type.equals(Type.SITE_VISIT) || type.equals(Type.IMAGE) || type.equals(Type.ORDER))
				return true;
		}
		return false;
	}

	private static void topXSimpleLTVCustomers(int i, StringBuilder jsonData ) {
		Map<String, List<String>> customerToVisit = new HashMap<String, List<String>>();
		Map<String, List<Float>> amountPaid = new HashMap<String, List<Float>>();

		List<String> listOfData = new ArrayList<String>(Arrays.asList(jsonData.toString().split(",")));
		for(String str: listOfData){
			JSONObject obj = new JSONObject(str);
			// for computing the site_visit
			if(obj.getString("type") == "SITE_VISIT"){
				String cust_id = obj.getString("customer_id");
				if(customerToVisit.containsKey(cust_id)){
					List temp = customerToVisit.get(cust_id);
					temp.add(obj.getString("event_time"));
					customerToVisit.put(cust_id, temp);

				}
				else{
					List temp = new ArrayList<>();
					temp.add(obj.getString("event_time"));
					customerToVisit.put(cust_id, temp);
				}
			}

			// for computing the amount spent per id / visit
			if(obj.getString("type") == "ORDER"){
				String cust_id = obj.getString("customer_id");
				if(amountPaid.containsKey(cust_id)){
					List temp = amountPaid.get(cust_id);
					temp.add(obj.getString("total_amount"));
					amountPaid.put(cust_id, temp);

				}
				else{
					List temp = new ArrayList<>();
					temp.add(obj.getString("event_time"));
					customerToVisit.put(cust_id, temp);
				}
			}			
		} // end of for
		getTheTopX(customerToVisit, amountPaid);
	}

	private static void getTheTopX(Map<String, List<String>> customerToVisit, Map<String, List<Float>> amountPaid) {
		// TODO Auto-generated method stub
		List<CustomerVisit> list = new ArrayList<>();
		Map<String, Integer>  map = mapOfCustToNoOfVisits(customerToVisit);
		// here the return map would have the key as customer_id and value as no of visits
		int i = 0;
		CustomerVisit[] custVisit = new CustomerVisit[map.size()];
		for(Map.Entry<String, Integer> entry: map.entrySet()){
			// now need to add comparator class so the map can be sorted based on value , i.e no of visits per customer
			CustomerVisit temp = new CustomerVisit(entry.getKey(), entry.getValue());
			list.add(temp);
		}
		Collections.sort(list, new CustomerVisitComparator()); // this will be sorted list based on the customer visit
		// we can extract as many no of users based on value of X
		for(CustomerVisit cv: list){
			float total_price = 0;
			List<Float> price = amountPaid.get(cv.getCust_id());
			for(Float f: price){
				total_price+=f;
			}
			float LTF = 52* total_price * cv.getVisit();
			// this would print the first customer's LTF in the list
			System.out.println(LTF);
		}
	}

	private static Map<String, Integer> mapOfCustToNoOfVisits(Map<String, List<String>> customerToVisit) {
		Map<String, Integer> mapOfCustomerToVisitCount = new HashMap<String, Integer>();
		for(Map.Entry<String, List<String>> entry: customerToVisit.entrySet()){
			mapOfCustomerToVisitCount.put(entry.getKey(), entry.getValue().size());
		}
		return mapOfCustomerToVisitCount;
	}

	private static boolean fileIsValid(String filePath) {
		File f = new File(filePath);
		return f.exists();
	}
}
