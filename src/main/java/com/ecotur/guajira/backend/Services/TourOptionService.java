package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.TourOption;
import com.ecotur.guajira.backend.Repository.TourOptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourOptionService extends GenericService<TourOption,String> {

    public TourOptionService(TourOptionRepository tourOptionRepository) {
        super(tourOptionRepository);
    }

    public ResponseEntity<ResponseTour> addSTourOption(TourOption tourOption) {
        return add(tourOption);
    }

    public ResponseEntity<ResponseTour> updateTourOption(TourOption tourOption) {
        return update(tourOption,TourOption::getId);
    }
    public ResponseEntity<ResponseTour> deleteTourOption(String id) {
        return delete(id);
    }
    public ResponseEntity<List<TourOption>> getAllTourOption() {
        return getAll();
    }

}
