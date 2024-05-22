import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Theatre {

    private static int[][] seatingRows = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private static ArrayList<Ticket> ticketArrayList = new ArrayList<Ticket>();
    // an array list of tickets to save all the Tickets.

    public static void main(String[] args) {
        System.out.println("Welcome to the New Theatre");
        Scanner getInput = new Scanner(System.in);

        while (true) {
            System.out.println("\n-------------------------------------------------");
            System.out.println("Please select an option:");
            System.out.println("1) Buy a ticket");
            System.out.println("2) Print seating area");
            System.out.println("3) Cancel ticket");
            System.out.println("4) List available seats");
            System.out.println("5) Save to file");
            System.out.println("6) Load from file");
            System.out.println("7) Print ticket information and total price");
            System.out.println("8) Sort tickets by price");
            System.out.println("\t0) Quit");
            System.out.println("-------------------------------------------------");
            System.out.print("\nEnter option: ");
            try {
                int choice = getInput.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Thank you for joining with our Theatre");
                        return;
                    case 1:
                        buyTicket();
                        break;
                    case 2:
                        print_seating_area();
                        break;
                    case 3:
                        cancel_ticket();
                        break;
                    case 4:
                        show_available();
                        break;
                    case 5:
                        save();
                        break;
                    case 6:
                        load();
                        break;
                    case 7:
                        show_tickets_info();
                        break;
                    case 8:
                        sort_tickets();
                        break;
                    default:
                        System.out.println("Invalid option. please enter a valid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer for the menu option.");
                getInput.nextLine(); // clear scanner buffer
            }
        }
    }

    public static void buyTicket() {
        Scanner getInput = new Scanner(System.in);
        boolean continueBooking = true;
        while (continueBooking) {
            try {
                System.out.print("\nEnter row number: ");
                int rowNumber = getInput.nextInt();
                System.out.print("Enter seat number: ");
                int seatNumber = getInput.nextInt();

                if (rowNumber < 1 || rowNumber > 3) {
                    System.out.println("Invalid row number.");
                } else if (seatNumber < 1 || seatNumber > seatingRows[rowNumber - 1].length) {
                    System.out.println("Invalid seat number.");
                } else if (seatingRows[rowNumber - 1][seatNumber - 1] == 1) {
                    System.out.println("Seat already sold.");
                } else {
                    try {
                        System.out.print("Please enter your name: ");
                        String name = getInput.next();
                        System.out.print("Please enter your surname: ");
                        String surname = getInput.next();
                        System.out.print("Please enter your email: ");
                        String email = getInput.next();
                        double price = (rowNumber == 1 ? 50 : (rowNumber == 2 ? 100 : 150));
                        double seatPrice = 0;
                        do {
                            System.out.println("Row1 Seat price = $50");
                            System.out.println("Row2 Seat price = $100");
                            System.out.println("Row3 Seat price = $150");
                            System.out.print("Enter seat price: ");
                            seatPrice = getInput.nextDouble();
                        } while (seatPrice != price);

                        Person person = new Person(name, surname, email);
                        double ticketPrice = seatPrice;
                        Ticket ticket = new Ticket(rowNumber, seatNumber, ticketPrice, person);
                        seatingRows[rowNumber - 1][seatNumber - 1] = 1;
                        ticketArrayList.add(ticket);
                        System.out.println("Ticket purchased for row " + rowNumber + ", seat " + seatNumber + ".");

                        Scanner input = new Scanner(System.in);
                        System.out.println("Do you want to buy more tickets? Enter 1 to buy more tickets, or 2 to continue.");
                        int input2 = input.nextInt();
                        if (input2 == 1) {
                            continueBooking = true;
                        } else {
                            continueBooking = false;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please try again.");
                        getInput.nextLine(); // consume the invalid input
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for both row and seat number.");
                getInput.nextLine(); // clear scanner buffer
            }
        }
    }


    public static void print_seating_area() {

        System.out.println("\n    ***********");
        System.out.println("    *  STAGE  *");
        System.out.println("    ***********");


        for (int i = 0; i < seatingRows.length; i++) {
            if(seatingRows[i].length==12){
                System.out.print("    ");
            } else if (seatingRows[i].length==16) {
                System.out.print("  ");
            }
            for (int j = 0; j < seatingRows[i].length; j++) {

                if((seatingRows[i].length/2) == j){
                    System.out.print(" ");
                }

                if (seatingRows[i][j] == 0) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    public static void cancel_ticket() {
        Scanner getInput = new Scanner(System.in);

        System.out.print("\nEnter row number:");
        try {
            int rowNumber = getInput.nextInt() - 1; // Adjust to 0-based index
            if (rowNumber < 0 || rowNumber >= seatingRows.length) {
                System.out.println("Invalid rowNumber number. Please try again.");
                return;
            }

            System.out.print("Enter seat number:");
            try {
                int seatNumber = getInput.nextInt() - 1; // Adjust to 0-based index
                if (seatNumber < 0 || seatNumber >= seatingRows[rowNumber].length) {
                    System.out.println("Invalid seatNumber number. Please try again.");
                    return;
                }

                if (seatingRows[rowNumber][seatNumber] == 0) {
                    System.out.println("This seatNumber is not occupied. Please try again.");
                    return;

                }else{
                    seatingRows[rowNumber][seatNumber] = 0;
                    rowNumber+=1;
                    seatNumber+=1;//makes a seat available again.

                    for (int i = 0; i < ticketArrayList.size(); i++) {
                        Ticket ticket = ticketArrayList.get(i);
                        if (ticket.getRow() == rowNumber && ticket.getSeat() == seatNumber) {
                            ticketArrayList.remove(i);//remove the ticket from the array list of tickets.
                        }
                    }
                    System.out.println("Ticket cancelled successfully!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid seat number. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid row number. Please try again.");
        }
    }


    public static void show_available() {
        for (int i = 0; i < seatingRows.length; i++) {
            System.out.print("\nSeats available in row " + (i + 1) + ": ");
            //loops through each row of the array and checks for seats with a value of 0.
            for (int j = 0; j < seatingRows[i].length; j++) {
                if (seatingRows[i][j] == 0) {
                    System.out.print((j + 1) + ", ");
                }
            }
            System.out.println();
        }
    }

    public static void save() {
        try {
            // saves the 3 arrays with the row’s information in a file.
            FileWriter writer = new FileWriter("seating.txt");
            for (int i = 0; i < seatingRows.length; i++) {
                for (int j = 0; j < seatingRows[i].length; j++) {
                    writer.write(seatingRows[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("\nSeating information saved to file.");
        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving seating information to file.");

        }
    }


    public static void load() {
        try {
            File myObj = new File("seating.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                // loads the saved file and restores the 3 arrays with the row’s information.
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void show_tickets_info() {
        double ticketTotal = 0.0;
        // Loop through the ArrayList using the for loop
        System.out.println("\nTicket information:\n");
        for (Ticket tickets : ticketArrayList) {
            tickets.print();//prints all the information for all tickets.
            System.out.println("_________________________________________");
            ticketTotal += tickets.getPrice();
        }
        System.out.println("Total price of all tickets: £" + ticketTotal);
    }


    public static void sort_tickets() {
        ArrayList<Ticket> sortTicketArrayList = new ArrayList<Ticket>(ticketArrayList);
        sortTicketArrayList.sort(Comparator.comparingDouble(Ticket::getPrice));
         //returns a new list of Tickets sorted by Ticket price in ascending order.

        System.out.println("\nSorted tickets by price (cheapest first):\n");
        for (Ticket tickets : sortTicketArrayList) {
            tickets.print();
            System.out.println("_________________________________________");
        }


    }


}