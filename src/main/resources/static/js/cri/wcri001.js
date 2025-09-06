/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00007
*/
window.addEventListener("load", function () {
    wcri001_init();
});

var IMOVEIS_GRID;
var DMFDiv, ABA, CONSUL;

const ACAOBUSCA = {};

function wcri001_init(){
    elementsForm_init();

    IMOVEIS_GRID               = new GridForm_init();
    IMOVEIS_GRID.id            = "tabela_CTO";
    IMOVEIS_GRID.columnName    = "codimovel,codproprietario,nome,tipo,status,preco,negociacao,endereco,area,quartos,vlrcondominio,datinicontrato,codtipo,codnegoc";
    IMOVEIS_GRID.columnLabel   = "Cód.Imovel,Cod. Prop.,Nome,Tipo,Situação,Valor (R$),Contrato";
    IMOVEIS_GRID.columnWidth   = "7,8,33,17,16,8,10";
    IMOVEIS_GRID.columnAlign   = "c,c,e,c,c,d,c";
    IMOVEIS_GRID.mousehouve    = false;
    IMOVEIS_GRID.destacarclick = true;
    IMOVEIS_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutenção";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_contrato";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit()

    iniciarEventos();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_selected_init("codcontrato,codproprietario");
    inputOnlyNumber('codcontrato,codproprietario,mcodproprietario,mmetrosquad,mquartos,mcondominio,mvlr');

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");

    CONSUL.filterChange('codproprietario','',`/gen/getNomeProp`,['codprop:codproprietario'],'descproprietario');
    CONSUL.filterChange('mcodproprietario','',`/gen/getNomeProp`,['codprop:mcodproprietario'],'mdescproprietario');

}

function event_click_table(obj,row,e){
    switch (obj) {
    case IMOVEIS_GRID: if (e.target === row.cells[0]) return;
                       const valoresLinha = IMOVEIS_GRID.getRowNode(row);

                       form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
                       form("stitulo").innerText = "Cadastro de Imóvel - " + form("sacao").innerText;

                       controlaTela("modal");
                       preencherDadosModal(valoresLinha);
                       DMFDiv.openModal("dmodalf_contrato");
                       break;
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

            carregaGridImoveis();
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
            form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;
            
            controlaTela("modal");
            DMFDiv.openModal("dmodalf_contrato");
        });
    }
    if(obj == 'bcadastro'){
        form(obj).addEventListener("click", function () {
            adicionarContratoImovel();
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
            case   "adicionarContratoImovel": alert("Ocorreu um erro ao cadastrar o Contrato", retorno, 4);
                                              break;
            }
            return;
        }

        switch (CONSUL.obj) {
        case            "buscarUserName": form("ideusu").value = retorno;
                                          break;

        case   "adicionarContratoImovel": alert("Sucesso!", "Imóvel adicionado com sucesso!", 4);
                                          DMFDiv.closeModal();
                                          break;

        case      "getOptionsTpContrato": fillSelect("mstpcontrato",retorno,true);
                                          form('mstpcontrato').childNodes[0].disabled = true;
                                          form('mstpcontrato').value  = ACAOBUSCA.getOptionsTpContrato.valorinicial;
                                          break;

        case        "getOptionsTpImovel": fillSelect("mstpimovel",retorno,true);
                                          form('mstpimovel').childNodes[0].disabled = true;
                                          form('mstpimovel').value  = ACAOBUSCA.getOptionsTpImovel.valorinicial;

                                          if(ACAOBUSCA.getOptionsTpImovel.origem == 'limpaTela') getOptionsTpContrato('0');
                                          break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codcontrato',     false);
        desabilitaCampo('codproprietario', false);
        desabilitaCampo('descproprietario', true);
        desabilitaCampo('raluguel',        false);
        desabilitaCampo('rvenda',          false);
        desabilitaCampo('rambas',          false);

        setDisplay("binserir", ehManutencao()?"block":"none");
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
        desabilitaCampo('codcontrato',     true);
        desabilitaCampo('codproprietario', true);
        desabilitaCampo('descproprietario', true);
        desabilitaCampo('raluguel',        true);
        desabilitaCampo('rvenda',          true);
        desabilitaCampo('rambas',          true);
    }
    if(opc == "modal"){
        desabilitaCampo('mcodproprietario',  !ehInserindo());
        desabilitaCampo('mstpimovel',        ehConsulta());
        desabilitaCampo('mstpcontrato',      ehConsulta());
        desabilitaCampo('mmetrosquad',       ehConsulta());
        desabilitaCampo('mquartos',          ehConsulta());
        desabilitaCampo('mcondominio',       ehConsulta());
        desabilitaCampo('mloc',              ehConsulta());
        desabilitaCampo('mvlr',              ehConsulta());
        desabilitaCampo('mperiodoini',       !ehInserindo());
        desabilitaCampo('bcadastro',         ehConsulta());
    }
}


