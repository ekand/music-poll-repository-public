package org.perscholas.musicpollwebsite.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "poll")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title")
    @NonNull
    private String title;

    @ManyToMany(targetEntity=Song.class)
    @JoinTable(name="poll_song",
            joinColumns = @JoinColumn(name="poll_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    @ToString.Exclude
    @NonNull
    List<Song> songList = new ArrayList<>();

    @OneToMany(targetEntity=Vote.class)
    @JoinColumn(name="poll_id")
    @ToString.Exclude
    List<Vote> voteList = new ArrayList<>();

    public Poll(String title, List<Song> songList) {
        this.songList = songList;
        this.title = title;
    }

    public Poll() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Poll poll = (Poll) o;
        return Objects.equals(id, poll.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}