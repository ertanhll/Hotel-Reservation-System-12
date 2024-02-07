
public class Room {
	private String type;
	private int dailyCost;
	private int roomSize;
	private boolean hasBath;

	public Room(String type, int dailyCost, int roomSize, boolean hasBath) {
		this.type = type;
		this.dailyCost = dailyCost;
		this.roomSize = roomSize;
		this.hasBath = hasBath;
	}

	public String getType() {
		return type;
	}

	public int getCost() {
		return dailyCost;
	}

	public void setCost(int dailyCost) {
		this.dailyCost = dailyCost;
	}

	public int getRoomSize() {
		return roomSize;
	}

	public boolean hasBath() {
		return hasBath;
	}

}
