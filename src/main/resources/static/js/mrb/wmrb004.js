/* 
    Dev: Lucas Leocadio de Souza
    Data: 25/08/25
    IM: 00017
*/
window.addEventListener("load", function () {
    wmrb004_init();
});

var USUBLOQ_GRID,BLOQUE_GRID,RESPBLOQUE_GRID;
var DMFDiv, ABA, CONSUL;
var ACAOBUSCA = {};

function wmrb004_init(){
    elementsForm_init();

    BLOQUE_GRID               = new GridForm_init();
    BLOQUE_GRID.id            = "tabela_bloqueio";
    BLOQUE_GRID.columnName    = "codacess,codmod,descmod,codapl,descapl,ativo,usuario,data";
    BLOQUE_GRID.columnLabel   = "Código acesso,Cód. Módulo,Módulo,Cód. Aplicação,Aplicação,Ativo,Usuário,Data";
    BLOQUE_GRID.columnWidth   = "10,10,22,10,22,6,10,10";
    BLOQUE_GRID.columnAlign   = "c,c,c,c,e,c,e,c";
    BLOQUE_GRID.mousehouve    = false;
    BLOQUE_GRID.destacarclick = true;
    BLOQUE_GRID.createGrid();

    USUBLOQ_GRID               = new GridForm_init();
    USUBLOQ_GRID.id            = "mbtabela_usubloq";
    USUBLOQ_GRID.columnName    = "ideusu,nome,";
    USUBLOQ_GRID.columnLabel   = "Usuário,Nome, ";
    USUBLOQ_GRID.columnWidth   = "30,50,20";
    USUBLOQ_GRID.columnAlign   = "e,e,c";
    USUBLOQ_GRID.mousehouve    = true;
    USUBLOQ_GRID.destacarclick = false;
    USUBLOQ_GRID.createGrid();

    RESPBLOQUE_GRID               = new GridForm_init();
    RESPBLOQUE_GRID.id            = "mbtabela_respobloq";
    RESPBLOQUE_GRID.columnName    = "ideusu1,nome1, ";
    RESPBLOQUE_GRID.columnLabel   = "Usuário,Nome, ";
    RESPBLOQUE_GRID.columnWidth   = "30,50,20";
    RESPBLOQUE_GRID.columnAlign   = "e,e,c";
    RESPBLOQUE_GRID.mousehouve    = true;
    RESPBLOQUE_GRID.destacarclick = false;
    RESPBLOQUE_GRID.createGrid();

    filaFetchGridInit();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_bloqueio,dmodalf_usuario,dmodalf_cadastro";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit();

    iniciarEventos();
    buscarUserName();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_selected_init("codapl,codmodel,muideusu");
    inputOnlyNumber('codapl,codmodel,mccodapl,mccodmod');

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("bcadastra");
    event_click("bsalvarcadas");
    event_click("bincluirusu");
    event_click("bincluirresp");
    event_click("bincluirresp");
    event_click("bsalvarusu");

    event_change("codmodel");
    event_change("codapl");
    event_change("mccodapl");
    event_change("mccodmod");
    event_change("muideusu");
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
        if(form(obj).id.substring(0,10) == "ativarresp"){
            var valoresLinha = RESPBLOQUE_GRID.getRowNode(form(obj).parentNode.parentNode);
            alteraEstadoBloqueioResp(valoresLinha[0])
            return;
        }
        if(form(obj).id.substring(0,9) == "ativarusu"){
            var valoresLinha = USUBLOQ_GRID.getRowNode(form(obj).parentNode.parentNode);
            alteraEstadoBloqueioUsuario(valoresLinha[0]);
            return;
        }

        switch (obj) {
            case   "bnovabusca": 
            case      "blimpar": controlaTela("inicia");
                                 break;
                             
            case      "bbuscar": controlaTela("buscar");
                                 carregarGridBloqueios();
                                 break;

            case    "bcadastra": form("mstitulocadas").innerText = "Cadastrar Bloqueio para aplicacão";
            
                                 controlaTela("cadastro");
                                 DMFDiv.openModal("dmodalf_cadastro");
                                 break;

            case "bsalvarcadas": cadastrarBloqueio();
                                 break;

            case  "bincluirusu": form("mstitulousu").innerText = "Inclusão de Usuário";
                                 form("sacaousu").innerText = "usuario";

                                 controlaTela("modalusu");

                                 DMFDiv.openModal("dmodalf_usuario");
                                 break;

            case "bincluirresp": form("mstitulousu").innerText = "Inclusão de Responsavel";
                                 form("sacaousu").innerText = "responsavel";

                                 controlaTela("modalusu");

                                 DMFDiv.openModal("dmodalf_usuario");
                                 break;

            case   "bsalvarusu": form("sacaousu").innerText == "usuario"? cadastrarBloqueioUsuario():cadastrarBloqueioResponsavel();
                                 break;
        }
    });
}

