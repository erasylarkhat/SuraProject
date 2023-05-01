package diplom.sura.rassai.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "t_like")
@Getter
@Setter
public class Like extends BaseEntity{

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Answer answer;

    @ManyToOne
    private User user;
}
