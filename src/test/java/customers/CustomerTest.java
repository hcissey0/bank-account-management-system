package customers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private TestCustomer customer;

    // Concrete implementation for testing abstract Customer class
    private static class TestCustomer extends Customer {
        TestCustomer(String name, int age, String contact, String address) {
            this.setName(name);
            this.setAge(age);
            this.setContact(contact);
            this.setAddress(address);
        }

        @Override
        public void displayCustomerDetails() {
        }

        @Override
        public String getCustomerType() {
            return "Test";
        }
    }

    @BeforeEach
    void setUp() {
        customer = new TestCustomer("John Doe", 30, "1234567890", "123 Main St");
    }

    @Test
    void testCustomerCreation() {
        assertNotNull(customer.getCustomerId());
        assertTrue(customer.getCustomerId().startsWith("CUS"));
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("John Doe", customer.getName());
        assertEquals(30, customer.getAge());
        assertEquals("1234567890", customer.getContact());
        assertEquals("123 Main St", customer.getAddress());

        customer.setName("Jane Doe");
        assertEquals("Jane Doe", customer.getName());

        customer.setAge(31);
        assertEquals(31, customer.getAge());

        customer.setContact("0987654321");
        assertEquals("0987654321", customer.getContact());

        customer.setAddress("456 Elm St");
        assertEquals("456 Elm St", customer.getAddress());
    }
}
