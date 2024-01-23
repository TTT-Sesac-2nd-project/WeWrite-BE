package site.hyundai.wewrite.domain.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TBL_COMMENT")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE TBL_COMMENT SET deleted_at = FROM_TZ(CAST(SYSTIMESTAMP AS TIMESTAMP), 'UTC') AT TIME ZONE 'Asia/Seoul' WHERE comment_id = ?")

public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @SequenceGenerator(name = "comment_seq_generator", sequenceName = "TBL_COMMENT_SEQ", allocationSize = 1)
    @Column(length = 2000, name = "comment_id")
    private Long commentId;

    @Lob
    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

}
