package ra.jv230627_jwt_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.jv230627_jwt_security.model.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {
    Optional<Users> findByUserNameAndStatus(String userName,boolean status);
}
