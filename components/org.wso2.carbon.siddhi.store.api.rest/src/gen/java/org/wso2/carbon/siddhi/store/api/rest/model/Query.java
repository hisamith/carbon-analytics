/*
 *   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */

package org.wso2.carbon.siddhi.store.api.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This clss represents the bean class or the query reqest body
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen", date = "2017-11-01T11:26:25.925Z")
public class Query {
    @JsonProperty("query")
    private String query = null;
    @JsonProperty("appName")
    private String appName = null;
    @JsonProperty("details")
    private boolean details = false;

    public Query query(String appName, String query) {
        this.query = query;
        this.appName = appName;
        return this;
    }

    public Query query(String appName, String query, boolean details) {
        this.query = query;
        this.appName = appName;
        this.details = details;
        return this;
    }

    @ApiModelProperty(example = "@PrimaryKey('firstname','lastname', 'age')" +
                                "@store(type='solr', url='localhost:9983', collection='StudentStore', " +
                                "base.config='baseconfig', shards='2', replicas='2', schema='firstname string stored," +
                                "lastname string stored, age int stored', commit.async='true')" +
                                "define table FooTable(firstname string, lastname string, age int);")
    public String getAppName() {
        return appName;
    }

    public String getQuery() {
        return query;
    }

    public void setAppName(String appName){
        this.appName = appName;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isDetails() {
        return details;
    }

    public void setDetails(boolean details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Query)) {
            return false;
        }

        Query query1 = (Query) o;

        if (!query.equals(query1.query)) {
            return false;
        }
        if (!appName.equals(query1.appName)) {
            return false;
        }
        if (details != query1.details) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = query.hashCode();
        result = 31 * result + appName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Query {\n");

        sb.append("    appName: ").append(toIndentedString(appName)).append("\n");
        sb.append("    query: ").append(toIndentedString(query)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

