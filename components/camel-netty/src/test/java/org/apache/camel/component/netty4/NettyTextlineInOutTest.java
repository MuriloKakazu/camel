/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.netty4;

import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

public class NettyTextlineInOutTest extends BaseNettyTest {

    @Test
    public void testTextlineInOut() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived("Hello World");

        String reply = template.requestBody("netty:tcp://localhost:{{port}}?textline=true&sync=true", "Hello World", String.class);
        assertEquals("Bye World", reply);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("netty:tcp://localhost:{{port}}?textline=true&sync=true")
                    // body should be a String when using textline codec
                    .validate(body().isInstanceOf(String.class))
                    .to("mock:result")
                    .transform(body().regexReplaceAll("Hello", "Bye"));
            }
        };
    }
}