package estacaoAutoatendimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import toConnectDatabase.ConnectionHSQL;

public class OrderTable<T> implements CRUD<T> {
	private final static String insertPedido = "INSERT INTO \"PUBLIC\".\"PEDIDO\"(\"NUMPEDIDO\", \"CPF\", \"PRAZOENTREGA\", \"PEDVALOR\") "
			+ "VALUES (?, ?, ?, ?);";
	private final static String insertVenda = "INSERT INTO \"PUBLIC\".\"VENDA\" (\"IDPRODUTO\", \"NUMPEDIDO\", \"QTD\") "
			+ "VALUES(?, ?, ?);";
	private final static String updatePedido = "UPDATE PRODUTO SET PRAZOENTREGA=? WHERE NUMPEDIDO=?";
	private final static String deletePedido = "DELETE FROM PRODUTO WHERE NUMPEDIDO=?";
	private final static String selectPedido = "SELECT * FROM PEDIDO ORDER BY NUMPEDIDO";
	private final static String selectProPed = "SELECT P.IDPRODUTO, P.NOME, P.TIPO, P.ANO, P.EDITORAGRAVADORA, P.PROPRIETARIO, P.VALOR, "
			+ "PE.NUMPEDIDO, PE.CPF, PE.PRAZOENTREGA, PE.PEDVALOR, V.QTD " + "FROM PRODUTO P, PEDIDO PE, VENDA V "
			+ "WHERE P.IDPRODUTO = V.IDPRODUTO AND PE.NUMPEDIDO = V.NUMPEDIDO " + "ORDER BY PE.NUMPEDIDO";

	private static PreparedStatement pst = null;

	@Override
	public void insert(T novo) {
		Connection c = ConnectionHSQL.conectar();

		try {
			pst = c.prepareStatement(insertPedido);
			pst.setInt(1, ((Pedido) novo).getNumPedido());
			pst.setString(2, ((Pedido) novo).getCpf());
			pst.setString(3, ((Pedido) novo).getEntrega());
			pst.setDouble(4, ((Pedido) novo).getValor());

			pst.execute();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao excutar o Statement " + e.toString());
		}
	}

	public static void insertVenda(int order, int product, int qtd) {
		Connection c = ConnectionHSQL.conectar();

		try {
			pst = c.prepareStatement(insertVenda);
			pst.setInt(1, product);
			pst.setInt(2, order);
			pst.setInt(3, qtd);

			pst.execute();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao excutar o Statement " + e.toString());
		}
	}

	@Override
	public LinkedList<T> readBD() {
		LinkedList<T> pedidos = new LinkedList<T>();
		Connection c = ConnectionHSQL.conectar();
		Pedido p = null;

		try {
			pst = c.prepareStatement(selectPedido);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				p = new Pedido();
				p.setNumPedido(rs.getInt("NUMPEDIDO"));
				p.setCpf(rs.getString("CPF"));
				p.setEntrega(rs.getString("PRAZOENTREGA"));
				p.setValor(rs.getDouble("PEDVALOR"));

				pedidos.add(p);
			}
			rs.close();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao executar o Statement " + e.toString());
		}

		return pedidos;
	}

	@Override
	public void update(T alter) {
		Connection c = ConnectionHSQL.conectar();

		try {
			pst = c.prepareStatement(updatePedido);
			pst.setString(1, ((Pedido) alter).getEntrega());
			pst.setInt(2, ((Pedido) alter).getNumPedido());
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
			pst = c.prepareStatement(deletePedido);
			pst.setInt(1, ((Pedido) delete).getNumPedido());

			pst.execute();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao executar o Statement " + e.toString());
		}
	}

	public static LinkedList<Pedido> readShopping() {
		Connection c = ConnectionHSQL.conectar();
		LinkedList<Produto> produtos = new LinkedList<Produto>();
		LinkedList<Pedido> ret = new LinkedList<Pedido>();
		Produto p = null;
		Pedido read = new Pedido();
		String toAdd[] = new String[8];

		try {
			pst = c.prepareStatement(selectProPed);
			ResultSet rs = pst.executeQuery();
			int helpNum = 0;

			while (rs.next()) {
				if (helpNum == rs.getInt("NUMPEDIDO")) {
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
					try {
						produtos.add(p.clone());
					} catch (CloneNotSupportedException e) {
						System.out.println("CLONAGEM NÃO SUPORTADA NA ORDER TABLE!");
					}
				} else {
					if (helpNum != 0) {
						read.carrinho = produtos;

						try {
							ret.add((Pedido) read.clone());
							read = new Pedido();
							produtos = new LinkedList<Produto>();
						} catch (CloneNotSupportedException e) {
							System.out.println("Clonagem não permitida");
						}
					}

					read.setNumPedido(rs.getInt("NUMPEDIDO"));
					read.setCpf(rs.getString("CPF"));
					read.setEntrega(rs.getString("PRAZOENTREGA"));
					read.setValor(rs.getDouble("PEDVALOR"));

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

					try {
						produtos.add(p.clone());
					} catch (CloneNotSupportedException e) {
						System.out.println("CLONAGEM NÃO SUPORTADA NA ORDER TABLE!");
					}
				}
				helpNum = rs.getInt("NUMPEDIDO");
			}

			read.carrinho = produtos;

			try {
				ret.add((Pedido) read.clone());
				read = new Pedido();
			} catch (CloneNotSupportedException e) {
				System.out.println("Clonagem não permitida");
			}

			rs.close();
			pst.close();
			ConnectionHSQL.fecharCNX();
		} catch (SQLException e) {
			System.out.println("Erro ao executar o Statement " + e.toString());
		}

		return ret;
	}
}
