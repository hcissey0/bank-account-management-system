package customers;

import java.util.logging.Logger;

/**
 * The type Regular customer.
 */
public class RegularCustomer extends Customer {

    private static final Logger logger = Logger.getLogger(RegularCustomer.class.getName());

    /**
     * Instantiates a new Regular customer.
     *
     * @param name    the name
     * @param age     the age
     * @param contact the contact
     * @param address the address
     */
    public RegularCustomer(String name, int age, String contact, String address) {
        this.setName(name);
        this.setAge(age);
        this.setContact(contact);
        this.setAddress(address);
    }

    @java.lang.Override
    public void displayCustomerDetails() {
        logger.info("+------------------+");
        logger.info("| Customer Details |");
        logger.info("+------------------+");
        logger.info("Customer Number: " + this.getCustomerId());
        logger.info("Name: " + this.getName());
        logger.info("Age: " + this.getAge());
        logger.info("Contact: " + this.getContact());
        logger.info("Address: " + this.getAddress());
        logger.info("Type: " + this.getCustomerType());
        logger.info("+--------------------------+");
    }

    @java.lang.Override
    public String getCustomerType() {
        return "Regular";
    }
}
