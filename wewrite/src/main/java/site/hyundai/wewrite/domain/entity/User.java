package site.hyundai.wewrite.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TBL_USER")
public class User extends Timestamped {

    @Id
    @Column(length = 2000, unique = true, name = "user_id")
    @NotNull
    @Size(max = 2000)
    private String userId;

    @Column(length = 100)
    @NotNull
    @Size(max = 100)
    private String userName;

    @Column(length = 2000)
    @NotNull
    @Size(max = 2000)
    private String userEmail;

    @Column(length = 2000)
    @NotNull
    @Size(max = 2000)
    private String userImage;
}
