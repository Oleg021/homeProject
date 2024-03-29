package com.nix.vyrvykhvost.context;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.repository.HeadphonesRepository;
import com.nix.vyrvykhvost.repository.LaptopRepository;
import com.nix.vyrvykhvost.repository.PhoneRepository;
import lombok.Getter;
import org.reflections.Reflections;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private static ApplicationContext instance;
    private final Reflections reflections;
    @Getter
    private final Map<Class<?>, Object> cache;

    private ApplicationContext() {
        cache = new LinkedHashMap<>();
        reflections = new Reflections("com.nix.vyrvykhvost");
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public void setCache() {
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Singleton.class);
        addRepositories(classSet);
        addServices(classSet);
    }

    private void addRepositories(Set<Class<?>> classes) {
        classes.stream().forEach(aClass -> {
            Arrays.stream(aClass.getDeclaredConstructors())
                    .forEach(constructor -> {
                        if (!(constructor.isAnnotationPresent(Autowired.class) && constructor.getParameterCount() == 0)) {
                            return;
                        }
                        try {
                            constructor.setAccessible(true);
                            Object object = constructor.newInstance();

                            Field field = aClass.getDeclaredField("instance");
                            field.setAccessible(true);
                            field.set(null, object);
                            cache.put(aClass, object);

                        } catch (InvocationTargetException | InstantiationException
                                 | IllegalAccessException | NoSuchFieldException e) {
                            System.out.println("Exception in constructor " + constructor.getName());
                            e.printStackTrace();
                        }

                    });
        });
    }

    private void addServices(Set<Class<?>> classes) {
        classes.forEach(aClass -> {
            Arrays.stream(aClass.getDeclaredConstructors())
                    .forEach(constructor -> {
                        if (!(constructor.isAnnotationPresent(Autowired.class)
                                && constructor.getParameterCount() == 1)
                        ) {
                            return;
                        }
                        Object repository = null;
                        String name = aClass.getClass().getSimpleName();
                        if (name.startsWith("Headphones")) {
                            repository = cache.get(HeadphonesRepository.class);
                        } else if (name.startsWith("Laptop")) {
                            repository = cache.get(LaptopRepository.class);
                        } else if (name.startsWith("Phone")) {
                            repository = cache.get(PhoneRepository.class);
                        }
                        try {
                            constructor.setAccessible(true);
                            Object object = constructor.newInstance(repository);
                            Field field = aClass.getDeclaredField("instance");
                            field.setAccessible(true);
                            field.set(null, object);
                            cache.put(aClass, object);
                        } catch (InvocationTargetException | InstantiationException
                                 | IllegalAccessException | NoSuchFieldException e) {
                            System.out.println("Exception in constructor " + constructor.getName());
                            e.printStackTrace();

                        }
                    });
        });
    }
}
