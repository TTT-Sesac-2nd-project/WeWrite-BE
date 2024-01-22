package site.hyundai.wewrite.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "TBL_IMAGE")
public class Image extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_generator")
    @SequenceGenerator(name = "image_seq_generator", sequenceName = "TBL_IMAGE_SEQ", allocationSize = 1)
    @Column(name = "image_id")
    private Long imageId;
    @Column(length = 2000)
    private String originalFileName;
    @Column(length = 2000)
    private String uploadFileName;
    @Column(length = 2000)
    private String uploadFilePath;
    @Column(length = 2000)
    private String uploadFileUrl;
}
