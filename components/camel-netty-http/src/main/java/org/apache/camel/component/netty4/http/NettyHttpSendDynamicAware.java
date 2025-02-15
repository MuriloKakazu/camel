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
package org.apache.camel.component.netty4.http;

import org.apache.camel.http.common.HttpSendDynamicAware;
import org.apache.camel.spi.annotations.SendDynamic;

@SendDynamic("netty-http")
public class NettyHttpSendDynamicAware extends HttpSendDynamicAware {

    @Override
    protected String[] parseUri(DynamicAwareEntry entry) {
        // camel-netty parses the uri a bit differently than camel-http-common base class

        String scheme = entry.getProperties().get("protocol");
        String host = entry.getProperties().get("host");
        String port = entry.getProperties().get("port");
        String path = entry.getProperties().get("path");

        String baseUrl = scheme + "://" + host;
        if (port != null) {
            baseUrl += ":" + port;
        }
        return new String[]{baseUrl, path};
    }

}

