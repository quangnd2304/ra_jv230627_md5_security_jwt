package ra.jv230627_jwt_security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Roles {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private ERoles name;
}
