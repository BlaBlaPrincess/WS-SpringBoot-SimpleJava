package com.blablaprincess.springboot_simplejava.business.controllercalls;

import java.time.OffsetDateTime;
import java.util.List;

public interface ControllerCallsHistory {
    void saveCall(ControllerCallDescriptionEntity call);
    List<ControllerCallDescriptionEntity> getCalls();
    List<ControllerCallDescriptionEntity> getCalls(OffsetDateTime from, OffsetDateTime to);
    List<ControllerCallDescriptionEntity> getLastCalls(int amount);
    List<ControllerCallDescriptionEntity> getLastCalls(OffsetDateTime from, OffsetDateTime to, int amount);
}
