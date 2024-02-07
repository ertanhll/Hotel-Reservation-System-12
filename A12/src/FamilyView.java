public class FamilyView extends Room {
	public FamilyView() {
		super("Family With View", 450, 50, true);
	}

	@Override
	public String toString() {
		return "Room Type: " + getType() + "," + " Daily Cost: " + getCost() + "," + " Room Size: " + getRoomSize()
				+ "," + " Has Bath: " + hasBath();
	}
}
