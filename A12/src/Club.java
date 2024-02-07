
public class Club extends Room {
	public Club() {
		super("Club", 250, 45, true);
	}

	@Override
	public String toString() {
		return "Room Type: " + getType() + "," + " Daily Cost: " + getCost() + "," + " Room Size: " + getRoomSize()
				+ "," + " Has Bath: " + hasBath();
	}
}