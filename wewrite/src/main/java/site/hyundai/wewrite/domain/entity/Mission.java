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
@Table(name = "TBL_MISSION")
public class Mission extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mission_seq_generator")
    @SequenceGenerator(name = "mission_seq_generator", sequenceName = "TBL_MISSION_SEQ", allocationSize = 1)
    @Column(name = "mission_id")
    private Long missionId;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="group_id")
    private Group group;

    @Column(length = 2000,name = "mission_name")
    @NotNull
    private String missionName;

    @Column(length = 2000,name = "mission_loc")
    @NotNull
    private String missionLoc;




    @Column(length = 2000,name = "mission_card_color")
    @NotNull
    private String missionCardColor;


}
