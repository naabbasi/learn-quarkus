package edu.learn.rest;

import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.core.MediaType;

public class UserMultipartBody {

    /*@FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;*/

    @FormParam("j_username")
    @PartType(MediaType.TEXT_PLAIN)
    public String username;

    @FormParam("j_password")
    @PartType(MediaType.TEXT_PLAIN)
    public String password;
}
