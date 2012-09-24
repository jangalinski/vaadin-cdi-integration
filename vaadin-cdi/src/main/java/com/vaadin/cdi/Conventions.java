package com.vaadin.cdi;

import com.vaadin.ui.UI;

public class Conventions {

    static String firstToLower(String name) {
        char firstLower = Character.toLowerCase(name.charAt(0));
        if (name.length() > 1) {
            return firstLower + name.substring(1);
        } else {
            return String.valueOf(firstLower);
        }
    }

    static String deriveNameFromConvention(Class<?> clazz) {
        return firstToLower(clazz.getSimpleName());
    }

    public static String deriveMappingForUI(Class<?> beanClass) {
        if (beanClass.isAnnotationPresent(Mapping.class)) {
            Mapping annotation = beanClass.getAnnotation(Mapping.class);
            String mapping = annotation.value();
            if (mapping != null && !mapping.isEmpty()) {
                return mapping;
            } else {
                return deriveNameFromConvention(beanClass);
            }
        }else{
            //TODO make Mapping optional
            return null;
        }
    }

    public static String deriveMappingForView(Class<?> clazz) {
        VaadinView annotation = clazz.getAnnotation(VaadinView.class);
        if (annotation == null || annotation.value().isEmpty()) {
            return Conventions.deriveNameFromConvention(clazz);
        } else {
            return annotation.value();
        }
    }

    public static String deriveMappingForUI(UI ui) {
        if(ui == null)
            return null;
        return deriveMappingForUI(ui.getClass());
    }
}
