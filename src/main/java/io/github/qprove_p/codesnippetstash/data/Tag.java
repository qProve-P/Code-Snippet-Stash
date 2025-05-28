package io.github.qprove_p.codesnippetstash.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class Tag {

    @Id
    private Long id;
    private String name;
    private String color;
}
