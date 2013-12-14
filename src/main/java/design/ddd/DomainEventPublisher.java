package design.ddd;

public interface DomainEventPublisher {
	void publish(DomainEvent event);
}
