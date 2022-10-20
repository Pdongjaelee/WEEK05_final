package com.sparta.post03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);

     }
    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,  //member가 삭제될 떄 Heart 엔티티도 같이 삭제
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<PostLike> hearts;
}

