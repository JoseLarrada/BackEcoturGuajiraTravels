package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.EmpresaInfo;
import com.ecotur.guajira.backend.Repository.EmpresaInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaInfoService extends GenericService<EmpresaInfo, String> {

    public EmpresaInfoService( EmpresaInfoRepository empresaInfoRepository) {
        super(empresaInfoRepository);
    }

    public ResponseEntity<List<EmpresaInfo>> getAllBussinees(){
        return getAll();
    }

    public ResponseEntity<ResponseTour> addEmpresa(EmpresaInfo empresaInfo){
        return add(empresaInfo);
    }

    public ResponseEntity<ResponseTour> updateEmpresa(EmpresaInfo empresaInfo){
        return update(empresaInfo,EmpresaInfo::getId);
    }
}
