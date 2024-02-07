
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	List<Calculable> calculableList = new ArrayList<Calculable>();
	private boolean isHeaderWritten = false;

	public MyFrame() {
		setTitle("Hotel Reservation System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		JButton displayReservationsButton = new JButton("Display Reservations");
		JButton displayExtraServicesButton = new JButton("Display Extra Services");
		JButton displayReservationForCityButton = new JButton("Disp. Res. for City");
		JButton multithreadSearchButton = new JButton("Multithread Search");
		JButton saveReservationsButton = new JButton("Save Reservations");
		JButton loadReservationsButton = new JButton("Load Reservations");

		JPanel firstRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
		firstRow.add(displayReservationsButton);
		firstRow.add(displayExtraServicesButton);

		add(firstRow, BorderLayout.NORTH);

		JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
		secondRow.add(displayReservationForCityButton);
		secondRow.add(multithreadSearchButton);

		add(secondRow, BorderLayout.CENTER);

		JPanel thirdRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
		thirdRow.add(saveReservationsButton);
		thirdRow.add(loadReservationsButton);

		JTextArea textArea = new JTextArea();
		textArea.setRows(14);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);

		JPanel southContainer = new JPanel(new BorderLayout());

		southContainer.add(scrollPane, BorderLayout.CENTER);
		southContainer.add(thirdRow, BorderLayout.SOUTH);

		add(southContainer, BorderLayout.SOUTH);

		createHotelReservationSystem();

		final List<Integer> reservationIDs = new ArrayList<>();

		multithreadSearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				if (calculableList.size() < 8) {
					JOptionPane.showMessageDialog(MyFrame.this,
							"Please create at least 8 reservations before using multithreaded search.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String hotelName = JOptionPane.showInputDialog(MyFrame.this,
						"Type a hotel name for a multithread search:");
				if (hotelName == null || hotelName.isEmpty()) {
					return;
				}

				reservationIDs.clear();
				textArea.append("Reservation ID(s) for " + hotelName + ":\n");

				final int NUMBER_OF_THREADS = 4;
				ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

				int chunkSize = calculableList.size() / NUMBER_OF_THREADS;

				for (int i = 0; i < NUMBER_OF_THREADS; i++) {
					final int startIndex = i * chunkSize;
					final int endIndex = (i + 1) == NUMBER_OF_THREADS ? calculableList.size() : (i + 1) * chunkSize;
					executorService.submit(new Runnable() {
						@Override
						public void run() {
							synchronized (reservationIDs) {
								for (int j = startIndex; j < endIndex; j++) {
									Calculable reserved = calculableList.get(j);
									if (reserved instanceof Reservation) {
										Reservation reservationInfos = (Reservation) reserved;
										if (reservationInfos.getHotelName().equals(hotelName)) {
											reservationIDs.add(j + 1);
										}
									}
								}
							}
						}
					});
				}

				executorService.shutdown();

				try {
					executorService.awaitTermination(10, TimeUnit.SECONDS);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				if (reservationIDs.isEmpty()) {
					textArea.append("No reservations found for " + hotelName + "\n");
				} else {
					for (int id : reservationIDs) {
						textArea.append(id + " ");
					}
					textArea.append("\n");
				}
			}
		});

		displayReservationsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int count = 0;
				for (Calculable reserved : calculableList) {
					if (reserved instanceof Reservation) {
						Reservation reservationInfos = (Reservation) reserved;

						String reservationDisplayInfo = "Reservation at " + reservationInfos.getHotelName()
								+ " starts on " + reservationInfos.getReservationMonth() + " "
								+ reservationInfos.getReservationStart() + " and ends on "
								+ reservationInfos.getReservationEnd() + ".\n";
						textArea.append("ReservationID #" + (++count) + "\n");
						textArea.append(reservationDisplayInfo);

					}
				}
				if (count == 0) {
					JOptionPane.showMessageDialog(MyFrame.this, "No reservations yet.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		displayExtraServicesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				textArea.setText("");

				int count = 0;
				for (Calculable service : calculableList) {
					if (service instanceof Spa) {
						Spa spa = (Spa) service;
						String spaDisplayInfo = "ReservationID #" + (++count) + " has " + spa.getDuration()
								+ " days of " + spa.getServiceType() + " services " + "\n";
						textArea.append(spaDisplayInfo);
					} else if (service instanceof Laundry) {
						Laundry laundry = (Laundry) service;
						String laundryDisplayInfo = "ReservationID #" + Reservation.getReservationID() + " has "
								+ laundry.getNumItems() + " piece(s) assigned for " + laundry.getServiceType()
								+ " Service" + "\n";
						textArea.append(laundryDisplayInfo);
					}
				}
				if (count == 0) {
					JOptionPane.showMessageDialog(MyFrame.this, "No extra services yet.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				textArea.append("\n");
			}
		});

		displayReservationForCityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int count = 0;
				int numOfReservations = Reservation.getReservationID();

				if (numOfReservations == 0) {
					JOptionPane.showMessageDialog(MyFrame.this, "No reservations found for any city.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String city = JOptionPane.showInputDialog(MyFrame.this, "Type a city name for a reservation search:");
				if (city == null)
					return;
				boolean foundReservation = false;
				textArea.append("Reservations for " + city + ":\n");
				for (Calculable service : calculableList) {
					if (service instanceof Reservation) {
						Reservation reservationCity = (Reservation) service;
						if (reservationCity.getCityName().contains(city)) {
							textArea.append("ReservationID #" + (++count) + "\n");
							textArea.append("Reservation at " + reservationCity.getHotelName() + " starts on "
									+ reservationCity.getReservationMonth() + " "
									+ reservationCity.getReservationStart() + " and ends on "
									+ reservationCity.getReservationMonth() + " " + reservationCity.getReservationEnd()
									+ "\n");

							foundReservation = true;
						}
					}
				}
				if (!foundReservation) {
					textArea.append("No reservations found for " + city + "\n");
				}
				textArea.append("\n");
			}
		});

		saveReservationsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (calculableList == null || calculableList.isEmpty()) {
					JOptionPane.showMessageDialog(MyFrame.this, "No reservations yet.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				saveReservations();
			}
		});

		loadReservationsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");

				boolean fileIsEmpty = isFileEmpty();

				if (fileIsEmpty) {
					JOptionPane.showMessageDialog(MyFrame.this, "No saved reservations in file yet.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!fileIsEmpty) {
					calculableList.clear();
					loadReservations();
				}

				int count = 0;
				for (Calculable reserved : calculableList) {
					if (reserved instanceof Reservation) {
						Reservation reservationInfos = (Reservation) reserved;

						String reservationDisplayInfo = "ReservationID #" + (++count) + " at "
								+ reservationInfos.getHotelName() + " starts on "
								+ reservationInfos.getReservationMonth() + " " + reservationInfos.getReservationStart()
								+ " and ends on " + reservationInfos.getReservationEnd() + ".\n";
						textArea.append(reservationDisplayInfo);

					}
				}

			}
		});

	}

	private void saveReservations() {

		try (FileWriter writer = new FileWriter("reservations.csv")) {

			if (isFileEmpty() && !isHeaderWritten) {
				writer.write("CityName,HotelName,ReservationMonth,ReservationStart,ReservationEnd\n");
				isHeaderWritten = true;
			}

			for (Calculable calculable : calculableList) {
				if (calculable instanceof Reservation) {
					Reservation reservation = (Reservation) calculable;

					writer.write(reservation.toCsv() + "\n");
				}
			}
			JOptionPane.showMessageDialog(null, "Saved!", "Information", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An error occurred while saving reservations.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void loadReservations() {

		isHeaderWritten = true;
		try (BufferedReader reader = new BufferedReader(new FileReader("reservations.csv"))) {
			String line;

			while ((line = reader.readLine()) != null) {
				if (isHeaderWritten) {
					isHeaderWritten = false;
					continue;
				}

				Reservation reservation = Reservation.fromCsv(line);
				calculableList.add(reservation);
			}

			JOptionPane.showMessageDialog(null, "Reservations loaded successfully!", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An error occurred while loading reservations.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean isFileEmpty() {
		File file = new File("reservations.csv");
		return !file.exists() || file.length() == 0;
	}

	private void createHotelReservationSystem() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		fileMenu.add(exitMenuItem);

		JMenu newMenu = new JMenu("New");
		JMenuItem reservationMenuItem = new JMenuItem("Reservation");

		reservationMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String cityName = null;
				while (cityName == null || cityName.isEmpty()) {
					cityName = JOptionPane.showInputDialog("Enter City Name:");
					if (cityName == null)
						return;
					if (cityName == null || cityName.isEmpty()) {
						JOptionPane.showMessageDialog(null, "City name cannot be empty. Please try again.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				String hotelName = null;
				while (hotelName == null || hotelName.isEmpty()) {
					hotelName = JOptionPane.showInputDialog("Enter Hotel Name:");
					if (hotelName == null)
						return;

					else if (hotelName.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Hotel name cannot be empty. Please try again.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				String reservationMonth = null;
				boolean isValidMonth = false;
				while (!isValidMonth) {
					reservationMonth = JOptionPane.showInputDialog("Enter Reservation Month:");
					if (reservationMonth == null) {
						return;
					}
					for (Month month : Month.values()) {
						if (month.getSelection().equals(reservationMonth)) {
							isValidMonth = true;
							break;
						}
					}
					if (!isValidMonth) {
						JOptionPane.showMessageDialog(null, "Invalid input for reservation month. Please try again.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}

				int reservationStart = 0;
				boolean isValidStart = false;
				while (!isValidStart) {
					String startInput = JOptionPane.showInputDialog("Enter Reservation Start (1-30):");
					if (startInput == null) {
						return;
					}

					try {
						reservationStart = Integer.parseInt(startInput);
						if (reservationStart < 1 || reservationStart > 30) {
							throw new IllegalArgumentException();
						}
						isValidStart = true;
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid input for reservation start day. Please enter a numeric value.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid input for reservation start day. Please enter a value between 1 and 30.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}

				int reservationEnd = 0;
				boolean isValidEnd = false;
				while (!isValidEnd) {
					String endInput = JOptionPane
							.showInputDialog("Enter Reservation End (" + reservationStart + "-30):");
					if (endInput == null) {
						return;
					}

					try {
						reservationEnd = Integer.parseInt(endInput);

						if (reservationEnd < reservationStart || reservationEnd > 30) {
							throw new IllegalArgumentException();
						}
						isValidEnd = true;
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid input for reservation end day. Please enter a numeric value.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid input for reservation end day. Please enter a value between "
										+ reservationStart + " and 30.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}

				Reservation reservationList = new Reservation(cityName, hotelName, reservationMonth, reservationStart,
						reservationEnd);

				calculableList.add(reservationList);
				reservationList.setCustomerID(Reservation.getReservationID());

				JOptionPane.showMessageDialog(null, "Reservation created successfully!");

			}
		});
		JMenuItem extraServiceMenuItem = new JMenuItem("Extra Service");

		extraServiceMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String serviceType = JOptionPane.showInputDialog(null,
						"Please select one of the extra services from below:\n 1. Laundry Service\n 2. Spa Service");

				if (serviceType == null) {
					return;
				}

				int customerID = 0;
				boolean isValidCustomerID = false;
				while (!isValidCustomerID) {
					String customerIDInput = JOptionPane.showInputDialog("Type the reservation ID:");
					if (customerIDInput == null) {
						return;
					}
					try {
						customerID = Integer.parseInt(customerIDInput);
						isValidCustomerID = true;
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid input for reservation ID. Please enter a numeric value.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				boolean roomReserved = false;
				if (customerID <= Reservation.getReservationID()) {
					roomReserved = true;
				}

				if (!roomReserved) {
					JOptionPane.showMessageDialog(null, "Cannot add laundry or spa service before reserving a room!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (serviceType.equals("1")) {
					int numItems;
					boolean isValidNumItems = false;
					while (!isValidNumItems) {
						String numItemsInput = JOptionPane.showInputDialog("How many pieces of clothing?");
						if (numItemsInput == null) {
							return;
						}

						try {
							numItems = Integer.parseInt(numItemsInput);
							if (numItems <= 0) {
								throw new IllegalArgumentException("Clothing count must be positive.");
							}
							isValidNumItems = true;
							Laundry laundry = new Laundry();
							laundry.setCustomerID(customerID);
							laundry.setNumItems(numItems);
							calculableList.add(laundry);
							JOptionPane.showMessageDialog(null, "Laundry service added successfully!");
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,
									"Invalid input for clothing count. Please enter a numeric value.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (IllegalArgumentException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (serviceType.equals("2")) {
					int duration;
					boolean isValidDuration = false;
					while (!isValidDuration) {
						String durationInput = JOptionPane.showInputDialog("How many days?");
						if (durationInput == null) {
							return;
						}

						try {
							duration = Integer.parseInt(durationInput);
							if (duration < 1) {
								throw new IllegalArgumentException("Day count must be a positive integer.");
							}
							isValidDuration = true;
							Spa spa = new Spa();
							spa.setCustomerID(customerID);
							spa.setDuration(duration);
							calculableList.add(spa);
							JOptionPane.showMessageDialog(null, "Spa service added successfully!");
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,
									"Invalid input for day count. Please enter a numeric value.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (IllegalArgumentException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		newMenu.add(reservationMenuItem);
		newMenu.add(extraServiceMenuItem);

		JMenu helpMenu = new JMenu("Help");

		JMenuItem contentsMenuItem = new JMenuItem("Contents");

		contentsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String Contents = "Instructions Contents:\n"
						+ "1. Start by making a room reservation using the 'New --> Reservation' option.\n"
						+ "2. Use the 'Display Reservations' option to view the existing reservations.\n"
						+ "3. Add extra services, laundry or spa using the 'New --> Extra Services' option.\n"
						+ "4. Explore other features";

				JOptionPane.showMessageDialog(null, Contents, "Instructions ", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem aboutMenuItem = new JMenuItem("About");

		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String developerInfo = "Name: Halil Ertan\n" + "Student ID: 20220702109\n"
						+ "Email: halil.ertan3@std.yeditepe.edu.tr";

				JOptionPane.showMessageDialog(null, developerInfo, "Developer Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(contentsMenuItem);
		helpMenu.add(aboutMenuItem);

		menuBar.add(fileMenu);
		menuBar.add(newMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
	}

}
