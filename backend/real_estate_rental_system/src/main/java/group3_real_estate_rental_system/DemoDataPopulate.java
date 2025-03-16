package group3_real_estate_rental_system;

import group3_real_estate_rental_system.Booking.BookingRepository;
import group3_real_estate_rental_system.Booking.BookingService;
import group3_real_estate_rental_system.Booking.BookingStatus;
import group3_real_estate_rental_system.Booking.dto.BookingDTO;
import group3_real_estate_rental_system.Booking.entity.Booking;
import group3_real_estate_rental_system.User.Role;
import group3_real_estate_rental_system.User.UserRepository;
import group3_real_estate_rental_system.User.UserService;
import group3_real_estate_rental_system.User.dto.UserDTO;
import group3_real_estate_rental_system.User.dto.UserRequest;
import group3_real_estate_rental_system.User.entity.User;
import group3_real_estate_rental_system.common.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Profile("dev")
@Configuration
public class DemoDataPopulate {

    //FIX it
    //TODO
    UserRepository userRepository;
    BookingRepository bookingRepository;
    UserService userService;
    BookingService bookingService;

    public DemoDataPopulate(UserRepository userRepository, BookingRepository bookingRepository, UserService userService, BookingService bookingService) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @Transactional
    @Bean
    public Object dataPopulate() {
        System.out.println("Welcome to RealEstateRentalSystem");

        // Dummy data for Tenant
        User tenant1 = new User();
        tenant1.setFirstName("John");
        tenant1.setLastName("Doe");
        tenant1.setUserName("johndoe");
        tenant1.setPassword("password123");
        tenant1.setAddress(new Address("123 Main St", "New York", "NY", "10001", "USA"));

        User tenant2 = new User();
        tenant2.setFirstName("Jane");
        tenant2.setLastName("Smith");
        tenant2.setUserName("janesmith");
        tenant2.setPassword("password456");
        tenant2.setAddress(new Address("456 Elm St", "Los Angeles", "CA", "90001", "USA"));

        tenant1 = userRepository.save(tenant1);
        tenant2 = userRepository.save(tenant2);

        System.out.println("Dummy Tenants Created");

        // Dummy data for Booking
        Booking booking1 = new Booking();
        booking1.setTenant(tenant1);
        booking1.setBookingDate(LocalDateTime.now());
        booking1.setBookingStatus(BookingStatus.PENDING);

        Booking booking2 = new Booking();
        booking2.setTenant(tenant2);
        booking2.setBookingDate(LocalDateTime.now().plusDays(1));
        booking2.setBookingStatus(BookingStatus.PENDING);

        Booking booking3 = new Booking();
        booking3.setTenant(tenant2);
        booking3.setBookingDate(LocalDateTime.now().plusDays(1));
        booking3.setBookingStatus(BookingStatus.PENDING);

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);
//		tenantRepository.save(tenant1);
        System.out.println("Dummy Bookings Created");

//			//get bookings for a particular tenant
        List<BookingDTO> bookings = bookingService.getBookingByTenantId(tenant1.getId());
        System.out.println(bookings);
        bookings.stream().forEach(System.out::println);


        Address address1 = new Address("123 Main St", "New York", "NY", "10001", "USA");
        Address address2 = new Address("456 Elm St", "Los Angeles", "CA", "90001", "USA");
        Address address3 = new Address("789 Oak St", "Chicago", "IL", "60601", "USA");
        Address address4 = new Address("321 Maple St", "Washington", "DC", "20001", "USA");
        Address address5 = new Address("654 Pine St", "Boston", "MA", "02101", "USA");

        User admin1 = new User("Alice", "Johnson", "alice_admin", "securepass123", "hewwi@", "283933839", "https://www.google.com/imgres?q=image&imgurl=https%3A%2F%2QAA", address1);


        userRepository.saveAll(List.of(admin1));

        // Example 1: Create Admin user
        createUserWithRole(userService, "John", "Doe", "john", "john.doe@example.com", "123-456-7890", "photo.jpg", "ADMIN");

        // Example 2: Create Tenant user
        createUserWithRole(userService, "Jane", "Smith", "jane", "jane.smith@example.com", "987-654-3210", "tenant-photo.jpg", "TENANT");

        // Example 3: Create Property Owner user
        createUserWithRole(userService, "Mike", "Johnson", "mike", "mike.johnson@example.com", "555-555-5555", "owner-photo.jpg", "PROPERTIES_OWNER");


        return null;
    }
    // Method to create a user with a specific role
    private static void createUserWithRole(UserService userService, String firstName, String lastName, String userName, String email, String phoneNumber, String photo, String roleType) {
        // Set the address (default or fetched from elsewhere)
        Address address = new Address("Default Street", "City", "State", "12345", "Country");

        // Determine role from the roleType string
        Role role = Role.valueOf(roleType.toUpperCase());

        // Create a new UserDTO with the provided information
        UserRequest userDTO = new UserRequest(firstName, lastName, userName, email, phoneNumber, photo, "123",role, address);
        userDTO.setRole(role); // Set the role for the user

        // Call the existing service method to add the user
        userService.addUser(userDTO); // This is where the original addUser method is called
    }
}
