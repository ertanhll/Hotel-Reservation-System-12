
public class Suite extends Room {
	public Suite() {
		super("Suite", 650, 80, true);
	}

	@Override
	public String toString() {
		return "Room Type: " + getType() + "," + " Daily Cost: " + getCost() + "," + " Room Size: " + getRoomSize()
				+ "," + " Has Bath: " + hasBath();
	}
}
