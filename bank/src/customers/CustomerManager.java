package customers;

import utils.ConsoleTablePrinter;
import utils.TablePrinter;

import java.util.Scanner;
import java.util.logging.Logger;

public class CustomerManager {
    private static final Logger logger = Logger.getLogger(CustomerManager.class.getName());
    private final Customer[] customers;
    private int customerCount;
    private final TablePrinter printer;

    public CustomerManager() {
        this.customers = new Customer[100];
        this.customerCount = 0;
        this.printer = new ConsoleTablePrinter();
    }

    public void addCustomer(Customer customer) {
        if (customerCount < customers.length) {
            this.customers[this.customerCount++] = customer;
            logger.info("Customer added: " + customer.getName());
        } else {
            logger.warning("Customer limit reached.");
        }
    }

    public Customer findCustomer(String customerId) {
        for (int i = 0; i < this.customerCount; i++) {
            if (this.customers[i].getCustomerId().equals(customerId)) {
                return this.customers[i];
            }
        }
        return null;
    }

    public void viewAllCustomers(Scanner scanner) {
        String[] headers = {
                "CUSTOMER ID",
                "NAME",
                "TYPE",
                "AGE",
                "CONTACT",
                "ADDRESS"
        };

        if (customerCount == 0) {
            logger.info("No customers available.");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        String[][] data = new String[customerCount][headers.length];
        for (int i = 0; i < customerCount; i++) {
            Customer c = customers[i];
            data[i][0] = c.getCustomerId();
            data[i][1] = c.getName();
            data[i][2] = c.getCustomerType();
            data[i][3] = String.valueOf(c.getAge());
            data[i][4] = c.getContact();
            data[i][5] = c.getAddress();
        }

        printer.printTable(headers, data);

        logger.info("Total Customers: " + customerCount);
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public int getCustomerCount() {
        return customerCount;
    }
}
