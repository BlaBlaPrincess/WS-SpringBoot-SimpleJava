package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControllerCallsHistoryService implements ControllerCallsHistory {

    private final ControllerCallDescriptionsRepository repository;
    private final StringUtils stringUtils;

    @Override
    @Transactional
    public void saveCall(ControllerCallDescriptionEntity call) {
        String croppedResponse = stringUtils.cropByMaxLength(call.getResponse(), ControllerCallDescriptionEntity.MAX_RESPONSE_LENGTH);
        call.setResponse(croppedResponse);
        repository.save(call);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ControllerCallDescriptionEntity> getCalls() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ControllerCallDescriptionEntity> getCalls(OffsetDateTime from, OffsetDateTime to) {
        return repository.findByTimestampIsBetween(from, to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ControllerCallDescriptionEntity> getLastCalls(int amount) {
        Pageable request = PageRequest.of(0, amount, Sort.by(Sort.DEFAULT_DIRECTION, "timestamp"));
        Page<ControllerCallDescriptionEntity> page = repository.findAll(request);
        return page.toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ControllerCallDescriptionEntity> getLastCalls(OffsetDateTime from, OffsetDateTime to, int amount) {
        Pageable request = PageRequest.of(0, amount, Sort.by("timestamp"));
        Page<ControllerCallDescriptionEntity> page = repository.findByTimestampIsBetween(from, to, request);
        return page.toList();
    }

}
