package diplom.sura.rassai.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

}
