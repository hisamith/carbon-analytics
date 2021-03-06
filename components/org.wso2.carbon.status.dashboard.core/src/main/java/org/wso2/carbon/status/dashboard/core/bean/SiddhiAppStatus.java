/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

package org.wso2.carbon.status.dashboard.core.bean;

import org.wso2.siddhi.core.util.statistics.metrics.Level;

import java.util.concurrent.TimeUnit;

/**
 * Bean class use for store siddhi app statistics.
 */
public class SiddhiAppStatus {
    private String appName;
    private String status;
    private long age;
    private String agetime;
    private Level siddhiStatEnabledLevel;
    private String isStatEnabled;
    private SiddhiAppMetricsHistory appMetricsHistory;
    
    public SiddhiAppStatus() {
    }
    
    public String getAppName() {
        return appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public long getAge() {
        return age;
    }
    
    public void setAge(long age) {
        this.age = age;
    }
    
    public String getAgetime() {
        return agetime;
    }
    
    public void populateAgetime() {
        this.agetime = getTimeAgo();
    }

    public String isStatEnabled() {
        return siddhiStatEnabledLevel.toString();
    }

    public Level getSiddhiStatEnabledLevel() {
        return siddhiStatEnabledLevel;
    }

    public String getEnabledStatLevelAsString() {
        return isStatEnabled;
    }

    public void setStatEnabled(Level statEnabled) {
        siddhiStatEnabledLevel = statEnabled;
        isStatEnabled = statEnabled.toString();
    }
    
    public SiddhiAppMetricsHistory getAppMetricsHistory() {
        return appMetricsHistory;
    }
    
    public void setAppMetricsHistory(SiddhiAppMetricsHistory appMetricsHistory) {
        this.appMetricsHistory = appMetricsHistory;
    }
    
    /**
     * Get human readable time format
     *
     * @return return human readable format
     */
    public String getTimeAgo() {
        String ageString = "";
        long days = TimeUnit.MILLISECONDS.toDays(this.age);
        long hrs = TimeUnit.MILLISECONDS.toHours(this.age) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays
                (this.age));
        long min = TimeUnit.MILLISECONDS.toMinutes(this.age) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours
                (this.age));
        long sec = TimeUnit.MILLISECONDS.toSeconds(this.age) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(this.age));
        
        if (days > 0) {
            ageString = ageString + " " + days + "d";
        }
        if (hrs > 0) {
            ageString = ageString + " " + hrs + "h";
        }
        if (min > 0) {
            ageString = ageString + " " + min + "m";
        }
        if (sec > 0) {
            ageString = ageString + " " + sec + "s";
        } else {
            ageString = ageString + " " + this.age + " ms";
        }
        return ageString + " ago";
    }
}
