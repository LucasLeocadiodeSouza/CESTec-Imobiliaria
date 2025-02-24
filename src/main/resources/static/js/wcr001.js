/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00007
*/

window.addEventListener("load", function () {
    iniciarEventos();
});

function iniciarEventos() {
    controlaTela("inicia");
    event_click("abas");
    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("bclose");
    imgFormat();
}

function event_click(obj) {
    if(obj == "abas"){        
        document.querySelectorAll(".aba").forEach(aba => {
            aba.addEventListener("click", function () {

                document.querySelectorAll(".aba").forEach(abareset => {
                    abareset.querySelector('.indentaba').style.backgroundColor =  'rgb(81, 81, 81)';
                    abareset.querySelector('.indentaba').style.removeProperty("background-color"); 
                    abareset.querySelector('.abaint').style.removeProperty("background-color");
                    abareset.querySelector('.abaint').style.pointerEvents = 'visible';
                });

                let abatraco   = this.querySelector(".indentaba");
                let abainterna = this.querySelector(".abaint");                

                abatraco.style.backgroundColor   = "rgb(0, 97, 7)";                
                abainterna.style.pointerEvents   = 'none';
                abainterna.style.backgroundColor = "rgb(193, 192, 192)";
            });
            form('aba1').addEventListener("click", function () {
                form('aba2').style.pointerEvents   = 'visible';              
                form('aba1').style.pointerEvents   = 'none';
                controlaTela('inicia');
            });
            form('aba2').addEventListener("click", function () {
                form('aba1').style.pointerEvents   = 'visible';               
                form('aba2').style.pointerEvents   = 'none';
                controlaTela('inicia');
            });
            
        });       
    }
    if(obj == "bnovabusca"){
        form(obj).addEventListener("click", function () {
            controlaTela("buscar");
        });
    }
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("novabusca");
        });        
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("DMF_external").style.display = "flex";
        });
    }
    if(obj == 'bclose'){
        form(obj).addEventListener("click", function () {
            form("DMF_external").style.display = "none";    
        });
    }
    if(obj == 'bcadastro'){
        form(obj).addEventListener("click", function () {
            
        });
    }
}

function controlaTela(opc){
    if(opc == "inicia" || opc == 'buscar'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codcontrato',     false);
        desabilitaCampo('codproprietario', false);
        desabilitaCampo('periodoini',      false);
        desabilitaCampo('periodofin',      false);
        desabilitaCampo('periodoindef',    false);
        desabilitaCampo('raluguel',        false);
        desabilitaCampo('rvenda',          false);

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "novabusca"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
        desabilitaCampo('codcontrato',     true);
        desabilitaCampo('codproprietario', true);
        desabilitaCampo('periodoini',      true);
        desabilitaCampo('periodofin',      true);
        desabilitaCampo('periodoindef',    true);
        desabilitaCampo('raluguel',        true);
        desabilitaCampo('rvenda',          true);
    }
}

function imgFormat(){
    document.querySelectorAll(".button").forEach(button => {
        let iconUrl = button.getAttribute("data-icon");
        button.style.paddingLeft = "30px";
        button.style.position    = "relative";
        
        button.style.setProperty("--icon-url",          `url(${iconUrl})`);
        button.style.setProperty("content",             `""`);
        button.style.setProperty("background-image",    `url(${iconUrl})`);
        button.style.setProperty("background-position", "10px center");
        button.style.setProperty("background-repeat",   "no-repeat");
        button.style.setProperty("background-size",     "20px 25px");
    });
}

function form(obj){
    return document.getElementById(obj);
}

function desabilitaCampo(obj,desahabilita){
    if (obj.substring(0,1) == "b"){
        form(obj).style.backgroundColor =  desahabilita?'rgb(210 212 218)': '#b4b6ba';
    }
    form(obj).disabled = desahabilita;
    form(obj).style.cursor = desahabilita?'not-allowed':'pointer';
}