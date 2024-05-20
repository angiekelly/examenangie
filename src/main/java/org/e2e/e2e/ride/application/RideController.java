package org.e2e.e2e.ride.application;

import org.e2e.e2e.ride.domain.RideService;
import org.e2e.e2e.ride.dto.CreateRideRequestDto;
import org.e2e.e2e.ride.dto.RideInfoResponseDto;
import org.e2e.e2e.ride.dto.BasicRideResponseDto;
import org.e2e.e2e.ride.dto.RidesByUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride")
public class RideController {
    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @PostMapping("/request")
    public ResponseEntity<BasicRideResponseDto> passengerBookRide(@RequestBody CreateRideRequestDto rideRequest) {
        BasicRideResponseDto response = rideService.createRide(rideRequest);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @PatchMapping("/assign/{rideId}")
    public ResponseEntity<RideInfoResponseDto> driverAssignRide(@PathVariable Long rideId) {
        RideInfoResponseDto response = rideService.assignRide(rideId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER') or hasRole('ROLE_DRIVER')")
    @PatchMapping("/{rideId}")
    public ResponseEntity<String> cancelRide(@PathVariable Long rideId) {
        rideService.cancelRide(rideId);
        return ResponseEntity.ok("Ride cancelled");
    }

    @PreAuthorize("hasRole('ROLE_DRIVER') or hasRole('ROLE_PASSENGER')")
    @PatchMapping("/{rideId}/status")
    public ResponseEntity<BasicRideResponseDto> updateRideStatus(@PathVariable Long rideId, @RequestParam String status) {
        BasicRideResponseDto response = rideService.updateRideStatus(rideId, status);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @GetMapping("/user")
    public ResponseEntity<Page<RidesByUserDto>> getRideByUser(@RequestParam int page, @RequestParam int size) {
        Page<RidesByUserDto> response = rideService.getRidesByUser(page, size);
        return ResponseEntity.ok(response);
    }

}
