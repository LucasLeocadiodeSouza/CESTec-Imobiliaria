/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/06/25
    IM: 00021
*/
window.addEventListener("load", function () {
    wmrb001_init();
});

var LIBACESS_GRID,CADAPL_GRID;
var DMFDiv, ABA, CONSUL, IMPRIMIR;
var ACAOBUSCA = {};

function wmrb001_init(){
    elementsForm_init();

    LIBACESS_GRID               = new GridForm_init();
    LIBACESS_GRID.id            = "tabela_libacess";
    LIBACESS_GRID.columnName    = "codacess,codapl,descapl,usuario,data";
    LIBACESS_GRID.columnLabel   = "Código acesso,Código Aplicação,Aplicação,Usuário,Data";
    LIBACESS_GRID.columnWidth   = "15,10,35,20,20";
    LIBACESS_GRID.columnAlign   = "c,c,e,e,c";
    LIBACESS_GRID.mousehouve    = false;
    LIBACESS_GRID.destacarclick = true;
    LIBACESS_GRID.createGrid();

    CADAPL_GRID               = new GridForm_init();
    CADAPL_GRID.id            = "tabela_APL";
    CADAPL_GRID.columnName    = "usuario,mcodmod,mdescmod,codapl,descapl,data,_role,_arqu_inic,_indcadastro,_indliberacao,_indanalise,_indgestao";
    CADAPL_GRID.columnLabel   = "Usuário,Código Mod.,Módulo Referente,Código Aplicação,Aplicação,Data";
    CADAPL_GRID.columnWidth   = "12,12,17,17,30,12";
    CADAPL_GRID.columnAlign   = "e,c,c,c,e,c";
    CADAPL_GRID.mousehouve    = true;
    CADAPL_GRID.clickgrid     = false;
    CADAPL_GRID.destacarclick = true;
    CADAPL_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Liberação de Acesso,Cadastro de Aplicação";
    ABA.icon = "/icons/acess_icon.png,/icons/clips_icon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_libacess,dmodalf_cadapl";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    IMPRIMIR = new imprimirForm_init();

    CONSUL = new consulForm_init();
    filaFetchInit();

    iniciarEventos();
    buscarUserName();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_selected_init("codapl,codmodel,mideusu,mcodapl,mdatvenc,mdescapl,mmodulo");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("bliberar");
    event_click("bcadastrar");
    event_click("blimpar");
    event_click("bcadastroapl");
    event_click("bimprimir");
    
    event_change("mmodulo");
}

function event_click_table(obj, row){
    // LIBACESS_GRID.click_table = ()=>{
    //     const clickedCell = event.target.closest('td');
    //     if (clickedCell && clickedCell.cellIndex === 0) return;

    //     const valoresLinha = LIBACESS_GRID.getRowNode(event.target.closest('tr'));

    //     controlaTela("modal");

    //     preencherDadosModal(valoresLinha);

    //     DMFDiv.openModal("dmodalf_libacess");
    // };

    switch (obj) {
    case CADAPL_GRID: const valoresLinha = CADAPL_GRID.getRowNode(row);
                      controlaTela("modalcadasapl");

                      preencherDadosModalCadasApl(valoresLinha);

                      DMFDiv.openModal("dmodalf_cadapl");
                      break;
    }
}

function event_click(obj) {
    if(obj == "bnovabusca"){
        form(obj).addEventListener("click", function () {
            controlaTela("novabusca");
        });
    }
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("buscar");
            ehCadastroDeApl()?carregaGridAplicacoes():null;
        });        
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            zoom("codapl");
            //controlaTela("inicia");
        });
    }
    if(obj == 'bliberar'){
        form(obj).addEventListener("click", function () {
            form("sacaolibacess").innerText   = "Inserindo";
            form("stitulolibacess").innerText = "Liberar Acesso Usuário - " + form("sacaolibacess").innerText;
            
            controlaTela("modallibacess");
            DMFDiv.openModal("dmodalf_libacess");
        });
    }
    if(obj == 'bcadastrar'){
        form(obj).addEventListener("click", function () {
            form("sacaocadcpl").innerText   = "Inserindo";
            form("stitulocadcpl").innerText = "Cadastrar Aplicação - " + form("sacaocadcpl").innerText;

            buscarRoleAcess(1);

            controlaTela("modalcadasapl");
            DMFDiv.openModal("dmodalf_cadapl");
            
        });
    }
    if(obj == 'bcadastroapl'){
        form(obj).addEventListener("click", function () {
            if(!confirm("Deseja mesmo adicionar essa aplicação?")) return;

            adicionarAplicacao();
        });
    }
    if(obj == "bimprimir"){
        form(obj).addEventListener("click", function () {
            IMPRIMIR.imprimirGrid(form(CADAPL_GRID.id).innerHTML, "Aplicacoes Cadastradas", "Aplicacoes Cadastradas no Sistema até o da ");
        });
    }
}

