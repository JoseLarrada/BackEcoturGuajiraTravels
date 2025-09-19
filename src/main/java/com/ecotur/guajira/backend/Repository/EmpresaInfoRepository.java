package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.EmpresaInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaInfoRepository extends MongoRepository<EmpresaInfo, String> {
}
