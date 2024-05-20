package org.e2e.e2e.empleado.domain;


import org.e2e.e2e.auth.utils.AuthorizationUtils;
import org.e2e.e2e.empleado.dto.EmpleadoResponseDto;
import org.e2e.e2e.empleado.dto.NewEmpleadoInfoDto;
import org.e2e.e2e.empleado.infrastructure.EmpleadoRepository;
import org.e2e.e2e.exceptions.ResourceNotFoundException;
import org.e2e.e2e.passenger.exceptions.UnauthorizeOperationException;
import org.e2e.e2e.vehicle.domain.Vehicle;
import org.e2e.e2e.vehicle.domain.VehicleService;
import org.e2e.e2e.vehicle.dto.VehicleBasicDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    AuthorizationUtils authorizationUtils;
    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.modelMapper = new ModelMapper();
    }

    public EmpleadoResponseDto getEmpleadoInfo(Long id) {
    	
    	if(!authorizationUtils.isAdminOrResourceOwner(id))
    		throw new UnauthorizeOperationException("Not authorize for resource "); 

        org.e2e.e2e.driver.domain.Empleado driver = empleadoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        EmpleadoResponseDto response = new EmpleadoResponseDto();
        response.setId(driver.getId());
        response.setCategory(driver.getCategory());
        response.setFirstName(driver.getFirstName());
        response.setLastName(driver.getLastName());
        response.setTrips(driver.getTrips());
        response.setAvgRating(driver.getAvgRating());

        return response;
    }

    public EmpleadoResponseDto getDriverOwnInfo() {
        // Here get the current user identifier (email) using Spring Security
    	String username = authorizationUtils.getCurrentUserEmail();
    	if(username==null)
    		throw new UnauthorizeOperationException("Not authorize for resource "); 

        org.e2e.e2e.driver.domain.Empleado driver = driverRepository
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

        org.e2e.e2e.driver.domain.Empleado driver = driverRepository
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

        org.e2e.e2e.driver.domain.Empleado driver = driverRepository
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
