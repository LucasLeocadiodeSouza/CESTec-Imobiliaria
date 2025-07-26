/* 
    Dev: Lucas Leocadio de Souza
    Data: 02/03/25
    IM: 00015
*/

window.addEventListener("load", function () {
    iniciarEventos();
});

var CONSUL;

function iniciarEventos() {
    CONSUL = new consulForm_init();
    filaFetchInit();

    //controlaTela("inicia");
    event_click("blogin");
}

function event_click(obj) {
    if(obj == "blogin"){
        form(obj).addEventListener("click", function () {
            buscarLogin();
        });
    }
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno, error)=>{
        if(error){
            switch (CONSUL.obj) {
            case   "buscarLogin": alert("Erro ao acessar o Portal!", "Usuário ou Senha Erradas! Por favor tente novamente.", 4);
                                  break;
            }
            return;
        }

        switch (CONSUL.obj) {
        case    "buscarLogin": window.location.href = "/home";
                               break;
        }
    }
}

function buscarLogin(){
    const login = {
        login: form("login").value,
        passkey: form("passkey").value,
    }

    CONSUL.consultar("buscarLogin",`/auth/login`,"POST","",{body: login})
}

/*
function enviarEmail(){
    const email = {
        to: form("memail").value,
        subject: "Bem Vinda a CESTec Enterprise 😊",
        body: "<html>"
            + "<head>"
            + "</head>"
            + "<body>"
            + "<h2>Olá Colaborador!</h2>"
            + "<p>Somos Uma Imobiliaria que focamos em oferecer o melhor Atendimento e Serviços para nossos colaboradores! </p>"
            + "<p>Gostariamos de Agradecer pela confiança e compromisso.</p>"
            + "</body>"
            + "</html>"
    }

    fetch("/email", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(email)        
    })
    .then(response => {})
    .then(data => {})
    .catch(error => alert(error.message));
}
*/

function controlaTela(opc){
    limparTela(opc);
}

function limparTela(opc){
}