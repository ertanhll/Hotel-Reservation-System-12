public class Family extends Room {
	public Family() {
		super("Family", 400, 50, false);
	}

	@Override
	public String toString() {
		return "Room Type: " + getType() + "," + " Daily Cost: " + getCost() + "," + " Room Size: " + getRoomSize()
				+ "," + " Has Bath: " + hasBath();
	}
}
