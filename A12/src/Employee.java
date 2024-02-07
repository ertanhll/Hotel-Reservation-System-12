public class Employee implements Calculable {
	private String name;
	private String surname;
	private int ID;
	private double salary;

	public Employee(String name, String surname, int ID, double salary) {
		this.name = name;
		this.surname = surname;
		this.ID = ID;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public double getCost() {
		return salary;
	}
}
