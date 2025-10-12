/* 
    Dev: Lucas Leocadio de Souza
    Data: 22/03/25
    IM: 00008
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

var ABA,DMFDiv,CONSUL,CONTRATOS_GRID,LEGENDA, IMPRIMIR;

let map;
var marcadorElementImovel;

const chamadaParam = {};

function iniciarEventos() {
    elementsForm_init();

    CONTRATOS_GRID               = new GridForm_init();
    CONTRATOS_GRID.id            = "tabela_aprovacao";
    CONTRATOS_GRID.columnName    = "codcontrato,situacao,codimovel,tipo,codproprietario,nomeProp,codcliente,nomeCliente,codcorretor,nomeCorretor,periodo,preco,valor,_vlrcondominio,_area,_quartos,_documento,_endereco,_vlrliber,_observacao,_ehpessoafisica,_banheiro";
    CONTRATOS_GRID.columnLabel   = "Contrato,Situação,Cód. Imovel,Tipo Imovel,Código Prop,Proprietario,Código Cliente,Cliente,Código Corretor,Corretor,Periodo,Preço (R$),Valor negoc. (R$)";
    CONTRATOS_GRID.columnWidth   = "5,5,6,9,6,11,6,11,6,10,10,7,8";
    CONTRATOS_GRID.columnAlign   = "c,c,c,e,c,e,c,e,c,e,c,d,d";
    CONTRATOS_GRID.mousehouve    = true;
    CONTRATOS_GRID.destacarclick = true;
    CONTRATOS_GRID.gridWidth     = "2200px";
    CONTRATOS_GRID.miniModalOver = true;
    CONTRATOS_GRID.colsModalOver = "1,2,3";
    CONTRATOS_GRID.createGrid();

    LEGENDA       = new legendaForm_init();
    LEGENDA.id    = 'dlegenda';
    LEGENDA.name  = 'Aberta,Aguardando aprovação,Aprovada,Reprovada';
    LEGENDA.color = '#5a7cd0, #ffeb00, #035e00, #ff8100';
    LEGENDA.createLegenda();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Analise Contrato";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    IMPRIMIR = new imprimirForm_init();

    CARROSSEL           = new carrosselform_init();
    CARROSSEL.container = "containermodais";
    CARROSSEL.createCarrossel();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_aprovacao";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit()

    controlaTela("inicia");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("baprovar");
    event_click("breprovar");
    event_click("bimprimir");
    
    event_blur_init("mvlrlib");
    event_selected_init("mvlrlib,mobs,codproprietario,codcliente,codcorretor");
    inputOnlyNumber('codcliente,codproprietario,mvlrlib,codcorretor');

    CONSUL.filterChange('codproprietario','',`/gen/getNomeProp`,['codprop:codproprietario'],'descproprietario');
    CONSUL.filterChange('codcliente','',`/gen/getNomeCliente`,['codcli:codcliente'],'desccliente');
    CONSUL.filterChange('codcorretor','',`/gen/getNomeByCodFunc`,['codfunc:codcorretor'],'desccorretor');

    initMap();
}


function event_click(obj) {
    form(obj).addEventListener("click", ()=>{
        switch (obj) {
            case "bnovabusca": controlaTela("novabusca");
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case    "bbuscar": controlaTela("buscar");
                               buscarContratoAprovacao();
                               break;

            case   "baprovar": if(!confirm("Deseja mesmo Aprovar esse contrato?")) return;
                               aprovarReprovarContrato("aprovar");
                               break;

            case  "breprovar": if(!confirm("Deseja mesmo Reprovar esse contrato?")) return;
                               aprovarReprovarContrato("reprovar");
                               break;

            case  "bimprimir": IMPRIMIR.imprimirGrid(form(CONTRATOS_GRID.id).innerHTML, "Contratos Cadastrados", "Contratos cadastrados no sistema");
                               break;
        }
    });
}

function event_click_table(obj,row){
    switch (obj) {
    case CONTRATOS_GRID: const valoresLinha = CONTRATOS_GRID.getRowNode(row);
                         form("sacao").innerText   = ehConsulta()?"Consultando":"Analisando";
                         form("stitulo").innerText = form("sacao").innerText + " Contrato - " + form("sacao").innerText;
                         controlaTela("modal");

                         puxarFichaContrato(valoresLinha);
        
                         form("hcodcorretor").value = valoresLinha[0];
                         form('mvlrlib').value      = valoresLinha[18];
                         form('mobs').value         = valoresLinha[19];

                         DMFDiv.fullScream = true;
                         DMFDiv.openModal("dmodalf_aprovacao");
                         DMFDiv.fullScream = false;
                         break;
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
    CONSUL.filaFetch = (retorno,error)=>{
        if(error) return;

        switch (CONSUL.obj) {
        case             "buscarUserName": form("ideusu").value = retorno;
                                           break;

        case    "aprovarReprovarContrato": alert("Sucesso!", "Contrato " + (chamadaParam.aprovarReprovarContrato == "aprovar"?"Aprovado":"Reprovado") + " com Sucesso!",4);
                                           //enviarEmailAprovacaoReprovacao(retorno);

                                           DMFDiv.closeModal();

                                           form("bnovabusca").click();
                                           form("bbuscar").click();
                                           break;

        case       "buscarImagensImovel": const arrayImages = retorno;

                                          if(arrayImages.lenght != 0){
                                              arrayImages.forEach((imagem,index) => {
                                                if(index == 0) form("image-principal").innerHTML = `<img src="${"/imoveisImages/" + imagem.src}" id="${imagem.id}" class="image-focus">`;
                                                criarContainersImagens(imagem.id, imagem.src);
                                              });
                                          }
                                          break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',        true);
        desabilitaCampo('bbuscar',           false);
        desabilitaCampo('codproprietario',   false);
        desabilitaCampo('codcliente',        false);
        desabilitaCampo('codcorretor',       false);
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',       false);
        desabilitaCampo('bbuscar',          true);
        desabilitaCampo('codproprietario',  true);
        desabilitaCampo('codcliente',       true);
        desabilitaCampo('codcorretor',      true);
    }
    if(opc == "modal"){
        desabilitaCampo('mvlrlib', ehConsulta());
        desabilitaCampo('mobs',    ehConsulta());

        setDisplay("baprovar",  ehManutencao()?"flex":"none");
        setDisplay("breprovar", ehManutencao()?"flex":"none");

        CARROSSEL.setPositionInicial(0);
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){
        form('codproprietario').value  = "0";
        form('descproprietario').value = "Todos os Proprietarios";
        form('codcliente').value       = "0";
        form('desccliente').value      = "Todos os Clientes";
        form('codcorretor').value      = "0";
        form('desccorretor').value     = "Todos os Corretores";
        setRadioValue('rimovel','0'); 
    }
    if(opc === "inicia" || opc === "novabusca"){
        CONTRATOS_GRID.clearGrid();
    }
    if(opc === "modal"){
        form("hcodcorretor").value = "";
        form('mvlrlib').value      = "0.00";
        form('mobs').value         = "";
        deletarContainersImagens();
    }
} 

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function puxarFichaContrato(valoresLinha){
    form('codimovel').innerText       = valoresLinha[2];
    form('tpimovel').innerText        = valoresLinha[3];
    form("codprop").innerText         = valoresLinha[4];
    form("nomeprop").innerText        = valoresLinha[5];
    form('vlrimovel').innerText       = valoresLinha[11];
    form('vlrcondominio').innerText   = valoresLinha[13];
    form('areatotal').innerText       = valoresLinha[14];
    form('numquartos').innerText      = valoresLinha[15];
    form('numbanheiros').innerText    = valoresLinha[21];
    form('endereco').innerText        = valoresLinha[17];
    form('codcontrato').innerText     = valoresLinha[0];
    form('tpcontrato').innerText      = valoresLinha[2];
    form('vlrcontrato').innerText     = valoresLinha[12];
    form('periodocontrato').innerText = valoresLinha[10];
    form('corretor').innerText        = valoresLinha[8] + " - " + valoresLinha[9];
    form('codnegociante').innerText   = valoresLinha[6];
    form('nomecliente').innerText     = valoresLinha[7];
    form('cpfcliente').innerText      = valoresLinha[16];
    form('ehpf').innerText            = valoresLinha[20];

    const dadosmarcador = {tipo:     valoresLinha[3],
                           price:    valoresLinha[11],
                           endereco: valoresLinha[17],
                           quarto:   valoresLinha[15],
                           banheiro: valoresLinha[21],
                           tamanho:  valoresLinha[14]};

    buscarImagensImovel(valoresLinha[2]);

    criarMarcadorImovel(map.center, dadosmarcador);
}

function buscarImagensImovel(codimovel){
    CONSUL.consultar("buscarImagensImovel",`/cri004/buscarImagensImovel`,['codimovel=' + codimovel]);
}

function buscarContratoAprovacao(){
    CONTRATOS_GRID.carregaGrid(`/cri005/buscarContratoAprovacao`,["codprop:codproprietario",
                                                                  "codcliente:codcliente",
                                                                  "codcorretor:codcorretor",
                                                                  "tipimovel=" + getRadioValue("rimovel"),
                                                                  "acao=" + ABA.getIndex()]);
}

//Montar pelo java
//
// function enviarEmailAprovacaoReprovacao(acao){
//     const email = {
//         to: form("memail").value,
//         subject: "Contrato " + (acao == 2?"Aprovado":"Reprovado") + "!",
//         body: "<html>"
//             + "<head>"
//             + "</head>"
//             + "<body style='font-family: Arial, sans-serif; color: #333;'>"
//             + '<div style="max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;">'
//             + "<p>Após análise o contrato foi " + (acao == 2?"Aprovado":"Reprovado") + " pelo gestor!</p>"
//             + "<p><strong>Código do contrato:</strong>" + form("hcodcorretor").value + "</p>"
//             + (acao == 1?"<p><strong>Valor Liberado:</strong> " + form("mvlrlib").value + "</p>":"")
//             + "<p><strong>Observacão:</strong> " + form("mobs").value + "</p>"
//             + "</div>"
//             + "</body>"
//             + "</html>"}

//     CONSUL.consultar("enviarEmailAprovacaoReprovacao",`/email`,[],"POST",{ "Content-Type": "application/json" },{body: email})
// }

function aprovarReprovarContrato(acao) {
    chamadaParam.aprovarReprovarContrato = {
        acao: acao
    }

    CONSUL.consultar("aprovarReprovarContrato",`/cri005/aprovarReprovarContrato`,["codcontrato:hcodcorretor",
                                                                                  "valorliberado:mvlrlib",
                                                                                  "observacao:mobs",
                                                                                  "acao=" + acao],
                                                                                  "POST");
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

async function criarMarcadorImovel(coordenada, registro){
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");

    if(marcadorElementImovel) marcadorElementImovel.setMap(null);

    marcadorElementImovel = new AdvancedMarkerElement({
        map,
        position: coordenada,
        content: buildContent(registro),
        title: registro.tipo
    });
    marcadorElementImovel.addListener("click", () => {
        EventoInfoWindow(marcadorElementImovel);
    });
}

function EventoInfoWindow(marcador) {
    if (marcador.content.classList.contains("highlight")) {
        marcador.content.classList.remove("highlight");
        marcador.zIndex = null;
    }
    else {
        marcador.content.classList.add("highlight");
        marcador.zIndex = 1;
    }
}

function buildContent(registro) {
    const content = document.createElement("div");
    content.classList.add("marcador");
    content.innerHTML = `
    <div class="icon">
        <img src="/icons/${registro.tipo.toLowerCase() == "casa"?"house_icon.png":"apartamento_icon.png"}">
    </div>
    <div class="details">
        <div class="price">R$${registro.price}</div>
        <div class="address">${registro.endereco}</div>
        <div class="features">
        <div>
            <span><img src="/icons/bedroom_icon.png" style="height: 17px; width: 17px; margin: 0px;" title="Quartos"></span>
            <span>${registro.quarto}</span>
        </div>
        <div>
            <span><img src="/icons/bathroom_icon.png" style="height: 17px; width: 17px; margin: 0px;" title="Banheiros"></span>
            <span>${registro.banheiro}</span>
        </div>
        <div>
            <span><img src="/icons/area_icon.png" style="height: 17px; width: 17px; margin: 0px;" title="Area"></span>
            <span>${registro.tamanho} m<sup>2</sup></span>
        </div>
        </div>
    </div>
    `;
    return content;
}

async function initMap() {
    const { Map } = await google.maps.importLibrary("maps");

    map = new Map(form("map"), {
      center: { lat: -23.421578, lng: -51.898191 },
      zoom: 15,
      fullscreenControl: false,
      cameraControl: false,
      mapId: "map",
      mapTypeId: 'roadmap'
    });
}

function criarContainersImagens(seqimg, src){
    const container = form("container-miniimages");

    const div = document.createElement("div");

    const img = document.createElement("img");
    img.src   = "/imoveisImages/" +  src;
    img.id    = seqimg; //Para o metodo de excluir a imagem do banco
    img.classList.add("mini-images");

    div.addEventListener("click", ()=>{
        form("image-principal").innerHTML = `<img src="${"/imoveisImages/" + src}" id="${seqimg}" class="image-focus">`
    });

    div.appendChild(img);
    container.appendChild(div);
}

function deletarContainersImagens(){
    form("container-miniimages").innerHTML = "";
    form("image-principal").innerHTML      = ""
}