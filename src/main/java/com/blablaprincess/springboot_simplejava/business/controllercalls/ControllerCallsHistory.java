package com.blablaprincess.springboot_simplejava.business.controllercalls;

import java.util.List;

public interface ControllerCallsHistory {
    void saveCall(ControllerCallDescriptionEntity call);
    List<ControllerCallDescriptionEntity> getLastCalls(ControllerCallsHistoryLastCallsArgs args);
}
