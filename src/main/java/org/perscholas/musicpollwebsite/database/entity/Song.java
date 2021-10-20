package org.perscholas.musicpollwebsite.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "song")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "title")
    @NonNull
    private String title;
    // --
    @ManyToMany(targetEntity=Song.class, fetch = FetchType.EAGER)
    @JoinTable(name="poll_song",
            joinColumns = @JoinColumn(name="poll_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    @ToString.Exclude
    List<Song> songList = new ArrayList<>();

    @ManyToMany(targetEntity=Poll.class)
    @JoinTable(name="poll_song",
            joinColumns = @JoinColumn(name="song_id"),
            inverseJoinColumns = @JoinColumn(name="poll_id"))
    List<Poll> pollList = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}