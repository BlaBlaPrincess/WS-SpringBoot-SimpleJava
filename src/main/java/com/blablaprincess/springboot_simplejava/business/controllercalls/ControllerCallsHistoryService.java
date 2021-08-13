package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.persistence.OptionalPredicateBuilder;
import com.blablaprincess.springboot_simplejava.business.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ControllerCallsHistoryService implements ControllerCallsHistory {

    private final ControllerCallDescriptionsRepository repository;
    private final StringUtils stringUtils;

    private final QControllerCallDescriptionEntity qEntity = QControllerCallDescriptionEntity.controllerCallDescriptionEntity;

    @Override
    @Transactional
    @Async("controllerCallsHistoryServiceTaskExecutor")
    public void saveCall(ControllerCallDescriptionEntity call) {
        String croppedResponse = stringUtils.cropByMaxLength(call.getResponse(), ControllerCallDescriptionEntity.MAX_RESPONSE_LENGTH);
        call.setResponse(croppedResponse);
        repository.save(call);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ControllerCallDescriptionEntity> getLastCalls(ControllerCallsHistoryLastCallsArgs args) {
        Predicate predicate
                = new OptionalPredicateBuilder().optionalAnd(args.getTimestampAfter(), qEntity.timestamp::goe)
                                                .optionalAnd(args.getTimestampBefore(), qEntity.timestamp::loe)
                                                .build();

        return Lists.newArrayList(repository.findAll(predicate));
    }

}
