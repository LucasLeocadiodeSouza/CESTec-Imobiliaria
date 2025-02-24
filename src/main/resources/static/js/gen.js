/* 
    Dev: Lucas Leocadio de Souza
    Data: 22/02/25
    Descricao: Arquivo de metodos gerais
*/

export function getElemento(posicao, corpo){
    const elementos = corpo.split(",");

    if (posicao <= 0 || posicao > elementos.length) {
        return "Invalido";
    }

    return elementos[posicao - 1];
    
}