package demo.rest.api.repositories;

import demo.rest.api.entites.RegistrationEntity;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRepo {

    public RegistrationRepo() {
        init();
    }

    public List<RegistrationEntity> database = new ArrayList();

    public void init()
    {
        RegistrationEntity re1 = new RegistrationEntity("user1","123","ABC");
        RegistrationEntity re2 = new RegistrationEntity("user2","123","CDE");
        RegistrationEntity re3 = new RegistrationEntity("user3","123","DEF");
        database.add(re1);
        database.add(re2);
        database.add(re3);
    }

    public RegistrationEntity findByUsername(String username)
    {
        for (int i =0; i < database.size(); i++)
        {
            if (database.get(i).getUsername().equals(username)) return database.get(i);
        }
        return null;
    }

    public void createRegistration(RegistrationEntity re)
    {
        database.add(re);
    }

    public boolean updateResgistration(String username, RegistrationEntity re)
    {
        RegistrationEntity reUp = findByUsername(username);
        if (reUp == null) return false;
        reUp.setFullname(re.getFullname());
        reUp.setPassword(re.getPassword());
        return true;
    }

    public List<RegistrationEntity> getUserByNameLenght(int n) {
        List<RegistrationEntity> list = new ArrayList<>();
        for (int i = 0; i < database.size(); i++) {
            if(database.get(i).getUsername().length() >= n)
                list.add(database.get(i));
        }
        return list;
    }

    public boolean deleteRegsitration(String username)
    {
        RegistrationEntity reDe = findByUsername(username);
        if (reDe == null) return false;
        database.remove(reDe);
        return true;
    }

    public List<RegistrationEntity> getDatabase() {
        return database;
    }

    public void setDatabase(List<RegistrationEntity> database) {
        this.database = database;
    }


}
