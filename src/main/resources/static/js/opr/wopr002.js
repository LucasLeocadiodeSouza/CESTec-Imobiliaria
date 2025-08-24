/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/06/25
    Obs: 
*/
window.addEventListener("load", function () {
    wopr002_init();
});

var CHAMADOS_GRID,SOLIC_GRID,SOLICDIR_GRID,PROGRAMAS_GRID;
var DMFDiv, ABA, CONSUL;
const ACAOBUSCA = {};

function wopr002_init(){
    elementsForm_init();

    CHAMADOS_GRID               = new GridForm_init();
    CHAMADOS_GRID.id            = "tabela_chamados";
    CHAMADOS_GRID.columnName    = "codchamado,titulo,datimpl,estado,prioridade,ideusu,_ideususolic,_desc,_prioridade,_complex,_estado,_datconcl,_feedback,_datprev,_obs,_fila,_codsetorfila,_setorfila,_codsetorsolic,_setorsolic,_podealterar";
    CHAMADOS_GRID.columnLabel   = "N. Chamado,Titulo,Data Solic.,Estado,Prioridade,Usuário";
    CHAMADOS_GRID.columnWidth   = "10,50,10,10,10,10";
    CHAMADOS_GRID.columnAlign   = "c,e,c,c,c,c";
    CHAMADOS_GRID.mousehouve    = true;
    CHAMADOS_GRID.destacarclick = true;
    CHAMADOS_GRID.createGrid();

    SOLIC_GRID               = new GridForm_init();
    SOLIC_GRID.id            = "tabela_solic";
    SOLIC_GRID.columnName    = "codchamado,titulo,datimpl,estado,prioridade,ideusu,_desc,_priorivalue,_estadovalue,_ideusufila";
    SOLIC_GRID.columnLabel   = "N. Chamado,Titulo,Data Solic.,Estado,Prioridade,Usuário";
    SOLIC_GRID.columnWidth   = "10,50,10,10,10,10";
    SOLIC_GRID.columnAlign   = "c,e,c,c,c,c";
    SOLIC_GRID.mousehouve    = true;
    SOLIC_GRID.destacarclick = true;
    SOLIC_GRID.createGrid();

    SOLICDIR_GRID               = new GridForm_init();
    SOLICDIR_GRID.id            = "tabela_solicdir";
    SOLICDIR_GRID.columnName    = "codchamado,titulo,datimpl,prioridade,ideusu,_desc,_priorivalue";
    SOLICDIR_GRID.columnLabel   = "N. Chamado,Titulo,Data Solic.,Prioridade,Usuário";
    SOLICDIR_GRID.columnWidth   = "10,60,10,10,10";
    SOLICDIR_GRID.columnAlign   = "c,e,c,c,c";
    SOLICDIR_GRID.mousehouve    = true;
    SOLICDIR_GRID.destacarclick = true;
    SOLICDIR_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Painel,Registros,Direcionamento";
    ABA.icon = "/icons/clips_icon.png,/icons/acess_icon.png,/icons/vincular_icon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_cadastrar,dmodalf_painel,dmodalf_direciona,dmodalf_programas,dmodalf_programas_mod";
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

    event_click("blimpar");
    event_click("bbuscar");
    event_click("binserir");
    event_click("bprograma");
    event_click("bincluirver");
    event_click("bsalvarsolic");
    event_click("bsolicitar");
    event_click("bdireciona");
    event_click("bsalvarprogmod");
    event_click("bfinalizar");

    event_change("mideusuvinc");

    event_selected_init("nmrsolic,mnummerge,mtitulocadas,mdesccadas,mbranchname,mprogname");
    inputOnlyNumber('nmrsolic,mnummerge');
}

