package site.hyundai.wewrite.domain.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "TBL_GROUP_IMAGE")
public class GroupImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_image_seq_generator")
    @SequenceGenerator(name = "group_image_seq_generator", sequenceName = "TBL_GROUP_IMAGE_SEQ", allocationSize = 1)
    @Column(length = 2000, name = "group_image_id")
    private Long groupImageId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

}
