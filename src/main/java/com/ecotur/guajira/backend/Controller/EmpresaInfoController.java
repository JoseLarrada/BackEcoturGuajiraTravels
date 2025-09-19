package com.ecotur.guajira.backend.Controller;


import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.EmpresaInfo;
import com.ecotur.guajira.backend.Services.EmpresaInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/empresa-info")
public class EmpresaInfoController {
    private final EmpresaInfoService empresaInfoService;

    @GetMapping
    public ResponseEntity<List<EmpresaInfo>> getEmpresas() {
        return empresaInfoService.getAllBussinees();
    }

    @PutMapping
    public ResponseEntity<ResponseTour> updateEmpresa(@RequestBody EmpresaInfo empresa) {
        return empresaInfoService.updateEmpresa(empresa);
    }

    @PostMapping
    public ResponseEntity<ResponseTour> addEmpresa(@RequestBody EmpresaInfo empresa) {
        return empresaInfoService.addEmpresa(empresa);
    }

}
