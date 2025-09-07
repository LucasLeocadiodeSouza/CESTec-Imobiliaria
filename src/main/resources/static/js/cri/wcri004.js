/* 
    Dev: Lucas Leocadio de Souza
    Data: 04/03/25
    IM: 00013
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

var ABA,DMFDiv,CONSUL,CONTRATOS_GRID;
const ACAOBUSCA = {};

function iniciarEventos() {
    elementsForm_init();

    CONTRATOS_GRID               = new GridForm_init();
    CONTRATOS_GRID.id            = "tabela_contrato";
    CONTRATOS_GRID.columnName    = "codcontrato,codcliente,nomeCliente,codproprietario,nomeProp,codimovel,tipo,negociacao,preco,_datinicio,_datfinal,_valor,_endereco_bairro,_codcorretor,_codtipo,_nomevendedor,_situacao";
    CONTRATOS_GRID.columnLabel   = "Contrato,Cod. Cli.,Nome Cli.,Cod. Prop.,Nome Pro.,Cod. Imov.,Tipo,Contrato,Valor (R$)";
    CONTRATOS_GRID.columnWidth   = "10,10,15,10,15,10,10,10,10";
    CONTRATOS_GRID.columnAlign   = "c,c,eoe,c,eoe,c,c,c,d";
    CONTRATOS_GRID.mousehouve    = true;
    CONTRATOS_GRID.destacarclick = true;
    CONTRATOS_GRID.createGrid();

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
    filaFetchInit();

    event_selected_init("codproprietario,codcliente,mcodcliente,mcodprop,mvlrnegociado,mvendedor");
    inputOnlyNumber('codcliente,codproprietario,mcodcliente,mcodprop,mvlrnegociado');

    controlaTela("inicia");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("binserir");
    event_click("bcadastro");
    event_click("bcancela");
    
    event_change("msimovel");

    CONSUL.filterChange('codcliente','',`/gen/getNomeCliente`,['codcli:codcliente'],'desccliente');
    CONSUL.filterChange('mcodcliente','',`/gen/getNomeCliente`,['codcli:mcodcliente'],'mdesccliente');
    CONSUL.filterChange('codproprietario','',`/gen/getNomeProp`,['codprop:codproprietario'],'descproprietario');
    CONSUL.filterChange('mcodprop','mcodprop',`/gen/getNomeProp`,['codprop:mcodprop'],'mdescprop');
    CONSUL.filterChange('mvendedor','',`/gen/getNomeByIdeusu`,['ideusu:mvendedor'],'mnome');
}

function event_click_table(obj,row){
    switch (obj) {
    case CONTRATOS_GRID: const valoresLinha = CONTRATOS_GRID.getRowNode(row);
                         form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
                         form("stitulo").innerText = "Cadastro de Contrato - " + form("sacao").innerText;
                         
                         form("hmsitcontrato").value = valoresLinha[16];

                         controlaTela("modal");
                         preencherModal(valoresLinha);

                         DMFDiv.openModal("dmodalf_contrato");
                         break;
    }
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case "bnovabusca": controlaTela("novabusca");
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case    "bbuscar": controlaTela("buscar");
                               buscarContratoGrid();
                               break;

            case   "binserir": form("sacao").innerText   = "Inserindo";
                               form("stitulo").innerText = "Cadastro de Contrato - " + form("sacao").innerText;
                               
                               form('hmsitcontrato').value = "";
                               controlaTela("modal");

                               getOptionsImovel(form('mcodprop').value, '0');

                               DMFDiv.openModal("dmodalf_contrato");
                               break;

            case "bcadastro": inserirAlterarContrato();
                              break;

            case   "bcancela": cancelarContrato();
                               break;
        }
    });
}

function event_change(obj){
    form(obj).addEventListener("change", function(){
        switch (obj) {
            case "msimovel": getTipoImovel();
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
    CONSUL.filaFetch = (retorno,error)=>{
        if(error){
            switch (CONSUL.obj) {
            case   "mcodprop": form("mcodprop").value    = "";
                               form("mdescprop").value   = "";
                               form("mtpimovel").value   = "";
                               form("mloc").value        = "";
                               form("mtpcontrato").value = "";
                               form("mvlrimovel").value  = "";
                               getOptionsImovel(form('mcodprop').value, "0");
                               break;
            }
            return;
        }

        switch (CONSUL.obj) {
        case            "buscarUserName": form("ideusu").value = retorno;
                                          break;

        case    "inserirAlterarContrato": alert("Contrato adicionado com sucesso!","O Contrato foi registrado com sucesso e ja esta disponivel para analise!",4);
                                          DMFDiv.closeModal();
                                          break;

        case                  "mcodprop": getOptionsImovel(form('mcodprop').value, "0");
                                          form("mtpimovel").value   = "";
                                          form("mloc").value        = "";
                                          form("mtpcontrato").value = "";
                                          form("mvlrimovel").value  = "";
                                          break;

        case          "getOptionsImovel": fillSelect("msimovel",retorno,true);
                                          form('msimovel').childNodes[0].disabled = true;
                                          form('msimovel').value  = ACAOBUSCA.getOptionsImovel.valorInicial;
                                          break;

        case             "getTipoImovel": form("mtpimovel").value = retorno;
                                          getEnderecoImovel();
                                          break;

        case         "getEnderecoImovel": form("mloc").value = retorno; 
                                          getTipoContratoImovel();
                                          break;

        case     "getTipoContratoImovel": form("mtpcontrato").value = retorno;
                                          getValorImovel();
                                          break;

        case            "getValorImovel": form("mvlrimovel").value    = retorno;
                                          form("mvlrnegociado").value = "";
                                          getProprietarioByImovel();
                                          break;

        case   "getProprietarioByImovel": form("mcodprop").value = retorno;
                                          getNomePropModal();
                                          break;

        case          "getNomePropModal": form("mdescprop").value = retorno;
                                          break;

        case          "cancelarContrato": alert("Sucesso!", "Cadastro cancelado com sucesso!", 4);

                                          buscarContratoGrid();
                                          DMFDiv.closeModal();
                                          break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codproprietario', false);
        desabilitaCampo('codcliente',      false);
        desabilitaCampo('raluguel',        false);
        desabilitaCampo('rvenda',          false);

        setDisplay("binserir", ehConsulta()?"none":"flex");
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
        desabilitaCampo('codproprietario', true);
        desabilitaCampo('codcliente',      true);
        desabilitaCampo('raluguel',        true);
        desabilitaCampo('rvenda',          true);
    }
    if(opc == "modal"){
        desabilitaCampo('mcodcliente',   ehAlterando() || ehConsulta());
        desabilitaCampo('mcodprop',      ehAlterando() || ehConsulta());
        desabilitaCampo('msimovel',      ehAlterando() || ehConsulta());
        desabilitaCampo('mvlrnegociado', ehConsulta() || (!ehSituacaoAberta() && ehAlterando()));
        desabilitaCampo('mvendedor',     ehConsulta() || (!ehSituacaoAberta() && ehAlterando()));
        desabilitaCampo('mperiodoini',   ehConsulta() || (!ehSituacaoAberta() && ehAlterando()));
        desabilitaCampo('mperiodofin',   ehConsulta() || (!ehSituacaoAberta() && ehAlterando()));
        desabilitaCampo('bcadastro',     ehConsulta() || (!ehSituacaoAberta() && ehAlterando()));
        desabilitaCampo('bcancela',      !ehManutencao() && !ehSituacaoAberta() && !ehAlterando());

        setDisplay("dmcontrato", ehInserindo()?"none":"flex");
        setDisplay("bcadastro",  ehConsulta() || (!ehSituacaoAberta() && ehAlterando())?"none":"flex");
        setDisplay("bcancela",   ehManutencao() && ehSituacaoAberta() && ehAlterando()?"block":"none");
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){        
        form('codproprietario').value  = "0";
        form('descproprietario').value = "";
        form('codcliente').value       = "0";
        form('desccliente').value      = "";
        setRadioValue('rimovel','0'); 
    }
    if(opc === "inicia" || opc === "novabusca"){
        CONTRATOS_GRID.clearGrid();
    }
    if(opc == "modal"){
        form('hmcodcontrato').value = "0";
        form('mcodcliente').value   = "0";
        form('mdesccliente').value  = "";
        form('mcodprop').value      = "0";
        form('mdescprop').value     = "";
        form('msimovel').value      = "0";
        form('mtpimovel').value     = "";
        form('mloc').value          = "";
        form('mtpcontrato').value   = "";
        form('mvlrimovel').value    = "";
        form('mvlrnegociado').value = "";
        form('mvendedor').value     = "";
        form('mnome').value         = "";
        form("mperiodoini").value   = "";
        form("mperiodofin").value   = "";
    }
} 

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function ehInserindo(){
    return form("sacao").innerText == "Inserindo";
}

function ehAlterando(){
    return form("sacao").innerText == "Alterando";
}

function ehSituacaoAberta(){
    return form("hmsitcontrato").value == "1";
}

function preencherModal(valoresLinha){                
    form('mcodprop').value      = valoresLinha[3];
    
    form("mcodcontrato").value  = valoresLinha[0];
    form("hmcodcontrato").value = valoresLinha[0];
    form('mcodcliente').value   = valoresLinha[1];
    form('mdesccliente').value  = valoresLinha[2];
    form('mdescprop').value     = valoresLinha[4];
    form('mtpimovel').value     = valoresLinha[6];
    form('mloc').value          = valoresLinha[12];
    form('mtpcontrato').value   = valoresLinha[7];
    form('mvlrimovel').value    = valoresLinha[8];
    form('mvlrnegociado').value = valoresLinha[11];
    form("mperiodoini").value   = valoresLinha[9];
    form("mperiodofin").value   = valoresLinha[10];
    form("mvendedor").value     = valoresLinha[13];
    form("mnome").value         = valoresLinha[15];
    
    getOptionsImovel("0", valoresLinha[5]);
}

function buscarContratoGrid(){
    CONTRATOS_GRID.carregaGrid(`/cri004/buscarContratoGrid`,['codprop:codproprietario',
                                                             'codcliente:codcliente',
                                                             'tipimovel=' + getRadioValue("rimovel")]);
}

function inserirAlterarContrato(){
    CONSUL.consultar("inserirAlterarContrato",`/cri004/inserirAlterarContrato`,["codcontrato:hmcodcontrato",
                                                                                "codcliente:mcodcliente",
                                                                                "codprop:mcodprop",
                                                                                "codimovel:msimovel",
                                                                                "vlrnegoc:mvlrnegociado",
                                                                                "ideusucorretor:mvendedor",
                                                                                "datini:mperiodoini",
                                                                                "datfim:mperiodofin"],
                                                                                "POST");
}

function cancelarContrato(){
    CONSUL.consultar("cancelarContrato",`/cri004/cancelarContrato`,["codcontrato:hmcodcontrato",
                                                                    "codimovel:msimovel"],
                                                                    "POST");
}


function getTipoImovel(){
    CONSUL.consultar("getTipoImovel",`/cri004/getBuscaTipoImovel`,["codimovel:msimovel"]);
} 

function getEnderecoImovel(){
    CONSUL.consultar("getEnderecoImovel",`/gen/getEnderecoImovel`, ["codimovel:msimovel"]);
}

function getTipoContratoImovel(){
    CONSUL.consultar("getTipoContratoImovel",`/cri004/getTipoContratoImovel`,["codimovel:msimovel"]);
} 

function getValorImovel(){
    CONSUL.consultar("getValorImovel",`/cri004/getValorImovel`,["codimovel:msimovel"]);
}

function getProprietarioByImovel(){
    CONSUL.consultar("getProprietarioByImovel",`/cri004/getProprietarioByImovel`,["codimovel:msimovel"]);
}

function getNomePropModal(){
    CONSUL.consultar("getNomePropModal",`/gen/getNomeProp`,['codprop:mcodprop']);
}

function getOptionsImovel(codprop, valorInicial){
    ACAOBUSCA.getOptionsImovel = {
        valorInicial:   valorInicial
    };

    CONSUL.consultar("getOptionsImovel",`/cri004/getOptionsImovel`,["codprop=" + codprop]);
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`);
}