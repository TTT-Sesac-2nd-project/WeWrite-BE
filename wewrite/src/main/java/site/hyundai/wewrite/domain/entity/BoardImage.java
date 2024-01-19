package site.hyundai.wewrite.domain.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "TBL_BOARD_IMAGE")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_image_seq_generator")
    @SequenceGenerator(name = "board_image_seq_generator", sequenceName = "TBL_BOARD_IMAGE_SEQ", allocationSize = 1)
    @Column(name = "board_image_id")
    private Long boardImageId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;
}