function event_change(obj){
    form(obj).addEventListener("change", function(){
        switch (obj) {
            case   "codmodel": getDescricaoModuloFiltro();
                               break;

            case     "codapl": getDescricaoAplicacaoFiltro();
                               break;

            case   "mccodapl": getDescricaoAplicacaoModal();
                               break;

            case   "mccodmod": getDescricaoModuloModel();
                               break;

            case   "muideusu": getNomeUsuario();
                               break;
        }
    });
}

function filaFetchGridInit(){
    USUBLOQ_GRID.filaFetchGrid = ()=>{
        eventClickLinksUsu();
        carregarGridUsuariosResp();
    }

    RESPBLOQUE_GRID.filaFetchGrid = ()=>{
        eventClickLinksResp();
    }
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno, error)=>{
        if(error){
            switch (CONSUL.obj) {
            case   "cadastrarBloqueio": alert("Erro ao salvar Bloqueio!", retorno, 4);
                                         break;

            case "cadastrarBloqueioUsuario": alert("Erro ao Incluir Usuário!", retorno, 4);
                                             break;

            case "cadastrarBloqueioResponsavel": alert("Erro ao Incluir Reponsavel!", retorno, 4);
                                                 break;

            case "alteraEstadoBloqueioUsuario": alert("Erro ao alterar estado do Usuário!", retorno, 4);
                                                break;

            case "alteraEstadoBloqueioResp": alert("Erro ao alterar estado do Responsavel!", retorno, 4);
                                             break;
            }
            return;
        }

        switch (CONSUL.obj) {
        case              "getNomeUsuario": if(retorno == "") {
                                                 alert("Erro ao Buscar usuário!", "Usuário não encontrado no sistema", 4);
                                                 return;
                                             }
                                             form('munomeusu').value  = retorno;
                                             break;

        case      "getDescricaoModuloModel": form("mcnomemod").value = retorno;
                                             break;

        case   "getDescricaoAplicacaoModal": form("mcnomeapl").value = retorno;
                                             break;

        case     "getDescricaoModuloFiltro": form("descmodel").value = retorno;
                                             break;

        case  "getDescricaoAplicacaoFiltro": form("descapl").value = retorno;
                                             break;

        case               "buscarUserName": form("ideusu").value = retorno;
                                             break;

        case            "cadastrarBloqueio": alert("Sucesso!", "O bloqueio foi cadastrado e já está disponivel para ser acessado!", 4);
                                             form("bnovabusca").click();
                                             form("bbuscar").click();

                                             DMFDiv.closeModal();
                                             break;
        
        case     "cadastrarBloqueioUsuario": alert("Sucesso!", "O Usuário foi incluíodo no bloqueio!", 4);
                                             carregarGridUsuariosBloq();
                                             DMFDiv.closeModal();
                                             break;

        case "cadastrarBloqueioResponsavel": alert("Sucesso!", "O Responsavel foi incluíodo no bloqueio!", 4);
                                             carregarGridUsuariosBloq();
                                             DMFDiv.closeModal();
                                             break;

        case     "alteraEstadoBloqueioResp": carregarGridUsuariosBloq();
                                             break;

        case  "alteraEstadoBloqueioUsuario": carregarGridUsuariosBloq();
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
        form('codapl').value    = "0";
        form('codmodel').value  = "0";
        form('descapl').value   = "Todas as Aplicações";
        form('descmodel').value = "Todas os Módulos";
    }
    if(opc === "inicia" || opc === "novabusca"){
        BLOQUE_GRID.clearGrid();
    }
    if(opc === "modalbloqueio"){
        form('mbmodulo').value     = "";
        form('mbdescmod').value    = "";
        form('mbaplicacao').value  = "";
        form('mbdescapl').value    = "";
    }
    if(opc === "modalusu"){
        form('muideusu').value  = "";
        form('munomeusu').value = "";
    }
    if(opc === "cadastro"){
        form('mccodapl').value     = "";
        form('mcnomeapl').value    = "";
        form('mccodmod').value     = "";
        form('mcnomemod').value    = "";
    }
}

