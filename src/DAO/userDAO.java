package DAO;


import Entity.DirMsgEntity;
import Entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by killeryuan on 2016/11/15.
 */
public class userDAO implements IUserDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean createDIR(DirMsgEntity entity) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    public List<DirMsgEntity> fileList(String current_path) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "from DirMsgEntity where path = '" + current_path + "' and isDel = 1";
            Query query = session.createQuery(hql);
            List<DirMsgEntity> list = query.list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<DirMsgEntity>();
        }finally {
            session.close();
        }
    }

    public boolean deleteFile(String path, String fname, String user){
        Session session = sessionFactory.openSession();
        try {
            String hql = "update DirMsgEntity  set isDel = 0 ,deluser = '"+user+"' where path = '" + path + "' " +
                    "and fname = '"+fname + "'";
            Query query = session.createQuery(hql);
            if(query.executeUpdate() == 0)
                return false;
            else
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    public boolean deleteDir(String dirName, String user){
        Session session = sessionFactory.openSession();
        try {
            System.out.println(123);
            session.beginTransaction();
            String hql = "update DirMsgEntity  set isDel = 0  where path like '" + dirName + "'";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            if(result == 0)
                return false;
            else
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    public boolean login(String username, String password) {
        Session session = sessionFactory.openSession();
        try{
            String hql = "from UserEntity where username = '"+username+"'";
            Query query = session.createQuery(hql);
            List<UserEntity> users = query.list();
            if(users.size()==0) return false;
            if(users.get(0).getPassword().equals(password)) return true;
            else return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    public boolean changePassword(String newPassword, String username){
        Session session = sessionFactory.openSession();
        try{
            String hql = "update UserEntity set password = '"+newPassword+"' where username = '"+username+"'";
            Query query = session.createQuery(hql);
            if(query.executeUpdate() !=0 ){
                return true;
            }else
                return false;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }


    }

    public boolean confirmUsername(String username) {
        Session session = sessionFactory.openSession();

        try {
            String hql = "from UserEntity  where username = '"+username+"'";
            Query query = session.createQuery(hql);
            if(query.list().size() == 0)
                return true;
            else return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean register(String username, String password ){

        Session session = sessionFactory.openSession();
        try{
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            session.beginTransaction();
            session.save(userEntity);
            session.getTransaction().commit();
            return true;
        } catch ( Exception e){
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public void upload( String user, String current_path, String fileName, long fileSize){
        Session session = sessionFactory.openSession();
        try{
            DirMsgEntity entity = new DirMsgEntity();
            entity.setFname( fileName);
            entity.setIsDir( 0);
            entity.setTime( System.currentTimeMillis() + "");
            entity.setPath( current_path);
            entity.setUser( user);
            entity.setLinked( "/user/download.do?path=" + current_path+"&name="+ fileName);
            entity.setDeltime("");
            entity.setFileSize(fileSize);
            entity.setIsDel(1);
            session.beginTransaction();
            session.save( entity);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
