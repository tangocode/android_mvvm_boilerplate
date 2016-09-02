package com.boiler_plate.mobile.managers.Network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juanpablogarcia on 3/24/16.
 */
public class NetworkApiDefinition {
    static Map<String, Object> body = new HashMap<>();
    static String servicePath;
    static HttpMethod apiMethod;
    static ServiceSecurity serviceSecurity;

    public Map<String, Object> getBody() {
        return body;
    }

    public String getServicePath() {
        return servicePath;
    }

    public HttpMethod getMethod() {
        return apiMethod;
    }

    public ServiceSecurity getServiceSecurity() {
        return serviceSecurity;
    }
}
