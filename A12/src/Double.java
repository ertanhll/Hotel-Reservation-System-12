public class Double extends Room {
	public Double() {
		super("Double", 180, 30, false);
	}

	@Override
	public String toString() {
		return "Room Type: " + getType() + "," + " Daily Cost: " + getCost() + "," + " Room Size: " + getRoomSize()
				+ "," + " Has Bath: " + hasBath();
	}
}
