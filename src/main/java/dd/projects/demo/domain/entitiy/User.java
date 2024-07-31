package dd.projects.demo.domain.entitiy;

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
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstName;
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastName;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "phonenumber", length = 20, nullable = true)
    private String phoneNumber;
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defaultdeliveryaddress_id", nullable = true)
    private Address defaultDeliveryAddress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defaultbillingaddress_id", nullable = true)
    private Address defaultBillingAddress;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> userCarts;
}
