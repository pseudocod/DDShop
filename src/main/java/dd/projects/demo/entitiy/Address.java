package dd.projects.demo.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "streetline", length = 255, nullable = false)
    private String streetLine;
    @Column(name = "postalcode", length = 50, nullable = false)
    private String postalCode;
    @Column(name = "city", length = 100, nullable = false)
    private String city;
    @Column(name = "county", length = 100, nullable = false)
    private String county;
    @Column(name = "country", length = 50, nullable = false)
    private String country;
    @OneToMany(mappedBy = "defaultDeliveryAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> defaultDeliveryUsers;
    @OneToMany(mappedBy = "defaultBillingAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> billingAddressUsers;
    @OneToMany(mappedBy = "deliveryAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> deliveryAddresses;
    @OneToMany(mappedBy = "invoiceAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> invoiceAddresses;
}
