package estacaoAutoatendimento;

public interface CRUD<T> {
	public void insert(T novo);

	public LinkedList<T> readBD();

	public void update(T alter);

	public void delete(T delete);
}
