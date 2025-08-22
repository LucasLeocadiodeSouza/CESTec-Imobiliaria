/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/06/25
    Obs:
*/
window.addEventListener("load", function () {
    wopr002_init();
});

var CHAMADOS_GRID,SOLIC_GRID,HORARIOS_GRID,PROGRAMAS_GRID,PROGRAMASMOD_GRID;
var DMFDiv, ABA, ABAPAINEL, CONSUL;

function wopr002_init(){
    elementsForm_init();

    CHAMADOS_GRID               = new GridForm_init();
    CHAMADOS_GRID.id            = "tabela_chamados";
    CHAMADOS_GRID.columnName    = "codchamado,titulo,datimpl,estado,prioridade,ideusu,_ideususolic,_desc,_prioridade,_complex,_estado,_datconcl,_feedback,_datprev,_obs";
    CHAMADOS_GRID.columnLabel   = "N. Chamado,Titulo,Data Solic.,Estado,Prioridade,Usuário";
    CHAMADOS_GRID.columnWidth   = "10,50,10,10,10,10";
    CHAMADOS_GRID.columnAlign   = "c,e,c,c,c,c";
    CHAMADOS_GRID.mousehouve    = false;
    CHAMADOS_GRID.destacarclick = true;
    CHAMADOS_GRID.createGrid();

    SOLIC_GRID               = new GridForm_init();
    SOLIC_GRID.id            = "tabela_solic";
    SOLIC_GRID.columnName    = "codchamado,titulo,datimpl,estado,prioridade,ideusu";
    SOLIC_GRID.columnLabel   = "N. Chamado,Titulo,Data Solic.,Estado,Prioridade,Usuário";
    SOLIC_GRID.columnWidth   = "10,50,10,10,10,10";
    SOLIC_GRID.columnAlign   = "c,e,c,c,c,c";
    SOLIC_GRID.mousehouve    = false;
    SOLIC_GRID.destacarclick = true;
    SOLIC_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Painel,Registros";
    ABA.icon = "/icons/clips_icon.png,/icons/acess_icon.png";
    ABA.createAba();

    ABAPAINEL      = new abaForm_init();
    ABAPAINEL.id   = "abaspainelsolic";
    ABAPAINEL.name = "Solicitação,Horários,Versionamento";
    ABAPAINEL.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_cadastrar,dmodalf_painel,dmodalf_programas,dmodalf_programas_mod";
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

    event_click("bbuscar");
    event_click("binserir");

    event_selected_init("nmrsolic");
}

function event_click_table(obj, row){
    switch (obj) {
    case CHAMADOS_GRID: const valoresLinha = CHAMADOS_GRID.getRowNode(row);
                        preencherModal(valoresLinha);

                        DMFDiv.fullScream = true;
                        DMFDiv.openModal("dmodalf_painel");
                        DMFDiv.fullScream = false;
                        break;
    }
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case  "bbuscar": carregarGridChamados();
                             break;

            case "binserir": form("sacaocadas").innerText   = "Inserindo";
                             form("stitulocadas").innerText = "Solicitacão de Chamado - " + form("sacaocadas").innerText;
                            
                             controlaTela("modalcadastra");

                             DMFDiv.openModal("dmodalf_cadastrar");
                             break;
        }
    });
}

