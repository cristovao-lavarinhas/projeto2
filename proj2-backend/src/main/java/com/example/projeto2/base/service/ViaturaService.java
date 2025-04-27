package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Viatura;
import com.example.projeto2.base.repository.ViaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViaturaService {

    private final ViaturaRepository viaturaRepository;

    public ViaturaService(ViaturaRepository viaturaRepository) {
        this.viaturaRepository = viaturaRepository;
    }

    // Listar todas as viaturas
    public List<Viatura> listarTodos() {
        return viaturaRepository.findAll();
    }

    // Procurar viatura por ID
    public Optional<Viatura> procurarPorId(Long id) {
        return viaturaRepository.findById(id);
    }

    // Criar ou salvar uma nova viatura
    public Viatura guardar(Viatura viatura) {
        return viaturaRepository.save(viatura);
    }

    // Atualizar viatura existente
    public Optional<Viatura> atualizar(Long id, Viatura viaturaAtualizada) {
        return viaturaRepository.findById(id).map(viatura -> {
            viatura.setMotorista(viaturaAtualizada.getMotorista());
            viatura.setMatricula(viaturaAtualizada.getMatricula());
            viatura.setModelo(viaturaAtualizada.getModelo());
            viatura.setAno(viaturaAtualizada.getAno());
            return viaturaRepository.save(viatura);
        });
    }

    // Deletar uma viatura
    public boolean deletar(Long id) {
        return viaturaRepository.findById(id).map(viatura -> {
            viaturaRepository.delete(viatura);
            return true;
        }).orElse(false);
    }
}

