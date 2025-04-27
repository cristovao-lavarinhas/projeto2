package com.example.projeto2.base.service;

import com.example.projeto2.base.model.CodigoPostal;
import com.example.projeto2.base.repository.CodigoPostalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodigoPostalService {

    private final CodigoPostalRepository codigoPostalRepository;

    public CodigoPostalService(CodigoPostalRepository codigoPostalRepository) {
        this.codigoPostalRepository = codigoPostalRepository;
    }

    // Listar todos os códigos postais
    public List<CodigoPostal> listarTodos() {
        return codigoPostalRepository.findAll();
    }

    // Procurar código postal por ID
    public Optional<CodigoPostal> procurarPorId(Long id) {
        return codigoPostalRepository.findById(id);
    }

    // Criar ou salvar um novo código postal
    public CodigoPostal guardar(CodigoPostal codigoPostal) {
        return codigoPostalRepository.save(codigoPostal);
    }

    // Atualizar um código postal existente
    public Optional<CodigoPostal> atualizar(Long id, CodigoPostal codigoPostalAtualizado) {
        return codigoPostalRepository.findById(id).map(codigoPostal -> {
            codigoPostal.setCodLocalidade(codigoPostalAtualizado.getCodLocalidade());
            return codigoPostalRepository.save(codigoPostal);
        });
    }

    // Deletar um código postal
    public boolean deletar(Long id) {
        return codigoPostalRepository.findById(id).map(codigoPostal -> {
            codigoPostalRepository.delete(codigoPostal);
            return true;
        }).orElse(false);
    }
}
