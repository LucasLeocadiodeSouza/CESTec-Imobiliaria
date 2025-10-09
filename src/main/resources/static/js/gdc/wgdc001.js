/* 
    Dev: Lucas Leocadio de Souza
    Data: 13/03/25
    IM: 00017
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

var ABA,DMFDiv,CONSUL,METAS_GRID;

function iniciarEventos() {
    elementsForm_init();

    METAS_GRID               = new GridForm_init();
    METAS_GRID.id            = "tabela_metas";
    METAS_GRID.columnName    = "codmeta,codcorretor,nome,vlrmeta,periodo,situacao,datiniciometa,datfinalmeta,_ideusucorr";
    METAS_GRID.columnLabel   = "Cód. Meta,Cód. Corretor,Nome,Meta (R$),Periodo,Situação";
    METAS_GRID.columnWidth   = "10,10,35,10,20,15";
    METAS_GRID.columnAlign   = "c,c,e,d,c,c";
    METAS_GRID.mousehouve    = true;
    METAS_GRID.destacarclick = true;
    METAS_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta";
    ABA.icon = "/icons/consultaLupa.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_meta";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");

    event_selected_init("codcorretor");

    CONSUL.filterChange('codcorretor','',`/gen/getNomeCorretorByIdeusu`,['ideusu:codcorretor'],'desccorretor');
    CONSUL.filterChange('mcodcorretor','',`/gen/getNomeCorretorByIdeusu`,['ideusu:mcodcorretor'],'mdesccorretor');
    //CONSUL.filterChange('mcodproprietario','',`/gen/getNomeProp`,['codprop:mcodproprietario'],'mdescproprietario');

    controlaTela("inicia");
}

function event_click_table(obj,row){
    switch (obj) {
    case METAS_GRID: const valoresLinha = METAS_GRID.getRowNode(row);

                     form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
                     form("stitulo").innerText = "Cadastro de Metas - " + form("sacao").innerText;
                     controlaTela("modal");

                     preencherModal(valoresLinha);
                     DMFDiv.openModal("dmodalf_meta");
                     break;
    }
}

function event_click_aba(){
    switch (ABA.getIndex()) {
    case 0: controlaTela("inicia");
            break;
    }
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case "bnovabusca": controlaTela("novabusca");
                               break;

            case    "bbuscar": controlaTela("buscar");
                               buscarMetasCorretoresGrid();
                               break;

            case   "binserir": form("sacao").innerText   = "Inserindo";
                               form("stitulo").innerText = "Cadastro de Meta - " + form("sacao").innerText;
                                           
                               controlaTela("modal");
                               DMFDiv.openModal("dmodalf_meta");
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case "bcadastro": salvarMetaCorretor();
                              break;
        }
    });
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{

        switch (CONSUL.obj) {
        case        "buscarUserName": form("ideusu").value = retorno;
                                      break;

        case    "salvarMetaCorretor": if(retorno != "OK") return alert(retorno);
                                      alert("Sucesso","Meta adicionada com sucesso!",4);

                                      DMFDiv.closeModal();
                                      break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',    true);
        desabilitaCampo('bbuscar',       false);
        desabilitaCampo('codcorretor',   false);
        desabilitaCampo('periodoini',    false);
        desabilitaCampo('periodofin',    false);
    }
    if(opc === "inicia" || opc === "novabusca"){
        METAS_GRID.clearGrid();
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',    false);
        desabilitaCampo('bbuscar',       true);
        desabilitaCampo('codcorretor',   true);
        desabilitaCampo('periodoini',    true);
        desabilitaCampo('periodofin',    true);
    }
    if(opc == "modal"){
        desabilitaCampo('mvlrmeta',      !ehInserindo());
        desabilitaCampo('mcodcorretor',  !ehInserindo());
        desabilitaCampo('mdesccorretor', true);
        desabilitaCampo('mperiodoini',   !ehInserindo());
        desabilitaCampo('mperiodofin',   !ehInserindo());
        desabilitaCampo('bcadastro',     !ehInserindo());
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'buscar'){        
        form('codcorretor').value  = "";
        form('desccorretor').value = "Todos os Corretores";
        form('periodoini').value   = "";
        form('periodofin').value   = "";
    }
    if(opc == "modal"){
        form('mcodcorretor').value  = "";
        form("mdesccorretor").value = "";
        form('mvlrmeta').value      = "0";
        form("ssituacao").value     = "";
        form("mperiodoini").value   = "";
        form("mperiodofin").value   = "";
    }
}

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehInserindo(){
    return form("sacao").innerText == "Inserindo";
}

function preencherModal(valoresLinha){
    form("mcodcorretor").value  = valoresLinha[8];
    form("mdesccorretor").value = valoresLinha[2];
    form("mvlrmeta").value      = valoresLinha[3];
    form("mperiodoini").value   = valoresLinha[6];
    form("ssituacao").innerText = "* " + valoresLinha[5];
    form("mperiodofin").value   = valoresLinha[7];

    if(valoresLinha[5] !== 2) form("ssituacao").classList.add("vermelho");
    else {
        form("ssituacao").classList.remove("vermelho");
        form("ssituacao").classList.add("verde");
    }
}

function buscarMetasCorretoresGrid(){
    METAS_GRID.carregaGrid("/cri006/buscarMetasCorretoresGrid","","");
}

function salvarMetaCorretor(){
    CONSUL.consultar("salvarMetaCorretor",`/cri006/salvarMetaCorretor`,["ideusucoor:mcodcorretor",
                                                                        "datini:mperiodoini",
                                                                        "datfim:mperiodofin",
                                                                        "vlrmeta:mvlrmeta"],
                                                                        "POST");
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`);
}