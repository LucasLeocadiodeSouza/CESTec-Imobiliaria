/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/06/25
    IM: 00021
*/
window.addEventListener("load", function () {
    wopr001_init();
});

var AGEN_GRID, FUNC_GRID;
var DMFDiv, ABA, ABAFILTRO, CONSUL;
var ACAOBUSCA = {};

function wopr001_init(){
    elementsForm_init();

    AGEN_GRID               = new GridForm_init();
    AGEN_GRID.id            = "tabela_agend";
    AGEN_GRID.columnName    = "codagen,titulo,descricao,motivo,datagen,horagen,ideusu,_motivos";
    AGEN_GRID.columnLabel   = "Código,Título,Descrição,Motivo,Data,Hora,Usuário";
    AGEN_GRID.columnWidth   = "8,15,30,15,12,9,11";
    AGEN_GRID.columnAlign   = "c,e,e,e,c,c,e";
    AGEN_GRID.mousehouve    = false;
    AGEN_GRID.destacarclick = true;
    AGEN_GRID.createGrid();

    FUNC_GRID               = new GridForm_init();
    FUNC_GRID.id            = "funcionarios_grid";
    FUNC_GRID.columnName    = "cb,codfunc,nomefunc,codcargo,cargo,codsetor,setor";
    FUNC_GRID.columnLabel   = "<input type='checkbox' id='marcatodos' name='marcatodos'>,Cód. Func,Funcionario,Cód. Carg,Cargo,Cód. Set,Setor";
    FUNC_GRID.columnWidth   = "7,13,24,13,16,12,15";
    FUNC_GRID.columnAlign   = "c,c,e,c,e,c,e";
    FUNC_GRID.gridHeight    = "250px";
    FUNC_GRID.mousehouve    = false;
    FUNC_GRID.destacarclick = true;
    FUNC_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutenção";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    ABAFILTRO      = new abaForm_init();
    ABAFILTRO.id   = "abasfiltroagen";
    ABAFILTRO.name = "Funcionario,Setor,Cargo";
    ABAFILTRO.icon = "/icons/work_icon2.png,/icons/group_icon.png,/icons/clips_icon.png";
    ABAFILTRO.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_agenda";
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

    event_click_table();
    event_click_aba();
    event_selected_init("codfunc,codcargo,codsetor");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("binserir");
    event_click("bagendar");
    
    event_change("codfunc");
    event_change("codcargo");
    event_change("codsetor");
}

function event_click_table(){
    AGEN_GRID.click_table = ()=>{
        const clickedCell = event.target.closest('td');
        if (clickedCell && clickedCell.cellIndex === 0) return;

        const valoresLinha = AGEN_GRID.getRowNode(event.target.closest('tr'));

        controlaTela("modal");

        preencherDadosModal(valoresLinha);
        
        carregaGridFuncionarios();

        DMFDiv.fullScream = true;
        DMFDiv.openModal("dmodalf_agenda");
        DMFDiv.fullScream = false;
    };
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
            carregarGridAgendamentos();
        });        
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Operar Agendamentos - " + form("sacao").innerText;
            
            controlaTela("modal");

            getOptionsMotivo("1");
            carregaGridFuncionarios();

            DMFDiv.fullScream = true;
            DMFDiv.openModal("dmodalf_agenda");
            DMFDiv.fullScream = false;
        });
    }
    if(obj == 'bagendar'){
        form(obj).addEventListener("click", function () {
            if(!confirm("Deseja mesmo adicionar esse agendamento?")) return;

            criarListaFunc();
            DMFDiv.closeModal();
        });
    } 
}

function event_change(obj){
    if(obj == "mmodulo"){
        form(obj).addEventListener("change", function(){
            //getDescricaoModulo();
        });
    }
}

