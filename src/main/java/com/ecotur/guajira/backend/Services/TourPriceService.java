package com.ecotur.guajira.backend.Services;


import com.ecotur.guajira.backend.Entities.Dto.ResponseTour;
import com.ecotur.guajira.backend.Entities.ToursPrice;
import com.ecotur.guajira.backend.Repository.ToursPriceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourPriceService extends GenericService<ToursPrice,String> {

    public TourPriceService(ToursPriceRepository toursPriceRepository) {
        super(toursPriceRepository);
    }

    public ResponseEntity<List<ToursPrice>> getAllToursPrice() {
        return getAll();
    }

    public ResponseEntity<ResponseTour> addToursPrice(ToursPrice tour) {
        return add(tour);
    }

    public ResponseEntity<ResponseTour> updateToursPrice(ToursPrice tour) {
        return update(tour,ToursPrice::getId);
    }

    public ResponseEntity<ResponseTour> deleteToursPrice(String id) {
        return delete(id);
    }
}
