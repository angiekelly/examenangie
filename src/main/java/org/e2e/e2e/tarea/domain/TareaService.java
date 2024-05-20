package org.e2e.e2e.tarea.domain;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.e2e.e2e.auth.utils.AuthorizationUtils;
import org.e2e.e2e.driver.dto.EmpleadoResponseDto;
import org.e2e.e2e.driver.dto.NewEmpleadoInfoDto;
import org.e2e.e2e.driver.infrastructure.EmpleadoRepository;
import org.e2e.e2e.exceptions.ResourceNotFoundException;
import org.e2e.e2e.passenger.exceptions.UnauthorizeOperationException;
import org.e2e.e2e.tarea.dto.TareaResponseDto;
import org.e2e.e2e.tarea.infrastructure.TareaRepository;
import org.e2e.e2e.vehicle.domain.Vehicle;
import org.e2e.e2e.vehicle.domain.VehicleService;
import org.e2e.e2e.vehicle.dto.VehicleBasicDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    AuthorizationUtils authorizationUtils;
    @Autowired
    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
        this.modelMapper = new ModelMapper();
    }

    public TareaResponseDto getTareaInfo(Long id) {
    	
    	if(!authorizationUtils.isAdminOrResourceOwner(id))
    		throw new UnauthorizeOperationException("Not authorize for resource "); 

        Tarea tarea = tareaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));

        TareaResponseDto response = new TareaResponseDto();
        response.setId(tarea.getId());
        response.setNombre(tarea.getNombre());
        response.setDescripcion(tarea.getDescripcion());
        response.setFechalimite(tarea.getFechalimite());
        response.setResponsable(tarea.getResponsable());
        return response;
    }

    public EmpleadoResponseDto getDriverOwnInfo() {
        // Here get the current user identifier (email) using Spring Security
    	String username = authorizationUtils.getCurrentUserEmail();
    	if(username==null)
    		throw new UnauthorizeOperationException("Not authorize for resource "); 

        Empleado driver = driverRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found"));

        return getDriverInfo(driver.getId());
    }

    public void deleteDriver(Long id) {
        // Check that the current user is the same as the owner of the resource
    	if(!authorizationUtils.isAdminOrResourceOwner(id))
    		throw new UnauthorizeOperationException("Not authorize for resource "); 
        if (!driverRepository.existsById(id))
            throw new ResourceNotFoundException("Driver not found");

        driverRepository.deleteById(id);
    }

    public void updateDriverInfo(Long id, NewEmpleadoInfoDto driverInfo) {
        // Check that the current user is the same as the owner of the resource
    	if(!authorizationUtils.isAdminOrResourceOwner(id))
    		throw new UnauthorizeOperationException("Not authorize for resource "); 

        Empleado driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        driver.setFirstName(driverInfo.getFirstName());
        driver.setLastName(driverInfo.getLastName());
        driver.setPhoneNumber(driverInfo.getPhoneNumber());

        driverRepository.save(driver);

    }

    public void updateDriverCar(Long id, VehicleBasicDto newVehicle) {
        // Check that the current user is the same as the owner of the resource..
    	if(!authorizationUtils.isAdminOrResourceOwner(id))
    		throw new UnauthorizeOperationException("Not authorize for resource "); 

        Empleado driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        Long vehicleId = driver.getVehicle().getId();
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        vehicle.setModel(newVehicle.getModel());
        vehicle.setLicensePlate(newVehicle.getLicensePlate());
        vehicle.setFabricationYear(newVehicle.getFabricationYear());
        vehicle.setCapacity(newVehicle.getCapacity());

        vehicleService.saveVehicle(vehicle);
    }
}
