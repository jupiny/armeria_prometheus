package com.example.armeria_prometheus;

import com.linecorp.armeria.common.logging.RequestOnlyLog;
import com.linecorp.armeria.common.metric.MeterIdPrefix;
import com.linecorp.armeria.common.metric.MeterIdPrefixFunctionCustomizer;

import io.micrometer.core.instrument.MeterRegistry;

public class MyMeterIdPrefixFunction implements MeterIdPrefixFunctionCustomizer {

    @Override
    public MeterIdPrefix apply(MeterRegistry registry, RequestOnlyLog log, MeterIdPrefix meterIdPrefix) {
        return meterIdPrefix.append(log.requestHeaders().method().name());
    }
}
