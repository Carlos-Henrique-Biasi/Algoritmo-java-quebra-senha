import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class View extends JFrame {

    private JTextField txtUrl;
    private JTextField txtUsuario;
    private JLabel lblStatusUsuario;
    private JLabel lblStatusSenha;

    public View() {
        setTitle("INF1LTR4TI0N");
        setSize(460, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); // Centraliza a janela no ecrã

        // Fundo preto da janela
        getContentPane().setBackground(Color.BLACK);

        // Estilo CMD (Fonte Consolas e cores padrão)
        Font fonteCmd = new Font("Consolas", Font.PLAIN, 13);
        Color corTexto = Color.WHITE;

        // --- 1. Input da URL/Rota ---
        JLabel l1 = new JLabel("URL / Rota:");
        l1.setBounds(40, 25, 360, 20);
        l1.setForeground(corTexto);
        l1.setFont(fonteCmd);
        add(l1);

        txtUrl = new JTextField("http://localhost:3000/api/login");
        txtUrl.setBounds(40, 50, 360, 32);
        txtUrl.setBackground(new Color(25, 25, 25)); 
        txtUrl.setForeground(corTexto);
        txtUrl.setCaretColor(corTexto);
        txtUrl.setFont(fonteCmd);
        txtUrl.setBorder(new RoundedBorder(8, new Color(70, 70, 70)));
        add(txtUrl);

        // --- 2. Input do Utilizador ---
        JLabel l2 = new JLabel("Nome do Utilizador:");
        l2.setBounds(40, 95, 360, 20);
        l2.setForeground(corTexto);
        l2.setFont(fonteCmd);
        add(l2);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 120, 360, 32);
        txtUsuario.setBackground(new Color(25, 25, 25));
        txtUsuario.setForeground(corTexto);
        txtUsuario.setCaretColor(corTexto);
        txtUsuario.setFont(fonteCmd);
        txtUsuario.setBorder(new RoundedBorder(8, new Color(70, 70, 70)));
        add(txtUsuario);

        // --- Botão "Botão" (Arredondado, vermelho escuro e visível) ---
       // --- Botão "Botão" (Arredondado, vermelho escuro e com animação de clique) ---
        JButton btnBotao = new JButton("Quebrar senha") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Verifica se o botão está a ser pressionado no momento do clique
                if (getModel().isPressed()) {
                    // Cor mais escura ou mais clara ao clicar (ex: um vermelho mais profundo)
                    g2.setColor(new Color(100, 0, 0));
                } else {
                    // Cor normal do botão
                    g2.setColor(getBackground());
                }
                
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        btnBotao.setBounds(40, 175, 360, 38);
        btnBotao.setBackground(new Color(139, 0, 0)); // Vermelho escuro/fechado
        btnBotao.setForeground(Color.WHITE);
        btnBotao.setFont(fonteCmd);
        btnBotao.setFocusPainted(false);
        btnBotao.setContentAreaFilled(false);
        btnBotao.setBorder(new RoundedBorder(12, new Color(180, 50, 50)));
        add(btnBotao);

        // --- 3. Os dois espaços em baixo ---
        lblStatusUsuario = new JLabel("Usuário: ");
        lblStatusUsuario.setBounds(40, 250, 360, 22);
        lblStatusUsuario.setForeground(corTexto);
        lblStatusUsuario.setFont(fonteCmd);
        add(lblStatusUsuario);

        lblStatusSenha = new JLabel("senha: ");
        lblStatusSenha.setBounds(40, 285, 360, 22);
        lblStatusSenha.setForeground(corTexto);
        lblStatusSenha.setFont(fonteCmd);
        add(lblStatusSenha);

        // Ação do Botão
       btnBotao.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            String urlDigitada = txtUrl.getText();
            String usuarioDigitado = txtUsuario.getText();

            lblStatusUsuario.setText("Usuário: " + usuarioDigitado);
            lblStatusSenha.setText("senha: Quebrando...");

            btnBotao.setEnabled(false);

            SwingWorker<String, Void> worker = new SwingWorker<>() {

                @Override
                protected String doInBackground() {
                    return Main.executarQuebra(
                            urlDigitada,
                            usuarioDigitado
                    );
                }

                @Override
                protected void done() {
                    try {
                        String senhaEncontrada = get();

                        if (senhaEncontrada != null) {
                            lblStatusSenha.setText("senha: " + senhaEncontrada);
                        } else {
                            lblStatusSenha.setText("senha: Não encontrada");
                        }

                    } catch (Exception erro) {
                        lblStatusSenha.setText("senha: Erro");
                        erro.printStackTrace();
                    }

                    btnBotao.setEnabled(true);
                }
        };

        worker.execute();
    }
});
    }

    // Classe auxiliar para criar bordas arredondadas e finas
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 8;
            insets.top = insets.bottom = 4;
            return insets;
        }
    }
}