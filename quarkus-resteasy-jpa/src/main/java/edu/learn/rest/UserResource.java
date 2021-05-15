package edu.learn.rest;

import edu.learn.entity.User;
import edu.learn.repository.UserRespository;
import edu.learn.services.UserService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    UserRespository userRespository;

    @GET
    public Response users() {
        return Response.ok(User.findAll().list()).build();
    }

    @Path("{userId}")
    @GET
    public Response userById(@PathParam("userId") Long userId) {
        User user = this.getUserById(userId);
        if(user == null)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(user).build();
    }

    @Transactional
    @POST
    public Response saveUser(@Valid User user) {
        this.userRespository.persist(user);

        if(this.userRespository.isPersistent(user)) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Path("{userId}")
    @Transactional
    @PUT
    public Response updateUser(@PathParam("userId") Long userId, User user) {
        User getUser = getUserById(userId);
        getUser.setUsername(user.getUsername());
        getUser.setPassword(user.getPassword());
        getUser.setRole(user.getRole());
        this.userRespository.persist(getUser);

        if(this.userRespository.isPersistent(getUser)) {
            return Response.status(Response.Status.ACCEPTED).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Path("{userId}")
    @Transactional
    @DELETE
    @Produces({MediaType.TEXT_HTML})
    public Response deleteUser(@PathParam("userId") Long userId) {
        User getUser = this.getUserById(userId);
        this.userRespository.delete(getUser);

        getUser = this.getUserById(userId);
        if(null == getUser) {
            return Response.status(Response.Status.NO_CONTENT).entity(String.format("User deleted against provided id: %d", userId)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    private User getUserById(Long userId) {
       return this.userRespository.findById(userId);
    }
}
