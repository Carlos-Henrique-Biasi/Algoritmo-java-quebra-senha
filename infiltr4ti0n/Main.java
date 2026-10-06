
    public class Main {
//teste
    public static void main(String[] args) {

        // SENHA APENAS PARA TESTAR O QUEBRADOR.
        //
        // Depois isso será removido quando vocês
        // fizerem a integração.
        String senhaTeste = "223654";


        // Cria o quebrador.
        QuebradorSenha quebrador = new QuebradorSenha();


        // Marca o tempo inicial.
        long inicio = System.nanoTime();


        // Começa a força bruta.
        String senhaEncontrada =
                quebrador.quebrar(senhaTeste);


        // Marca o tempo final.
        long fim = System.nanoTime();


        // Converte o tempo para segundos.
        double tempo =
                (fim - inicio) / 1_000_000_000.0;


        // Mostra os resultados.
        System.out.println(
                "Senha encontrada: " + senhaEncontrada
        );

        System.out.println(
                "Tentativas: " + quebrador.getTentativas()
        );

        System.out.println(
                "Tempo: " + tempo + " segundos"
        );
    }
}

