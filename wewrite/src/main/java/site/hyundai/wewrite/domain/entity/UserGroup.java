package site.hyundai.wewrite.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TBL_USER_GROUP")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_group_seq_generator")
    @SequenceGenerator(name = "user_group_seq_generator", sequenceName = "TBL_USER_GROUP_SEQ", allocationSize = 1)
    @Column(name = "user_group_id")
    private Long userGroupId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="group_id", nullable = false)
    private Group group;
}
