package manager;

import DAO.IUserDAO;
import Entity.DirMsgEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by killeryuan on 2016/11/15.
 */
public class UserManager implements IUserManager{
    private IUserDAO userDAO ;

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean createDIR(String dirPath, String fname, String user){

        DirMsgEntity entity = new DirMsgEntity();
        entity.setPath(dirPath);
        entity.setIsDel(1);
        entity.setFname(fname);
        entity.setUser(user);
        entity.setTime(System.currentTimeMillis() + "");
        entity.setIsDir(1);
        entity.setDeltime("");
        return userDAO.createDIR(entity);
    }

    public String fileList(String current_path){
        List<DirMsgEntity> list = userDAO.fileList(current_path);
        try {
            return  objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public boolean deleteFile(String path, String fname, String user){

        return userDAO.deleteFile(path, fname, user);
    }

    public boolean deleteDir(String dirName, String user){

        return userDAO.deleteDir(dirName, "wzy1");
    }

    @Override
    public boolean login(String username, String password)  {

        // md5

        return userDAO.login(username, password);
    }

    @Override
    public boolean confirmPassword(String password, String username)  {
        return login(username, password);
    }



    @Override
    public boolean changePassword(String newPassword, String username)  {
        return userDAO.changePassword(newPassword, username);
    }

    @Override
    public boolean confirmUsername(String username)  {
        return userDAO.confirmUsername(username);
    }

    @Override
    public boolean register(String username, String password)  {
        return userDAO.register(username, password);
    }

    public void upload( String user, String current_path, String fileName, long fileSize){
        userDAO.upload( user, current_path, fileName, fileSize);
    }
}
