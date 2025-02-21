/*  Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00002
*/
window.addEventListener("load", function () {
    /* console.log("Página carregada. Chamando back-end...");
    fetch("/api/teste")
        .then(response => response.text())
        .then(data => {
            console.log("Resposta do back-end:", data);
            document.getElementById("resposta").innerText = data;
        })
        .catch(error => {
            console.error("Erro ao chamar back-end:", error);
        }); */

    iniciarEventos();
});

/*
function gerarContrato() {
    let nome = document.getElementById("nome").value;
    let valor = document.getElementById("valor").value;

    fetch("/contratos/gerar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ nome: nome, valor: parseFloat(valor) }) //exemplo de parametros
    })
    .then(response => response.text())
    .then(data => {
        alert("Resposta do servidor: " + data);
    })
    .catch(error => console.error("Erro:", error));
}*/

function iniciarEventos() {
        controlaTela("inicio");
        event_click("dimcontratoint");
        event_click("bimmenuint");
        event_click("ditensrelatorioint");
}

function event_click(obj) {
    /* document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratos"; 
        }); Para mudar de pagina no click*/

    if(obj == "dimcontratoint"){
        document.getElementById(obj).addEventListener("click", function() {
            form("ditenscontratoext").style.backgroundColor = form("bimcvercontratos").style.display == "flex"?"#dedede":"#29582c";
            controlaTela("menuitens");
        });
    }
    if(obj == "bimmenuint"){
        document.getElementById(obj).addEventListener("click", function() {
            form("ditensmenuext").style.backgroundColor = form("dimmfinanciamento").style.display == "flex"?"#dedede":"#29582c";
            controlaTela("menu");
        });
    }
    if(obj == "ditensrelatorioint"){
        document.getElementById(obj).addEventListener("click", function() {
            form("ditensrelatorioext").style.backgroundColor = form("dimrvendas").style.display == "flex"?"#dedede":"#29582c";
            controlaTela("relatorio");
        });
    }
}

function controlaTela(opc){
    if(opc == "inicio"){
            form("dimmfinanciamento").style.display = "none";
            form("bimcvercontratos").style.display  = "none";
            form("bimcnovocontrato").style.display  = "none";
            form("bimcassinatura").style.display    = "none";
            form("bimcaprovacao").style.display     = "none";
            form("dimrvendas").style.display        = "none";
            form("dimrimovel").style.display        = "none";
    }
    if(opc == "menu"){
        form("dimmfinanciamento").style.display  = form("dimmfinanciamento").style.display == "flex"?"none":"flex";
    }
    if(opc == "menuitens"){
        form("bimcvercontratos").style.display  = form("bimcvercontratos").style.display == "flex"?"none":"flex";
        form("bimcnovocontrato").style.display  = form("bimcnovocontrato").style.display == "flex"?"none":"flex";
        form("bimcassinatura").style.display    = form("bimcassinatura").style.display   == "flex"?"none":"flex";
        form("bimcaprovacao").style.display     = form("bimcaprovacao").style.display    == "flex"?"none":"flex";
    }
    if(opc == "relatorio"){
        form("dimrvendas").style.display  = form("dimrvendas").style.display == "flex"?"none":"flex";
        form("dimrimovel").style.display  = form("dimrimovel").style.display == "flex"?"none":"flex";
    }
}

function form(obj){
    return document.getElementById(obj);
}