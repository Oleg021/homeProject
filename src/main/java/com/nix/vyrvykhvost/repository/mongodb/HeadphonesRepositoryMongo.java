package com.nix.vyrvykhvost.repository.mongodb;

import com.google.gson.*;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.MongoDBConfig;
import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.model.laptop.Laptop;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Singleton
public class HeadphonesRepositoryMongo implements CrudeRepository<Headphones> {
    private static HeadphonesRepositoryMongo instance;
    private final MongoDatabase DATABASE = MongoDBConfig.getMongoDatabase();
    private final MongoCollection<Document> collection;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext)
                    -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(json.getAsString() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)))
            .create();

    @Autowired
    public HeadphonesRepositoryMongo() {
        collection = DATABASE.getCollection(Laptop.class.getSimpleName());
    }

    public static HeadphonesRepositoryMongo getInstance() {
        if (instance == null) {
            instance = new HeadphonesRepositoryMongo();
        }
        return instance;
    }


    @Override
    public void save(Headphones headphone) {
        collection.insertOne(Document.parse(gson.toJson(headphone)));
    }

    @Override
    public void saveAll(List<Headphones> headphones) {
        List<Document> dphones = new ArrayList<>();
        for (Headphones headphone : headphones) {
            dphones.add(Document.parse(gson.toJson(headphone)));
        }

        collection.insertMany(dphones);
    }

    @Override
    public List<Headphones> findAll() {
        return collection.find()
                .map(x -> gson.fromJson(x.toJson(), Headphones.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Headphones> findById(String id) {
        Bson filter = Filters.eq("id", id);
        return collection.find(filter).map(x -> gson.fromJson(x.toJson(), Headphones.class))
                .into(new ArrayList<>()).stream().findFirst();
    }

    @Override
    public boolean update(Headphones headphone) {
        Bson filter = Filters.eq("id", headphone.getId());
        Bson updates = Updates.combine(
                Updates.set("count", headphone.getCount()),
                Updates.set("title", headphone.getTitle()),
                Updates.set("price", headphone.getPrice()),
                Updates.set("model", headphone.getModel()),
                Updates.set("manufacturer", headphone.getManufacturer().name()));
                Updates.set("headphonesType", headphone.getHeadphonesType().name());
        try {
            collection.updateOne(filter, updates);
            return true;
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
            return false;
        }

    }

    @Override
    public boolean delete(String id) {
        Bson filter = Filters.eq("id", id);
        try {
            collection.deleteOne(filter);
            return true;
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
            return false;
        }

    }

    @Override
    public List<Headphones> getAll() {
        return collection.find()
                .map(x -> gson.fromJson(x.toJson(), Headphones.class))
                .into(new ArrayList<>());
    }
}
