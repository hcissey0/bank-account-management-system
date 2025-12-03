package customers;

/**
 * The type Regular customer.
 */
public class RegularCustomer extends Customer {

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
        System.out.println("+------------------+");
        System.out.println("| Customer Details |");
        System.out.println("+------------------+");
        System.out.println("Customer Number: " + this.getCustomerId());
        System.out.println("Name: " + this.getName());
        System.out.println("Age: " + this.getAge());
        System.out.println("Contact: " + this.getContact());
        System.out.println("Address: " + this.getAddress());
        System.out.println("Type: " + this.getCustomerType());
        System.out.println("+--------------------------+");
    }

    @java.lang.Override
    public String getCustomerType() {
        return "Regular";
    }
}
