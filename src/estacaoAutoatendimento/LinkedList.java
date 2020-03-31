package estacaoAutoatendimento;

@SuppressWarnings("unchecked")
public class LinkedList<T> {
	private No<T> lista;
	private int qtd;

	public LinkedList() {
		lista = new No<T>();
		qtd = 0;
	}

	public void add(Object novo) {
		if (lista.getObj() == null) {
			lista.setObj((T) novo);
		} else {
			No<T> novoNo = new No<T>();
			novoNo.setObj((T) novo);

			No<T> aux = (No<T>) lista;
			while (aux.getNext() != null) {
				aux = aux.getNext();
			}
			aux.setNext((No<T>) novoNo);
		}
		qtd++;
	}

	public int remove(Object remover) {
		if (lista.getObj() == null) {
			return 0;
		} else {
			No<T> ant = null;
			No<T> aux = (No<T>) lista;

			while (!aux.getObj().equals(remover) && aux.getNext() != null) {
				ant = aux;
				aux = aux.getNext();
			}
			if (aux.getObj().equals(remover)) {
				if (ant == null) {
					lista = aux.getNext();
					if (lista == null) {
						lista = new No<T>();
					}
				} else {
					ant.setNext(aux.getNext());
				}
				qtd--;
				aux = null;
				return 1;
			} else {
				return 0;
			}
		}
	}

	public int size() {
		return qtd;
	}

	public int contains(Object verificar) {
		No<T> aux = (No<T>) lista;

		while (aux != null) {
			if (aux.getObj() == verificar) {
				return 1;
			} else {
				aux = aux.getNext();
			}
		}
		return 0;
	}

	public void clear() {
		lista.setObj(null);
	}

	public No<T> getList() {
		return (No<T>) lista;
	}

	public int getQtd() {
		return qtd;
	}

}
