package com.project.sds.service;


import com.project.sds.models.Fees;
import com.project.sds.repository.FeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class FeesServiceImpl implements FeesService {



    @Autowired
    private FeesRepository feesRepository;

    @Override
    public Fees save(Fees fees) {
        if (fees.isFeesStatus()) {
            ZonedDateTime now = ZonedDateTime.now();
            String monthYear = now.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));
            fees.setPaidMonth(monthYear);
        } else {
            fees.setPaidMonth(null);
        }
            return feesRepository.save(fees);
    }
}
