package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.DiaryDao;
import org.personalized.dashboard.service.api.DiaryService;
import org.springframework.stereotype.Repository;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDao diaryDao;

    @Inject
    public DiaryServiceImpl(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }
}
