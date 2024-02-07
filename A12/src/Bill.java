public class Bill implements Calculable {
	private String type;
	private String month;
	private double amount;

	public Bill(String type, String month, double amount) {
		this.type = type;
		this.month = month;
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public double getCost() {
		return amount;
	}
}
