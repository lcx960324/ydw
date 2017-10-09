package controller;


import Entity.Operate;
import Utils.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import manager.IUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;


/**
 * Created by killeryuan on 2016/11/15.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Resource(name = "userManager")
    private IUserManager userManager;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;
    
    @RequestMapping("download")
    public void download(String path,String name, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        crossDomainSetting(response,request);
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String ctxPath = Config.map.get("upload.path") + File.separator + "files";
        String downLoadPath = ctxPath + File.separator +  path + File.separator + name;

        try {
            long fileLength = new File(downLoadPath).length();
            response.setContentType( "application/x-msdownload;");
            response.setHeader( "Content-disposition", "attachment; filename="
                    + new String(name.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader( "Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }


    @RequestMapping("createDIR")
    public void createDIR(String current_path, String name, HttpServletRequest request, HttpServletResponse response) throws IOException{
        crossDomainSetting(response,request);
        String rootPath = Config.map.get("upload.path") + File.separator + "files";
        Operate o = new Operate();
        o.setOperate("createDIR");
        if( request.getSession().getAttribute("user") != null ) {
            String user = (String)request.getSession().getAttribute("user");
            try {
                String path = current_path.equals("/")? rootPath + current_path + name: rootPath + current_path + File.separator + name;
                System.out.println(path);
                File dir = new File( path );
                if ( !dir.isDirectory()) {
                    System.out.println(dir.mkdir());
                    Process proc = Runtime.getRuntime().exec("chmod 777 " + path);
                    if(!userManager.createDIR(current_path , name, user)){
                        dir.delete();
                        o.setError(2);
                    }else {
                        o.setError(0);
                    }
                } else if ( dir.isDirectory()) {
                    o.setError(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                o.setError(2);
            }

            response.getWriter().print(new ObjectMapper().writeValueAsString(o));
        } else {
            o.setError(3);
            response.getWriter().print(new ObjectMapper().writeValueAsString(o));
        }
    }

    @RequestMapping("fileList")
    public void fileList(String current_path, String dirName , HttpServletResponse response, HttpServletRequest request) throws IOException {
        crossDomainSetting(response,request);
        if( request.getSession().getAttribute("user") != null) {
            String rootPath = Config.map.get("upload.path") + File.separator + "files";
            String path = current_path.equals("/") ? current_path + (dirName == null?"":dirName): current_path + File.separator + dirName;
            response.getWriter().print(userManager.fileList(path));
        }else {
            response.getWriter().println("{\"error\":\"3\"}");
        }
    }

    @RequestMapping("deleteDIR")
    public void deleteDir(String current_path, String dName, HttpServletResponse response, HttpServletRequest request ) throws IOException{
        crossDomainSetting(response,request);
        Operate o = new Operate();
        o.setOperate("deleteDir");

        String path = current_path.equals("/") ? current_path + dName :current_path + File.separator + dName;

        if(request.getSession().getAttribute("user") != null) {
            String rootPath = Config.map.get("upload.path") + File.separator + "files";
            if (userManager.deleteDir(path, "dele")) {
                File file = new File(rootPath + current_path);
                file.delete();
                o.setError(0);
            } else {
                o.setError(1);
            }
        }else{
            o.setError(3);
        }

        response.getWriter().print(objectMapper.writeValueAsString(o));
    }

    @RequestMapping("deleteFile")
    public void deleteFile(String current_path, String fname, HttpServletRequest request, HttpServletResponse response) throws IOException{
        crossDomainSetting(response,request);
        String rootPath = Config.map.get("upload.path") + File.separator + "files";
        Operate o = new Operate();
        o.setOperate("deleteFile");
        String user = (String)request.getSession().getAttribute("user");
        if( user !=null) {
            if (userManager.deleteFile(current_path, fname, user)) {
                File file = new File(rootPath + File.separator + current_path + File.separator + fname);
                deleteDir(file, user, rootPath);
                System.out.println(file.delete());
                o.setError(0);
            } else {
                o.setError(1);
            }
        }else {
            o.setError(3);
        }
        response.getWriter().print(objectMapper.writeValueAsString(o));
    }


    @RequestMapping("login")
    public void login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        crossDomainSetting(response,request);
        Operate o = new Operate();
        o.setOperate("login");

        if( userManager.login(username, password)){
            request.getSession(true).setAttribute("user", username);
            o.setError(0);
            response.getWriter().print( objectMapper.writeValueAsString(o));
        }else {
            o.setError(1);
            response.getWriter().print( objectMapper.writeValueAsString(o));
        }
    }

    @RequestMapping("getUsername")
    public void isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
        crossDomainSetting(response,request);
        String username = (String)request.getSession().getAttribute("user");
        if(username == null){
            response.getWriter().print("{\"error\":0,\"username\",\""+username+"\"}");
        } else {
            response.getWriter().print("{\"error\":1}");
        }
    }

    @RequestMapping("confirmPassword")
    public void confirmPassword(String password, HttpServletResponse response, HttpServletRequest request) throws IOException{
        crossDomainSetting(response,request);
        Operate o =new Operate();
        o.setOperate("confirmPassword");
        String username = (String) request.getSession().getAttribute("user");
        if( username != null) {
            if(userManager.confirmPassword(password, username)){
                o.setError(0);
            }else o.setError(1);
        }else o.setError(3);
        response.getWriter().print(objectMapper.writeValueAsString(o));
    }

    @RequestMapping("changePassword")
    public void changePassword(String newPassword, HttpServletRequest request, HttpServletResponse response) throws IOException{
        crossDomainSetting(response,request);
        Operate o =new Operate();
        o.setOperate("changePassword");
        String username = (String) request.getSession().getAttribute("user");
        if( username != null) {
            if(userManager.changePassword(newPassword, username)){
                o.setError(0);
            }else o.setError(1);
        }else o.setError(3);
        response.getWriter().print(objectMapper.writeValueAsString(o));


    }

    @RequestMapping("upload")
    public void upload(String current_path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        crossDomainSetting(response,request);
        Operate o = new Operate();
        o.setOperate("upload");
        System.out.println(0);
        String user = (String)request.getSession().getAttribute("user");
        String rootPath = Config.map.get("upload.path") + File.separator + "files";
        System.out.println(rootPath);
        if(user != null) {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                System.out.println(2);
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multipartHttpServletRequest.getFileNames();
                while (iter.hasNext()) {
                    System.out.println(3);
                    String fileName = iter.next();
                    MultipartFile file = multipartHttpServletRequest.getFile(fileName);
                    fileName = file.getOriginalFilename();
                    if( file != null && !file.isEmpty()){
                        System.out.println(4);
                        String path = rootPath + File.separator + current_path + File.separator + fileName;
                        File targetFile = new File(path);
                        if( !targetFile.exists()){
                            System.out.println(5);
                            targetFile.createNewFile();
                            file.transferTo(targetFile);
                            long fileSize = file.getSize();
                            userManager.upload( user, current_path, fileName, fileSize);
                            o.setError(0);
                        }else {
                            // has exist
                            o.setError(1);
                        }
                    }
                }
            }
        } else o.setError(3);

        response.getWriter().println(objectMapper.writeValueAsString(o));
    }

    private void crossDomainSetting(HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods","POST");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
        response.setHeader("Access-Control-Allow-Credentials","true"); 
    }

    private void deleteDir(File file, String user, String rootPath){
        if( file.isDirectory()){
            String[] names = file.list();
            for (String name: names){
                File temp = new File( file.getPath() + File.separator + name);
                deleteDir(temp, user,rootPath);
            }
        }else {

            userManager.deleteFile(file.getParent().replace( rootPath, ""), file.getName(), user);
            file.delete();
        }
    }


//    @RequestMapping("confirmUsername")
//    public void confirmUsername(String username, HttpServletResponse response) throws IOException{
//        Operate o = new Operate();
//        o.setOperate("confirmUsername");
//        if( userManager.confirmUsername(username)){
//            o.setError(0);
//        }else o.setError(1);
//        response.getWriter().print(objectMapper.writeValueAsString(o));
//    }
//
//    @RequestMapping("register")
//    public void register(String username, String password, HttpServletRequest request, HttpServletResponse response ) throws IOException{
//        Operate o = new Operate();
//        o.setOperate("register");
//
//        if( userManager.register(username, password)){
//            request.getSession().setAttribute("user", username);
//            o.setError(0);
//        }else o.setError(1);
//        response.getWriter().print(objectMapper.writeValueAsString(o));
//    }
}
