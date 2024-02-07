
public enum MenuOptions {
	A("Create new Reservation with Room Type"), 
	B("Display all Reservations"),
	C("List the reservations for a specific city"), 
	D("Add extra services to a reservation"),
	E("Calculate total cost for each service"), 
	F("Display the total cost of every customer"), 
	G("Add an employee"),
	H("Add a bill"), 
	I("Get monthly balance"), 
	J("List all Services sorted based on cost"),
	K("List all Reservations sorted based on hotel names"),
	L("Exit");

	private String selection;

	MenuOptions(String selection) {
		this.selection = selection;

	}

	public String getSelection() {
		return selection;
	}

}