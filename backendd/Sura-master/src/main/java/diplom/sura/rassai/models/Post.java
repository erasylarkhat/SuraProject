package diplom.sura.rassai.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_post")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Post extends BaseEntity{

    @Lob
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "post")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<PostImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "post")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "post")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Dislike> dislikes = new HashSet<>();

    public Post orElseThrow() {
        return null;
    }
}
