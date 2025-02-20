/*  Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00002
*/
window.onload = comWindow();

function comWindow(){
    
    
    iniciarEventos();
}

function iniciarEventos(){
    controlaTela("inicio");
    event_click("dimcontratoint");
    event_click("bimmenuint");
    event_click("ditensrelatorioint");
}

function event_click(obj) {
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