function limparTela(opc){
    if(opc === "inicia"){
        form('codcontrato').value      = "0";
        form('codproprietario').value  = "0";
        form('descproprietario').value = "Todos os Proprietarios";
        setRadioValue('rimovel','0'); 
    }
    if(opc === "inicia" || opc === "novabusca"){
        IMOVEIS_GRID.clearGrid();
    }
    if(opc === "modal"){
        getOptionsTpImovel('0', 'limpaTela');

        form('mcodproprietario').value     = "";
        form('mdescproprietario').value    = "";
        form('mmetrosquad').value          = "";
        form('mquartos').value             = "";
        form('mcondominio').value          = "";
        form('mloc').value                 = "";
        form('mvlr').value                 = "";
        form('mperiodoini').value          = "";
    }
}

function preencherDadosModal(valores){
    getOptionsTpContrato(valores[13]);
    getOptionsTpImovel(valores[12], "table");

    form("mcodimovel").value        = valores[0];
    form("mcodproprietario").value  = valores[1];
    form("mdescproprietario").value = valores[2];
    form("msituacao").value         = valores[4];
    form("mvlr").value          	= valores[5];
    form("mloc").value     	        = valores[7];
    form("mmetrosquad").value     	= valores[8];
    form("mquartos").value       	= valores[9];
    form("mcondominio").value     	= valores[10];
    form("mperiodoini").value     	= valores[11];
}

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function adicionarContratoImovel() {
    const imovel = { codimovel:         form("mcodimovel").value,
                     tipo:              form("mstpimovel").value,
                     negociacao:        form('mstpcontrato').value,
                     quartos:           form('mquartos').value,
                     area:              parseFloat(form("mmetrosquad").value),
                     vlrcondominio:     parseFloat(form("mcondominio").value),
                     preco:             parseFloat(form("mvlr").value),
                     status:            1,
                     endereco:          form("mloc").value,
                     periodo:           form("mperiodoini").value, 
                     datiregistro:      new Date().toISOString().split('T')[0],
                     datinicontrato:    form("mperiodoini").value};

    CONSUL.consultar("adicionarContratoImovel",`/cri001/salvarImovel`,['codprop:mcodproprietario'],"POST","",{body: imovel});
}

function getOptionsTpContrato(valorinicial) {
    ACAOBUSCA.getOptionsTpContrato = {
        valorinicial: valorinicial
    };

    CONSUL.consultar("getOptionsTpContrato",`/cri001/getOptionsTpContrato`);
}

function getOptionsTpImovel(valorinicial, origem) {
    ACAOBUSCA.getOptionsTpImovel = {
        valorinicial: valorinicial,
        origem:     origem
    };

    CONSUL.consultar("getOptionsTpImovel",`/cri001/getOptionsTpImovel`);
}

function carregaGridImoveis(){
    IMOVEIS_GRID.carregaGrid(`/cri001/buscarImoveis`,["codcontrato:codcontrato",
                                                      "codprop:codproprietario",
                                                      "tipimovel=" + getRadioValue("rimovel")]);
}

function ehInserindo(){
    return form("sacao").innerText == "Inserindo";
}