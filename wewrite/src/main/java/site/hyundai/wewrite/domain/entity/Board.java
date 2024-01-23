package site.hyundai.wewrite.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TBL_BOARD")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE TBL_BOARD SET deleted_at = FROM_TZ(CAST(SYSTIMESTAMP AS TIMESTAMP), 'UTC') AT TIME ZONE 'Asia/Seoul' WHERE board_id = ?")
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
    @SequenceGenerator(name = "board_seq_generator", sequenceName = "TBL_BOARD_SEQ", allocationSize = 1)
    @Column(name = "board_id")
    private Long boardId;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="group_id")
    private Group group;

    @Column(length = 2000,name = "board_title")
    @NotNull
    private String boardTitle;

    @Column(length = 2000,name = "board_loc")
    private String boardLoc;

    @Lob
    @Column(length = 2000,name = "board_content")
    @NotNull
    private String boardContent;

    @Column(length = 2000,name = "board_created_date")
    private LocalDateTime boardCreatedDate;

    @Column(name = "board_view")
    @ColumnDefault("0")
    @NotNull
    private Long boardView;

    @Column(name = "board_lat")
    @NotNull
    private String boardLat;

    @Column(name = "board_long")
    @NotNull
    @Size(max = 2000)
    private String boardLong;

}
