
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			 MyFrame frame = new MyFrame();
	         frame.setVisible(true);

			
			}
		});
		
		
         
         
         
         
         
		Scanner input = new Scanner(System.in);

		Single singleRoom = new Single();
		Double doubleRoom = new Double();
		Club clubRoom = new Club();
		Family familyRoom = new Family();
		FamilyView familyViewRoom = new FamilyView();
		Suite suiteRoom = new Suite();

		List<Calculable> calculableList = new ArrayList<Calculable>();

		int option;

		while (true) {
			int order = 0;
			for (MenuOptions select : MenuOptions.values()) {
				System.out.println((++order) + ". " + select.getSelection());
			}

			try {
				option = input.nextInt();
				input.nextLine();

			} catch (InputMismatchException e) {
				System.err.println("You entered an invalid menu option. Enter again.");
				input.nextLine();
				continue;
			}

			int selectedIndex = option - 1;
			MenuOptions menuOptions;
			try {
				menuOptions = MenuOptions.values()[selectedIndex];
			} catch (IndexOutOfBoundsException e) {
				System.err.println("You entered an invalid menu option. Enter again.");
				System.out.println("\n");
				continue;
			}
			System.out.println("\n");

			ArrayList<Room> roomTypesList = new ArrayList<>(
					Arrays.asList(singleRoom, doubleRoom, clubRoom, familyRoom, familyViewRoom, suiteRoom));

			switch (menuOptions) {

			case A:
				System.out.println("ROOM INFOS:\n\n");
				for (Room room : roomTypesList) {
					System.out.println(room);
				}
				System.out.println("\n");
				System.out.print("Hotel Name: ");

				String hotelName = input.nextLine();

				ArrayList<String> roomTypes = new ArrayList<>(
						Arrays.asList("Single", "Double", "Club", "Family", "Family with View", "Suite"));

				String roomTypeOption = null;

				while (true) {
					try {
						System.out.print("Room Type: ");
						roomTypeOption = input.nextLine();						
						if (!roomTypes.contains(roomTypeOption)) {
							throw new RoomTypeException("Room type is not valid!");
						}

						break;
					} catch (RoomTypeException e) {
						System.err.println(e.getMessage());
					

					}
				}

				boolean isValidMonth = false;
				String reservationMonth = null;

				while (!isValidMonth) {
					System.out.print("Reservation Month: ");
					reservationMonth = input.nextLine();

					for (Month month : Month.values()) {
						if (month.getSelection().equals(reservationMonth)) {
							isValidMonth = true;
							break;
						}
					}
					if (!isValidMonth) {
						System.out.println("Invalid input");
					}
				}
				int reservationStart = 0;
				boolean isValidStart = false;
				while (!isValidStart) {
					System.out.print("Reservation Start (1-30): ");
					try {
						reservationStart = input.nextInt();
						if (reservationStart <= 0 || reservationStart > 30) {
							throw new IllegalArgumentException();
						}
						isValidStart = true;
					} catch (InputMismatchException e) {
						System.err.println("Reservation Start must be a numeric value!");
						input.nextLine();
					
					} catch (IllegalArgumentException e) {
						System.err.println("Reservation Start must be a positive numeric value between 1 and 30!");
						input.nextLine();
						
					}
				}

				int reservationEnd = 0;
				boolean isValidEnd = false;
				while (!isValidEnd) {
					System.out.print("Reservation End (" + reservationStart + "-30): ");
					try {
						reservationEnd = input.nextInt();
						if (reservationEnd <= 0 || reservationEnd > 30 || reservationEnd < reservationStart) {
							throw new IllegalArgumentException();
						} 
						isValidEnd = true;
					} catch (InputMismatchException e) {
						System.err.println("Reservation End must be a numeric value!");
						input.nextLine();
					
					} catch (IllegalArgumentException e) {
						System.err.println("Invalid end day. End day must be greater than or equal to the start day.");
						input.nextLine();
					
					}
				}

				Room room = null;
				for (String roomType : roomTypes) {
					if (roomTypeOption.equals(roomType)) {

						switch (roomType) {
						case "Single":
							room = singleRoom;
							break;
						case "Double":
							room = doubleRoom;
							break;
						case "Club":
							room = clubRoom;
							break;
						case "Family":
							room = familyRoom;
							break;
						case "Family with View":
							room = familyViewRoom;
							break;
						case "Suite":
							room = suiteRoom;
							break;
						}
						break;
					}
				}

				Reservation reservationList = new Reservation(hotelName, reservationMonth, reservationStart,
						reservationEnd, room);

				calculableList.add(reservationList);
				reservationList.setCustomerID(Reservation.getReservationID());

				System.out.println("\nReservation ID: " + Reservation.getReservationID() + " is created!\n");

				break;

			case B:

				for (Calculable reserved : calculableList) {
					if (reserved instanceof Reservation) {
						Reservation reservationInfos = (Reservation) reserved;
						System.out.println("Reservation for a " + reservationInfos.getRoom().getType() + " room in "
								+ reservationInfos.getHotelName() + " starts on "
								+ reservationInfos.getReservationMonth() + " " + reservationInfos.getReservationStart()
								+ " and ends on " + reservationInfos.getReservationEnd() + ".");
						System.out.println(
								"Reservation has a total cost of $" + (int) reservationInfos.calculateService() + ".");
						System.out.println("\n");
					}
				}
				break;

			case C:
				int numOfReservations = Reservation.getReservationID();
				if (numOfReservations == 0) {
					System.out.println("There are no reservations yet. Please try again later.\n");
					break;
				}

				System.out.print("Type a city name for a reservation search: ");
				String city = input.nextLine();

				boolean foundReservation = false;
				System.out.println("Reservations for " + city + ":");
				for (Calculable service : calculableList) {
					if (service instanceof Reservation) {
						Reservation reservationCity = (Reservation) service;
						if (reservationCity.getHotelName().contains(city)) {
							System.out.println(reservationCity.getHotelName());
							foundReservation = true;
						}
					}
				}

				if (!foundReservation) {
					System.out.println("No reservations found for the specified city.\n");
				}
				System.out.println("\n");
				break;

			case D:
				int serviceType = 0;
				boolean isValidChoose = false;

				while (!isValidChoose) {
					try {
						System.out.println("Please select one of the extra services from below: ");
						System.out.println("1. Laundry");
						System.out.println("2. Spa");
						serviceType = input.nextInt();

						if (serviceType != 1 && serviceType != 2) {
							throw new InputMismatchException();
						}

						isValidChoose = true;
					} catch (InputMismatchException e) {
						System.err.println("Invalid service type! Please enter 1 or 2.");
						input.nextLine();
						
					}
				}

				System.out.print("\nType the reservation ID to credit this service: \n");
				int customerID = input.nextInt();
				System.out.println();
				boolean roomReserved = false;
				if (customerID <= Reservation.getReservationID()) {
					roomReserved = true;

				}
				if (!roomReserved) {
					System.out.println("Cannot add laundry or spa service before reserving a room!\n");

					break;
				}

				if (serviceType == 1) {
					boolean isValidNumItems = false;
					int numItems = 0;
					while (!isValidNumItems) {
						try {
							System.out.println("How many pieces of clothing?");
							numItems = input.nextInt();
							if (numItems <= 0) {
								throw new IllegalArgumentException("Clothing count must be positive.");
							}
							isValidNumItems = true;
						} catch (InputMismatchException e) {
							System.err.println("Clothing count must be a numeric value!");
							input.nextLine();
						} catch (IllegalArgumentException e) {
							System.err.println(e.getMessage());
						}
					}

					Laundry laundry = new Laundry();
					laundry.setCustomerID(customerID);
					laundry.setNumItems(numItems);
					calculableList.add(laundry);
					System.out.println("Laundry service added successfully!\n");

				} else if (serviceType == 2) {
					int duration = 0;
					boolean isValidDuration = false;

					while (!isValidDuration) {
						System.out.println("How many days?");
						try {
							duration = input.nextInt();
							if (duration < 1) {
								throw new IllegalArgumentException("Day count must be a positive integer.");
							}
							isValidDuration = true;
						} catch (InputMismatchException e) {
							System.err.println("Day count must be a numeric value!");
							input.nextLine();
						} catch (IllegalArgumentException e) {
							System.err.println(e.getMessage());
						}
					}

					Spa spa = new Spa();
					spa.setCustomerID(customerID);
					spa.setDuration(duration);
					calculableList.add(spa);
					System.out.println("Spa service added successfully!");
					System.out.println("\n");
				}
				break;

			case E:

				for (Calculable service : calculableList) {
					if (service instanceof Reservation) {
						Reservation reservationRoom = (Reservation) service;

						System.out.println(
								"The cost for the " + reservationRoom.getServiceType() + " service of reservation ID "
										+ reservationRoom.getCustomerID() + ": " + reservationRoom.calculateService());
					} else if (service instanceof Spa) {
						Spa spa = (Spa) service;
						System.out.println("The cost for the " + spa.getServiceType() + " service of customer ID "
								+ spa.getCustomerID() + ": " + spa.calculateService());
					} else if (service instanceof Laundry) {
						Laundry laundry = (Laundry) service;
						System.out.println("The cost for the " + laundry.getServiceType() + " service of customer ID "
								+ laundry.getCustomerID() + ": " + laundry.calculateService());

					}
				}
				System.out.println();
				break;

			case F:

				int numCustomers = Reservation.getReservationID();
				double totalCostCustomer = 0;
				for (int i = 1; i <= numCustomers; i++) {
					for (Calculable service : calculableList) {
						if (service instanceof Reservation) {
							Reservation reservation = (Reservation) service;
							if (reservation.getCustomerID() == i) {
								totalCostCustomer += reservation.calculateService();
							}
						} else if (service instanceof Laundry) {
							Laundry laundry = (Laundry) service;
							if (laundry.getCustomerID() == i) {
								totalCostCustomer += laundry.calculateService();
							}
						} else if (service instanceof Spa) {
							Spa spa = (Spa) service;
							if (spa.getCustomerID() == i) {
								totalCostCustomer += spa.calculateService();
							}
						}
					}
					System.out.println(
							"The total cost of all services for customer ID " + i + " is $" + totalCostCustomer);
					totalCostCustomer = 0;
				}
				System.out.println();

				break;

			case G:

				String name = "";
				boolean isValidName = false;
				while (!isValidName) {
					try {
						System.out.println("Name:");
						name = input.nextLine();
						if (name.isEmpty()) {
							throw new IllegalArgumentException("Name cannot be empty");
						}
						if (name.matches(".*\\d.*")) {
							throw new IllegalArgumentException("Name cannot contain numbers");
						}
						isValidName = true;
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
						
					} 
					
				}

				String surname = "";
				boolean isValidSurname = false;
				while (!isValidSurname) {
					try {
						System.out.println("Surname:");
						name = input.nextLine();
						if (name.isEmpty()) {
							throw new IllegalArgumentException("Surname cannot be empty");
						}
						if (name.matches(".*\\d.*")) {
							throw new IllegalArgumentException("Surname cannot contain numbers");
						}
						isValidSurname = true;
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
						
					}
					
				}

				int ID = 0;
				boolean isValidID = false;

				while (!isValidID) {
					try {
						System.out.println("ID: ");
						ID = input.nextInt();
						if (ID <= 0) {
							throw new IllegalArgumentException("ID must be a positive integer");
						}
						isValidID = true;
					} catch (InputMismatchException e) {
						System.err.println("Invalid input. Please enter a valid integer ID.");
						input.nextLine();
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
					}
				}

				input.nextLine();
				double monthlyPayment = 0;
				boolean isValidPayment = false;
				while (!isValidPayment) {
					try {
						System.out.println("Monthly payment: ");
						monthlyPayment = input.nextDouble();
						if (monthlyPayment < 0) {
							throw new IllegalArgumentException("Monthly payment cannot be negative.");
						}
						isValidPayment = true;
					} catch (InputMismatchException e) {
						System.err.println("Monthly Payment must be a numeric value!");
						input.nextLine();
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
						input.nextLine();
					}
				}

				input.nextLine();

				Employee employee = new Employee(name, surname, ID, monthlyPayment);
				calculableList.add(employee);
				System.out.println("Employee added successfully.\n");
				break;

			case H:

				String type = "";
				boolean isValidType = false;
				while (!isValidType) {
					try {
						System.out.println("Type:");
						name = input.nextLine();
						if (name.isEmpty()) {
							throw new IllegalArgumentException("Type cannot be empty");
						}
						if (name.matches(".*\\d.*")) {
							throw new IllegalArgumentException("Type cannot contain numbers");
						}
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
						
					} 
					isValidType = true;
				}

				double amount = 0;
				boolean isValidAmount = false;
				while (!isValidAmount) {
					try {
						System.out.println("Amount: ");
						amount = input.nextDouble();
						
						if (amount < 0) {
							throw new IllegalArgumentException("Bill Amount cannot be negative.");
						}
						isValidAmount = true;
					} catch (InputMismatchException e) {
						System.err.println("Bill Amount must be a numeric value!");
						input.nextLine();
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
					}
				}

				input.nextLine();
				Month months = null;
				boolean isValidMonths = false;
				while (!isValidMonths) {
					try {
						System.out.println("Month: ");
						String inputMonth = input.nextLine();
						boolean found = false;
						for (Month m : Month.values()) {
							if (m.getSelection().equalsIgnoreCase(inputMonth)) {
								months = m;
								found = true;
								break;
							}
						}
						if (!found) {
							throw new IllegalArgumentException("Invalid month. Please enter a valid month.");
						}
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
						
					}
					isValidMonths = true;
				}
				String month = months.getSelection();

				Bill bill = new Bill(type, month, amount);
				calculableList.add(bill);
				System.out.println("Bill added successfully.\n");

				break;

			case I:

				System.out.println("Enter Month: ");
				String chosenMonth = input.nextLine();

				double totalIncome = 0;
				double totalBills = 0;
				double totalEmployeeCost = 0;

				for (Calculable service : calculableList) {
					if (service instanceof Reservation) {
						Reservation reservation = (Reservation) service;
						if (reservation.getReservationMonth().equals(chosenMonth)) {
							System.out.println("For reservation ID: " + reservation.getCustomerID() + ", Service type: "
									+ reservation.getServiceType() + ", Service Cost: "
									+ reservation.calculateService());
							totalIncome += reservation.calculateService();
						}
					} else if (service instanceof Spa) {
						Spa spa = (Spa) service;

						for (Calculable reservation : calculableList) {
							if (reservation instanceof Reservation
									&& ((Reservation) reservation).getCustomerID() == spa.getCustomerID()
									&& ((Reservation) reservation).getReservationMonth().equals(chosenMonth)) {
								System.out.println("For reservation ID: " + spa.getCustomerID() + ", Service type: "
										+ spa.getServiceType() + ", Service Cost: " + spa.calculateService());
								totalIncome += spa.calculateService();
								break;
							}
						}
					} else if (service instanceof Laundry) {
						Laundry laundry = (Laundry) service;

						for (Calculable reservation : calculableList) {
							if (reservation instanceof Reservation
									&& ((Reservation) reservation).getCustomerID() == laundry.getCustomerID()
									&& ((Reservation) reservation).getReservationMonth().equals(chosenMonth)) {
								System.out.println("For reservation ID: " + laundry.getCustomerID() + ", Service type: "
										+ laundry.getServiceType() + ", Service Cost: " + laundry.calculateService());
								totalIncome += laundry.calculateService();
								break;
							}
						}
					}
				}

				for (Calculable desiredList : calculableList) {
					if (desiredList instanceof Bill) {
						Bill bills = (Bill) desiredList;
						if (bills.getMonth().equals(chosenMonth)) {
							double billCost = bills.getCost();
							totalBills += billCost;
						}
					}
				}

				for (Calculable calc : calculableList) {
					if (calc instanceof Employee) {
						Employee employees = (Employee) calc;
						double monthlySalary = employees.getCost();
						totalEmployeeCost += monthlySalary;
					}
				}

				double endOfMonthBalance = totalIncome - totalBills - totalEmployeeCost;
				System.out.println("Total monthly income: " + totalIncome);
				System.out.println("Total monthly bills due: " + totalBills);
				System.out.println("Total monthly employee cost: " + totalEmployeeCost);
				System.out.println("End of month balance: " + endOfMonthBalance);
				System.out.println();
				break;

			case J:

				List<Service> tempServicesList = new ArrayList<>();
				for (Calculable calculatable : calculableList) {
					if (calculatable instanceof Service) {
						tempServicesList.add((Service) calculatable);
					}
				}

				Collections.sort(tempServicesList, new CostComparator());

				for (Service service : tempServicesList) {
					service.displayServiceInfo();
				}
				System.out.println();
				break;

			case K:

				List<Reservation> reservations = new ArrayList<>();
				for (Calculable service : calculableList) {
					if (service instanceof Reservation) {
						reservations.add((Reservation) service);
					}
				}

				Collections.sort(reservations);

				for (Reservation reservation : reservations) {
					System.out.print("Hotel Name: " + reservation.getHotelName() + ", ");
					reservation.displayServiceInfo();
				}
				System.out.println();
				break;

			case L:
				System.out.println("Goodbye");
				input.close();
				return;

			}
		}
	}
}
