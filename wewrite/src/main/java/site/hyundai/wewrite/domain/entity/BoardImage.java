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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmark_seq_generator")
    @SequenceGenerator(name = "bookmark_seq_generator", sequenceName = "TBL_BOOKMARK_SEQ", allocationSize = 1)
    @Column(name = "board_image_id")
    private Long boardImageId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;
}
