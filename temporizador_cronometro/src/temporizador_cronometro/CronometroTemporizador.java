import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CronometroTemporizador extends JFrame {
    private JLabel labelTempo;
    private JButton btnIniciar, btnPausar, btnParar, btnRedefinir;
    private JTextField txtMinutos, txtSegundos;
    private Timer timer;
    private int segundos = 0, minutos = 0, tempoTotal = 0;
    private boolean modoCronometro = true;

    public CronometroTemporizador() {
        setTitle("Cronômetro e Temporizador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Painel para exibir o tempo
        JPanel painelTempo = new JPanel();
        labelTempo = new JLabel("00:00");
        labelTempo.setFont(new Font("Arial", Font.BOLD, 48));
        painelTempo.add(labelTempo);

        // Painel para botões de controle
        JPanel painelControles = new JPanel();
        btnIniciar = new JButton("Iniciar");
        btnPausar = new JButton("Pausar");
        btnParar = new JButton("Parar");
        btnRedefinir = new JButton("Redefinir");
        painelControles.add(btnIniciar);
        painelControles.add(btnPausar);
        painelControles.add(btnParar);
        painelControles.add(btnRedefinir);

        // Painel para definir temporizador
        JPanel painelDefinicao = new JPanel();
        painelDefinicao.add(new JLabel("Minutos:"));
        txtMinutos = new JTextField("0", 2);
        painelDefinicao.add(txtMinutos);
        painelDefinicao.add(new JLabel("Segundos:"));
        txtSegundos = new JTextField("0", 2);
        painelDefinicao.add(txtSegundos);

        add(painelTempo);
        add(painelDefinicao);
        add(painelControles);

        // Configurando o Timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modoCronometro) {
                    atualizarCronometro();
                } else {
                    atualizarTemporizador();
                }
            }
        });

        // Ações dos botões
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciar();
            }
        });

        btnPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausar();
            }
        });

        btnParar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parar();
            }
        });

        btnRedefinir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redefinir();
            }
        });
    }

    private void iniciar() {
        if (!timer.isRunning()) {
            if (!modoCronometro) {
                int min = Integer.parseInt(txtMinutos.getText());
                int seg = Integer.parseInt(txtSegundos.getText());
                tempoTotal = min * 60 + seg;
            }
            timer.start();
        }
    }

    private void pausar() {
        timer.stop();
    }

    private void parar() {
        timer.stop();
        minutos = 0;
        segundos = 0;
        tempoTotal = 0;
        atualizarDisplay();
    }

    private void redefinir() {
        parar();
        if (!modoCronometro) {
            int min = Integer.parseInt(txtMinutos.getText());
            int seg = Integer.parseInt(txtSegundos.getText());
            tempoTotal = min * 60 + seg;
            atualizarDisplay();
        }
    }

    private void atualizarCronometro() {
        segundos++;
        if (segundos == 60) {
            segundos = 0;
            minutos++;
        }
        atualizarDisplay();
    }

    private void atualizarTemporizador() {
        if (tempoTotal > 0) {
            tempoTotal--;
            minutos = tempoTotal / 60;
            segundos = tempoTotal % 60;
            atualizarDisplay();
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Tempo esgotado!");
        }
    }

    private void atualizarDisplay() {
        labelTempo.setText(String.format("%02d:%02d", minutos, segundos));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CronometroTemporizador().setVisible(true);
            }
        });
    }
}
