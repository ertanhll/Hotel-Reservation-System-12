public abstract class Service implements Calculable {

	private int customerID;

	public Service(int customerID) {
		this.customerID = customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public abstract String getServiceType();

	public abstract double calculateService();

	public int getCustomerID() {
		return customerID;
	}

	public void displayServiceInfo() {
		if (this instanceof Reservation) {
			Reservation reservation = (Reservation) this;
			System.out.println("Customer ID: " + reservation.getCustomerID() + ", Service Type: "
					+ reservation.getServiceType() + ", Cost: " + reservation.getCost());
		} else if (this instanceof Spa) {
			Spa spa = (Spa) this;
			System.out.println("Customer ID: " + spa.getCustomerID() + ", Service Type: " + spa.getServiceType()
					+ ", Cost: " + spa.getCost());
		} else if (this instanceof Laundry) {
			Laundry laundry = (Laundry) this;
			System.out.println("Customer ID: " + laundry.getCustomerID() + ", Service Type: " + laundry.getServiceType()
					+ ", Cost: " + laundry.getCost());
		}
	}

}
