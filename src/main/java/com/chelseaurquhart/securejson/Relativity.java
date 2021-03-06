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

/**
 * Specifies the relativeTo state of a serialization target. Absolute means relative to the root of the JSON map. Note
 * that the absolute position whenever we are inside a collection-like entity (collection, array, or map.) Relative is
 * the default setting and usually makes the most sense.
 */
public enum Relativity {
    /**
     * Absolute relativeTo state means that all fields are serialized relative to the root JSON. In the case of fields
     * that are nested within arrays/sets, the root becomes that array/set.
     */
    ABSOLUTE,

    /**
     * Relative state means that all fields are serialized relative to their position in a Map. This is the default.
     */
    RELATIVE
}
