package site.hyundai.wewrite.domain.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TBL_GROUP")
public class Group extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq_generator")
    @SequenceGenerator(name = "group_seq_generator", sequenceName = "TBL_GROUP_SEQ", allocationSize = 1)
    @Column(name = "group_id")
    private Long groupId;

    @Column(length = 2000)
    @NotNull
    private String groupName;

    @Column(length = 2000)
    @NotNull
    private String groupCode;

}