function event_click_table(obj, row){
    switch (obj) {
    case CHAMADOS_GRID: var valoresLinha = CHAMADOS_GRID.getRowNode(row);
                        preencherModalCham(valoresLinha);

                        DMFDiv.fullScream = true;
                        DMFDiv.openModal("dmodalf_painel");
                        DMFDiv.fullScream = false;
                        break;

    case    SOLIC_GRID: var valoresLinha = SOLIC_GRID.getRowNode(row);
                        preencherModalSolic(valoresLinha);

                        DMFDiv.openModal("dmodalf_cadastrar");
                        break;

    case SOLICDIR_GRID: var valoresLinha = SOLICDIR_GRID.getRowNode(row);
                        form("sacaodir").innerText   ="Alterando";
                        form("stitulodir").innerText = "Direcionando Solicitação - " + form("sacaodir").innerText;

                        controlaTela("modaldirec");
                        
                        form('mmmnmrsolic').value  = valoresLinha[0];
                        form('mmtitulodir').value  = valoresLinha[1];
                        form('mmdescdir').value    = valoresLinha[6];

                        getOptionsComplex(0);

                        DMFDiv.openModal("dmodalf_direciona");
                        break;

    case PROGRAMAS_GRID: var valoresLinha = PROGRAMAS_GRID.getRowNode(row);
                         form("stituloprogmod").innerText = "Cadastro de Versionamento";
                         controlaTela("modalversiona");

                         form('mnummerge').value   = valoresLinha[0];
                         form('mbranchname').value = valoresLinha[1];
                         form('mprogname').value   = valoresLinha[2];

                         DMFDiv.openModal("dmodalf_programas_mod");
                         break;
    }
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case  "blimpar": limparTela("inicia");
                             break;
                             
            case  "bbuscar": if(ehAbaPainel())carregarGridChamados();
                             if(ehAbaRegistros())carregarGridChamSolicitados();
                             if(ehAbaVincular())carregarGridChamadosParaVinc();
                             break;

            case "binserir": form("sacaocadas").innerText   = "Inserindo";
                             form("stitulocadas").innerText = "Solicitação de Chamado - " + form("sacaocadas").innerText;
                            
                             controlaTela("modalcadastra");
                             
                             getOptionsPrioridade("0");
                             DMFDiv.openModal("dmodalf_cadastrar");
                             break;

            case "bprograma": form("stituloprog").innerText = "Controle de Versionamento";
                            
                              controlaTela("modalprog");

                              criarProgramasGrid();
                              DMFDiv.openModal("dmodalf_programas");
                              break;

            case "bincluirver": form("stituloprogmod").innerText = "Cadastro de Versionamento";
                            
                               limparTela("modalversiona");

                               DMFDiv.openModal("dmodalf_programas_mod");
                               break;

             case "bsalvarsolic": abrirSolicitacao();
                                  break; 
                    
            case    "bsolicitar": if(!confirm("Deseja mesmo enviar a Solicitação para Fila de analista?")) return;
                                  enviarSolicitacao();
                                  break; 

            case    "bdireciona": if(!confirm("Deseja mesmo direcionar a Solicitação para o usuário informado?")) return;
                                  direcionarSolic();
                                  break;
            
            case "bsalvarprogmod": if(!confirm("Deseja mesmo incluir o versionamento? Após incluido não será mais possível alterar.")) return;
                                   incluirVersionamento();
                                   break;

            case     "bfinalizar": if(!confirm("Deseja mesmo finalizar a solicitação? Após Finalizada será redirecionado para o Solicitante aprovar as alterações.")) return;
                                   finalizarSolicitacao();
                                   break;
        }
    });
}

function event_change(obj){
    form(obj).addEventListener("change", function(){
         switch (obj) {
            case  "mideusuvinc": getNomeUsuario();
                                 break;
         }
    });
}

function event_click_aba(obj){
    if(obj.id == "abas"){
        //Aba principal
        switch (ABA.getIndex()) {
        case 0: 
        case 1: 
        case 2: controlaTela("inicia");
                break;;
        }
    }
}