function event_click_aba(){
    ABA.setAba_init(()=>{
        switch (ABA.getIndex()) {
        case 0: 
        case 1: controlaTela("inicia");
                break;
        }
    });

    ABAFILTRO.setAba_init(()=>{
        switch (ABAFILTRO.getIndex()) {
        case 0: 
        case 1:
        case 2: controlaTela("modal");
                carregaGridFuncionarios();
                break;
        }
    });
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{
        switch (CONSUL.obj) {
        case           "buscarUserName": form("ideusu").value = retorno;
                                         break;

        case     "cadastrarAgendamento": //if(data != "OK") return alert(data);
                                         form("hcodagen").value = retorno;
                                         vincularAgendamentoFunc(ACAOBUSCA.cadastrarAgendamento.listafunc);
                                         break;

        case  "vincularAgendamentoFunc": if(retorno != "OK") return alert(retorno);
                                         break;
        
        case         "getOptionsMotivo": form("mmotivo").innerHTML = "";
                                         fillSelect("mmotivo",retorno,true);
                                         form("mmotivo").value = ACAOBUSCA.getOptionsMotivo.valorinicial;
                                         break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('codfunc',         false);
        desabilitaCampo('codcargo',        false);
        desabilitaCampo('codsetor',        false);
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);

        setDisplay("binserir",   ehManutencao()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('codfunc',         true);
        desabilitaCampo('codcargo',        true);
        desabilitaCampo('codsetor',        true);
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
    }
    if(opc == "modal"){
        desabilitaCampo('mtituloagen',  !ehManutencao());
        desabilitaCampo('mdataagen',    !ehManutencao());
        desabilitaCampo('mhoraagen',    !ehManutencao());
        desabilitaCampo('mdescagen',    !ehManutencao());
        desabilitaCampo('mmotivo',      !ehManutencao());
        
        setDisplay("dmfiltrofunc", ehManutencao()?"block":"none");
        setDisplay("bagendar",     ehManutencao()?"block":"none");
    }
}

function limparTela(opc){
    if(opc === "inicia" || opc === 'novabusca'){        
        form('codfunc').value   = "0";
        form('descfunc').value  = "Todos os Funcionarios";
        form('codcargo').value  = "0";
        form('desccargo').value = "Todos os Cargos";
        form('codsetor').value  = "0";
        form('descsetor').value = "Todos os Setores";
    }
    if(opc === "inicia" || opc === "novabusca"){
        AGEN_GRID.clearGrid();
    }
    if(opc === "modal"){
        form('hcodagen').value     = '';
        form('mtituloagen').value  = "";
        form('mdataagen').value    = "";
        form('mhoraagen').value    = "";
        form('mdescagen').value    = "";
        form('mmotivo').value      = "0";
        form('mfuncionario').value = "";

        FUNC_GRID.clearGrid();
    }
}

function preencherDadosModal(valores){
    form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
    form("stitulo").innerText = "Cadastrar Agendamento - " + form("sacao").innerText;

    //buscarRoleAcess(valores[6]);
    
    form("hcodagen").value    = valores[0];
    form("mtituloagen").value = valores[1];
    form("mdescagen").value   = valores[2];
    form("mdataagen").value   = valores[4];
    form("mhoraagen").value   = valores[5];
    getOptionsMotivo(valores[7]);
}

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function criarListaFunc(){
    let listafunc = [];

    for(var i = 0; i < FUNC_GRID.getTableNode().length; i++){
        const checkbox = FUNC_GRID.getTableNode()[i].childNodes[0].childNodes[0];

        if(!checkbox.checked) continue

        const idfunc = FUNC_GRID.getTableNode()[i].childNodes[1].innerText;

        listafunc.push({id: idfunc});
    }

    cadastrarAgendamento(listafunc);
}

function cadastrarAgendamento(listafunc) {
    ACAOBUSCA.cadastrarAgendamento = {
        listafunc: listafunc
    };

    const agendamento = { codagenda:  form("hcodagen").value,
                          titulo:     form("mtituloagen").value,
                          descricao:  form("mdescagen").value,
                          motivo:     form('mmotivo').value,
                          datagen:    form('mdataagen').value,
                          horagen:    form('mhoraagen').value,
                          ideusu:     form('ideusu').value};

    CONSUL.consultar("cadastrarAgendamento",`/opr001/cadastrarAgendamento`,"POST","",{body: agendamento});
}

function vincularAgendamentoFunc(listafunc) {
    var error = false;
    listafunc.forEach(element => {
        const agendfunc = { codfunc:   element.id,
                            codagenda: form("hcodagen").value,
                            ideusu:    form('ideusu').value};

        CONSUL.consultar("vincularAgendamentoFunc",`/opr001/vincularAgendamentoFunc`,"POST","",{body: agendfunc})
        .then(data =>{
            error = data != "OK";
        });
    });

    if(!error){
        alert("Agendamento cadastrado com sucesso!");
        form("bnovabusca").click();
        form("bbuscar").click();

        DMFDiv.closeModal();
    }
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`);
}

function getOptionsMotivo(valorinicial){
    ACAOBUSCA.getOptionsMotivo = {
        valorinicial: valorinicial
    };

    CONSUL.consultar("getOptionsMotivo", `/opr001/getOptionsMotivo`);
}

function carregaGridFuncionarios(){
    FUNC_GRID.carregaGrid(`/opr001/carregarGridFucnionarios?codagend=${form("hcodagen").value}&nomefunc=${form("mfuncionario").value}&acao=${form("sacao").innerText}`,"","");
}

function carregarGridAgendamentos(){
    AGEN_GRID.carregaGrid(`/opr001/carregarGridAgendamentos?codfunc=${form("codfunc").value}&codcargo=${form("codcargo").value}&codsetor=${form("codsetor").value}`,"","");
}