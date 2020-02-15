package com.project.spring.controllers;

import com.project.spring.Exception.ResourceNotFoundException;
import com.project.spring.models.Task;
import com.project.spring.models.User;
import com.project.spring.repository.TaskRepository;
import com.project.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class TestController {

	@Autowired
	TaskRepository todoRepository;
	@Autowired
	UserRepository userRepository;

	@GetMapping("/all")
	public String allAccess() {
		return "In development.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Task> getAllTodos() {
		return todoRepository.findAll();
	}

	@GetMapping(value="/user/{id}")
	public Optional<Task> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Task employee = todoRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: "+ employeeId));
		return todoRepository.findById(employeeId);
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	@PostMapping("/create_employee")
	public Task createEmployee(@Valid @RequestBody Task employee) {
		return todoRepository.save(employee);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<Task> updateEmployee(@PathVariable(value = "id") Long employeeId,
											   @Valid @RequestBody Task employeeDetails) throws ResourceNotFoundException {
		Task employee = todoRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		employee.setStatus(employeeDetails.getStatus());
		final Task updatedEmployee = todoRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping(value="/user/{id}")
	public ResponseEntity<?> deleteCust(@PathVariable Long id) {
		todoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
