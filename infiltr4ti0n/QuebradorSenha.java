public class QuebradorSenha {

    // Todos os caracteres que o programa vai testar.
    private static final String CARACTERES =
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "0123456789";

    // Tamanho máximo permitido no projeto.
    private static final int tamanhoMaximo = 6;

    // Conta quantas tentativas foram feitas.
    private long tentativas;

    // Esse método inicia o processo de força bruta.
    public String quebrar(String url, String usuario) {

        // Zera o contador sempre que começar uma nova execução.
        tentativas = 0;

        // Começa tentando senhas de tamanho 1.
        // Depois tamanho 2, 3, 4...
        // Até chegar em 6.
        for (int tamanho = 1; tamanho <= tamanhoMaximo; tamanho++) {
            // Cria um array de caracteres do tamanho atual.
            // Exemplo:
            // tamanho = 3
            //
            // tentativa:
            // [ ][ ][ ]
            char[] tentativa = new char[tamanho];

            // Começa a gerar as combinações.
            String resultado =
                    gerarCombinacoes(tentativa, 0, url, usuario);

            // Se encontrou a senha,
            // devolve ela e encerra o processo.
            if (resultado != null) {

                return resultado;
            }
        }

        // Se não encontrou nenhuma combinação até 6 caracteres.
        return null;
    }

    // Método responsável por gerar todas as combinações.
    private String gerarCombinacoes(
            char[] tentativa,
            int posicao,
            String url,
            String usuario) {

        // Se chegamos ao final do array,
        // significa que uma combinação inteira foi formada.
        if (posicao == tentativa.length) {
            // Conta mais uma tentativa.
            tentativas++;

            // Transforma o array de char em String.
            String candidata = new String(tentativa);

            // Verifica se acertou.
            // Aqui iremos jogar a senha para rota de API e verificar se o resultado voltou Positivo.

            int resposta = ApiNodeJS.enviarDadosParaApi(url, usuario, candidata);

            if (resposta == 1) {

                return candidata;
            }

            // Se não acertou, retorna null.
            return null;
        }

        // Percorre todos os caracteres permitidos.
        for (int i = 0; i < CARACTERES.length(); i++) {
            // Coloca um caractere na posição atual.
            tentativa[posicao] = CARACTERES.charAt(i);

            // Vai para a próxima posição.
            String resultado =
                    gerarCombinacoes(
                            tentativa,
                            posicao + 1,
                            url,
                            usuario
                    );

            // Se encontrou a senha em alguma tentativa,
            // devolve o resultado.
            // Aqui iremos jogar a senha para rota de API e verificar se o resultado voltou Positivo.

            if (resultado != null) {

                return resultado;
            }
        }

        // Se nenhuma combinação funcionou.
        return null;
    }

    // Getter para saber quantas tentativas foram feitas.
    public long getTentativas() {

        return tentativas;
    }
}