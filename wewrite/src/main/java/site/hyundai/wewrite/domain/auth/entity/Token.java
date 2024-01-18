package site.hyundai.wewrite.domain.auth.entity;

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
@Table(name = "TBL_TOKEN")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq_generator")
    @SequenceGenerator(name = "token_seq_generator", sequenceName = "TBL_TOKEN_SEQ", allocationSize = 1)
    @Column(name = "token_id")
    private Long tokenId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2000)
    @NotNull
    @Size(max = 2000)
    private String tokenValue;

}
