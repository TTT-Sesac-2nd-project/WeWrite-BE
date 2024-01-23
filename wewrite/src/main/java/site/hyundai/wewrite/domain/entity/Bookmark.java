package site.hyundai.wewrite.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TBL_BOOKMARK")
public class Bookmark extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmark_seq_generator")
    @SequenceGenerator(name = "bookmark_seq_generator", sequenceName = "TBL_BOOKMARK_SEQ", allocationSize = 1)
    @Column(length = 2000, name = "bookmark_id")
    private Long bookmarkId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Bookmark(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
