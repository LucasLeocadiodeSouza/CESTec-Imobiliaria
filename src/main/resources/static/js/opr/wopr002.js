/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/06/25
    Obs:
*/
window.addEventListener("load", function () {
    wopr002_init();
});

var CHAMADOS_GRID,HORARIOS_GRID,PROGRAMAS_GRID;
var DMFDiv, ABA, ABAPAINEL, CONSUL;

function wopr002_init(){
    elementsForm_init();

    CHAMADOS_GRID               = new GridForm_init();
    CHAMADOS_GRID.id            = "tabela_chamados";
    CHAMADOS_GRID.columnName    = "codchamado,desc,datimpl,ideusu,prioridade,hrtrab";
    CHAMADOS_GRID.columnLabel   = "N. Chamado,Descrição,Data Solic.,Estado,Prioridade,Usuário,Horas trabalhadas";
    CHAMADOS_GRID.columnWidth   = "10,50,10,10,10,10";
    CHAMADOS_GRID.columnAlign   = "c,e,c,e,c,c";
    CHAMADOS_GRID.mousehouve    = false;
    CHAMADOS_GRID.destacarclick = true;
    CHAMADOS_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Painel,Registros";
    ABA.icon = "/icons/clips_icon.png,/icons/acess_icon.png";
    ABA.createAba();

    ABAPAINEL      = new abaForm_init();
    ABAPAINEL.id   = "abaspainelsolic";
    ABAPAINEL.name = "Solicitação,Horários";
    ABAPAINEL.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_painel,dmodalf_programas";
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

    event_selected_init("nmrsolic");
}

function event_click_table(obj, row){
    switch (obj) {
    case CHAMADOS_GRID: const valoresLinha = CHAMADOS_GRID.getRowNode(row);
                        controlaTela("modalcadasapl");

                        DMFDiv.openModal("dmodalf_painel");
                        break;
    }
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        
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

        //setDisplay("bliberar",   ehLiberacaoDeAcesso()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('nmrsolic',        true);
        desabilitaCampo('bbuscar',         true);
    }
    if(opc == "modal"){
        setDisplay("mficha-solic",   ehAbaSolic()?"flex":"none");
        setDisplay("fhorarios",      ehAbaHorarios()?"flex":"none");
        
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){
        form('nmrsolic').value   = "0";
    }
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
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