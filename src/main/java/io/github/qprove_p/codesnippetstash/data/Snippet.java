package io.github.qprove_p.codesnippetstash.data;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@Log4j2
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="snippet_tag", joinColumns=@JoinColumn(name="snippet_id"), inverseJoinColumns=@JoinColumn(name="tag_id"))
    private List<Tag> tags;
    @Lob
    @Column(columnDefinition="TEXT")
    private String code;
    private boolean favourite = false;

    public void addTag(Tag tag) {
        tags.add(tag);
        log.info("Tag added to: ", this.name);
    }
}
