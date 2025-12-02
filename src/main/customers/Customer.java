package main.customers;

public abstract class Customer {
    private static int customerCounter = 0;
    private final String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    Customer() {
        this.customerId = generateCustomerId();
    }

    private static String generateCustomerId() {
        // "CUS" +
        return "CUS" + String.format("%03d", ++customerCounter);
    }

    // getters


    public static int getCustomerCounter() {
        return customerCounter;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public abstract void displayCustomerDetails();

    public abstract String getCustomerType();

}
