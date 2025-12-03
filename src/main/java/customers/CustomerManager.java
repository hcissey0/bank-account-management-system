package customers;

import utils.ConsoleTablePrinter;
import utils.TablePrinter;
import utils.InputReader;

public class CustomerManager {

    private final Customer[] customers;
    private int customerCount;
    private final TablePrinter printer;
    private static final int MAX_CUSTOMERS = 100;

    public CustomerManager() {
        this.customers = new Customer[MAX_CUSTOMERS];
        this.customerCount = 0;
        this.printer = new ConsoleTablePrinter();
    }

    public void addCustomer(Customer customer) {
        if (customerCount < MAX_CUSTOMERS)
            this.customers[this.customerCount++] = customer;
        else
            System.out.println("Customer limit reached.");
    }
    

    public Customer findCustomer(String customerId) {
        for (int i = 0; i < this.customerCount; i++) {
            if (this.customers[i].getCustomerId().equals(customerId)) {
                return this.customers[i];
            }
        }
        return null;
    }

    public void viewAllCustomers(InputReader inputReader) {
        String[] headers = {
                "CUSTOMER ID",
                "NAME",
                "TYPE",
                "AGE",
                "CONTACT",
                "ADDRESS"
        };

        if (customerCount == 0) {
            System.out.println("No customers available.");
            inputReader.waitForEnter();
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

        System.out.println("Total Customers: " + customerCount);
        inputReader.waitForEnter();
    }

    public int getCustomerCount() {
        return customerCount;
    }
}
