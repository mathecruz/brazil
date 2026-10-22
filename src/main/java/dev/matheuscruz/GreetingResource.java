package dev.matheuscruz;

import io.quarkiverse.flow.Flow;
import io.quarkus.logging.Log;
import io.serverlessworkflow.impl.WorkflowModel;
import io.smallrye.common.annotation.Identifier;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Path("/hello")
public class GreetingResource {

    @Inject
    @Identifier("flow:expression-scope-descriptors")
    Flow expr;

    @Inject
    @Identifier("flow:for-task-name-collision")
    Flow exprFor;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/expr")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> expr() {
        return expr.startInstance()
                .onItem()
                .transform(model -> {
                    String msg = model.asText().orElseThrow();
                    Log.infov("Message: {0}", msg);
                    return msg;
                });
    }

    @GET
    @Path("/expr/for")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<WorkflowModel> exprFor() {
        return exprFor.startInstance(Map.of(
                "items", List.of("Ana Sara", "Jessica Marques")
        ));
    }
}
