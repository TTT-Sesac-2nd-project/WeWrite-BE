package site.hyundai.wewrite.domain.auth.dto.response;

import lombok.Getter;
import lombok.Setter;
import site.hyundai.wewrite.domain.entity.User;

@Getter
@Setter
public class UserResponseDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private String userImage;
    private int numberOfMyGroups;

    public UserResponseDTO(User user, int numberOfMyGroups) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userImage = user.getUserImage();
        this.numberOfMyGroups = numberOfMyGroups;
    }
}