function event_change(obj){
    if(obj == "mmodulo"){
        form(obj).addEventListener("change", function(){
            getDescricaoModulo();
        });
    }
}

function event_click_aba(){
    switch (ABA.getIndex()) {
    case 0: 
    case 1: controlaTela("inicia");
            break;
    }
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

        setDisplay("bliberar",   ehLiberacaoDeAcesso()?"flex":"none");
        setDisplay("bcadastrar", ehCadastroDeApl()?"flex":"none");

        setDisplay("tabela_libacess", ehLiberacaoDeAcesso()?"block":"none");
        setDisplay("tabela_APL",      ehCadastroDeApl()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('codapl',          true);
        desabilitaCampo('codmodel',        true);
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
    }
    if(opc == "modallibacess"){
        desabilitaCampo('mideusu',      !ehLiberacaoDeAcesso());
        desabilitaCampo('mcodapl',      !ehLiberacaoDeAcesso());
        desabilitaCampo('mdatvenc',     !ehLiberacaoDeAcesso());
        desabilitaCampo('bcadastrolib', !ehLiberacaoDeAcesso());
    }
    if(opc == "modalcadasapl"){
        desabilitaCampo('mdescapl',     !ehCadastroDeApl());
        desabilitaCampo('mmodulo',      !ehCadastroDeApl());
        desabilitaCampo('mrestrole',    !ehCadastroDeApl());
        desabilitaCampo('bcadastroapl', !ehCadastroDeApl());
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){        
        form('codapl').value   = "0";
        form('codmodel').value = "0";
    }
    if(opc === "inicia" || opc === "novabusca"){
        LIBACESS_GRID.clearGrid();
        CADAPL_GRID.clearGrid();
    }
    if(opc === "modallibacess"){
        form('mideusu').value     = "";
        form('mdescideusu').value = "";
        form('mcodapl').value     = "0";
        form('mdescapl').value    = "";
        form('mdatvenc').value    = "";
    }
    if(opc === "modalcadasapl"){
        form('hcodapl').value    = "";
        form('mdescapl').value   = "";
        form('mmodulo').value    = "0";
        form('mdescmod').value   = "";
        form('mrestrole').value  = "0";
        form("marqinit").value   = "";
        setRadioValue("rindcadas", "false");
        setRadioValue("rindlib",   "false");
        setRadioValue("rindanali", "false");
        setRadioValue("rindgest",  "false");
    }
}

function preencherDadosModalCadasApl(valores){
    form("sacaocadcpl").innerText   = "Alterando";
    form("stitulocadcpl").innerText = "Cadastro de Aplicação - " + form("sacaocadcpl").innerText;

    buscarRoleAcess(valores[6]);
    
    form("hcodapl").value   = valores[3];
    form("mdescapl").value  = valores[4];
    form("mmodulo").value   = valores[1];
    form("mdescmod").value  = valores[2];
    form("marqinit").value  = valores[7];
    
    setRadioValue("rindcadas", valores[8]);
    setRadioValue("rindlib",   valores[9]);
    setRadioValue("rindanali", valores[10]);
    setRadioValue("rindgest",  valores[11]);
}

function ehLiberacaoDeAcesso(){
    return ABA.getIndex() === 0;
}

function ehCadastroDeApl(){
    return ABA.getIndex() === 1;
}

function adicionarAplicacao() {
    const aplicacao = { id:            form("hcodapl").value,
                        idmodulo:      form("mmodulo").value,
                        role:          form("mrestrole").value,
                        descricao:     form('mdescapl').value,
                        arquivo_inic:  form('marqinit').value,
                        ideusu:        form('ideusu').value};

    CONSUL.consultar("adicionarAplicacao",`/mrb001/cadastrarAplicacao`,["indcadastro=" + getRadioValue("rindcadas"),
                                                                        "indliberacao=" + getRadioValue("rindlib"),
                                                                        "indanalise=" + getRadioValue("rindanali"),
                                                                        "indgestao=" + getRadioValue("rindgest")],
                                                                        "POST",
                                                                        "",
                                                                        {body: aplicacao});
}

function buscarRoleAcess(valorinicial){
    ACAOBUSCA.buscarRoleAcess = {
        valorinicial: valorinicial
    }

    CONSUL.consultar("buscarRoleAcess",`/mrb001/buscarRoleAcess`)
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function getDescricaoModulo(){
    CONSUL.consultar("getDescricaoModulo",`/mrb001/getDescricaoModulo`,["codmodulo:mmodulo"]);
}

function carregaGridAplicacoes(){
    CADAPL_GRID.carregaGrid(`/mrb001/buscarAplicacoesGrid`,["codapl:codapl","codmodu:codmodel","ideusu:ideusu"],"","");
}