/* 
    Dev: Lucas Leocadio de Souza
    Data: 20/09/25
    IM: 00013
*/
window.addEventListener("load", function () {    
    iniciarEventos();
});

var DMFDiv,CONSUL,PGA_GRID, IMPRIMIR;

function iniciarEventos() {
    elementsForm_init();

    PGA_GRID               = new GridForm_init();
    PGA_GRID.id            = "tabela_boletos";
    PGA_GRID.columnName    = "idcliente,nomecliente,doc,idcontrato,idFatura,valor,datencformat,tipopga,_situacao,_numdoc,_codconta,_codimovel,_desconto,_datvenc";
    PGA_GRID.columnLabel   = "Num. Doc.,Cód. Cliente,Cliente,Documento,Cod. Contrato,Cód. Fatura,Valor,Vencimento,Tipo";
    PGA_GRID.columnWidth   = "10,10,20,10,10,10,7,12,10";
    PGA_GRID.columnAlign   = "c,c,e,c,c,c,d,c,c";
    PGA_GRID.mousehouve    = false;
    PGA_GRID.destacarclick = false;
    PGA_GRID.createGrid();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_geracaoboleto";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    IMPRIMIR = new imprimirForm_init();

    CONSUL = new consulForm_init();
    filaFetchInit();

    buscarUserName();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("boletolink");
    event_click("bimprimir");

    event_change("mcodcontrato");

    event_blur_init("mvlr,mdesconto");
    event_selected_init("mvlr,mdesconto,mcodcontrato,codconvenio,codboleto,documento");
    inputOnlyNumber('mvlr,mdesconto,mcodcontrato,codconvenio,codboleto,documento');

    event_change("mcodcontrato");

    event_blur_init("mvlr,mdesconto");
    event_selected_init("mvlr,mdesconto,mcodcontrato,codconvenio,codboleto,documento");
    inputOnlyNumber('mvlr,mdesconto,mcodcontrato,codconvenio,codboleto,documento');

    controlaTela("inicia");
}


function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case "bnovabusca": controlaTela("novabusca");
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case "bimprimir": IMPRIMIR.imprimirGrid(form(PGA_GRID.id).innerHTML, "Pagamentos Cadastrados", "Pagamentos cadastrados no sistema");
                              break;

            case    "bbuscar": controlaTela("buscar");
                               carregaGrid();
                               break;

            case "boletolink": verBoleto(form("hfatura").value);
                               break;
        }
    });
}

function event_click_table(obj,row){
    switch (obj) {
    case PGA_GRID: const valoresLinha = PGA_GRID.getRowNode(row);

                   form("sacao").innerText   = "Consultando";
                   form("stitulo").innerText = form("sacao").innerText + " o Contrato " + valoresLinha[4] + " - " + valoresLinha[8] + " " + valoresLinha[0];

                   controlaTela("modal");
        
                   preencherDadosModal(valoresLinha)
                   DMFDiv.openModal("dmodalf_geracaoboleto");
    };
}

