package design.ddd;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.Serializable;

import org.fest.assertions.api.AbstractAssert;

public class DomainEventPublisherAssert extends AbstractAssert<DomainEventPublisherAssert, EventPublisher> {

	public DomainEventPublisherAssert(EventPublisher actual) {
		super(actual, DomainEventPublisherAssert.class);
	}

	public DomainEventPublisherAssert published(Class<? extends Serializable> eventClass) {
		verify(actual).publish(isA(eventClass));
		return this;
	}

	public DomainEventPublisherAssert didNotPublish(Class<? extends Serializable> eventClass) {
		verify(actual, times(0)).publish(isA(eventClass));
		return this;
	}

}
