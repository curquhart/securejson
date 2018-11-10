/*
 * Copyright 2018 Chelsea Urquhart
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

import org.testng.Assert;
import org.testng.annotations.Test;

public final class CharQueueTest {
    private CharQueueTest() {
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testAddBufferOverflow() {
        final CharQueue myQueue = new CharQueue(4);
        myQueue.add('a');
        myQueue.add('b');
        myQueue.add('c');
        myQueue.add('d');
        myQueue.add('e');
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testPopBufferUnderflow() {
        final CharQueue myQueue = new CharQueue(4);
        myQueue.pop();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testPeekBufferUnderflow() {
        final CharQueue myQueue = new CharQueue(4);
        myQueue.peek();
    }

    @Test
    public void testWrapping() {
        final CharQueue myQueue = new CharQueue(4);
        myQueue.add('a');
        myQueue.add('b');
        myQueue.add('c');
        myQueue.add('d');
        Assert.assertEquals(myQueue.peek(), 'a');
        Assert.assertEquals(myQueue.pop(), 'a');
        myQueue.add('e');
        Assert.assertEquals(myQueue.pop(), 'b');
        Assert.assertEquals(myQueue.pop(), 'c');
        Assert.assertEquals(myQueue.pop(), 'd');
        Assert.assertEquals(myQueue.pop(), 'e');
        Assert.assertTrue(myQueue.isEmpty());
    }
}