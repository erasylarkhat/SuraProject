package diplom.sura.rassai.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_question")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Question extends BaseEntity {
    @Lob
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Answer> answers;

    @OneToMany(mappedBy = "question")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Answer> answers = new ArrayList<>();


}
