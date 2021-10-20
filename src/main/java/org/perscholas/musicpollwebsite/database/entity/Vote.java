package org.perscholas.musicpollwebsite.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "vote",
        uniqueConstraints = { @UniqueConstraint(columnNames =  { "poll_id", "voter_user_id" }) })
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "poll_id")
    @NonNull
    private Integer pollId;

    @Column(name = "song_id")
    @NonNull
    private Integer songId;

    @Column(name = "voter_user_id")
    @NonNull
    private Integer voterUserId;

    public Vote() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}