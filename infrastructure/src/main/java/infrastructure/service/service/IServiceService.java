package infrastructure.service.service;

import domain.entity.services.Service;

import java.util.List;

/**
 * Created by Никита on 30.12.2016.
 */
public interface IServiceService {

    List<Service> findAll();

    Service save(Service service);

    Service update(Service service);

    void delete(Long id);
}
