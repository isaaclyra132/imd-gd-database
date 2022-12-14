/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mongodb;

import static spark.Spark.get;
import static spark.Spark.port;

public class SparkRoutes {
    public static void main(String[] args) {
    	//port(8080);
        get("/", (req, res) -> {return "Hello World";});
        get("/test", (req, res) -> {return "This is a test page";});
        get("/echo/:thing", (req, res) -> {return req.params(":thing");});
    }
}
