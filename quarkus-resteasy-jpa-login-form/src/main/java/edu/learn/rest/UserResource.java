package edu.learn.rest;

import edu.learn.entity.User;
import edu.learn.repository.UserRespository;
import edu.learn.services.UserService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    UserRespository userRespository;

    @RolesAllowed({"admin"})
    @GET
    public Response users(@Context SecurityContext securityContext) {
        System.out.println(securityContext.getUserPrincipal().getName());
        return Response.ok(User.findAll().list()).build();
    }

    @RolesAllowed({"admin", "user"})
    @Path("{userId}")
    @GET
    public Response findUserById(@Context SecurityContext securityContext, @PathParam("userId") Long userId) {
        //System.out.println(securityContext.getUserPrincipal().getName());
        return Response.ok(this.userRespository.findById(userId)).build();
    }

    @RolesAllowed({"admin"})
    @Transactional
    @POST
    public Response saveUser(User user) {
        this.userRespository.persist(user);

        if(this.userRespository.isPersistent(user)) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @RolesAllowed({"admin"})
    @Path("{userId}")
    @Transactional
    @PUT
    public Response updateUser(@PathParam("userId") Long userId, User user) {
        User getUser = this.userRespository.findById(userId);
        getUser.setUsername(user.getUsername());
        getUser.setPassword(user.getPassword());
        getUser.setRole(user.getRole());
        this.userRespository.persist(getUser);

        if(this.userRespository.isPersistent(getUser)) {
            return Response.status(Response.Status.ACCEPTED).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response loginUser(@FormParam("j_username") String j_username, @FormParam("j_password") String j_password) {
        System.out.println(j_username);
        System.out.println(j_password);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Path("/loginfd")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response loginUser(@MultipartForm UserMultipartBody multipartBody) {

        System.out.println(multipartBody.username);
        System.out.println(multipartBody.password);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
