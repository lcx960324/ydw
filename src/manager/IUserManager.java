package manager;

import Entity.Operate;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by killeryuan on 2016/11/15.
 */
public interface IUserManager {

    public boolean createDIR(String dirPath, String fname, String user);

    public String fileList(String current_path);

    public boolean deleteFile(String path, String fname,  String user);

    public boolean deleteDir(String dirName, String user);


    public boolean login(String username, String password) throws IOException ;

    public boolean confirmPassword(String password, String username ) throws IOException;

    public boolean changePassword(String newPassword, String username) throws IOException;

    public boolean confirmUsername(String username) throws IOException;

    public boolean register(String username, String password ) throws IOException;

    public void upload( String user, String current_path, String fileName, long fileSize);
}