function preencherDadosModal(valores){
    form("sacao").innerText   = "Alterando";
    form("stitulo").innerText = "Bloquear Acesso - " + form("sacao").innerText;
    
    form("hcodbloqueio").value = valores[0];
    form("mbmodulo").value     = valores[1];
    form("mbdescmod").value    = valores[2];
    form("mbaplicacao").value  = valores[3];
    form("mbdescapl").value    = valores[4];

    carregarGridUsuariosBloq();
}

function eventClickLinksUsu(){
    const links = document.querySelectorAll("a");

    links.forEach(link =>{
        if(link.id.substring(0,9) == "ativarusu") event_click("ativarusu" + link.id.substring(9));
    });
}

function eventClickLinksResp(){
    const links = document.querySelectorAll("a");

    links.forEach(link =>{
        if(link.id.substring(0,10) == "ativarresp") event_click("ativarresp" + link.id.substring(10));
    });
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`);
}

function getDescricaoAplicacaoFiltro(){
    CONSUL.consultar("getDescricaoAplicacaoFiltro",`/gen/getDescricaoAplicacao?codapl=${form("codapl").value}`);
}

function getDescricaoModuloFiltro(){
    CONSUL.consultar("getDescricaoModuloFiltro",`/gen/getDescricaoModulo?codmod=${form("codmodel").value}`);
}

function getDescricaoAplicacaoModal(){
    CONSUL.consultar("getDescricaoAplicacaoModal",`/gen/getDescricaoAplicacao?codapl=${form("mccodapl").value}`);
}

function getDescricaoModuloModel(){
    CONSUL.consultar("getDescricaoModuloModel",`/gen/getDescricaoModulo?codmod=${form("mccodmod").value}`);
}

function cadastrarBloqueio(){
    CONSUL.consultar("cadastrarBloqueio",`/mrb004c/cadastrarBloqueio?codapl=${form("mccodapl").value}&codmod=${form("mccodmod").value}&ideusu=${form("ideusu").value}`, "POST");
}

function cadastrarBloqueioUsuario(){
    CONSUL.consultar("cadastrarBloqueioUsuario",`/mrb004c/cadastrarBloqueioUsuario?ideusuSolic=${form("muideusu").value}&codbloqueio=${form("hcodbloqueio").value}&ideusu=${form("ideusu").value}`, "POST");
}

function cadastrarBloqueioResponsavel(){
    CONSUL.consultar("cadastrarBloqueioResponsavel",`/mrb004c/cadastrarBloqueioResponsavel?ideusuSolic=${form("muideusu").value}&codbloqueio=${form("hcodbloqueio").value}&ideusu=${form("ideusu").value}`, "POST");
}

function alteraEstadoBloqueioResp(idusuario){
    CONSUL.consultar("alteraEstadoBloqueioResp",`/mrb004c/alteraEstadoBloqueioResp?ideusuSolic=${idusuario}&codbloqueio=${form("hcodbloqueio").value}&ideusu=${form("ideusu").value}`, "POST");
}

function alteraEstadoBloqueioUsuario(idusuario){
    CONSUL.consultar("alteraEstadoBloqueioUsuario",`/mrb004c/alteraEstadoBloqueioUsuario?ideusuSolic=${idusuario}&codbloqueio=${form("hcodbloqueio").value}&ideusu=${form("ideusu").value}`, "POST");
}

function getNomeUsuario(){
    CONSUL.consultar("getNomeUsuario",`/gen/getNomeByIdeusu?ideusu=${form("muideusu").value}`);
}

function carregarGridBloqueios(){
    BLOQUE_GRID.carregaGrid(`/mrb004c/carregarGridBloqueios?codapl=${form("codapl").value}&codmodu=${form("codmodel").value}`,"","");
}

function carregarGridUsuariosBloq(){
    USUBLOQ_GRID.carregaGrid(`/mrb004c/carregarGridUsuariosBloq?idbloq=${form("hcodbloqueio").value}`,"","");
}

function carregarGridUsuariosResp(){
    RESPBLOQUE_GRID.carregaGrid(`/mrb004c/carregarGridUsuariosResp?idbloq=${form("hcodbloqueio").value}`,"","");
}