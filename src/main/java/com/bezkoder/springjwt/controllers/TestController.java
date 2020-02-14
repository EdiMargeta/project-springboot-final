package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.Task;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.TodoRepository;
import com.bezkoder.springjwt.repository.UserRepository;
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
	TodoRepository todoRepository;
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

	@PostMapping("/createuser")
	public Task createEmployee(@Valid @RequestBody Task employee) {
		return todoRepository.save(employee);
	}
	
	@PutMapping(value = "/user")
	public Task updateTodo(@Valid @RequestBody Task task) {
		Task todo = todoRepository.findById(task.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Todo", "id", task.getId()));
		todo.setFirstName(task.getFirstName());
		todo.setLastName(task.getLastName());
		todo.setTaskName(task.getTaskName());
		todo.setTimeFrom(task.getTimeFrom());
		todo.setTimeTill(task.getTimeTill());
		todo.setStatus(task.getStatus());
		Task updatedTask = todoRepository.save(todo);
		return updatedTask;
	}

	@DeleteMapping(value="/user/{id}")
	public ResponseEntity<?> deleteCust(@PathVariable Long id) {
		todoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
