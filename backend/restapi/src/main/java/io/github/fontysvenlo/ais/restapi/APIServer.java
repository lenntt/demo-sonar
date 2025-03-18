package io.github.fontysvenlo.ais.restapi;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.get;

import io.javalin.Javalin;
import io.github.fontysvenlo.ais.businesslogic.api.BusinessLogic;
import java.util.Map;

/**
 * This class is responsible for starting the REST server and defining the
 * routes.
 */
public class APIServer {

    private final BusinessLogic businessLogic;

    /**
     * Initializes the REST API server
     *
     * @param businessLogic the business logic implementation to communicate
     * with
     */
    public APIServer(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    /**
     * Starts the REST API server
     * @param configuration the configuration of the server
     */
    public void start(ServerConfig configuration) {
        var app = Javalin.create(config -> {
            config.router.contextPath = "/api/v1";
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.allowHost("http://localhost:" + configuration.cors(), "127.0.0.1:" + configuration.cors());
                });
            });
            config.router.apiBuilder(() -> {
                crud("customers/{customer-id}", new CustomerResource(businessLogic.getCustomerManager()));

                get("login", ctx -> {
                    var password = ctx.queryParam("password");
                    if (!password.equals("MyPassword")) {
                        throw new IllegalArgumentException("Invalid password");
                    }
                    ctx.json(Map.of("token", "my-token"));
                });

                get("error", ctx -> {
                    BusinessLogic businessBla = null;
                    if(ctx.queryParam("init").equals("true")){
                        businessBla = this.businessLogic;
                    }
                    ctx.json(businessBla.getCustomerManager().list());
                });
            });
        });

        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(422).json(Map.of("error", e.getMessage()));
        });

        app.start(configuration.port());
    }
}