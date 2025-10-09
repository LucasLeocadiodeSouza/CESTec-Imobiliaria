window.addEventListener("load", function () {
    elementsForm_init();
    var CONSULTA;

    CONSULTA = new consulForm_init();

    const codmodulo = document.getElementById("hcodmodulo_sis").value;
    const codaplica = document.getElementById("hcodapl_sis").value;

    CONSULTA.consultar("",`/gen/usuarioTemAcessoAplicacao?codmod=${codmodulo}&codapl=${codaplica}`)
    .then(data => {
        const validaAcess = data;

        if(!validaAcess){
            includeBloquearApl("Usuário não possui permissão para acessar a Aplicacão");
        }
    });
});