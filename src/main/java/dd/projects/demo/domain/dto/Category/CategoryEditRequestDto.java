package dd.projects.demo.domain.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEditRequestDto {
    private Long id;
    private String name;
    private String description;
}
