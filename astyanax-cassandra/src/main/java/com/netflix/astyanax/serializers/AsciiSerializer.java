/**
 * Copyright 2013 Netflix, Inc.
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
package com.netflix.astyanax.serializers;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.netflix.astyanax.shaded.org.apache.cassandra.db.marshal.AsciiType;

/**
 * Almost identical to StringSerializer except we use the US-ASCII character set
 * code
 * 
 * @author zznate
 */
public final class AsciiSerializer extends AbstractSerializer<String> {

    private static final String US_ASCII = "US-ASCII";
    private static final AsciiSerializer instance = new AsciiSerializer();
    private static final Charset charset = Charset.forName(US_ASCII);

    public static AsciiSerializer get() {
        return instance;
    }

    @Override
    public String fromByteBuffer(final ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        ByteBuffer dup = byteBuffer.duplicate();
        return charset.decode(dup).toString();
    }

    @Override
    public ByteBuffer toByteBuffer(String obj) {
        if (obj == null) {
            return null;
        }
        return ByteBuffer.wrap(obj.getBytes(charset));
    }

    @Override
    public ComparatorType getComparatorType() {
        return ComparatorType.ASCIITYPE;
    }

    @Override
    public ByteBuffer fromString(String str) {
        return AsciiType.instance.fromString(str);
    }

    @Override
    public String getString(final ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        return AsciiType.instance.getString(byteBuffer.duplicate());
    }
}
