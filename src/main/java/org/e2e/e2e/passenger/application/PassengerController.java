package org.e2e.e2e.passenger.application;

import org.e2e.e2e.passenger.domain.PassengerService;
import org.e2e.e2e.passenger.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @GetMapping("/me")
    public ResponseEntity<PassengerSelfResponseDTO> getPassenger() {
        return ResponseEntity.ok(passengerService.getPassengerOwnInfo());
    }

    @PreAuthorize("hasRole('ROLE_DRIVER') or hasRole('ROLE_PASSENGER')")
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerInfo(id));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @PatchMapping("/me")
    public ResponseEntity<Void> updatePassenger(@RequestBody PatchPassengerInfoDto passengerSelfResponseDTO) {
        passengerService.updatePassenger(passengerSelfResponseDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @PostMapping("/places")
    public ResponseEntity<Void> addPassengerPlace(@RequestBody NewPassengerLocationDTO passengerLocationResponseDTO) {
        String location = passengerService.addPassengerPlace(passengerLocationResponseDTO);
        URI locationHeader = URI.create(location);
        return ResponseEntity.created(locationHeader).build();
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @GetMapping("/places")
    public ResponseEntity<List<PassengerLocationResponseDTO>> getPassengerPlaces() {
        return ResponseEntity.ok(passengerService.getPassengerPlaces());
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @DeleteMapping("/places/{coordinateId}")
    public ResponseEntity<Void> deletePassengerPlace(@PathVariable Long coordinateId) {
        passengerService.deletePassengerPlace(coordinateId);
        return ResponseEntity.noContent().build();
    }
}
