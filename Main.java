import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.awt.*;

class Produto {
    int id;
    String nome;
    double preco;
    String categoria;

    public Produto(int id, String nome, double preco, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Preço: R$ " + preco + " | Categoria: " + categoria;
    }
}

 class EstoqueGUI extends JFrame {
    private JTextField campoNome, campoPreco, campoId;
    private JComboBox<String> comboCategoria;
    private JTextArea areaExibicao;
    private List<Produto> produtos = new ArrayList<>();

    public EstoqueGUI() {
        setTitle("Sistema de Estoque");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Painel de entrada de dados ===
        JPanel painelFormulario = new JPanel(new GridLayout(5, 2));

        painelFormulario.add(new JLabel("ID:"));
        campoId = new JTextField();
        painelFormulario.add(campoId);

        painelFormulario.add(new JLabel("Nome do Produto:"));
        campoNome = new JTextField();
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Preço:"));
        campoPreco = new JTextField();
        painelFormulario.add(campoPreco);

        painelFormulario.add(new JLabel("Categoria:"));
        comboCategoria = new JComboBox<>(new String[]{"Limpeza", "Comida", "Bebida"});
        painelFormulario.add(comboCategoria);

        JButton btnAdicionar = new JButton("Adicionar Produto");
        painelFormulario.add(btnAdicionar);

        JButton btnListar = new JButton("Listar Produtos");
        painelFormulario.add(btnListar);

        add(painelFormulario, BorderLayout.NORTH);

        // === Área de exibição dos produtos ===
        areaExibicao = new JTextArea();
        areaExibicao.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaExibicao);
        add(scrollPane, BorderLayout.CENTER);

        // === Ações dos botões ===
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnListar.addActionListener(e -> listarProdutosPorCategoria());

        setVisible(true);
    }

    private void adicionarProduto() {
        try {
            int id = Integer.parseInt(campoId.getText());
            String nome = campoNome.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            String categoria = (String) comboCategoria.getSelectedItem();

            Produto p = new Produto(id, nome, preco, categoria);
            produtos.add(p);

            JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: verifique os valores digitados.");
        }
    }

    private void listarProdutosPorCategoria() {
        Map<String, List<Produto>> porCategoria = new HashMap<>();
        for (Produto p : produtos) {
            porCategoria.computeIfAbsent(p.categoria, k -> new ArrayList<>()).add(p);
        }

        areaExibicao.setText("");
        for (String categoria : porCategoria.keySet()) {
            areaExibicao.append("== Categoria: " + categoria + " ==\n");
            for (Produto p : porCategoria.get(categoria)) {
                areaExibicao.append(p.toString() + "\n");
            }
            areaExibicao.append("\n");
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoPreco.setText("");
        comboCategoria.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EstoqueGUI::new);
    }
}
