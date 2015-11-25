package fr.soat.java.payload.wrappers;

public class ResponseWrapper<T> {

	public enum Severity {
		ERROR,
		SUCCESS
	}

	private Severity severity;

	private T data;

	public ResponseWrapper() {
		this.severity = Severity.SUCCESS;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