function event_click_aba(obj){
    if(obj.id == "abas"){
        //Aba principal
        switch (ABA.getIndex()) {
        case 0: 
        case 1: controlaTela("inicia");
                break;
        }
    }

    if(obj.id == "abaspainelsolic"){
        //Aba modal
        switch (ABAPAINEL.getIndex()) {
        case 0: controlaTela("modal");
                break;

        case 1: controlaTela("modal");
                criarHorariosGrid();
                break;
        }
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
        case        "buscarUserName": form("ideusu").value = retorno;
                                      break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia"){
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('binserir',        false);

        setDisplay("binserir",   ehAbaRegistros()?"block":"none");
        setDisplay("tabela_chamados", ehAbaPainel()?"block":"none");
        setDisplay("tabela_solic",    ehAbaRegistros()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('nmrsolic',        true);
        desabilitaCampo('binserir',        true);
    }
    if(opc == "modal"){
        setDisplay("mficha-solic",   ehAbaSolic()?"flex":"none");
        setDisplay("fhorarios",      ehAbaHorarios()?"flex":"none");
    }
    if(opc === "modalcadastra"){
        desabilitaCampo('mtitulocadas', !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('mdesccadas',   !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('mprioricadas', !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('bsalvarsolic', !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('bsolicitar',   !ehInserindoSolic() && !ehAlterandoSolic());

        setDisplay("bsalvarsolic",    ehInserindoSolic()?"flex":"none");
        setDisplay("bsolicitar",      ehAlterandoSolic()?"flex":"none");
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){
        form('nmrsolic').value    = "0";
        setRadioValue("rsituacao",'0');
        CHAMADOS_GRID.clearGrid();
        SOLIC_GRID.clearGrid();
    }
    if(opc === "modalsolic"){
        form('mtitulosolic').value    = "";
        form('mnmrsolic').value       = "";
        form('mdescsolic').value      = "";
        form('mestadosolic').value    = "";
        form('mpriorisolic').value    = "";
        form('mususolic').value       = "";
        form('msetsolic').value       = "";
        form('mcomplexsolic').value   = "";
        form('mprevisaosolic').value  = "";
        form('mfila').value           = "";
        form('msetfilasolic').value   = "";
        form('mobssolic').value       = "";
        form('mdatconclsolic').value  = "";
        form('mobsfinalsolic').value  = "";
    }
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function ehInserindoSolic(){
    return form("sacaocadas").innerText == "Inserindo";
}
function ehAlterandoSolic(){
    return form("sacaocadas").innerText == "Alterando";
}

function ehAbaPainel(){
    return ABA.getIndex() === 0;
}

function ehAbaRegistros(){
    return ABA.getIndex() === 1;
}

function ehAbaSolic(){
    return ABAPAINEL.getIndex() === 0;
}

function ehAbaHorarios(){
    return ABAPAINEL.getIndex() === 1;
}

function ehAbaVersionamento(){
    return ABAPAINEL.getIndex() === 2;
}

function preencherModal(valoresLinha){
    form("stitulopainel").innerText = "Solicitacão de chamado  " + valoresLinha[0];
    form("nmrservpainel").innerText   = valoresLinha[0];

    controlaTela("modalsolic");
    
    form('mtitulosolic').innerText   = valoresLinha[1];
    form('mnmrsolic').innerText      = valoresLinha[0];
    form('mdescsolic').innerText     = valoresLinha[6];
    form('mestadosolic').innerText   = valoresLinha[10] + " - " + valoresLinha[3];
    form('mpriorisolic').innerText   = valoresLinha[7] + " - " + valoresLinha[4];
    form('mususolic').innerText      = valoresLinha[5];
    form('msetsolic').innerText      = valoresLinha[0];
    form('mcomplexsolic').innerText  = valoresLinha[8] + (valoresLinha[8] != "0"?" - " + valoresLinha[9] : "");
    form('mprevisaosolic').innerText = valoresLinha[13];
    form('mfila').innerText          = valoresLinha[15];
    form('msetfilasolic').innerText  = valoresLinha[0];
    form('mobssolic').innerText      = valoresLinha[14];
    form('mdatconclsolic').innerText = valoresLinha[11];
    form('mobsfinalsolic').innerText = valoresLinha[14];
}

function criarHorariosGrid(){
    form("tabela_horario").innerHTML = "";

    HORARIOS_GRID               = new GridForm_init();
    HORARIOS_GRID.id            = "tabela_horario";
    HORARIOS_GRID.columnName    = "dathist,horinicio,horfinal,ideusu";
    HORARIOS_GRID.columnLabel   = "Data Historico,Hora Inicio,Hora Final,Usuário";
    HORARIOS_GRID.columnWidth   = "25,25,25,25";
    HORARIOS_GRID.columnAlign   = "c,c,c,e";
    HORARIOS_GRID.mousehouve    = false;
    HORARIOS_GRID.destacarclick = false;
    HORARIOS_GRID.createGrid();
}

function criarProgramasGrid(){
    form("tabela_programas").innerHTML = "";

    PROGRAMAS_GRID              = new GridForm_init();
    PROGRAMAS_GRID.id            = "tabela_programas";
    PROGRAMAS_GRID.columnName    = "merge,branch,programas,data,ideusu";
    PROGRAMAS_GRID.columnLabel   = "N. Merge,Branch name,Programas,Data,Usuário";
    PROGRAMAS_GRID.columnWidth   = "20,20,20,20,20";
    PROGRAMAS_GRID.columnAlign   = "c,e,e,c,c";
    PROGRAMAS_GRID.mousehouve    = false;
    PROGRAMAS_GRID.destacarclick = false;
    PROGRAMAS_GRID.createGrid();
}

function criarProgramasGrid(){
    form("tabela_programas_mod").innerHTML = "";

    PROGRAMASMOD_GRID              = new GridForm_init();
    PROGRAMASMOD_GRID.id            = "tabela_programas_mod";
    PROGRAMASMOD_GRID.columnName    = "nome,ext";
    PROGRAMASMOD_GRID.columnLabel   = "Nome,Extensão";
    PROGRAMASMOD_GRID.columnWidth   = "80,20";
    PROGRAMASMOD_GRID.columnAlign   = "e,c";
    PROGRAMASMOD_GRID.mousehouve    = true;
    PROGRAMASMOD_GRID.destacarclick = false;
    PROGRAMASMOD_GRID.createGrid();
}

function carregarGridChamados(){
    CHAMADOS_GRID.carregaGrid(`/opr002/carregarGridChamados?ideusu=${form("ideusu").value}&somenteAtivo=${getRadioValue("rsituacao")}`,"","");
}