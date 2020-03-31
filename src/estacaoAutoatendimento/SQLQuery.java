package estacaoAutoatendimento;

public class SQLQuery<T> {
	public void insert(CRUD<T> c, T i) {
		c.insert(i);
	}

	public LinkedList<T> readBD(CRUD<T> c) {
		return c.readBD();
	}

	public void update(CRUD<T> c, T u) {
		c.update(u);
	}

	public void delete(CRUD<T> c, T d) {
		c.delete(d);
	}

	public void insertVenda(int order, int idProduto, int qtd) {
		OrderTable.insertVenda(order, idProduto, qtd);
	}

	public LinkedList<Pedido> readShopping() {
		return OrderTable.readShopping();
	}
}
