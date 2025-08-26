/* 
    Dev: Lucas Leocadio de Souza
    Data: 25/08/25
    IM: 00017
*/
window.addEventListener("load", function () {
    wmrb004_init();
});

var USUBLOQ_GRID,BLOQUE_GRID;
var DMFDiv, ABA, CONSUL;
var ACAOBUSCA = {};

function wmrb004_init(){
    elementsForm_init();

    BLOQUE_GRID               = new GridForm_init();
    BLOQUE_GRID.id            = "tabela_bloqueio";
    BLOQUE_GRID.columnName    = "codacess,codapl,descapl,usuario,data";
    BLOQUE_GRID.columnLabel   = "Código acesso,Código Aplicação,Aplicação,Usuário,Data";
    BLOQUE_GRID.columnWidth   = "15,10,35,20,20";
    BLOQUE_GRID.columnAlign   = "c,c,e,e,c";
    BLOQUE_GRID.mousehouve    = false;
    BLOQUE_GRID.destacarclick = true;
    BLOQUE_GRID.createGrid();

    USUBLOQ_GRID               = new GridForm_init();
    USUBLOQ_GRID.id            = "mbtabela_usubloq";
    USUBLOQ_GRID.columnName    = "ideusu,nome,";
    USUBLOQ_GRID.columnLabel   = "Usuário,Nome,";
    USUBLOQ_GRID.columnWidth   = "30,50,20";
    USUBLOQ_GRID.columnAlign   = "e,e,c";
    USUBLOQ_GRID.mousehouve    = true;
    USUBLOQ_GRID.destacarclick = false;
    USUBLOQ_GRID.createGrid();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_bloqueio";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();

    iniciarEventos();
    buscarUserName();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_selected_init("codapl,codmodel,muideusu");
    inputOnlyNumber('codapl,codmodel');

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");

    event_change("mmodulo");
}

function event_click_table(obj, row){
    switch (obj) {
    case BLOQUE_GRID: const valoresLinha = BLOQUE_GRID.getRowNode(row);
                      controlaTela("modalbloqueio");

                      preencherDadosModal(valoresLinha);

                      DMFDiv.openModal("dmodalf_bloqueio");
                      break;
    }
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case  "bnovabusca": 
            case     "blimpar": controlaTela("inicia");
                                break;
                             
            case     "bbuscar": controlaTela("busca");
                                carregarGridBloqueio();
                                break;

            case "bincluirusu": form("mstitulousu").innerText = "Inclusão de Usuário";

                                controlaTela("modalusu");

                                DMFDiv.openModal("dmodalf_usuario");
                                break;
        }
    });
}

function event_change(obj){
    form(obj).addEventListener("change", function(){
        switch (obj) {
            case   "mbmodulo": getDescricaoModulo();
                               break;
        }
    });
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno, error)=>{
        if(error){
            switch (CONSUL.obj) {
            case   "adicionarAplicacao": alert("Erro ao salvar Aplicação!", retorno, 4);
                                         break;
            }
            return;
        }

        switch (CONSUL.obj) {
        case    "getDescricaoModulo": form("mdescmod").value = retorno;
                                      break;

        case        "buscarUserName": form("ideusu").value = retorno;
                                      break;

        case       "buscarRoleAcess": form("mrestrole").innerHTML = "";
                                      fillSelect("mrestrole",retorno,true);
                                      form("mrestrole").value = ACAOBUSCA.buscarRoleAcess.valorinicial;
                                      break;

        case    "adicionarAplicacao": alert("Sucesso!", "A aplicação foi salva com sucesso e já está disponivel para ser acessada!", 4);

                                      form("bnovabusca").click();
                                      form("bbuscar").click();

                                      DMFDiv.closeModal();
                                      break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('codapl',          false);
        desabilitaCampo('codmodel',        false);
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
    }
    if(opc == "buscar"){
        desabilitaCampo('codapl',          true);
        desabilitaCampo('codmodel',        true);
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){        
        form('codapl').value   = "0";
        form('codmodel').value = "0";
    }
    if(opc === "inicia" || opc === "novabusca"){
        BLOQUE_GRID.clearGrid();
    }
    if(opc === "modalbloqueio"){
        form('mbmodulo').value     = "";
        form('mbdescmod').value    = "";
        form('mbaplicacao').value  = "";
        form('mbdescapl').value    = "";
        form('mbcodacesso').value  = "";
        form('mbdescacesso').value = "";
    }
    if(opc === "modalusu"){
        form('muideusu').value  = "";
        form('munomeusu').value = "";
    }
}

function preencherDadosModal(valores){
    form("sacao").innerText   = "Alterando";
    form("stitulo").innerText = "Bloquear Acesso - " + form("sacao").innerText;
    
    form("mbmodulo").value     = valores[0];
    form("mbdescmod").value    = valores[0];
    form("mbaplicacao").value  = valores[0];
    form("mbdescapl").value    = valores[0];
    form("mbcodacesso").value  = valores[0];
    form("mbdescacesso").value = valores[0];
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function getDescricaoModulo(){
    CONSUL.consultar("getDescricaoModulo",`/mrb001/getDescricaoModulo?codmodulo=${form("mmodulo").value}`)
}

function carregaGridAplicacoes(){
    PROGRAMAS_GRID.carregaGrid(`/mrb001/buscarAplicacoesGrid?codapl=${form("codapl").value}&codmodu=${form("codmodel").value}&ideusu=${form("ideusu").value}`,"","");
}

function carregaGridAplicacoes(){
    PROGRAMAS_GRID.carregaGrid(`/mrb001/buscarAplicacoesGrid?codapl=${form("codapl").value}&codmodu=${form("codmodel").value}&ideusu=${form("ideusu").value}`,"","");
}

function carregaGridAplicacoes(){
    USUBLOQ_GRID.carregaGrid(`/mrb001/buscarAplicacoesGrid?codapl=${form("codapl").value}&codmodu=${form("codmodel").value}&ideusu=${form("ideusu").value}`,"","");
}