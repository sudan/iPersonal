package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.PinDao;
import org.personalized.dashboard.model.Pin;
import org.personalized.dashboard.service.api.PinService;
import org.personalized.dashboard.utils.auth.SessionManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class PinServiceImpl implements PinService {

    private final PinDao pinDao;
    private final SessionManager sessionManager;

    @Inject
    public PinServiceImpl(PinDao pinDao, SessionManager sessionManager){
        this.pinDao = pinDao;
        this.sessionManager = sessionManager;
    }

    @Override
    public String createPin(Pin pin) {
        return pinDao.create(pin, sessionManager.getUserIdFromSession());
    }

    @Override
    public Pin getPin(String pinId) {
        return pinDao.get(pinId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long updatePin(Pin pin) {
        return  pinDao.update(pin, sessionManager.getUserIdFromSession());
    }

    @Override
    public void deletePin(String pinId) {
        pinDao.delete(pinId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long countPins() {
        return pinDao.count(sessionManager.getUserIdFromSession());
    }

    @Override
    public List<Pin> fetchPins(int limit, int offset) {
        return pinDao.get(limit, offset, sessionManager.getUserIdFromSession());
    }
}
