package com.imooc.ecommerce.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 *
 * <h1>自定义告警</h1>
 *
 * @author zzy
 * @date 2022/4/10
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class EcommerceNotifier extends AbstractEventNotifier {

    protected EcommerceNotifier(InstanceRepository repository) {
        super(repository);
    }

    /**
     *
     * <2>实现对事件的通知</2>
     *
     * @param event
     * @param instance
     * @return
     */
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {

        return Mono.fromRunnable(()->{

            if (event instanceof InstanceStatusChangedEvent){
                log.info("Instance Status Change: [{}],[{}],[{}]",
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus()
                );
            } else {
                log.info("Instance Info: [{}],[{}],[{}]",
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        event.getType());
            }
        });
    }

}
