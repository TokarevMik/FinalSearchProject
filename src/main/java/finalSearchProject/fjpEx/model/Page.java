package finalSearchProject.fjpEx.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Page {

    String domain;
    @Getter
    @Transient
    Map<String, Integer> countLemmas = new HashMap<>();

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "path", columnDefinition = "TEXT", nullable = false, unique = true)
    private String path;
    @Column(name = "code", nullable = false)
    private int code;
    @Column(name = "content", columnDefinition = "MEDIUMTEXT",nullable = false)
    private String content;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "site_id",nullable = false)
    private Site site;

}
