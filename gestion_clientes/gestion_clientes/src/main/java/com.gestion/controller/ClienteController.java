package com.gestion.controller;

import com.gestion.exception.ResourceNotFoundException;
import com.gestion.model.Clientes;
import com.gestion.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")

public class ClienteController{

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public List<Clientes> listarClientes(){
        return clienteRepository.findAll();
    }

    @PostMapping("/clientes")
    public Clientes guardarClientes(@RequestBody Clientes clientes){
        return clienteRepository.save(clientes);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Clientes> listarClientesPorId(@PathVariable Long id, @RequestBody Clientes clienteRequest){
        Clientes clientes = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El id no existe: " + id));

        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Clientes> actualizarClientes(@PathVariable Long id, @RequestBody Clientes clienteRequest){
        Clientes clientes = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El id no existe: " + id));

        clientes.setNombre(clienteRequest.getNombre());
        clientes.setApellido(clienteRequest.getApellido());
        clientes.setEmail(clienteRequest.getEmail());

        Clientes clienteActualizado = clienteRepository.save(clientes);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarClientes(@PathVariable Long id){
        Clientes clientes = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El id no existe: " + id));

        clienteRepository.delete(clientes);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }




}
