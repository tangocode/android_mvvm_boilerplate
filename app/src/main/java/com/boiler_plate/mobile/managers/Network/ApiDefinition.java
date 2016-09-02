package com.boiler_plate.mobile.managers.Network;

/**
 * Created by juanpablogarcia on 3/28/16.
 */
public class ApiDefinition extends NetworkApiDefinition {
    public static ApiDefinition dummyApiRequest(int itemId) {
        servicePath = "customers/" + itemId;
        apiMethod = HttpMethod.GET;
        serviceSecurity = ServiceSecurity.AUTHORIZATION;
        return new ApiDefinition();
    }
}
