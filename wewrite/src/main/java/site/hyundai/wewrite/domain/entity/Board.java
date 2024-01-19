package site.hyundai.wewrite.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @NotNull
    @Size(max = 2000)
    private LocalDateTime boardCreatedDate;

    @Column(name = "board_view")
    @NotNull
    @Size(max = 2000)
    @ColumnDefault("0")
    private Long boardView;

    @Column(name = "board_lat")
    @NotNull
    @Size(max = 2000)
    private String boardLat;

    @Column(name = "board_long")
    @NotNull
    @Size(max = 2000)
    private String boardLong;

}
