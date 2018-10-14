package com.chelseaurquhart.securejson;

import java.io.IOException;

class WordReader implements IReader<Object> {
    @Override
    public boolean isStart(final IterableCharSequence parIterator) {
        final char myChar = parIterator.peek();

        return myChar == JSONSymbolCollection.Token.NULL.getShortSymbol()
            || myChar == JSONSymbolCollection.Token.FALSE.getShortSymbol()
            || myChar == JSONSymbolCollection.Token.TRUE.getShortSymbol();
    }

    @Override
    public Object read(final IterableCharSequence parIterator)
            throws IOException {

        final char myChar = parIterator.peek();

        if (myChar == JSONSymbolCollection.Token.NULL.getShortSymbol()) {
            return readWord(parIterator, JSONSymbolCollection.Token.NULL);
        }
        if (myChar == JSONSymbolCollection.Token.FALSE.getShortSymbol()) {
            return readWord(parIterator, JSONSymbolCollection.Token.FALSE);
        }
        if (myChar == JSONSymbolCollection.Token.TRUE.getShortSymbol()) {
            return readWord(parIterator, JSONSymbolCollection.Token.TRUE);
        }

        throw new JSONDecodeException.InvalidTokenException(parIterator);
    }

    private Object readWord(final IterableCharSequence parIterator, final JSONSymbolCollection.Token parToken)
            throws IOException {
        final CharSequence myWord = parToken.toString().toLowerCase();
        final int myCheckingLength = myWord.length();

        for (int myIndex = 0; myIndex < myCheckingLength; myIndex++) {
            if (!parIterator.hasNext()) {
                throw new JSONDecodeException.InvalidTokenException(parIterator);
            }
            if (myWord.charAt(myIndex) != parIterator.next()) {
                throw new JSONDecodeException.InvalidTokenException(parIterator);
            }
        }

        if (!parIterator.hasNext()) {
            return parToken.getValue();
        }

        final char myChar = parIterator.peek();
        if (JSONSymbolCollection.TOKENS.containsKey(myChar) || JSONSymbolCollection.WHITESPACES.containsKey(myChar)) {
            return parToken.getValue();
        }

        throw new JSONDecodeException.InvalidTokenException(parIterator);
    }
}
