package design.ddd;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.fest.assertions.api.AbstractAssert;

public class EventPublisherAssert extends AbstractAssert<EventPublisherAssert, EventPublisher> {

	public EventPublisherAssert(EventPublisher actual) {
		super(actual, EventPublisherAssert.class);
	}

	public EventPublisherAssert published(Class<? extends Event> eventClass) {
		verify(actual).publish(isA(eventClass));
		return this;
	}

	public EventPublisherAssert notPublished(Class<? extends Event> eventClass) {
		verify(actual, times(0)).publish(isA(eventClass));
		return this;
	}

}
