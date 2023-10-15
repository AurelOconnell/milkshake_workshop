package com.wildcodeschool.milkshake.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wildcodeschool.milkshake.entity.Waiter;
import com.wildcodeschool.milkshake.repository.WaiterRepository;

@RestController
public class WaiterController {

    private final WaiterRepository waiterRepository;

    public WaiterController(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @GetMapping("/waiters")
    public List<Waiter> getAllwaiters() {
        return waiterRepository.findAll();
    }

    @GetMapping("/waiter/{id}")
    public String getwaiter(Model model,
            @RequestParam(required = false) Long id) {
        Waiter waiter = new Waiter();
        if (id != null) {
            Optional<Waiter> optionalwaiter = waiterRepository.findById(id);
            if (optionalwaiter.isPresent()) {
                waiter = optionalwaiter.get();
            }
        }
        model.addAttribute("waiter", waiter);
        return "waiter";
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

    @GetMapping("/waiter/delete")
    public String deletewaiter(@RequestParam Long id) {
        waiterRepository.deleteById(id);
        return "redirect:/waiter";
    }

}
