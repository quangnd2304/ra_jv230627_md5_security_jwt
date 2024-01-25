package ra.jv230627_jwt_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.jv230627_jwt_security.model.ERoles;
import ra.jv230627_jwt_security.model.Roles;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(ERoles eRoles);
}
