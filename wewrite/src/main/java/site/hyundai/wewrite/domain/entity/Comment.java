package site.hyundai.wewrite.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TBL_COMMENT")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @SequenceGenerator(name = "comment_seq_generator", sequenceName = "TBL_COMMENT_SEQ", allocationSize = 1)
    @Column(length = 2000, name = "comment_id")
    private Long commentId;

    @Lob
    @Column(nullable = false)
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

}
