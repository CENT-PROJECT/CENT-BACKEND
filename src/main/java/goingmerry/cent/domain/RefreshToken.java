package goingmerry.cent.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payload;

    @OneToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @Builder
    public RefreshToken(Long id, String payload, User user) {
        this.id = id;
        this.payload = payload;
        this.user = user;
    }
}
