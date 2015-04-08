package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.PinDao;
import org.personalized.dashboard.service.api.PinService;
import org.springframework.stereotype.Repository;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class PinServiceImpl implements PinService {

    private final PinDao pinDao;

    @Inject
    public PinServiceImpl(PinDao pinDao){
        this.pinDao = pinDao;
    }
}
