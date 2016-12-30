package infrastructure.service.service;

import domain.entity.services.Service;
import infrastructure.repository.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Никита on 30.12.2016.
 */

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {

    @Autowired
    private IServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Service update(Service service) {
        Service find = serviceRepository.findOne(service.getId());
        find.setName(service.getName());
        return serviceRepository.save(find);
    }

    @Override
    public void delete(Long id) {
        serviceRepository.delete(id);
    }
}
