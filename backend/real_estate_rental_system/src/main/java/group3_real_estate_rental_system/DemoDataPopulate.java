package group3_real_estate_rental_system;

import group3_real_estate_rental_system.booking.BookingRepository;
import group3_real_estate_rental_system.booking.BookingService;
import group3_real_estate_rental_system.booking.BookingStatus;
import group3_real_estate_rental_system.booking.dto.BookingDTO;
import group3_real_estate_rental_system.booking.entity.Booking;
import group3_real_estate_rental_system.User.Role;
import group3_real_estate_rental_system.User.UserService;
import group3_real_estate_rental_system.User.dto.UserRequest;
import group3_real_estate_rental_system.User.entity.User;
import group3_real_estate_rental_system.common.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;


@Profile("dev")
@Configuration
public class DemoDataPopulate {

    BookingRepository bookingRepository;
    UserService userService;
    BookingService bookingService;

    public DemoDataPopulate(BookingRepository bookingRepository, UserService userService, BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    // Method to create a user with a specific role
    private static User createUserWithRole(UserService userService, String firstName, String lastName, String userName, String email, String phoneNumber, String photo, Role role) {
        // Set the address (default or fetched from elsewhere)
        Address address = new Address("Default Street", "City", "State", "12345", "Country");

        // Create a new UserDTO with the provided information
        UserRequest userDTO = new UserRequest(firstName, lastName, userName, email, phoneNumber, "123", role, address);
        userDTO.setRole(role); // Set the role for the user

        // Call the existing service method to add the user
        userService.addUser(userDTO, null);

        return userService.getUserByUsername(userName);
    }

    @Bean
    public Object dataPopulate() {
        System.out.println("Welcome to RealEstateRentalSystem");
        try {

            // Example 1: Create Admin user
            User admin = createUserWithRole(userService, "John", "Doe", "admin", "john.doe@example.com", "123-456-7890", "photo.jpg", Role.ROLE_ADMIN);

            // Example 2: Create Tenant user
            User tenant = createUserWithRole(userService, "Jane", "Smith", "ten", "jane.smith@example.com", "987-654-3210", "tenant-photo.jpg", Role.ROLE_TENANT);

            // Example 3: Create Property Owner user
            User propertiesOwner = createUserWithRole(userService, "Mike", "Johnson", "pro", "mike.johnson@example.com", "555-555-5555", "owner-photo.jpg", Role.ROLE_PROPERTIES_OWNER);


            Booking booking1 = new Booking();
            booking1.setTenant(tenant);
            booking1.setBookingDate(LocalDateTime.now());
            booking1.setBookingStatus(BookingStatus.PENDING);

            Booking booking2 = new Booking();
            booking2.setTenant(tenant);
            booking2.setBookingDate(LocalDateTime.now().plusDays(1));
            booking2.setBookingStatus(BookingStatus.PENDING);

            Booking booking3 = new Booking();
            booking3.setTenant(tenant);
            booking3.setBookingDate(LocalDateTime.now().plusDays(1));
            booking3.setBookingStatus(BookingStatus.PENDING);

            bookingRepository.save(booking1);
            bookingRepository.save(booking2);
            bookingRepository.save(booking3);
            System.out.println("Dummy Bookings Created");

            List<BookingDTO> bookings = bookingService.getBookingByTenantId(tenant.getId());
            System.out.println(bookings);
            bookings.stream().forEach(System.out::println);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
