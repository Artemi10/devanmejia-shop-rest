package devanmejia.models.entities;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="pictures")
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    @Id
    @Column(name="picture_name")
    private String pictureName;
    @Column(name="picture_url")
    private String URL;
    private String pictureContent;


}
