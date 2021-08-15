package com.blablaprincess.springboot_simplejava.business.methodcalls;

import java.util.List;

public interface MethodCallsHistory {
    void saveCall(MethodCallDescriptionEntity call);
    List<MethodCallDescriptionEntity> getLastCalls(MethodCallsHistoryLastCallsArgs args);
}
