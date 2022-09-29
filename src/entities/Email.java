package entities;

import java.util.Objects;

import interfaces.Plotable;

public class Email implements Plotable {

	private String email;
	
	public Email(String email) {
		this.email = email;
	}
	
	@Override
	public String getLabel() {
		return email;
	}

	@Override
	public void setLabel(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		return getLabel().equals(other.getLabel());
	}

	@Override
	public String toString() {
		return "[" + email + "]";
	}
	
}
