/* 
    Dev: Lucas Leocadio de Souza
    Data: 22/03/25
    IM: 00008
*/
window.addEventListener("load", function () {    
    iniciarEventos();
});

var DMFDiv,CONSUL,PGA_GRID;

function iniciarEventos() {
    elementsForm_init();

    PGA_GRID               = new GridForm_init();
    PGA_GRID.id            = "tabela_boletos";
    PGA_GRID.columnName    = "idcliente,nomecliente,doc,idcontrato,idFatura,valor,datenc,tipopga,situacao,numdoc,codconta";
    PGA_GRID.columnLabel   = "Cód. Cliente,Cliente,Documento,Cod. Contrato,Cód. Fatura,Valor,Vencimento,Tipo";
    PGA_GRID.columnWidth   = "10,20,14,10,10,12,12,12";
    PGA_GRID.columnAlign   = "c,e,c,c,c,d,c,c";
    PGA_GRID.mousehouve    = false;
    PGA_GRID.destacarclick = false;
    PGA_GRID.createGrid();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_geracaoboleto";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit();

    buscarUserName();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("bclose");
    event_click("blimpar");
    event_click("binserir");
    event_click("boletolink");

    controlaTela("inicia");
}


function event_click(obj) {
    if(obj == "bnovabusca"){
        form(obj).addEventListener("click", function () {
            controlaTela("novabusca");
        });
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("buscar");
            carregaGrid();
        });        
    }
    if(obj == "boletolink"){
        form(obj).addEventListener("click", ()=>{
            verBoleto(form("hfatura").value);
        })
    }
}

function event_click_table(obj,row){
    switch (obj) {
    case PGA_GRID: const valoresLinha = PGA_GRID.getRowNode(row);
                   controlaTela("modal");
        
                   preencherDadosModal(valoresLinha)
                   DMFDiv.openModal("dmodalf_geracaoboleto");
    };
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{
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
                            link.download = `boleto_${codfatura}.pdf`;
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
        desabilitaCampo("codcliente",       false);
        desabilitaCampo('bnovabusca',       true);
        desabilitaCampo('bbuscar',          false);
    }
    if(opc == "buscar"){
        desabilitaCampo("codboleto",        true);
        desabilitaCampo("codconvenio",      true);
        desabilitaCampo("codcliente",       true);
        desabilitaCampo('bnovabusca',       false);
        desabilitaCampo('bbuscar',          true);
    }
}

function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){
        form('codboleto').value    = "";
        form('codconvenio').value  = "";
        form('codcliente').value   = "";
    }
    if(opc == "modal"){
        form("mdescfatura").innerText    = "";
        form("mcodcliente").value        = "";
        form("mdescpagamento").innerText = "";
        form("mbenef").value             = "";
        form("mdocumento").value         = "";
        form("mcodcontrato").value       = "";
        form("mnmrboleto").value         = "";
        form("mvlr").value               = "";
        form("mdescsituacao").innerText  = "";
        form("mdatavenc").value          = "";
    }
} 

function preencherDadosModal(valoresLinha){
    form("sacao").innerText   = "Consultando";
    form("stitulo").innerText = form("sacao").innerText + " o Contrato " + valoresLinha[3] + " - " + valoresLinha[7] + " " + valoresLinha[9];

    form("hfatura").value            = valoresLinha[4];
    form("mdescpagamento").innerText = valoresLinha[10] + " - " + valoresLinha[9] + " - " + valoresLinha[7]
    form("mdescfatura").innerText    = valoresLinha[4];
    form("mcodcliente").value        = valoresLinha[0];
    form("mbenef").value             = valoresLinha[1];
    form("mdocumento").value         = valoresLinha[2];
    form("mcodcontrato").value       = valoresLinha[3];
    form("mnmrboleto").value         = valoresLinha[9];
    form("mvlr").value               = valoresLinha[5];
    form("mdescsituacao").innerText  = valoresLinha[8].replace("_"," ");
    form("mdatavenc").value          = valoresLinha[6];
}

function getDescCliente(codigo, retorno){
    // fetch(`/cliente/${form(codigo).value}/findNomeClienteById`,{
    //     method: "GET",
    //     headers: {"Content-Type":"application/json"}
    // })
    // .then(response => {return response.text()})
    // .then(data => { return data })
    // .catch(error => alert(error.message))
}

function getDescCorretor(obj, retorno){
    // fetch(`/contrato/${form(obj).value}/getNomeByIdeusu`,{
    //     method: "GET",
    //     headers: {"Content-type":"application/json"}
    // })
    // .then(response => { return response.text()})
    // .then(data => {return data })
    // .catch(error => alert(error.message))
}

// function registrarFatura(){
//     const corpo = {  "id"              : form("mfatura").value,
//                      "tipo"            : "RECEITA",
//                      "situacao"        : "NAO_PAGA",
//                      "valor"           : form("mvlr").value,
//                      "data_vencimento" : form("mdatavenc").value
//                   }

//     CONSUL.consultar("registrarFatura", `/faturas/registrarFatura/${form("mcodcliente").value}`,"POST","", {body: corpo});

//     // fetch(`/faturas/registrarFatura/${form("mcodcliente").value}`, {
//     //     method:  "POST",
//     //     headers: {"Content-Type":"application/json"},
//     //     body:   JSON.stringify(json)
//     // })
//     // .then(response => {return response.json()})
//     // .then(data => {})
//     // .catch(error => alert(error.message))
// }

function registrarBoleto(){
    // CONSUL.consultar("registrarBoleto",`/faturas/registrarBoleto/${form("mfatura").value}`,"POST","",{body: aplicacao})
    // .then(data =>{
    //     //form("ideusu").value = data
    // });
}

function verBoleto(codfatura){
    CONSUL.consultar("verBoleto",`/faturas/${codfatura}/boleto/pdf`, "GET", { "Accept": "application/pdf" }, { responseType: 'arraybuffer' })
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function carregaGrid(){
    PGA_GRID.carregaGrid(`/pag001/buscarFaturaCliente`);
}

// function downloadPDF(base64, filename) {
//     const link = document.createElement('a');
//     link.href = `data:application/pdf;base64,${base64}`;
//     link.download = filename;
//     link.click();
// }

// function mostrarBoletoEmIframe(faturaId) {
//     const iframe = document.createElement('iframe');
//     iframe.src = `/faturas/${faturaId}/boleto/pdf`;
//     iframe.style.width = '100%';
//     iframe.style.height = '100vh';
//     iframe.style.border = 'none';
    
//     // Abre nova janela e adiciona o iframe
//     const novaJanela = window.open('', '_blank');
//     novaJanela.document.body.appendChild(iframe);
//     novaJanela.document.title = 'Boleto - ' + faturaId;
//   }