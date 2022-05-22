package Realisation;

import Resource.ResourceHungarian;
import Resource.ResourcePortuguese;
import Resource.ResourceRussian;
import Resource.ResourceSpanish;

import java.io.Serializable;
import java.util.ListResourceBundle;

public enum Languages implements Serializable {
    RUSSIAN("Русский") {
        @Override
        public ListResourceBundle getResources() {
            return new ResourceRussian();
        }
    },
    SPANISH("Español") {
        @Override
        public ListResourceBundle getResources() {
            return new ResourceSpanish();
        }
    },
    HUNGARIAN("Magyar") {
        @Override
        public ListResourceBundle getResources() {
            return new ResourceHungarian();
        }
    },
    PORTUGUESE("Português") {
        @Override
        public ListResourceBundle getResources() {
            return new ResourcePortuguese();
        }
    };

    private final String name;

    Languages(String name) {
        this.name = name;
    }

    public abstract ListResourceBundle getResources();

    public String getName() {
        return name;
    }

    public static Languages getEnum(String value) {
        for (Languages language : values()) {
            if (language.getName().equals(value)) return language;
        }
        return null;
    }
}