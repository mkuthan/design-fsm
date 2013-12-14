package design.ddd;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.fest.assertions.api.AbstractAssert;

public class DomainEventPublisherAssert extends AbstractAssert<DomainEventPublisherAssert, DomainEventPublisher> {

	public DomainEventPublisherAssert(DomainEventPublisher actual) {
		super(actual, DomainEventPublisherAssert.class);
	}

	public DomainEventPublisherAssert published(Class<? extends DomainEvent> eventClass) {
		verify(actual).publish(isA(eventClass));
		return this;
	}

	public DomainEventPublisherAssert didNotPublish(Class<? extends DomainEvent> eventClass) {
		verify(actual, times(0)).publish(isA(eventClass));
		return this;
	}

}
