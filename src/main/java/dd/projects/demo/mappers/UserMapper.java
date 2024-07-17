package dd.projects.demo.mappers;

import dd.projects.demo.domain.dto.User.UserSummaryDto;
import dd.projects.demo.domain.entitiy.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserSummaryDto toUserSummaryDto(User user);

    User toUser(UserSummaryDto userSummaryDto);
}
