/*
 * Copyright 2019 Chelsea Urquhart
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chelseaurquhart.securejson;

import  com.chelseaurquhart.securejson.JSONDecodeException.MalformedNumberException;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Provider of numeric data, used by multiple test suites.
 */
@SuppressWarnings("PMD.AddEmptyString")
public final class NumberProvider {
    private static final MathContext HUGE_PRECISION_MATH_CONTEXT = new MathContext((int) Math.pow(2, 17),
        RoundingMode.UNNECESSARY);
    static final String DATA_PROVIDER_NAME = "NumberProvider";

    private NumberProvider() {
    }

    /**
     * This provider is consumed by the number parser as well as the json reader. The json reader tests are much less
     * strict than those of the number parser, but it confirms that numbers read (and fail to read) appropriately.
     *
     * @param parMethod The method being executed.
     * @return A collection of parameters.
     * @throws IOException If message loading failed.
     */
    @DataProvider(name = DATA_PROVIDER_NAME, parallel = true)
    public static Object[] dataProvider(final Method parMethod) throws IOException {
        return new Object[]{
            // whole numbers at edges of type ranges
            buildParameters(
                "short min - 1",
                "" + (Short.MIN_VALUE - 1),
                (int) Short.MIN_VALUE - 1,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "short min",
                "" + Short.MIN_VALUE,
                (int) Short.MIN_VALUE,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "short min + 1",
                "" + (Short.MIN_VALUE + 1),
                (Short.MIN_VALUE + 1),
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "int min - 1",
                "" + ((long) Integer.MIN_VALUE - 1),
                (long) Integer.MIN_VALUE - 1,
                Long.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "int min",
                "" + Integer.MIN_VALUE,
                Integer.MIN_VALUE,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "int min + 1",
                "" + (Integer.MIN_VALUE + 1),
                Integer.MIN_VALUE + 1,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "long min - 1",
                new BigDecimal(BigInteger.valueOf(Long.MIN_VALUE)).subtract(BigDecimal.ONE).toString(),
                new BigDecimal(BigInteger.valueOf(Long.MIN_VALUE)).subtract(BigDecimal.ONE).doubleValue(),
                Double.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "long min",
                "" + Long.MIN_VALUE,
                Long.MIN_VALUE,
                Long.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "long min + 1",
                "" + (Long.MIN_VALUE + 1),
                Long.MIN_VALUE + 1,
                Long.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "short max - 1",
                "" + (Short.MAX_VALUE - 1),
                Short.MAX_VALUE - 1,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "short max",
                "" + Short.MAX_VALUE,
                (int) Short.MAX_VALUE,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "short max + 1",
                "" + (Short.MAX_VALUE + 1),
                (int) Short.MAX_VALUE + 1,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "int max - 1",
                "" + (Integer.MAX_VALUE - 1),
                Integer.MAX_VALUE - 1,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "int max",
                "" + Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "int max + 1",
                "" + ((long) Integer.MAX_VALUE + 1),
                (long) Integer.MAX_VALUE + 1,
                Long.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "long max - 1",
                "" + (Long.MAX_VALUE - 1),
                Long.MAX_VALUE - 1,
                Long.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "long max",
                "" + Long.MAX_VALUE,
                Long.MAX_VALUE,
                Long.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "long max + 1",
                new BigDecimal(BigInteger.valueOf(Long.MAX_VALUE))
                    .add(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT).toString(),
                new BigDecimal(BigInteger.valueOf(Long.MAX_VALUE))
                    .add(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT).doubleValue(),
                Double.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "double min - 1",
                new BigDecimal(Double.MIN_VALUE).negate().subtract(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT)
                    .toString(),
                new BigDecimal(Double.MIN_VALUE).negate().subtract(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT),
                BigDecimal.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "double min",
                new BigDecimal(Double.MIN_NORMAL).toString(),
                Double.MIN_NORMAL,
                Double.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "double min + 1",
                "" + (Double.MIN_VALUE + 1),
                Double.MIN_VALUE + 1,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "double max - 1",
                new BigDecimal(Double.MAX_VALUE).subtract(BigDecimal.ONE).toString(),
                new BigDecimal(Double.MAX_VALUE).subtract(BigDecimal.ONE),
                BigDecimal.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "double max",
                new BigDecimal(Double.MAX_VALUE).toString(),
                new BigDecimal(Double.MAX_VALUE),
                BigDecimal.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "double max + 1",
                new BigDecimal(Double.MAX_VALUE).add(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT).toString(),
                new BigDecimal(Double.MAX_VALUE).add(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT),
                BigDecimal.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "simple standard notation with decimal padded",
                "1.0000000",
                1d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple standard notation with plus prefix",
                "+0001.000000",
                1d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence())),
            buildParameters(
                "lots of decimals",
                "1.000000123",
                1.000000123d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "1.0 padded, negative",
                "-1.00000000",
                -1d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "1.0 unpadded, negative",
                "-1.0",
                -1d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "1.00001 negative",
                "-1.00001",
                -1.00001,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "1.00001 positive",
                "1.00001",
                1.00001d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "1.0 padded, no sign",
                "00000001.00000000",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(1))),
            buildParameters(
                "negative zero",
                "-0",
                -0,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "1.0 padded, positive sign",
                "+00000001.00000000",
                1d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence())),
            buildParameters(
                "1.0 unpadded, no sign",
                "1.0",
                1d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "zero",
                "0",
                0,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "0e+",
                "0e+",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(2))),
            buildParameters(
                "0e1",
                "0e1",
                0,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "0e+1",
                "0e+1",
                0,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "no integer part, decimal",
                "-.1",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(1))),
            buildParameters(
                "no integer part, exponent",
                "-e1",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(1))),
            buildParameters(
                "1.0 unpadded, positive sign",
                "+1.0",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence())),
            buildParameters(
                "trailing decimal on 0",
                "0.",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(1))),
            buildParameters(
                "trailing decimal on 11",
                "11.",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(2))),
            buildParameters(
                "just decimal",
                ".",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence())),
            buildParameters(
                "just minus",
                "-",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence())),
            buildParameters(
                "minus decimal",
                "-.",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(1))),
            buildParameters(
                "exponent minus",
                "e-",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence())),
            buildParameters(
                "exponent minus with number",
                "3e-",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(2))),
            buildParameters(
                "simple standard notation with no decimal, negative",
                "-1",
                -1,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple standard notation with no decimal, positive",
                "1",
                1,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple scientific notation lowercase no sign",
                "1.0e2",
                100d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple scientific notation uppercase no sign",
                "1.0E2",
                100d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple scientific notation positive sign",
                "1.0e+2",
                100d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple scientific notation negative sign",
                "1.0e-2",
                0.01d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple scientific notation no decimal negative sign",
                "1e-2",
                0.01d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple scientific notation no decimal no sign",
                "1e2",
                100,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "simple scientific notation 2 digits no decimal no sign",
                "22e1",
                220,
                Integer.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "0.0",
                "0.0",
                0.0d,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "decimal at start",
                ".5",
                null,
                Double.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence())),
            buildParameters(
                "enormous number",
                new BigDecimal(1234567890).pow(1234, HUGE_PRECISION_MATH_CONTEXT).toString(),
                new BigDecimal(1234567890).pow(1234, HUGE_PRECISION_MATH_CONTEXT),
                BigDecimal.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "enormous number (negative)",
                new BigDecimal(-1234567890).pow(1234, HUGE_PRECISION_MATH_CONTEXT).toString(),
                new BigDecimal(-1234567890).pow(1234, HUGE_PRECISION_MATH_CONTEXT),
                BigDecimal.class,
                HUGE_PRECISION_MATH_CONTEXT
            ),
            buildParameters(
                "decimal in exponent",
                "1e2.0",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(3))),
            buildParameters(
                "overflow",
                new BigDecimal(Double.MAX_VALUE).add(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT).toString(),
                new HugeDecimal(new BigDecimal(Double.MAX_VALUE).add(BigDecimal.ONE, HUGE_PRECISION_MATH_CONTEXT)
                    .toString(), new NumberReader(Settings.DEFAULTS)),
                HugeDecimal.class,
                NumberReader.DEFAULT_MATH_CONTEXT
            ),
            buildParameters(
                "multiple decimals",
                "1.2.3",
                null,
                null,
                NumberReader.DEFAULT_MATH_CONTEXT
            ).exception(new MalformedNumberException(new PresetIterableCharSequence(3)))
        };
    }

    private static <T extends Number> Parameters<T> buildParameters(final String parTestName,
                                                                    final String parInputNumber,
                                                                    final T parExpectedNumber,
                                                                    final Class<T> parExpectedClass,
                                                                    final MathContext parMathContext) {
        return new Parameters<T>(
            parTestName,
            newSecureCharBuffer(parInputNumber),
            parExpectedNumber,
            parExpectedClass,
            parMathContext
        );
    }

    private static CharSequence newSecureCharBuffer(final CharSequence parInput) {
        if (parInput == null || parInput.length() == 0) {
            return null;
        }

        final ManagedSecureCharBuffer mySecureBuffer = new ManagedSecureCharBuffer(parInput.length());
        mySecureBuffer.append(parInput);

        return mySecureBuffer;
    }

    /**
     * Parameters for numeric tests.
     */
    static class Parameters<T extends Number> {
        final String testName;
        final CharSequence number;
        final T expected;
        final Class<T> expectedNumberClass;
        final MathContext mathContext;
        Exception expectedException;

        Parameters(final String parTestName, final CharSequence parNumber, final T parExpected,
                   final Class<T> parExpectedNumberClass, final MathContext parMathContext) {
            this.testName = parTestName;
            this.number = parNumber;
            this.expected = parExpected;
            this.expectedNumberClass = parExpectedNumberClass;
            this.mathContext = parMathContext;
        }

        Parameters<T> exception(final Exception parException) {
            expectedException = parException;
            return this;
        }

        @Override
        public String toString() {
            return testName;
        }
    }
}
