
public enum Month {
	JANUARY("January"), FEBRUARY("February"), MARCH("March"), APRIL("April"), MAY("May"), JUNE("June"), JULY("July"),
	AUGUST("August"), SEPTEMBER("September"), OCTOBER("Oktober"), NOVEMBER("November"), DECEMBER("December");

	private String selection;

	Month(String selection) {
		this.selection = selection;
	}

	public String getSelection() {
		return selection;
	}
}
