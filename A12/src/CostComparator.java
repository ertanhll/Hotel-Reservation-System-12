import java.util.Comparator;

public class CostComparator implements Comparator<Service> {
	@Override
	public int compare(Service o1, Service o2) {
		double cost1 = o1.getCost();
		double cost2 = o2.getCost();

		if (cost1 < cost2) {
			return 1;
		} else if (cost1 > cost2) {
			return -1;
		} else {
			return 0;
		}
	}
}
