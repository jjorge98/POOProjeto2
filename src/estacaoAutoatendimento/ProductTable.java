package estacaoAutoatendimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import toConnectDatabase.ConnectionHSQL;

public class ProductTable<T> implements CRUD<T> {
	private final static String insertProduct = "INSERT INTO \"PUBLIC\".\"PRODUTO\"(\"NOME\", \"QTD\", \"TIPO\", \"PROPRIETARIO\", \"ANO\", "
			+ "\"EDITORAGRAVADORA\", \"VALOR\") VALUES (?, ?, ?, ?, ?, ?, ?);";
	private final static String updateProduct = "UPDATE PRODUTO SET QTD=?, VALOR=? WHERE IDPRODUTO=?";
	private final static String deleteProduct = "DELETE FROM PRODUTO WHERE IDPRODUTO=?";
	private final static String selectProduct = "SELECT * FROM PRODUTO";

	private static PreparedStatement pst = null;

	@Override
	public void insert(T novo) {
		Connection c = ConnectionHSQL.conectar();

		try {
			pst = c.prepareStatement(insertProduct);
			pst.setString(1, ((Produto) novo).getNome().toUpperCase());
			pst.setInt(2, ((Produto) novo).getQtd());
			pst.setString(3, ((Produto) novo).getTipo().toUpperCase());
			pst.setString(4, ((Produto) novo).getProprietario().toUpperCase());
			pst.setInt(5, ((Produto) novo).getAno());
			pst.setString(6, ((Produto) novo).getEdiGra().toUpperCase());
			pst.setDouble(7, ((Produto) novo).getValor());

			pst.execute();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao excutar o Statement " + e.toString());
		}
	}

	@Override
	public LinkedList<T> readBD() {
		LinkedList<T> produtos = new LinkedList<T>();
		Connection c = ConnectionHSQL.conectar();
		Produto p = null;
		String toAdd[] = new String[8];

		try {
			pst = c.prepareStatement(selectProduct);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				toAdd[0] = rs.getString("IDPRODUTO");
				toAdd[1] = rs.getString("NOME");
				toAdd[2] = rs.getString("QTD");
				toAdd[3] = rs.getString("TIPO");
				toAdd[4] = rs.getString("ANO");
				toAdd[5] = rs.getString("EDITORAGRAVADORA");
				toAdd[6] = rs.getString("PROPRIETARIO");
				toAdd[7] = rs.getString("VALOR");
				if (toAdd[3].equals("CD")) {
					p = new CD(Integer.parseInt(toAdd[0]), toAdd[1], Integer.parseInt(toAdd[2]), toAdd[3],
							Integer.parseInt(toAdd[4]), toAdd[5], toAdd[6], Double.parseDouble(toAdd[7]));
				} else if (toAdd[3].equals("DVD")) {
					p = new DVD(Integer.parseInt(toAdd[0]), toAdd[1], Integer.parseInt(toAdd[2]), toAdd[3],
							Integer.parseInt(toAdd[4]), toAdd[5], toAdd[6], Double.parseDouble(toAdd[7]));
				} else if (toAdd[3].equals("LIVRO")) {
					p = new Livro(Integer.parseInt(toAdd[0]), toAdd[1], Integer.parseInt(toAdd[2]), toAdd[3],
							Integer.parseInt(toAdd[4]), toAdd[5], toAdd[6], Double.parseDouble(toAdd[7]));
				}
				produtos.add(p);
			}
			rs.close();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao executar o Statement " + e.toString());
		}

		return produtos;
	}

	@Override
	public void update(T alter) {

		Connection c = ConnectionHSQL.conectar();

		try {
			pst = c.prepareStatement(updateProduct);
			pst.setInt(1, ((Produto) alter).getQtd());
			pst.setDouble(2, ((Produto) alter).getValor());
			pst.setInt(3, ((Produto) alter).getId());

			pst.execute();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao excutar o Statement " + e.toString());
		}
	}

	@Override
	public void delete(T delete) {
		Connection c = ConnectionHSQL.conectar();

		try {
			pst = c.prepareStatement(deleteProduct);
			pst.setInt(1, ((Produto) delete).getId());

			pst.execute();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao executar o Statement " + e.toString());
		}
	}
}