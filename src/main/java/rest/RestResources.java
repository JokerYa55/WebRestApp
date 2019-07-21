/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import beans.AppParams;
import beans.SessionParams;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * REST Web Service
 *
 * @author vasil
 */
@Path("/")
@Stateless
public class RestResources {

    Logger log = Logger.getLogger(getClass().getName());

    @Context
    private UriInfo context;
    @Context
    private Request request;
//    @Context
//    private UriInfo uriInfo;
    @Context
    private HttpHeaders requestHeaders;
    @Context
    private HttpServletRequest req;
    @Context
    private Response response;
    @Context
    HttpServletRequest servletRequest;

    @Inject
    AppParams app_params;

    @Inject
    SessionParams session_params;

    /**
     * Creates a new instance of GenericResource
     */
    public RestResources() {
    }

    /**
     * Retrieves representation of an instance of rest.RestResources
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/page")
    @Produces(MediaType.TEXT_HTML)
    public Response page() throws URISyntaxException {
        log.info("page");
        //TODO return proper representation object
        return Response.temporaryRedirect(new URI("/test")).build();
    }


    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    public Response getPage(@PathParam("route") String route) {
        log.info(String.format("test \n\tapp_params = %s\n\tsession_params = %s", app_params, session_params));
        Response rsp = null;
        session_params.setSessionId(req.getSession().getId());
        log.info(String.format("session = %s", session_params));

        // Prepare response
//        try {
//            rsp = Response.status(200).entity(new String(FreemarkerProvider.templateProcess(viewModel, route).toByteArray(), "UTF-8")).build();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("msg", "5000");
        String answer = genMessage("Ваш код = ${msg}", templateData);
        rsp = Response.status(200).entity(answer).build();
        return rsp;
    }

    private boolean getProperties(String filename) {
        Properties prop = new Properties();
        try (InputStream input = (getClass().getClassLoader().getResourceAsStream(filename))) {
            prop.load(input);
            //HashMap<String, String> properties = new HashMap();
            prop.forEach((t, u) -> {
                log.info(String.format("t = %s u = %s", t, u));
                //this.getParams().put((String) t, ((String) u).trim());
            });
        } catch (Exception e) {
        }
        return true;
    }

    /**
     *
     * @param p_template
     * @param params
     * @return
     */
    private String genMessage(String p_template, Map<String, Object> params) {
        log.info(String.format("*************** %s ************* \n\tparams = %s", "genMessage", params));
        String res = null;
        try {
            Configuration cfg = new Configuration();
            //cfg.setClassForTemplateLoading(getClass(), "/");
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setDefaultEncoding("UTF-8");

            //Template template = cfg.getTemplate("message.ftl");
            //template = cfg.
            Template template = new Template("message", new StringReader(p_template), cfg);
            try (StringWriter out = new StringWriter()) {
                template.process(params, out);
                log.info(out.getBuffer().toString());
                res = out.toString();
                out.flush();
            }
        } catch (Exception e) {
            log.log(Level.ERROR, e);
        }
        log.info(String.format("*************** %s *************\n", "END genMessage"));
        return res;
    }

}
