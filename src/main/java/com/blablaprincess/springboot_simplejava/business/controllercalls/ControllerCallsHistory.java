package com.blablaprincess.springboot_simplejava.business.controllercalls;

import java.util.Date;
import java.util.List;

public interface ControllerCallsHistory {
    void saveCall(ControllerCallDescriptionEntity call);
    List<ControllerCallDescriptionEntity> getCalls();
    List<ControllerCallDescriptionEntity> getCalls(Date from, Date to);
    List<ControllerCallDescriptionEntity> getLastCalls(int amount);
    List<ControllerCallDescriptionEntity> getLastCalls(Date from, Date to, int amount);
}
