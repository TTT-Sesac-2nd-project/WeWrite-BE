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
@Table(name = "TBL_BOOKMARK")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmark_seq_generator")
    @SequenceGenerator(name = "bookmark_seq_generator", sequenceName = "TBL_BOOKMARK_SEQ", allocationSize = 1)
    @Column(length = 2000, name = "bookmark_id")
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
