package com.nix.vyrvykhvost.repository.mongodb;

import com.google.gson.*;
import com.mongodb.client.MongoDatabase;
import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.MongoDBConfig;
import com.nix.vyrvykhvost.model.Invoice;
import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.model.ProductType;
import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.model.laptop.Laptop;
import com.nix.vyrvykhvost.model.phone.Phone;
import com.nix.vyrvykhvost.repository.InvoiceRepository;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

@Singleton
public class InvoiceRepositoryMongo implements InvoiceRepository {

    private static InvoiceRepositoryMongo instance;
    private final MongoDatabase DATABASE = MongoDBConfig.getMongoDatabase();
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> collectionHeadphones;
    private final MongoCollection<Document> collectionLaptops;
    private final MongoCollection<Document> collectionPhones;
    private final Gson gson;

    @Autowired
    public InvoiceRepositoryMongo() {
        collection = DATABASE.getCollection(Invoice.class.getSimpleName());
        collectionHeadphones = DATABASE.getCollection(Headphones.class.getSimpleName());
        collectionPhones = DATABASE.getCollection(Phone.class.getSimpleName());
        collectionLaptops = DATABASE.getCollection(Laptop.class.getSimpleName());
        GsonBuilder timemodule = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                        new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                        LocalDateTime.parse(json.getAsString() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").
                                withLocale(Locale.ENGLISH)));
        gson = timemodule.registerTypeAdapter(Product.class, (JsonDeserializer<Product>) (json, type, jsonDeserializationContext) -> {
                    Product product = collectionLaptops.find(Filters.eq("id", json.getAsJsonObject().get("id").getAsString())).map(document -> {
                                ProductType productType = ProductType.valueOf(document.get("type").toString());
                                Gson time = timemodule.create();
                                return switch (productType) {
                                    case HEADPHONES -> time.fromJson(document.toJson(), Headphones.class);
                                    case LAPTOP -> time.fromJson(document.toJson(), Laptop.class);
                                    case PHONE -> time.fromJson(document.toJson(), Phone.class);
                                };

                            })
                            .first();
                    return product;
                })
                .create();
    }

    public static InvoiceRepositoryMongo getInstance() {
        if (instance == null) {
            instance = new InvoiceRepositoryMongo();
        }
        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        collection.insertOne(Document.parse(gson.toJson(invoice)));
    }

    @Override
    public Optional<Invoice> findById(String id) {
        Bson filter = Filters.eq("id", id);
        return collection.find(filter).map(x -> gson.fromJson(x.toJson(), Invoice.class))
                .into(new ArrayList<>()).stream().findFirst();
    }

    @Override
    public void update(Invoice invoice) {
        Bson filter = Filters.eq("id", invoice.getId());
        Bson updates = Updates.combine(
                Updates.set("sum", invoice.getSum()),
                Updates.set("products", invoice.getProducts()),
                Updates.set("time", invoice.getTime()));
        try {
            collection.updateOne(filter, updates);
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }

    }

    @Override
    public List<Invoice> findAllGreaterSumInvoices(double sum) {
        Bson filter = Filters.gte("sum", sum);
        return collection.find(filter).map(x -> gson.fromJson(x.toJson(), Invoice.class))
                .into(new ArrayList<>());

    }

    @Override
    public int getInvoiceCount() {
        return (int) collection.countDocuments();
    }

    @Override
    public Map<Double, Integer> sortBySum() {
        Map<Double, Integer> result = new TreeMap<>();
        Bson filter = Aggregates.group("$sum", Accumulators.sum("count", 1));
        collection.aggregate(List.of(filter))
                .map(document -> gson.fromJson(document.toJson(), JsonObject.class))
                .forEach((Consumer<? super JsonObject>) jsonObject -> {
                    double sum = jsonObject.get("_id").getAsDouble();
                    int count = jsonObject.get("count").getAsInt();
                    result.put(sum, count);
                });
        return result;
    }


}
