package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Historico;
import com.example.demo.repository.HistoricoReposytory;

@Service
public class HistoricoService {

    private HistoricoReposytory historicoReposytory;

    public HistoricoService(HistoricoReposytory historicoReposytory) {
        this.historicoReposytory = historicoReposytory;
    }

    public void salvarHistorico() {
        //historicoReposytory.save(new Historico(null, null, comando, usuario, host));
    }


}
