package mappersTest;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserEntityMapperTest {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testFromUserCreateDto() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .name("Anna")
                .lastName("Orange")
                .email("aor@gmail.com")
                .username("aor")
                .build();

        UserEntity userEntity = userMapper.fromUserCreateDto(userCreateDto);
        assertNotNull(userEntity);
        assertEquals("Anna", userEntity.getName());
        assertEquals("Orange", userEntity.getLastName());
        assertEquals("aor@gmail.com", userEntity.getEmail());
        assertEquals("aor", userEntity.getUsername());
    }
}
