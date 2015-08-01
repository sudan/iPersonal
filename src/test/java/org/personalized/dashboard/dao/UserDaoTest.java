package org.personalized.dashboard.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.bootstrap.ConfigManager;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.UserDao;
import org.personalized.dashboard.dao.impl.UserDaoImpl;
import org.personalized.dashboard.model.User;
import org.personalized.dashboard.utils.ConfigKeys;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

/**
 * Created by sudan on 26/7/15.
 */
@ActiveProfiles("test")
public class UserDaoTest {

    private UserDao userDao;

    @Before
    public void initialize() throws IOException {
        ConfigManager.init();
        this.userDao = new UserDaoImpl(new IdGenerator());
    }

    @Test
    public void testUserDao() {

        Boolean isDebugMode = Boolean.valueOf(ConfigKeys.MONGO_DEBUG_FLAG);

        /*
            To run these test cases enable isDebugMode in config.properties
         */
        if (isDebugMode) {
            MongoBootstrap.init();
            MongoBootstrap.getMongoDatabase().getCollection(Constants.USERS).drop();

            User user = new User();
            user.setUsername("sudan");
            user.setEmail("ssudan16@gmail.com");
            user.setProfilePicURL("http://www.google.com");
            String userId = userDao.upsert(user);

            User newUser = userDao.get(userId);

            Assert.assertEquals("UserId validation", userId, newUser.getUserId());
            Assert.assertEquals("Username validation", "sudan", newUser.getUsername());
            Assert.assertEquals("email validation", "ssudan16@gmail.com", newUser.getEmail());
            Assert.assertEquals("Image url validation", "http://www.google.com", newUser.getProfilePicURL());

            user = new User();
            user.setUserId("noeffect");
            user.setUsername("sudan1");
            user.setEmail("ssudan16@gmail.com");
            user.setProfilePicURL("http://www.yahoo.com");
            String updatedUserId = userDao.upsert(user);

            newUser = userDao.get(updatedUserId);

            Assert.assertEquals("UserId validation", updatedUserId, newUser.getUserId());
            Assert.assertEquals("Username validation", "sudan1", newUser.getUsername());
            Assert.assertEquals("email validation", "ssudan16@gmail.com", newUser.getEmail());
            Assert.assertEquals("Image url validation", "http://www.yahoo.com", newUser.getProfilePicURL());


        }
    }
}
