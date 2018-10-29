package com.chelseaurquhart.securejson;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Serializer to/from Objects.
 */
public class ObjectSerializer {
    /**
     * Convert an object to a JSON character sequence. If it cannot be converted, throws JSONEncodeException. After the
     * consumer returns, the buffer will be destroyed so it MUST be fully consumed.
     *
     * @param parInput The input object to toJSONAble to JSON.
     * @param parConsumer The consumer to provide the JSON character sequence to when completed.
     * @throws JSONEncodeException On encode failure.
     */
    public static void toJSON(final Object parInput, final IConsumer<CharSequence> parConsumer)
            throws JSONEncodeException {
        SecureJSON.toJSON(parInput, parConsumer, new ObjectWriter());
    }

    /**
     * Convert an object to a JSON byte array. If it cannot be converted, throws JSONEncodeException. After the consumer
     * returns, the buffer will be destroyed so it MUST be fully consumed.
     *
     * @param parInput The input object to toJSONAble to JSON.
     * @param parConsumer The consumer to provide the JSON character sequence to when completed.
     * @throws JSONEncodeException On encode failure.
     */
    public static void toJSONBytes(final Object parInput, final IConsumer<byte[]> parConsumer)
            throws JSONEncodeException {
        SecureJSON.toJSONBytes(parInput, parConsumer, new ObjectWriter());
    }

    /**
     * Convert an object to a JSON string, writing to the provided stream.
     *
     * @param parInput The input object to toJSONAble to JSON.
     * @param parOutputStream The stream to write to.
     * @param parCharset The charset to use.
     * @throws JSONEncodeException On encode failure.
     */
    public static void toJSON(final Object parInput, final OutputStream parOutputStream, final Charset parCharset)
            throws JSONEncodeException {
        SecureJSON.toJSON(parInput, parOutputStream, parCharset, new ObjectWriter());
    }

    final SerializationSettings getSerializationSettings(final Field parField) {
        final Serialize myAnnotation = parField.getAnnotation(Serialize.class);
        String[] mySerializationTarget = null;
        Relativity mySerializationTargetStrategy = Relativity.RELATIVE;

        if (myAnnotation != null) {
            final String[] myValue = myAnnotation.name();
            if (myValue.length > 0) {
                mySerializationTarget = myValue;
            }
            mySerializationTargetStrategy = myAnnotation.relativeTo();
        }

        if (mySerializationTarget == null) {
            mySerializationTarget = new String[]{parField.getName()};
        }

        return new SerializationSettings(mySerializationTarget, mySerializationTargetStrategy);
    }

    final boolean isSimpleType(final Object parInput) {
        return parInput == null || parInput instanceof Number || parInput instanceof CharSequence
            || parInput instanceof Boolean;
    }

    final boolean isCollectionType(final Object parInput) {
        return parInput instanceof Collection;
    }

    final boolean isMapType(final Object parInput) {
        return parInput instanceof Map;
    }

    final boolean isArrayType(final Object parInput) {
        return parInput != null && isArrayType(parInput.getClass());
    }

    final boolean isArrayType(final Class<?> parInput) {
        return parInput.isArray();
    }

    final Collection<Field> getFields(final Class parClass) {
        final List<Field> myCollection = new LinkedList<>();
        for (final Field myField : parClass.getDeclaredFields()) {
            if (Modifier.isTransient(myField.getModifiers()) || myField.isSynthetic()) {
                // ignore transient and synthetic fields.
                continue;
            }
            myField.setAccessible(true);
            myCollection.add(myField);
        }
        final Class mySuperClass = parClass.getSuperclass();
        if (mySuperClass != null) {
            myCollection.addAll(getFields(mySuperClass));
        }

        return Collections.unmodifiableCollection(myCollection);
    }

    final Object getValue(final Field parField, final Object parInstance) throws JSONException {
        try {
            return parField.get(parInstance);
        } catch (final IllegalAccessException | ExceptionInInitializerError | IllegalArgumentException myException) {
            throw new JSONException(myException);
        }
    }

    final void setValueIfNotNull(final Field parField, final Object parInput, final Object parValue)
            throws JSONException {
        try {
            if (parValue != null) {
                parField.set(parInput, parValue);
            }
        } catch (final IllegalAccessException | ExceptionInInitializerError | IllegalArgumentException myException) {
            throw new JSONException(myException);
        }
    }

    @SuppressWarnings("unchecked")
    final Map<CharSequence, Object> castToMap(final Object parValue) throws JSONException {
        try {
            return (Map) parValue;
        } catch (final ClassCastException myException) {
            throw new JSONException(myException);
        }
    }

    static final class SerializationSettings {
        private final CharSequence[] target;
        private final Relativity strategy;

        private SerializationSettings(final CharSequence[] parTarget, final Relativity parStrategy) {
            target = parTarget;
            strategy = parStrategy;
        }

        CharSequence[] getTarget() {
            return target;
        }

        Relativity getStrategy() {
            return strategy;
        }

        @Override
        public boolean equals(final Object parObject) {
            if (this == parObject) {
                return true;
            }
            if (parObject == null || getClass() != parObject.getClass()) {
                return false;
            }
            final SerializationSettings myThat = (SerializationSettings) parObject;
            return Arrays.equals(target, myThat.target);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(target);
        }
    }
}
