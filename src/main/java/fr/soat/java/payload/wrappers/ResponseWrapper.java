package fr.soat.java.payload.wrappers;

import fr.soat.java.enums.Status;

public class ResponseWrapper<T> {

	private Status status;

	private T data;

	public ResponseWrapper() {
		this.status = Status.SUCCESS;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
