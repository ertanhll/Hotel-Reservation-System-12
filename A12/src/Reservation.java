public class Reservation extends Service implements Comparable<Reservation> {
	private String hotelName;
	private String reservationMonth;
	private int reservationStart;
	private int reservationEnd;
	private Room room;
	private String cityName;
	private static int reservationID = 0;

	public Reservation(String hotelName, String reservationMonth, int reservationStart, int reservationEnd, Room room) {

		super(0);
		this.hotelName = hotelName;
		this.room = room;
		this.reservationMonth = reservationMonth;
		this.reservationStart = reservationStart;
		this.reservationEnd = reservationEnd;

		reservationID++;
	}

	public Reservation(String cityName, String hotelName, String reservationMonth, int reservationStart,
			int reservationEnd) {

		super(0);
		this.hotelName = hotelName;
		this.reservationMonth = reservationMonth;
		this.reservationStart = reservationStart;
		this.reservationEnd = reservationEnd;
		this.cityName = cityName;

		reservationID++;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getReservationMonth() {
		return reservationMonth;
	}

	public void setReservationMonth(String reservationMonth) {
		this.reservationMonth = reservationMonth;
	}

	public int getReservationStart() {
		return reservationStart;
	}

	public void setReservationStart(int reservationStart) {
		this.reservationStart = reservationStart;
	}

	public int getReservationEnd() {
		return reservationEnd;
	}

	public void setReservationEnd(int reservationEnd) {
		this.reservationEnd = reservationEnd;
	}

	public static int getReservationID() {
		return reservationID;
	}

	public static void setReservationID(int reservationID) {
		Reservation.reservationID = reservationID;
	}

	@Override
	public int getCustomerID() {
		return super.getCustomerID();
	}

	@Override
	public void setCustomerID(int customerID) {
		super.setCustomerID(customerID);
	}

	@Override
	public String getServiceType() {

		return "Room booking";
	}

	@Override
	public double calculateService() {
		{
			int multiplier = 1;
			if (reservationMonth.equals("June") || reservationMonth.equals("July")
					|| reservationMonth.equals("August")) {
				multiplier = 2;
			}
			return room.getCost() * multiplier * (reservationEnd - reservationStart);
		}
	}

	@Override
	public double getCost() {
		return calculateService();
	}

	@Override
	public int compareTo(Reservation o) {
		return this.hotelName.compareTo(o.hotelName);
	}

	public String toCsv() {

		return cityName + "," + hotelName + "," + reservationMonth + "," + reservationStart + "," + reservationEnd;

	}

	public static Reservation fromCsv(String csv) {
		String[] data = csv.split(",");
		return new Reservation(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
	}

}
