public class Laundry extends Service {
	private int numItems;

	public Laundry() {
		super(0);
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	@Override
	public void setCustomerID(int customerID) {
		super.setCustomerID(customerID);
	}

	@Override
	public int getCustomerID() {
		return super.getCustomerID();

	}

	@Override
	public String getServiceType() {
		return "Laundry";
	}

	@Override
	public double calculateService() {
		return numItems * 20;
	}

	@Override
	public double getCost() {
		return calculateService();
	}
}
