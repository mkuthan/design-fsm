package design.ddd;

import java.io.Serializable;

public interface EventPublisher {
	void publish(Serializable event);
}
