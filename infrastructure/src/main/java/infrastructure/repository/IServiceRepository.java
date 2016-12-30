package infrastructure.repository;

import domain.entity.services.Service;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Никита on 30.12.2016.
 */
public interface IServiceRepository extends JpaRepository<Service,Long> {
}
