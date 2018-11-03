package com.chelseaurquhart.securejson;

import com.chelseaurquhart.securejson.JSONException.JSONRuntimeException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @exclude
 */
class ObjectWriter extends ObjectSerializer implements IObjectMutator {
    @Override
    public Object accept(final Object parInput) {
        final Map<CharSequence, Object> myRootMap = new LinkedHashMap<>();
        try {
            return accept(parInput, myRootMap, myRootMap);
        } catch (final IOException myException) {
            throw new JSONRuntimeException(myException);
        }
    }

    private Object accept(final Object parInput, final Map<CharSequence, Object> parRelMap,
                          final Map<CharSequence, Object> parAbsMap) throws IOException {
        final Object myInput = resolve(parInput);

        if (isSimpleType(myInput)) {
            return myInput;
        } else if (isArrayType(myInput)) {
            final Object[] myArray = (Object[]) myInput;
            final Object[] myOutput = new Object[myArray.length];
            for (int myIndex = 0; myIndex < myArray.length; myIndex++) {
                myOutput[myIndex] = accept(myArray[myIndex]);
            }
            return myOutput;
        } else if (isCollectionType(myInput)) {
            final Collection<?> myArray = (Collection<?>) myInput;
            final Collection<Object> myOutput = new ArrayList<>(myArray.size());
            for (final Object myEntry : myArray) {
                myOutput.add(accept(myEntry));
            }
            return myOutput;
        } else if (isMapType(myInput)) {
            final Map<?, ?> myMap = (Map<?, ?>) myInput;
            final Map<CharSequence, Object> myOutput = new LinkedHashMap<>(myMap.size());
            for (final Map.Entry<?, ?> myEntry : myMap.entrySet()) {
                final Object myKey = resolve(myEntry.getKey());
                if (!(myKey instanceof CharSequence)) {
                    throw new JSONEncodeException(Messages.Key.ERROR_INVALID_MAP_KEY_TYPE_STRICT);
                }
                myOutput.put((CharSequence) myKey, accept(myEntry.getValue(), parRelMap, parAbsMap));
            }
            return myOutput;
        } else {
            return addObjectToMap(myInput, parRelMap, parAbsMap);
        }
    }

    private Object resolve(final Object parInput) {
        if (parInput instanceof IJSONSerializeAware) {
            return resolve(((IJSONSerializeAware) parInput).toJSONable());
        }

        return parInput;
    }

    private Map<CharSequence, Object> addObjectToMap(final Object parInput, final Map<CharSequence, Object> parRelMap,
                                                     final Map<CharSequence, Object> parAbsMap) throws IOException {
        for (final Field myField : getFields(parInput.getClass())) {
            final SerializationSettings mySerializationSettings = getSerializationSettings(myField);
            final Object myFieldValue = accept(getValue(myField, parInput), parRelMap, parAbsMap);
            final Map<CharSequence, Object> myTargetMap;
            if (mySerializationSettings.getStrategy() == Relativity.ABSOLUTE) {
                myTargetMap = parAbsMap;
            } else {
                myTargetMap = parRelMap;
            }
            addToMap(myFieldValue, myTargetMap, parAbsMap, mySerializationSettings.getTarget());
        }

        return parAbsMap;
    }

    private void addToMap(final Object parFieldValue, final Map<CharSequence, Object> parTargetMap,
                          final Map<CharSequence, Object> parAbsMap, final CharSequence[] parTarget)
            throws IOException {

        Map<CharSequence, Object> myTargetMap = parTargetMap;

        for (int myIndex = 0; myIndex < parTarget.length; myIndex++) {
            if (myIndex < parTarget.length - 1) {
                Object myValue = myTargetMap.get(parTarget[myIndex]);
                if (myValue == null) {
                    myValue = new LinkedHashMap<>();
                    myTargetMap.put(parTarget[myIndex], myValue);
                } else if (!(myValue instanceof Map)) {
                    throw new JSONEncodeException(Messages.Key.ERROR_ATTEMPT_TO_ADD_MAP_ENTRY_TO_NON_MAP);
                }

                myTargetMap = castToMap(myValue);
            } else {
                if (myTargetMap.containsKey(parTarget[myIndex])) {
                    throw new JSONEncodeException(Messages.Key.ERROR_INVALID_SERIALIZATION_CONFIG);
                }
                myTargetMap.put(parTarget[myIndex], accept(parFieldValue, myTargetMap, parAbsMap));
            }
        }
    }
}
