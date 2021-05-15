package edu.learn.rest;

import edu.learn.entity.User;
import edu.learn.repository.UserRepository;
import edu.learn.services.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
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
    UserRepository userRepository;

    @Inject
    JsonWebToken jwt;

    @PermitAll
    @GET
    public Response users() {
        return Response.ok(User.findAll().list()).build();
    }

    @RolesAllowed({"admin", "user"})
    @Path("{userId}")
    @GET
    public Response userById(@PathParam("userId") Long userId) {
        User user = this.getUserById(userId);
        if(user == null)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(user).build();
    }

    @RolesAllowed({"admin", "user"})
    @POST
    public Response saveUser(@Valid User user) {

        if(this.userRepository.saveOrUpdate(user) != null) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @RolesAllowed({"admin", "user"})
    @Path("{userId}")
    @PUT
    public Response updateUser(@PathParam("userId") Long userId, User user) {

        if(this.userRepository.saveOrUpdate(user) != null) {
            return Response.status(Response.Status.ACCEPTED).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @RolesAllowed({"admin", "user"})
    @Path("{userId}")
    @DELETE
    @Produces({MediaType.TEXT_HTML})
    public Response deleteUser(@PathParam("userId") Long userId) {
        this.userRepository.deleteUser(userId);

        User getUser = this.getUserById(userId);
        if(null == getUser) {
            return Response.status(Response.Status.NO_CONTENT).entity(String.format("User deleted against provided id: %d", userId)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @RolesAllowed({"admin", "user"})
    @Path("userInfo")
    @GET
    @Produces({MediaType.TEXT_HTML})
    public Response loggedInUserInfo(@Context SecurityContext securityContext) {

        return Response.ok(securityContext.getUserPrincipal().getName()).build();
    }

    @GET()
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        return getResponseString(ctx);
    }

    @GET()
    @Path("roles")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloJWTRoles(@Context SecurityContext ctx) {
        return getResponseString(ctx) + ", birthdate: " + jwt.getClaim("birthdate").toString();
    }

    private User getUserById(Long userId) {
       return this.userRepository.findById(userId);
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello + %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }
}
