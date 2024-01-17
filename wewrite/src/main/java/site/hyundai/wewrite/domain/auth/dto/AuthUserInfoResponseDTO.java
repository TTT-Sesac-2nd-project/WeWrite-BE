package site.hyundai.wewrite.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserInfoResponseDTO {
    private String userId;
    private String userName;
    private String userImg;
}
