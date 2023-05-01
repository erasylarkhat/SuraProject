package diplom.sura.rassai.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_dislike")
@Getter
@Setter
public class Dislike extends BaseEntity {
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Answer answer;

    @ManyToOne
    private User user;
}
