package org.e2e.e2e.driver.application;

import org.e2e.e2e.driver.domain.TareaService;
import org.e2e.e2e.driver.dto.EmpleadoResponseDto;
import org.e2e.e2e.driver.dto.NewEmpleadoInfoDto;
import org.e2e.e2e.vehicle.dto.VehicleBasicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final TareaService driverService;

    @Autowired
    public DriverController(TareaService driverService) {
        this.driverService = driverService;
    }


    @PreAuthorize("hasRole('ROLE_DRIVER') or hasRole('ROLE_PASSENGER')")
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> getDriver(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverInfo(id));
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @GetMapping("/me")
    public ResponseEntity<EmpleadoResponseDto> getDriver() {
        return ResponseEntity.ok(driverService.getDriverOwnInfo ());
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateDriverInfo(@PathVariable Long id, @RequestBody NewEmpleadoInfoDto driverInfo) {
        driverService.updateDriverInfo(id, driverInfo);
        return ResponseEntity.ok("Driver info updated");
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @PatchMapping("/{id}/car")
    public ResponseEntity<String> updateDriverCar(@PathVariable Long id, @RequestBody VehicleBasicDto newVehicle) {
        driverService.updateDriverCar(id, newVehicle);
        return ResponseEntity.ok("Driver car updated");
    }

}
