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
//var GEOCODER;

const ACAOBUSCA = {};

function wcri001_init(){
    elementsForm_init();

    IMOVEIS_GRID               = new GridForm_init();
    IMOVEIS_GRID.id            = "tabela_CTO";
    IMOVEIS_GRID.columnName    = "codimovel,codproprietario,nome,tipo,status,preco,negociacao,endereco_bairro,endereco_numero,endereco_postal,endereco_cidade,endereco_estado,endereco_rua,area,quartos,vlrcondominio,datinicontrato,codtipo,codnegoc,codativo,banheiros";
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

    event_selected_init("codcontrato,codproprietario,mcodproprietario,mmetrosquad,mquartos,mbanheiro,mcondominio,mvlr,mbairro,mnmr,mrua,mcepini,mcepdigito,mcidade,muf");
    inputOnlyNumber('codcontrato,codproprietario,mcodproprietario,mmetrosquad,mquartos,mbanheiro,mcondominio,mvlr');

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");
    event_click("bcancela");

    CONSUL.filterChange('codproprietario','',`/gen/getNomeProp`,['codprop:codproprietario'],'descproprietario');
    CONSUL.filterChange('mcodproprietario','',`/gen/getNomeProp`,['codprop:mcodproprietario'],'mdescproprietario');

    //GEOCODER = new google.maps.Geocoder();
}

