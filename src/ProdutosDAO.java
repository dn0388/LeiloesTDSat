import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    
    public void cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, "Disponível");

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
        }
    }

    
    public ArrayList<ProdutosDTO> listarProdutos() {

        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getDouble("valor"));
                p.setStatus(resultset.getString("status"));

                lista.add(p);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + erro.getMessage());
        }

        return lista;
    }

    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        ArrayList<ProdutosDTO> vendidos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getDouble("valor"));
                p.setStatus(resultset.getString("status"));

                vendidos.add(p);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + erro.getMessage());
        }

        return vendidos;
    }

    
    public void venderProduto(int idProduto) {

        String select = "SELECT * FROM produtos WHERE id = ?";
        String insertVenda = "INSERT INTO vendas (produto_id, valor_vendido) VALUES (?, ?)";
        String updateProduto = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            conn = new conectaDAO().connectDB();

            // Buscar produto
            prep = conn.prepareStatement(select);
            prep.setInt(1, idProduto);
            resultset = prep.executeQuery();

            if (resultset.next()) {

                double valor = resultset.getDouble("valor");

                // Inserir na tabela vendas
                prep = conn.prepareStatement(insertVenda);
                prep.setInt(1, idProduto);
                prep.setDouble(2, valor);
                prep.executeUpdate();

                // Atualizar status
                prep = conn.prepareStatement(updateProduto);
                prep.setInt(1, idProduto);
                prep.executeUpdate();

                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");

            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender: " + e.getMessage());
        }
    }
}
