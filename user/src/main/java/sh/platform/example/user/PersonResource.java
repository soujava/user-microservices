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

@Path("people")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    private static final Logger LOGGER = Logger.getLogger(PersonResource.class.getName());
    @GET
    public List<Person> get() {
        return Person.listAll(Sort.ascending("name"));
    }

    @GET
    @Path("{id}")
    public Person getSingle(@PathParam Long id) {

        Optional<Person> entity = Person.findByIdOptional(id);
        return entity.orElseThrow(() -> new WebApplicationException("Person with id of " + id + " does not exist.",
                Response.Status.NOT_FOUND));
    }

    @POST
    @Transactional
    public Response create(Person person) {
        person.persist();
        return Response.ok(person).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Person update(@PathParam Long id, Person person) {
        LOGGER.info("the parameters " + person);
        LOGGER.info("the id " + id);
        Person entity = Person.<Person>findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Person with id of " + id + " does not exist.",
                        Response.Status.NOT_FOUND));
        LOGGER.info("the entity found " + entity);
        entity.update(person);
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        boolean deleted = Person.deleteById(id);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        throw new WebApplicationException("Person with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
    }

}
