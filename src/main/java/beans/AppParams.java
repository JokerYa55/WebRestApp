/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author vasil
 */
@ApplicationScoped
public class AppParams {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final Map<String, Object> app_params = new HashMap();

    public AppParams() {
    }

    private boolean loadProperties() {
        boolean result = false;
        Properties prop = new Properties();
        try (InputStream input = (getClass().getClassLoader().getResourceAsStream("app.properties"))) {
            prop.load(input);
            //HashMap<String, String> properties = new HashMap();
            prop.forEach((t, u) -> {
                log.info(String.format("t = %s u = %s", t, u));
                this.app_params.put((String) t, ((String) u).trim());
            });
            result = true;
        } catch (Exception e) {
            log.log(Level.ERROR, e);
        }
        return result;
    }

    @Override
    public String toString() {
        return "AppParams{" + "app_params=" + app_params + '}';
    }

}
