package com.wildcodeschool.milkshake.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wildcodeschool.milkshake.entity.Recipe;
import com.wildcodeschool.milkshake.entity.Waiter;
import com.wildcodeschool.milkshake.repository.WaiterRepository;

@RestController
public class WaiterController {

    private final WaiterRepository waiterRepository;

    public WaiterController(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @GetMapping("/waiters")
    public List<Waiter> getAllWaiters() {
        return waiterRepository.findAll();
    }

    @GetMapping("/waiters/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Waiter> optionalWaiter = waiterRepository.findById(id);
        if (optionalWaiter.isPresent()) {
            Waiter waiter = optionalWaiter.get();
            return ResponseEntity.ok(waiter);
        } else {
            return new ResponseEntity<>("Nope", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/add")
    public String addNewwaiter(@RequestBody Waiter waiter) {
        waiterRepository.save(waiter);
        return "saved";
    }

    @PutMapping("/waiters/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Waiter waiter) {
        Optional<Waiter> optionalWaiterToUpdate = waiterRepository.findById(id);
        if (optionalWaiterToUpdate.isPresent()) {
            Waiter waiterToUpdate = optionalWaiterToUpdate.get();
            waiterToUpdate.setName(waiter.getName());
            waiterToUpdate.setAge(waiter.getAge());
            return ResponseEntity.ok(waiterRepository.save(waiterToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/waiter/{id}")
    public String deletewaiter(@RequestParam Long id) {
        waiterRepository.deleteById(id);
        return "deleted";
    }

}
