package finalSearchProject.fjpEx.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "INT NOT NULL AUTO_INCREMENT")
    int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('INDEXING', 'INDEXED', 'FAILED')",nullable = false)
    private  Status status = Status.FAILED;
    @Column(name = "status_time", columnDefinition ="DATETIME NOT NULL", nullable = false)
    private Date status_time;
    @Column(name = "last_error", columnDefinition="TEXT")
    @Lob
    private String last_error;
    @Column(name = "url", nullable = false, columnDefinition = "VARCHAR(255)")
    private String url;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @OneToMany(mappedBy = "site",cascade = CascadeType.ALL)
    List<Page> listPages;
    public void setListPages(List<Page> listPages) {
        if(listPages!=null){
            listPages.forEach(a->a.setSite(this));
        }
        this.listPages = listPages;
    }

}