function event_change(obj){
    form(obj).addEventListener("change", function(){
        switch (obj) {
            case "mcodcontrato": findCodClienteByContrato();
                                 break;
        }
    });
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno,erro)=>{
        if(erro) return;

        switch (CONSUL.obj) {
        case  "verBoleto": // Verificação do conteúdo
                            if (!retorno || retorno.byteLength === 0) {
                                throw new Error('O PDF retornado está vazio');
                            }

                            // Verificação da assinatura do PDF (%PDF)
                            const signature = new Uint8Array(retorno.slice(0, 4));
                            if (String.fromCharCode(...signature) !== '%PDF') {
                                throw new Error('O arquivo não é um PDF válido');
                            }

                            const blob = new Blob([retorno], { type: 'application/pdf' });
                            
                            // Cria URL temporária
                            const blobUrl = URL.createObjectURL(blob);
                            
                            const link    = document.createElement('a');
                            link.href     = blobUrl;
                            link.download = `boleto_${form("ideusu").value}.pdf`;
                            link.click();

                            // Abre em nova janela
                            // const janela = window.open(blobUrl, '_blank');

                            // Fallback se o navegador bloquear window.open()
                            // if (!janela || janela.closed || typeof janela.closed === 'undefined') {
                            //     const link = document.createElement('a');
                            //     link.href = blobUrl;
                            //     link.download = `boleto_${codfatura}.pdf`;
                            //     document.body.appendChild(link);
                            //     link.click();
                            //     document.body.removeChild(link);
                            // }
                            break;

        case "findCodClienteByContrato": form("mcodcliente").value = retorno;
                                         getNomeBeneficiario();
                                         break;

        case "getNomeBeneficiario": form("mbenef").value = retorno;
                                    findCodImovelByContrato();
                                    break;

        case "findCodImovelByContrato": form("mcodimovel").value = retorno;
                                        getDocumentoCliente();
                                        break;

        case "getDocumentoCliente": form("mdocumento").value = retorno;
                                    getValorLiberadoContrato();
                                    break;

        case "getValorLiberadoContrato": form("mvlr").value = retorno;
                                         getValorDescontoContrato();
                                         break;

        case "getValorDescontoContrato": form("mdesconto").value = retorno;
                                         break;

        case    "buscarUserName": form("ideusu").value = retorno;
                                  break;
        
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo("codboleto",        false);
        desabilitaCampo("codconvenio",      false);
        desabilitaCampo("documento",        false);
        desabilitaCampo('bnovabusca',       true);
        desabilitaCampo('bbuscar',          false);
    }
    if(opc == "buscar"){
        desabilitaCampo("codboleto",        true);
        desabilitaCampo("codconvenio",      true);
        desabilitaCampo("documento",        true);
        desabilitaCampo('bnovabusca',       false);
        desabilitaCampo('bbuscar',          true);
    }
}

function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){
        form('codboleto').value    = "";
        form('codconvenio').value  = "";
        form('documento').value    = "";
    }
    if(opc == "modal"){
        form("mdescfatura").innerText    = "";
        form("mdescpagamento").innerText = "";
        form("mcodcontrato").value       = "";
        form("mcodcliente").value        = "";
        form("mbenef").value             = "";
        form("mdocumento").value         = "";
        form("mcodimovel").value         = "";
        form("mnmrboleto").value         = "";
        form("mvlr").value               = "";
        form("mdescsituacao").innerText  = "";
        form("mdesconto").value          = "";
        form("mdatavenc").value          = "";
    }
} 

function preencherDadosModal(valoresLinha){
    form("hfatura").value            = valoresLinha[5];
    form("mdescpagamento").innerText = valoresLinha[10] + " - " + valoresLinha[0] + " - " + valoresLinha[7]
    form("mdescfatura").innerText    = valoresLinha[5];
    form("mcodcliente").value        = valoresLinha[1];
    form("mbenef").value             = valoresLinha[2];
    form("mdocumento").value         = valoresLinha[3];
    form("mcodcontrato").value       = valoresLinha[4];
    form("mnmrboleto").value         = valoresLinha[0];
    form("mvlr").value               = valoresLinha[6];
    form("mdescsituacao").innerText  = valoresLinha[9].replace("_"," ");
    form("mdatavenc").value          = valoresLinha[13];
    form("mcodimovel").value         = valoresLinha[11];
    form("mdesconto").value          = valoresLinha[12];
}

function verBoleto(codfatura){
    CONSUL.consultar("verBoleto",`/faturas/${codfatura}/boleto/pdf`,[],"GET", { "Accept": "application/pdf" }, { responseType: 'arraybuffer'})
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function findCodClienteByContrato(){
    CONSUL.consultar('findCodClienteByContrato',`/gen/findCodClienteByContrato`,['codcontrato:mcodcontrato']);
} 

function getNomeBeneficiario(){
    CONSUL.consultar('getNomeBeneficiario',`/gen/getNomeCliente`,['codcli:mcodcliente']);
}

function findCodImovelByContrato(){
    CONSUL.consultar('findCodImovelByContrato',`/gen/findCodImovelByContrato`,['codcontrato:mcodcontrato']);
}

function getDocumentoCliente(){
    CONSUL.consultar('getDocumentoCliente',`/gen/getDocumentoCliente`,['codcliente:mcodcliente']);
}

function getValorLiberadoContrato(){
    CONSUL.consultar('getValorLiberadoContrato',`/pag001/getValorLiberadoContrato`,['codcontrato:mcodcontrato']);
}

function getValorDescontoContrato(){
    CONSUL.consultar('getValorDescontoContrato',`/pag001/getValorDescontoContrato`,['codcontrato:mcodcontrato']);
}

function carregaGrid(){
    PGA_GRID.carregaGrid(`/pag001/buscarFaturaCliente`);
}