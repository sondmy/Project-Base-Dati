package it.unibo.zoo.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {

    static Map<String, String> env; 

    private static final String ENV_PATH = Path.of("postgres-docker", ".env").toString();

    private static final Map<String, String> ENV = loadEnv();

    private EnvLoader(){
        // impedisce l'istanziazione della classe
    }
    
    private static Map<String, String> loadEnv() {
        Map<String, String> env = new HashMap<>();

        try {
            Files.lines(Path.of(ENV_PATH))
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .filter(line -> !line.startsWith("#"))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            env.put(parts[0], parts[1]);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Impossibile leggere il file .env", e);
        }

        return Map.copyOf(env);
    }

    public static String get(String key) {
        return ENV.get(key);
    }
}
