package io.pucman.common.reflect.accessors;

import io.pucman.common.generic.GenericUtil;
import io.pucman.common.reflect.ReflectUtil;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Wrapper for reflected fields.
 * @see ReflectUtil#getField(Class, String, ReflectUtil.Type).
 * @param <T> - return type of the field.
 */
public class FieldAccessor<T>
{
    private Field field;


    public FieldAccessor(Field field)
    {
        this.field = field;
        field.setAccessible(true);
    }

    /**
     * Ascertain the value defined in this field.
     * @param instance - instance that this will be invoked on.
     * @return T
     */
    @SneakyThrows
    public T get(Object instance)
    {
        return GenericUtil.cast(field.get(instance));
    }

    /**
     * Set a new value to this field.
     * @param instance - instance that this will be invoked on.
     * @param value - value the field will be set to.
     */
    @SneakyThrows
    public void set(Object instance, T value)
    {
        field.set(instance, value);
    }

    /**
     * @return all the annotations accessible in this class.
     */
    public Annotation[] getPublicAnnotations()
    {
        return field.getAnnotations();
    }

    /**
     * @return all annotations accessible only in this class.
     */
    public Annotation[] getPrivateAnnotations()
    {
        return field.getDeclaredAnnotations();
    }

    /**
     * To check if the field has the annotation.
     * @param annotation - annotation to check.
     * @return whether it has that annotation.
     */
    public boolean hasAnnotation(Class<? extends Annotation> annotation)
    {
        return field.isAnnotationPresent(annotation);
    }

    /**
     * Get an annotation, returns null if annotation is not found.
     * @param annotation - the annotation.
     * @return the annotation.
     */
    public Annotation getAnnotation(Class<? extends Annotation> annotation, ReflectUtil.Type annotationType)
    {
        return annotationType == ReflectUtil.Type.PUBLIC ? field.getAnnotation(annotation) : field.getDeclaredAnnotation(annotation);
    }

    /**
     * @return type of field it is.
     */
    public Class<?> getType()
    {
        return field.getType();
    }

    /**
     * @return the field.
     */
    public Field get()
    {
        return field;
    }

    /**
     * @return field identifier.
     */
    public String getName()
    {
        return field.getName();
    }
}