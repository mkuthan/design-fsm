package design.fsm;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class OrderIdentifier {

	private String identifier;

	public OrderIdentifier(String identifier) {
		this.identifier = requireNonNull(identifier);
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OrderIdentifier that = (OrderIdentifier) obj;
		return Objects.equals(this.identifier, that.identifier);
	}

	@Override
	public String toString() {
		return identifier;
	}

}
