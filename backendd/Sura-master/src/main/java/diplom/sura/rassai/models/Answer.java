package diplom.sura.rassai.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_answer")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Answer extends BaseEntity {
    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;

    @OneToMany(mappedBy = "answer")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "answer")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Dislike> dislikes = new HashSet<>();
}
