/* 
    Dev: Lucas Leocadio de Souza
    Data: 22/03/25
    IM: 00008
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

import { GridForm_init }     from "./modules/gridForm.js";
import { DMFForm_init }      from "./modules/dmfForm.js";
import { abaForm_init }      from "./modules/abaForm.js";
import { consulForm_init }   from "./modules/consulForm.js";
import { elementsForm_init } from "./modules/elementsForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay,event_blur_init,event_selected_init } from "./modules/utils.js";

var ABA,DMFDiv,CONSUL,CONTRATOS_GRID;

function iniciarEventos() {
    elementsForm_init();

    CONTRATOS_GRID               = new GridForm_init();
    CONTRATOS_GRID.id            = "tabela_aprovacao";
    CONTRATOS_GRID.columnName    = "codcontrato,codimovel,tipo,codproprietario,nomeProp,codcliente,nomeCliente,codcorretor,nomeCorretor,periodo,preco,valor,vlrcondominio,area,quartos,documento,endereco,vlrliber,observacao";
    CONTRATOS_GRID.columnLabel   = "Cód. Contrato,Cód. Imovel,Tipo Imovel,Código Prop,Proprietario,Código Cliente,Cliente,Código Corretor,Corretor,Periodo,Preco (R$),Valor negoc. (R$)";
    CONTRATOS_GRID.columnWidth   = "7,6,9,6,11,6,11,6,11,11,8,8";
    CONTRATOS_GRID.columnAlign   = "c,c,e,c,e,c,e,c,e,c,d,d";
    CONTRATOS_GRID.mousehouve    = true;
    CONTRATOS_GRID.destacarclick = false;
    CONTRATOS_GRID.gridWidth     = "2200px";
    CONTRATOS_GRID.gridHeight    = "auto";
    CONTRATOS_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutencão";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_aprovacao";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("bclose");
    event_click("blimpar");
    event_click("baprovar");
    event_click("breprovar");

    event_click_aba();
    event_click_table();
    
    event_blur_init("mvlrlib");
    event_selected_init("mvlrlib,mobs,codproprietario,codcliente,codcorretor");

    event_change("codproprietario");
    event_change("codcliente");
    event_change("codcorretor");

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

            buscarContratoAprovacao();
        });        
    }
    if(obj == "baprovar"){
        form(obj).addEventListener("click", function () {
            if(!confirm("Deseja mesmo Aprovar esse contrato?")) return;

            aprovarReprovarContrato(2);
        });
    }
    if(obj == "breprovar"){
        form(obj).addEventListener("click", function () {
            if(!confirm("Deseja mesmo Reprovar esse contrato?")) return;

            aprovarReprovarContrato(3);
        });
    }
}

function event_change(obj){
    if(obj == "codproprietario"){
        form(obj).addEventListener("change", function(){
            form("descproprietario").value = form(obj).value!=""?descProprietario(obj) : "Todos";
        });
    }
    if(obj == "codcliente"){
        form(obj).addEventListener("change", function(){
            form("desccliente").value = form(obj).value!=""? getDescCliente(obj) : "Todos os Clientes";
        });
    }
    if(obj == "codcorretor"){
        form(obj).addEventListener("change", function(){
            form("desccorretor").value = form(obj).value != ""? getDescCorretor(obj):"Todos os Corretores";           
        });
    }
}

function event_click_table(){
    CONTRATOS_GRID.click_table = ()=>{
        const valoresLinha = CONTRATOS_GRID.getRowNode(event.target.closest('tr'));
        controlaTela("modal");

        form("sacao").innerText   = ehConsulta()?"Consultando":"Analisando";
        form("stitulo").innerText = form("sacao").innerText + " o Contrato - " + form("sacao").innerText;

        puxarFichaContrato(valoresLinha);
        
        form("hcodcorretor").value = valoresLinha[0];
        form('mvlrlib').value      = valoresLinha[17];
        form('mobs').value         = valoresLinha[18];

        DMFDiv.openModal("dmodalf_aprovacao");
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
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){
        form('codproprietario').value   = "";
        form('descproprietario').value  = "Todos os Proprietarios";
        form('codcliente').value        = "";
        form('desccliente').value       = "Todos os Clientes";
        form('codcorretor').value       = "";
        form('desccorretor').value      = "Todos os Corretores";
    }
    if(opc === "inicia" || opc === "novabusca"){
        CONTRATOS_GRID.clearGrid();
    }
    if(opc === "modal"){
        form("hcodcorretor").value = "";
        form('mvlrlib').value      = "0";
        form('mobs').value         = "";
    }
} 

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function puxarFichaContrato(valoresLinha){
    form('codimovel').innerText       = valoresLinha[1];
    form('tpimovel').innerText        = valoresLinha[2];
    form('vlrimovel').innerText       = valoresLinha[10];
    form('vlrcondominio').innerText   = valoresLinha[12];
    form('areatotal').innerText       = valoresLinha[13];
    form('numquartos').innerText      = valoresLinha[14];
    form('endereco').innerText        = valoresLinha[16];
    form('codcontrato').innerText     = valoresLinha[0];
    form('tpcontrato').innerText      = valoresLinha[1];
    form('vlrcontrato').innerText     = valoresLinha[11];
    form('periodocontrato').innerText = valoresLinha[9];
    form('corretor').innerText        = valoresLinha[7] + " - " + valoresLinha[8];
    form('codnegociante').innerText   = valoresLinha[5];
    form('nomecliente').innerText     = valoresLinha[6];
    form('cpfcliente').innerText      = valoresLinha[15];
}

function buscarContratoAprovacao(){
    CONTRATOS_GRID.carregaGrid(`/wcr006c/buscarContratoAprovacao?codprop=${form("codproprietario").value}&codcliente=${form("codcliente").value}&codcorretor=${form("codcorretor").value}&acao=${ABA.getIndex()}`,"","");
}

function enviarEmailAprovacaoReprovacao(acao){
    const email = {
        to: form("memail").value,
        subject: "Contrato " + (acao == 2?"Aprovado":"Reprovado") + "!",
        body: "<html>"
            + "<head>"
            + "</head>"
            + "<body style='font-family: Arial, sans-serif; color: #333;'>"
            + '<div style="max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;">'
            + "<p>Após análise o contrato foi " + (acao == 2?"Aprovado":"Reprovado") + " pelo gestor!</p>"
            + "<p><strong>Código do contrato:</strong>" + form("hcodcorretor").value + "</p>"
            + (acao == 1?"<p><strong>Valor Liberado:</strong> " + form("mvlrlib").value + "</p>":"")
            + "<p><strong>Observacão:</strong> " + form("mobs").value + "</p>"
            + "</div>"
            + "</body>"
            + "</html>"}

    CONSUL.consultar(`/email`,"POST",{ "Content-Type": "application/json" },email)
}

function aprovarReprovarContrato(acao) {
    const contrato = {codcontrato:         form("hcodcorretor").value,
                      valorliberado:       parseFloat(form("mvlrlib").value),
                      observacao:          form("mobs").value,
                      situacao:            acao,
                      ideusu:              form('ideusu').value};

    CONSUL.consultar(`/wcr006c/aprovarReprovarContrato`,"POST",{ "Content-Type": "application/json" },contrato)
    .then(data =>{
        if(data != "OK") return alert(data);
        alert("Contrato " + (acao == 2?"Aprovado":"Reprovado") + " com Sucesso!");
        enviarEmailAprovacaoReprovacao(acao);

        DMFDiv.closeModal();

        form("bnovabusca").click();
        form("bbuscar").click();
    });
}

function getDescCliente(codigo){
    CONSUL.consultar(`/cliente/${form(codigo).value}/findNomeClienteById`)
    .then(data =>{return data});
}

function descProprietario(codigo) {
    CONSUL.consultar(`/contratosCadastroClientes/proprietario/${form(codigo).value}/nomepropri`)
    .then(data =>{return data});
}

function getDescCorretor(obj){
    CONSUL.consultar(`/contrato/${form(obj).value}/getNomeByIdeusu`)
    .then(data =>{return data});
}

function buscarUserName(){
    CONSUL.consultar(`/home/userlogin`)
    .then(data =>{
        form("ideusu").value = data
    });
}