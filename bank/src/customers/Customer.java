package customers;

/**
 * The type Customer.
 */
public abstract class Customer {
    private static int customerCounter = 0;
    private final String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    /**
     * Instantiates a new Customer.
     */
    Customer() {
        this.customerId = generateCustomerId();
    }

    private static String generateCustomerId() {
        // "CUS" +
        return "CUS" + String.format("%03d", ++customerCounter);
    }

    // getters


    /**
     * Gets customer counter.
     *
     * @return the customer counter
     */
    public static int getCustomerCounter() {
        return customerCounter;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
// setters
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Display customer details.
     */
    public abstract void displayCustomerDetails();

    /**
     * Gets customer type.
     *
     * @return the customer type
     */
    public abstract String getCustomerType();

}
