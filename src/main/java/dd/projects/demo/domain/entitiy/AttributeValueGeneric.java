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
@Table(name = "ATTRIBUTE_VALUE_GENERIC")
public class AttributeValueGeneric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", length = 255, nullable = false)
    private String value;

    @OneToMany(mappedBy = "value", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttributeValueConcrete> attributeValues;
}
