public class Main {

    public static void main(String[] args) {
        new View().setVisible(true);
    }

    public static String executarQuebra(String url, String usuario) {

        QuebradorSenha quebrador = new QuebradorSenha();

        long inicio = System.nanoTime();

        String senhaEncontrada = quebrador.quebrar(url, usuario);

        long fim = System.nanoTime();

        double tempo = (fim - inicio) / 1_000_000_000.0;

        System.out.println("Senha encontrada: " + senhaEncontrada);
        System.out.println("Tentativas: " + quebrador.getTentativas());
        System.out.println("Tempo: " + tempo + " segundos");

        return senhaEncontrada;
    }
}