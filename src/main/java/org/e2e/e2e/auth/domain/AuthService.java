package org.e2e.e2e.auth.domain;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.e2e.e2e.auth.dto.JwtAuthResponse;
import org.e2e.e2e.auth.dto.LoginRequest;
import org.e2e.e2e.auth.dto.RegisterRequest;
import org.e2e.e2e.auth.exceptions.UsernameAlreadyExistException;
import org.e2e.e2e.config.JwtService;
import org.e2e.e2e.driver.domain.Empleado;
import org.e2e.e2e.passenger.domain.Passenger;
import org.e2e.e2e.user.domain.Role;
import org.e2e.e2e.user.domain.User;
import org.e2e.e2e.user.infrastructure.BaseUserRepository;
import org.e2e.e2e.vehicle.domain.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final BaseUserRepository<User> userRepository;
    final JwtService jwtService;
    final PasswordEncoder passwordEncoder;
    final ModelMapper modelMapper;

    @Autowired
    public AuthService(BaseUserRepository<User> userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public JwtAuthResponse login(LoginRequest req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public JwtAuthResponse register(RegisterRequest req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UsernameAlreadyExistException("Email is already registered");
        
        if(req.getIsDriver()) {
        	Empleado driver = new Empleado();
        	driver.setCategory(req.getCategory());
        	driver.setVehicle(modelMapper.map(req.getVehicle(),Vehicle.class));
        	driver.setTrips(0);
        	driver.setAvgRating(1f);
        	driver.setCreatedAt(ZonedDateTime.now());
        	driver.setRole(Role.DRIVER);
        	driver.setFirstName(req.getFirstName());
        	driver.setLastName(req.getLastName());
        	driver.setEmail(req.getEmail());
        	driver.setPassword(passwordEncoder.encode(req.getPassword()));
        	driver.setPhoneNumber(req.getPhone());
        	userRepository.save(driver);
	    	  JwtAuthResponse response = new JwtAuthResponse();
	          response.setToken(jwtService.generateToken(driver));
	          return response;
        }else {
            Passenger passenger = new Passenger();
            passenger.setCreatedAt(ZonedDateTime.now());
            passenger.setRole(Role.PASSENGER);
            passenger.setFirstName(req.getFirstName());
            passenger.setLastName(req.getLastName());
            passenger.setEmail(req.getEmail());
            passenger.setPassword(passwordEncoder.encode(req.getPassword()));
            passenger.setPhoneNumber(req.getPhone());
            passenger.setTrips(0);
            passenger.setAvgRating(1f);
        	userRepository.save(passenger);
	    	  JwtAuthResponse response = new JwtAuthResponse();
	          response.setToken(jwtService.generateToken(passenger));
	          return response;
        
        }
    }

}
