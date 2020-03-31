package estacaoAutoatendimento;

@SuppressWarnings("unchecked")
public class No<T> {
	private No<T> next = null;
	private T obj = null;

	public void setNext(No<T> next) {
		this.next = (No<T>) next;
	}

	public No<T> getNext() {
		return (No<T>) this.next;
	}

	public void setObj(Object novo) {
		this.obj = (T) novo;
	}

	public T getObj() {
		return (T) obj;
	}
}