function filaFetchInit(){
    CONSUL.filaFetch = (retorno, error)=>{
        if(error){
            switch (CONSUL.obj) {
            case     "abrirSolicitacao": alert("Erro ao salvar a Solicitação!", retorno, 4);
                                         break;

            case "incluirVersionamento": alert("Erro ao Incluir o versionamento!", retorno, 4);
                                         break;
                                         
            case "finalizarSolicitacao": alert("Erro ao Finalizar a solicitação!", retorno, 4);
                                         break;
            }
            return;
        }

        switch (CONSUL.obj) {
        case        "buscarUserName": form("ideusu").value = retorno;
                                      break;
                            
        case      "abrirSolicitacao": alert("Solicitação salva com sucesso!", "A Solicitação foi salva no sistema. Mas ainda precisa ser confirmada!", 4);

                                      carregarGridChamSolicitados();
                                      DMFDiv.closeModal();
                                      break;

        case     "enviarSolicitacao": alert("Solicitação enviada com sucesso!", "A Solicitação foi enviada para a Fila. Após o direcionamento para um analista a solicitação será iniciada!", 4);

                                      carregarGridChamSolicitados();
                                      DMFDiv.closeModal();
                                      break;

        case      "direcionarSolic": alert("Solicitação direcionada com sucesso!", "A Solicitação foi direcionada para o usuário. Ele já pode iniciar a tarefa!", 4);

                                      carregarGridChamadosParaVinc();
                                      DMFDiv.closeModal();
                                      break;

        case  "getOptionsPrioridade": fillSelect("mprioricadas",retorno,true);
                                      form('mprioricadas').value  = ACAOBUSCA.getOptionsPrioridade.valorinicial;
                                      form('mprioricadas').childNodes[0].disabled = true;
                                      break;

        case     "getOptionsComplex": fillSelect("mscomplex",retorno,true);
                                      form('mscomplex').value  = ACAOBUSCA.getOptionsComplex.valorinicial;
                                      form('mscomplex').childNodes[0].disabled = true;
                                      break;

        case        "getNomeUsuario": if(retorno == "") {
                                         alert("Erro ao Buscar usuário!", "Usuário não encontrado no sistema", 4);
                                         return;
                                      }
                                      form('mnomeideusuvinc').value  = retorno;
                                      break;

        case  "incluirVersionamento": carregarGridversionamento();

                                      DMFDiv.closeModal();
                                      break;

        case "finalizarSolicitacao": alert("Solicitação finalizada com sucesso!", "A Solicitação foi direcionada para o usuário solicitante. Após aprovado pelo solicitante você poderá conferir seu feedback!", 4);

                                     carregarGridChamados();
                                     DMFDiv.closeModal();
                                     break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia"){
        desabilitaCampo('bbuscar',    false);
        desabilitaCampo('binserir',   false);

        setDisplay("binserir",        ehAbaRegistros()?"block":"none");
        setDisplay("tabela_chamados", ehAbaPainel()?"block":"none");
        setDisplay("tabela_solic",    ehAbaRegistros()?"block":"none");
        setDisplay("tabela_solicdir", ehAbaVincular()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('nmrsolic',   true);
        desabilitaCampo('binserir',   true);
    }
    if(opc == "modalsolic"){
        desabilitaCampo('mobssolic',   !podeAlterarSolic());
        desabilitaCampo('bfinalizar',  !podeAlterarSolic());
        desabilitaCampo('bcancelar',   !podeAlterarSolic());
    }
    if(opc === "modalprog"){
        desabilitaCampo('bincluirver', !podeAlterarSolic());
        setDisplay("bincluirver",       podeAlterarSolic()?"flex":"none");
    }
    if(opc === "modalcadastra"){
        desabilitaCampo('mtitulocadas', !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('mdesccadas',   !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('mprioricadas', !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('bsalvarsolic', !ehInserindoSolic() && !ehAlterandoSolic());
        desabilitaCampo('bsolicitar',   !ehAlterandoSolic());
        desabilitaCampo('bcancelsol',   !ehAlterandoSolic());

        setDisplay("bsalvarsolic",   !ehConsultandoSolic()?"flex":"none");
        setDisplay("bsolicitar",      ehAlterandoSolic()?"flex":"none");
        setDisplay("bcancelsol",      ehAlterandoSolic()?"flex":"none");
        setDisplay("dmestado",       !ehInserindoSolic()?"flex":"none");
        setDisplay("dmfila",         !ehInserindoSolic()?"flex":"none");
        setDisplay("dmnmrsol",       !ehInserindoSolic()?"flex":"none");
        setDisplay("dmabrirficha",   !ehInserindoSolic()?"flex":"none");
    }
    if(opc === "modalversiona"){
        desabilitaCampo('mnummerge',      true);
        desabilitaCampo('mbranchname',    true);
        desabilitaCampo('mprogname',      true);
        desabilitaCampo('bsalvarprogmod', true);
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){
        form('nmrsolic').value    = "0";
        setRadioValue("rsituacao",'1');
        CHAMADOS_GRID.clearGrid();
        SOLIC_GRID.clearGrid();
        SOLICDIR_GRID.clearGrid();
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
    if(opc === "modalcadastra"){
        form('mmnmrsolic').value    = "";
        form('mtitulocadas').value  = "";
        form('mdesccadas').value    = "";
        form('mprioricadas').value  = "0";
        form('mestadomod').value    = "";
        form('mfilamod').value      = "";
    }
    if(opc === "modaldirec"){
        form('mmmnmrsolic').value     = "";
        form('mmtitulodir').value     = "";
        form('mmdescdir').value       = "";
        form('mideusuvinc').value     = "";
        form('mnomeideusuvinc').value = "";
        form('mscomplex').value       = "0";
    }

    if(opc === "modalversiona"){
        form('mnummerge').value    = "";
        form('mbranchname').value  = "";
        form('mprogname').value    = "";
    }
}

function incluirVersionamento(){
    CONSUL.consultar("incluirVersionamento",`/opr002/incluirVersionamento?ideusu=${form("ideusu").value}&idsolic=${form("nmrservpainel").value}&merge=${form("mnummerge").value}&branch=${form("mbranchname").value}&prog=${form("mprogname").value}`,"POST");
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function abrirSolicitacao(){
    const solic = {idsolic:     form("mmnmrsolic").value,
                   titulo:      form("mtitulocadas").value,
                   descricao:   form("mdesccadas").value,
                   prioridade:  form("mprioricadas").value,
                   ideususolic: form("ideusu").value}

    CONSUL.consultar("abrirSolicitacao",`/opr002/abrirSolicitacao`,"POST","",{body: solic})
}

function enviarSolicitacao(){
    CONSUL.consultar("enviarSolicitacao",`/opr002/enviarSolicitacao?ideusu=${form("ideusu").value}&idsolic=${form("mmnmrsolic").value}`,"POST");
}

function finalizarSolicitacao(){
    CONSUL.consultar("finalizarSolicitacao",`/opr002/finalizarSolicitacao?ideusu=${form("ideusu").value}&idsolic=${form("nmrservpainel").value}&obs=${form("mobssolic").value}`,"POST");
}

function direcionarSolic(){
    CONSUL.consultar("direcionarSolic",`/opr002/direcionarSolic?ideusu=${form("ideusu").value}&ideusudirec=${form("mideusuvinc").value}&complex=${form("mscomplex").value}&idsolic=${form("mmmnmrsolic").value}`,"POST")
} 

function getNomeUsuario(){
    CONSUL.consultar("getNomeUsuario",`/opr002/getNomeUsuario?ideusu=${form("mideusuvinc").value}`);
}

function getOptionsPrioridade(valorinicial){
    ACAOBUSCA.getOptionsPrioridade = {
        valorinicial: parseInt(valorinicial)
    };

    CONSUL.consultar("getOptionsPrioridade",`/opr002/getOptionsPrioridade`)
}

function getOptionsComplex(valorinicial){
    ACAOBUSCA.getOptionsComplex = {
        valorinicial: parseInt(valorinicial)
    };

    CONSUL.consultar("getOptionsComplex",`/opr002/getOptionsComplex`)
}

function ehInserindoSolic(){
    return form("sacaocadas").innerText == "Inserindo";
}
function ehAlterandoSolic(){
    return form("sacaocadas").innerText == "Alterando";
}
function ehConsultandoSolic(){
    return form("sacaocadas").innerText == "Consultando";
}

function podeAlterarSolic(){
    return form("mpodealterar").value == "true";
}

function ehAbaPainel(){
    return ABA.getIndex() === 0;
}

function ehAbaRegistros(){
    return ABA.getIndex() === 1;
}

function ehAbaVincular(){
    return ABA.getIndex() === 2;
}

function preencherModalSolic(valoresLinha){
    form("sacaocadas").innerText   = valoresLinha[8] =="0"?"Alterando":"Consultando";
    form("stitulocadas").innerText = "Solicitação de chamado - " + form("sacaocadas").innerText;

    controlaTela("modalcadastra");
    
    form('mmnmrsolic').value   = valoresLinha[0];
    form('mtitulocadas').value = valoresLinha[1];
    form('mdesccadas').value   = valoresLinha[6];
    form('mestadomod').value   = valoresLinha[3];
    form('mfilamod').value     = valoresLinha[9];
    
    getOptionsPrioridade(valoresLinha[7]);
}

function preencherModalCham(valoresLinha){
    form("stitulopainel").innerText = "Solicitação de chamado  " + valoresLinha[0];
    form("nmrservpainel").value     = valoresLinha[0];
    form("mpodealterar").value      = valoresLinha[20];

    controlaTela("modalsolic");
    
    form('mtitulosolic').innerText   = valoresLinha[1];
    form('mnmrsolic').innerText      = valoresLinha[0];
    form('mdescsolic').innerText     = valoresLinha[6];
    form('mestadosolic').innerText   = valoresLinha[10] + " - " + valoresLinha[3];
    form('mpriorisolic').innerText   = valoresLinha[7] + " - " + valoresLinha[4];
    form('mususolic').innerText      = valoresLinha[5];
    form('msetsolic').innerText      = valoresLinha[18] + " - " + valoresLinha[19];
    form('mcomplexsolic').innerText  = valoresLinha[8] + (valoresLinha[8] != "0"?" - " + valoresLinha[9] : "");
    form('mprevisaosolic').innerText = valoresLinha[13];
    form('mfila').innerText          = valoresLinha[15];
    form('msetfilasolic').innerText  = valoresLinha[16] + " - " + valoresLinha[17];
    form('mobssolic').value          = valoresLinha[14];
    form('mdatconclsolic').innerText = valoresLinha[11];
    form('mobsfinalsolic').innerText = valoresLinha[14];
}

function criarProgramasGrid(){
    form("tabela_programas").innerHTML = "";

    PROGRAMAS_GRID              = new GridForm_init();
    PROGRAMAS_GRID.id            = "tabela_programas";
    PROGRAMAS_GRID.columnName    = "merge,branch,programas,data,ideusu";
    PROGRAMAS_GRID.columnLabel   = "N. Merge,Branch name,Programas,Data,Usuário";
    PROGRAMAS_GRID.columnWidth   = "15,31,23,15,16";
    PROGRAMAS_GRID.columnAlign   = "c,e,e,c,c";
    PROGRAMAS_GRID.mousehouve    = true;
    PROGRAMAS_GRID.destacarclick = false;
    PROGRAMAS_GRID.createGrid();

    carregarGridversionamento();
}

function carregarGridChamSolicitados(){
    SOLIC_GRID.carregaGrid(`/opr002/carregarGridChamSolicitados?ideusu=${form("ideusu").value}&somenteAtivo=${getRadioValue("rsituacao")}`,"","");
}

function carregarGridChamados(){
    CHAMADOS_GRID.carregaGrid(`/opr002/carregarGridChamados?ideusu=${form("ideusu").value}&somenteAtivo=${getRadioValue("rsituacao")}&acao=direcionadas`,"","");
}

function carregarGridChamadosParaVinc(){
    SOLICDIR_GRID.carregaGrid(`/opr002/carregarGridChamados?somenteAtivo=${getRadioValue("rsituacao")}&acao=direcionar`,"","");
}

function carregarGridversionamento(){
    PROGRAMAS_GRID.carregaGrid(`/opr002/carregarGridversionamento?idsolic=${form("nmrservpainel").value}`,"","");
} 