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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_generator")
    @SequenceGenerator(name = "image_seq_generator", sequenceName = "TBL_IMAGE_SEQ", allocationSize = 1)
    @Column(name = "image_id")
    private Long imageId;
    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadFileUrl;
}
