package com.chelseaurquhart.securejson;

/**
 * @exclude
 */
class Settings {
    static final boolean DEFAULT_STRICT_STRINGS = true;
    static final boolean DEFAULT_STRICT_MAP_KEY_TYPES = true;
    static final IFunction<Integer, IWritableCharSequence> DEFAULT_WRITABLE_CHAR_BUFFER_FACTORY =
            new IFunction<Integer, IWritableCharSequence>() {
        @Override
        public IWritableCharSequence accept(final Integer parCapacity) {
            return new ManagedSecureCharBuffer.ObfuscatedByteBuffer(parCapacity);
        }
    };
    static final Settings DEFAULTS = new Settings();

    private final boolean strictStrings;
    private final boolean strictMapKeyTypes;
    private final IFunction<Integer, IWritableCharSequence> writableCharBufferFactory;

    Settings() {
        strictStrings = DEFAULT_STRICT_STRINGS;
        strictMapKeyTypes = DEFAULT_STRICT_MAP_KEY_TYPES;
        writableCharBufferFactory = DEFAULT_WRITABLE_CHAR_BUFFER_FACTORY;
    }

    Settings(final SecureJSON.Builder parBuilder) {
        strictStrings = parBuilder.isStrictStrings();
        strictMapKeyTypes = parBuilder.isStrictMapKeyTypes();
        writableCharBufferFactory = parBuilder.getWritableCharBufferFactory();
    }

    boolean isStrictStrings() {
        return strictStrings;
    }

    boolean isStrictMapKeyTypes() {
        return strictMapKeyTypes;
    }

    IFunction<Integer, IWritableCharSequence> getWritableCharBufferFactory() {
        return writableCharBufferFactory;
    }
}