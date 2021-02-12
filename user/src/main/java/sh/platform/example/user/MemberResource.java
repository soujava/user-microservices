package sh.platform.example.user;

import io.quarkus.panache.common.Sort;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Path("members")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberResource {

    private static final Logger LOGGER = Logger.getLogger(MemberResource.class.getName());
    @GET
    public List<Member> get() {
        return Member.listAll(Sort.ascending("name"));
    }

    @GET
    @Path("{id}")
    public Member getSingle(@PathParam Long id) {

        Optional<Member> entity = Member.findByIdOptional(id);
        return entity.orElseThrow(() -> new WebApplicationException("Member with id of " + id + " does not exist.",
                Response.Status.NOT_FOUND));
    }

    @POST
    @Transactional
    public Response create(Member member) {
        member.persist();
        return Response.ok(member).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Member update(@PathParam Long id, Member member) {
        LOGGER.info("the parameters " + member);
        LOGGER.info("the id " + id);
        Member entity = Member.<Member>findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Member with id of " + id + " does not exist.",
                        Response.Status.NOT_FOUND));
        LOGGER.info("the entity found " + entity);
        entity.update(member);
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        boolean deleted = Member.deleteById(id);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        throw new WebApplicationException("Member with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
    }

}
