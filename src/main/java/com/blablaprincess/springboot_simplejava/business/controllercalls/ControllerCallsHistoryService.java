package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControllerCallsHistoryService implements ControllerCallsHistory {

    private final ControllerCallDescriptionsRepository repository;
    private final StringUtils stringUtils;

    @Override
    public void saveCall(ControllerCallDescriptionEntity call) {
        String croppedResponse = stringUtils.cropByMaxLength(call.getResponse(), ControllerCallDescriptionEntity.MAX_RESPONSE_LENGTH);
        call.setResponse(croppedResponse);
        repository.save(call);
    }

    @Override
    public List<ControllerCallDescriptionEntity> getCalls() {
        return repository.findAll();
    }

    @Override
    public List<ControllerCallDescriptionEntity> getCalls(OffsetDateTime from, OffsetDateTime to) {
        return repository.findByTimestampIsBetween(from, to);
    }

    @Override
    public List<ControllerCallDescriptionEntity> getLastCalls(int amount) {
        Page<ControllerCallDescriptionEntity> page = repository.findAll(Pageable.ofSize(amount));
        return page.toList();
    }

    @Override
    public List<ControllerCallDescriptionEntity> getLastCalls(OffsetDateTime from, OffsetDateTime to, int amount) {
        Page<ControllerCallDescriptionEntity> page = repository.findByTimestampIsBetween(from, to, Pageable.ofSize(amount));
        return page.toList();
    }

}
