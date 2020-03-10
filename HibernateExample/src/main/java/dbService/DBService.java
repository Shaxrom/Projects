package base;
/**
 * For example,i only implements UserDao
 */

import base.dataSets.User;

import java.util.List;

public interface DBService {

    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet readByName(String name);

    UserDataSet read(long id);

    List<UserDataSet> readAll();

    void shutdown();
}
