package infrastructure.repository;

import domain.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by admin on 15.11.2016.
 */
public interface IRoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Query("DELETE FROM Role r WHERE r.user.id = :id")
    void deleteRolesByUserId(@Param("id") Long id);

}