function event_click_table(obj,row,e){
    switch (obj) {
    case IMOVEIS_GRID: if (e.target === row.cells[0]) return;
                       const valoresLinha = IMOVEIS_GRID.getRowNode(row);
                       form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
                       form("stitulo").innerText = "Cadastro de Imóvel - " + form("sacao").innerText;
                       
                       form("msituacao").value = valoresLinha[14];

                       controlaTela("modal");

                       preencherDadosModal(valoresLinha);
                       DMFDiv.openModal("dmodalf_contrato");
                       break;
    };
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case "bnovabusca": controlaTela("novabusca");
                               break;

            case    "bbuscar": controlaTela("buscar");

                               carregaGridImoveis();
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case   "binserir": form("sacao").innerText   = "Inserindo";
                               form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;
                               
                               form("msituacao").value = "";
                               controlaTela("modal");
                               DMFDiv.openModal("dmodalf_contrato");
                               break;

            case  "bcadastro": adicionarContratoImovel();
                               break;

            case   "bcancela": inativarImovel();
                               break;
        
        }
    });
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
        // if(error){
        //     switch (CONSUL.obj) {
        //     case   "": break;
        //     }
        //     return;
        // }

        switch (CONSUL.obj) {
        case            "buscarUserName": form("ideusu").value = retorno;
                                          break;

        case   "adicionarContratoImovel": alert("Sucesso!", "Imóvel adicionado com sucesso!", 4);

                                          carregaGridImoveis();
                                          DMFDiv.closeModal();
                                          break;

        case            "inativarImovel": alert("Sucesso!", "Imóvel desativado com sucesso!", 4);

                                          carregaGridImoveis();
                                          DMFDiv.closeModal();
                                          break;

        case      "getOptionsTpContrato": fillSelect("mstpcontrato",retorno,true);
                                          form('mstpcontrato').childNodes[0].disabled = true;
                                          form('mstpcontrato').value  = ACAOBUSCA.getOptionsTpContrato.valorinicial;
                                          break;

        case        "getOptionsTpImovel": fillSelect("mstpimovel",retorno,true);
                                          form('mstpimovel').childNodes[0].disabled = true;
                                          form('mstpimovel').value  = ACAOBUSCA.setOptionImovelContrato.valorImovel;

                                          getOptionsTpContrato(ACAOBUSCA.setOptionImovelContrato.valorContrato);
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

        setDisplay("binserir", ehManutencao()?"flex":"none");
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
        desabilitaCampo('mcodproprietario', !ehInserindo());
        desabilitaCampo('mstpimovel',      (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mstpcontrato',    (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mmetrosquad',     (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mquartos',        (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mbanheiro',       (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mcondominio',     (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mbairro',         (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mnmr',            (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mrua',            (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mcepini',         (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mcepdigito',      (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mcidade',         (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('muf',             (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mvlr',            (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());
        desabilitaCampo('mperiodoini',      !ehInserindo());
        desabilitaCampo('bcadastro',       (!ehImovelAtivo() && !ehInserindo()) || ehConsulta());
        desabilitaCampo('bcancela',        (!ehImovelAtivo() && !ehInserindo()) ||  ehConsulta());

        setDisplay("bcadastro", (ehImovelAtivo() && !ehConsulta()) || ehInserindo()?"flex":"none");
        setDisplay("bcancela",  ehImovelAtivo() && !ehConsulta()?"flex":"none");
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
        setOptionImovelContrato('0', '0');

        form("mcodimovel").value           = "";
        form('mcodproprietario').value     = "";
        form('mdescproprietario').value    = "";
        form('mmetrosquad').value          = "";
        form('mquartos').value             = "";
        form('mbanheiro').value            = "";
        form('mcondominio').value          = "";
        form("mbairro").value              = "";
        form("mnmr").value                 = "";
        form("mrua").value                 = "";
        form("mcepini").value              = "";
        form("mcepdigito").value           = "";
        form("mcidade").value              = "";
        form("muf").value                  = "";
        form('mvlr').value                 = "";
        form('mperiodoini').value          = "";
    }
}

function preencherDadosModal(valores){
    setOptionImovelContrato(valores[17], valores[18]);

    form("mcodimovel").value        = valores[0];
    form("mcodproprietario").value  = valores[1];
    form("mdescproprietario").value = valores[2];
    form("msituacao").value         = valores[19];
    form("mvlr").value          	= valores[5];
    form("mbairro").value     	    = valores[7];
    form("mnmr").value     	        = valores[8];
    form("mrua").value     	        = valores[12];
    form("mcepini").value     	    = valores[9].substring(0,5);
    form("mcepdigito").value     	= valores[9].substring(6,9);
    form("mcidade").value     	    = valores[10];
    form("muf").value     	        = valores[11];
    form("mmetrosquad").value     	= valores[13];
    form("mquartos").value       	= valores[14];
    form("mcondominio").value     	= valores[15];
    form("mperiodoini").value     	= valores[16];
    form("mbanheiro").value         = valores[20];
}

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function adicionarContratoImovel() {
    const endereco = form("mrua").value + ", " + form("mnmr").value + " - " + form("mbairro").value + ", " + form("mcidade").value + " - " + form("muf").value + ", " + form("mcepini").value + "-" + form("mcepdigito").value;
    console.log(endereco);
    //const coordenadas =  getCoordenadasEndereco(endereco);

    const imovel = { codimovel:         form("mcodimovel").value,
                     tipo:              form("mstpimovel").value,
                     negociacao:        form('mstpcontrato').value,
                     quartos:           form('mquartos').value,
                     banheiros:         form('mbanheiro').value,
                     area:              parseFloat(form("mmetrosquad").value),
                     vlrcondominio:     parseFloat(form("mcondominio").value),
                     preco:             parseFloat(form("mvlr").value),
                     //latitude:          coordenadas.Lat,
                     //longitude:         coordenadas.Lng,
                     endereco_bairro:   form("mbairro").value,
                     endereco_numero:   form("mnmr").value,
                     endereco_postal:   form("mcepini").value + form("mcepdigito").value,
                     endereco_cidade:   form("mcidade").value,
                     endereco_estado:   form("muf").value,
                     endereco_rua:      form("mrua").value,
                     datinicontrato:    form("mperiodoini").value};

    CONSUL.consultar("adicionarContratoImovel",`/cri001/salvarImovel`,['codprop:mcodproprietario'],"POST","",{body: imovel});
} 

function inativarImovel() {
    CONSUL.consultar("inativarImovel",`/cri001/inativarImovel`,['codimovel:mcodimovel'],"POST");
}

function getOptionsTpContrato(valorinicial) {
    ACAOBUSCA.getOptionsTpContrato = {
        valorinicial: valorinicial
    };

    CONSUL.consultar("getOptionsTpContrato",`/cri001/getOptionsTpContrato`);
}

function setOptionImovelContrato(valorImovel, valorContrato) {
    ACAOBUSCA.setOptionImovelContrato = {
        valorImovel:   valorImovel,
        valorContrato: valorContrato
    };

    CONSUL.consultar("getOptionsTpImovel",`/cri001/getOptionsTpImovel`);
}

// function getCoordenadasEndereco(endereco){
//     console.log(endereco);

//     GEOCODER.geocode( { 'address': endereco}, function(results, status) {
//         console.log(result);
//         console.log(status);

//        if (status == 'OK') return results[0].geometry.location;
//        else alert('Não foi possível localizar as coordenadas do endereco informado. Status retornado: ' + status);
//     });

//     return;
// }

function carregaGridImoveis(){
    IMOVEIS_GRID.carregaGrid(`/cri001/buscarImoveis`,["codcontrato:codcontrato",
                                                      "codprop:codproprietario",
                                                      "tipimovel=" + getRadioValue("rimovel")]);
}

function ehInserindo(){
    return form("sacao").innerText == "Inserindo";
}

function ehImovelAtivo(){
    return form("msituacao").value == "1";
}