/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.stream.processor.core.ha;

import org.wso2.carbon.sp.metrics.core.SPThroughputMetric;
import org.wso2.carbon.stream.processor.core.internal.StreamProcessorDataHolder;
import org.wso2.carbon.stream.processor.core.internal.util.SiddhiAppProcessorConstants;
import org.wso2.siddhi.core.stream.input.source.SourceHandler;
import org.wso2.siddhi.core.stream.input.source.SourceHandlerManager;
import org.wso2.siddhi.core.util.SiddhiConstants;

/**
 * Implementation of {@link SourceHandlerManager} used for 2 node minimum HA
 */
public class HACoordinationSourceHandlerManager extends SourceHandlerManager {
    private SPThroughputMetric throughputTracker;

    public HACoordinationSourceHandlerManager() {
        if (throughputTracker == null) {
            throughputTracker = (SPThroughputMetric) StreamProcessorDataHolder.getStatisticsConfiguration().
                    getFactory().createThroughputTracker(SiddhiAppProcessorConstants.HA_METRICS_PREFIX +
                            SiddhiConstants.METRIC_DELIMITER +
                            SiddhiAppProcessorConstants.HA_METRICS_SENDING_THROUGHPUT,
                    StreamProcessorDataHolder.getStatisticsManager());
        }
    }

    @Override
    public SourceHandler generateSourceHandler(String sourceType) {
        return new HACoordinationSourceHandler(throughputTracker, sourceType);
    }
}
