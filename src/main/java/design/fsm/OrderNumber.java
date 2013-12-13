package design.fsm;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class OrderNumber {

	private String number;

	public OrderNumber(String number) {
		this.number = requireNonNull(number);
	}

	public String getNumber() {
		return number;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
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
		OrderNumber that = (OrderNumber) obj;
		return Objects.equals(this.number, that.number);
	}

	@Override
	public String toString() {
		return number;
	}

}
