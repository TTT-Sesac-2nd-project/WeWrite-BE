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
@Table(name = "TBL_EMOTION")
public class Emotion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emotion_seq_generator")
    @SequenceGenerator(name = "emotion_seq_generator", sequenceName = "TBL_EMOTION_SEQ", allocationSize = 1)
    @Column(name = "emotion_id")
    private Long emotionId;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id")
    //@Column(name = "board_writer" ,length=2000)
    private User user;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="board_id")
    //@Column(name = "board_writer" ,length=2000)
    private Board board;

    @Column(length = 2000,name = "emotion_type")
    @Enumerated(EnumType.STRING)
    private EmotionStatus emotionType;
}
