package org.personalized.dashboard.auth.user;

import com.google.inject.Inject;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * Created by sudan on 25/7/15.
 */
public final class SimpleConnectionSignup implements ConnectionSignUp{

    private final IdGenerator idGenerator;

    @Inject
    public SimpleConnectionSignup(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public String execute(Connection<?> connection) {
        return idGenerator.generateId(Constants.USER_PREFIX, Constants.ID_LENGTH);
    }
}
