package customers;

import java.util.logging.Logger;

public class PremiumCustomer extends Customer {
    private static final Logger logger = Logger.getLogger(PremiumCustomer.class.getName());
    private final double minimumBalance;

    public PremiumCustomer(String name, int age, String contact, String address) {
        this.minimumBalance = 10000.0;

        this.setName(name);
        this.setAge(age);
        this.setContact(contact);
        this.setAddress(address);
    }

    public boolean hasWaivedFees() {
        return true;
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
        logger.info("Minimum Balance: " + this.minimumBalance);
        logger.info("Monthly fee: Waived");
        logger.info("+--------------------------+");
    }

    @java.lang.Override
    public String getCustomerType() {
        return "Premium";
    }
}
