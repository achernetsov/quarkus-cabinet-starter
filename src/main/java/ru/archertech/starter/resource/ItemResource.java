package ru.archertech.starter.resource;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import ru.archertech.starter.model.Item;
import ru.archertech.starter.model.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Path("items")
public class ItemResource {

    @Inject
    SecurityIdentity identity;

    @CheckedTemplate
    static class Templates {
        
        static native TemplateInstance items(User user, List<Item> items);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(new BigDecimal(10), "Apple"));
        items.add(new Item(new BigDecimal(16), "Pear"));
        items.add(new Item(new BigDecimal(30), "Orange"));
        return Templates.items(new User(identity.getPrincipal().getName()), items);
    }

    /**
     * This template extension method implements the "discountedPrice" computed property.
     */
    @TemplateExtension
    static BigDecimal discountedPrice(Item item) {
        return item.price.multiply(new BigDecimal("0.9"));
    }

}
