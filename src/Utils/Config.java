package Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tend on 2017/9/5.
 */
public class Config implements ServletContextListener {

    public static Map<String, String> map = new HashMap<>();


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("load conf");
        String path = servletContextEvent.getServletContext().getRealPath("") + File.separator + "files";
        File file = new File(path + File.separator + "config.conf");
        try {
            BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream( file)));
            String line = br.readLine();
            while ( line  != null ){
                if( !line.startsWith("#") && !line.equals("")){
                    String parts[] = line.replaceAll(" ","").replaceAll("\t","").split("=");
                    map.put( parts[0], parts[1]);
                    System.out.println( parts[0] + " "+ parts[1]);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("end");
    }
}
