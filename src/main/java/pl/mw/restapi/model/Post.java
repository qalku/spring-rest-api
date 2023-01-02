package pl.mw.restapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private LocalDateTime created;

    // jeden do wielu relacja
    @OneToMany(cascade = CascadeType.REMOVE)                     // przy update gdybysmy chcieli usunac komentarze/relacje to @OneToMany(orphanRemoval = true), przy usuwaniu by usunac relacje dajemy cascade = CascadeType.ALL
    @JoinColumn( name = "postId", updatable = false, insertable = false) // nazwa pola w bazie danych post_id zamieniona na encje, aby nie usuwac komentarzy ustawiamy updatable=false
    private List<Comment> comment;
}
