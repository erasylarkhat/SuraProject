package diplom.sura.rassai.models;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(updatable = false, name = "created_at", nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @OrderBy("created_at DESC")
    private Date created_at;

}
