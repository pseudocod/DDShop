package dd.projects.demo.domain.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CartEntry> cartEntries = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    @Enumerated(EnumType.STRING)
    @Column(name = "paymenttype", length = 50, nullable = false)
    private PaymentType paymentType;
    @Column(name = "totalprice", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;
    @Column(name = "orderdate", nullable = false)
    private LocalDate orderDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryaddress_id", nullable = false)
    private Address deliveryAddress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoiceaddress_id", nullable = false)
    private Address invoiceAddress;
}
