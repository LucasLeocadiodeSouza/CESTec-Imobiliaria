/* 
    Dev: Lucas Leocadio de Souza
    Data: 02/03/25
    IM: 00015
*/

window.addEventListener("load", function () {
    iniciarEventos();
});

function iniciarEventos() {
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

function buscarLogin(){
    const login = {
        login: form("login").value,
        passkey: form("passkey").value,
    }

    fetch('/auth/login',{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(login)
    })
    .then(response =>{response.ok?window.location.href = "/home":alert("Usuário ou Senha Erradas! Por favor tente novamente.")})
    .catch(error => console.log(error.message));
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

function form(obj){
    return document.getElementById(obj);
}