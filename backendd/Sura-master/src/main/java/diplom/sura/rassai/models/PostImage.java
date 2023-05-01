package diplom.sura.rassai.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_image")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PostImage extends BaseEntity{
    @Lob
    @Column(name="name")
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
}
