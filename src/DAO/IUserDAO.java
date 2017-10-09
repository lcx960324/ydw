package DAO;

import Entity.DirMsgEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by killeryuan on 2016/11/15.
 */
public interface IUserDAO {

    public boolean createDIR(DirMsgEntity entity);

    public List<DirMsgEntity> fileList(String current_path);

    public boolean deleteDir(String dirName, String user);

    public boolean deleteFile(String path, String fname,  String user);

    public boolean login(String username, String password) ;

    public boolean changePassword(String newPassword, String username) ;

    public boolean confirmUsername(String username) ;

    public boolean register(String username, String password ) ;

    public void upload( String user, String current_path, String fileName, long fileSize);
}
