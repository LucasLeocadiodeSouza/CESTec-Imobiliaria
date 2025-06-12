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
import { imgFormat,form,desabilitaCampo,setDisplay } from "./modules/utils.js";

var ABA,DMFDiv,CONSUL,CONTRATOS_GRID;

function iniciarEventos() {
    elementsForm_init();

    CONTRATOS_GRID               = new GridForm_init();
    CONTRATOS_GRID.id            = "tabela_aprovacao";
    CONTRATOS_GRID.columnName    = "codcontrato,negociacao,codimovel,tipo,codproprietario,nomeProp,codcliente,nomeCliente,codcorretor,nomeCorretor,datinicio,datfinal,preco,valor";
    CONTRATOS_GRID.columnLabel   = "Cód. Contrato,Contrato,Cód. Imovel,Tipo Imovel,Código Prop,Proprietario,Código Cliente,Cliente,Código Corretor,Corretor,Inicio,Final,Preco,Valor negoc. (R$)";
    CONTRATOS_GRID.columnWidth   = "6,5,6,6,6,10,6,10,6,10,7,7,6,6";
    CONTRATOS_GRID.columnAlign   = "c,c,c,c,c,c,c,c,c,c,c,c,c,c";
    CONTRATOS_GRID.mousehouve    = true;
    CONTRATOS_GRID.destacarclick = false;
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
    // event_click("bcadastro");

    event_click_aba();
    event_click_table();
    
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

        puxarFichaContrato(valoresLinha[0]);
        //preencherModal(valoresLinha);
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
} 

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function puxarFichaContrato(codcontrato){
    fetch(`http://localhost:8080/fichaContrato?codcontrato=${codcontrato}`)
    .then(response => response.text())
    .then(data => {form('fichacontrato').innerHTML = data})
    .catch(error => console.error('Não foi possivel carregar a ficha. \n', error));
}

function preencherModal(valoresLinha){
    const situacao = valoresLinha[5] == '1'?"Não batida":"Concluída";

    form("mcodcorretor").value  = valoresLinha[1];
    form("mdesccorretor").value = valoresLinha[2];
    form("mvlrmeta").value      = valoresLinha[3];
    form("mperiodoini").value   = valoresLinha[4];
    form("ssituacao").innerText = "* " + situacao;
    form("mperiodofin").value   = valoresLinha[6];

    if(valoresLinha[5] !== 2) form("ssituacao").classList.add("vermelho");
    else {
        form("ssituacao").classList.remove("vermelho");
        form("ssituacao").classList.add("verde");
    }
}

function buscarContratoAprovacao(){
    CONTRATOS_GRID.carregaGrid("/wcr006c/buscarContratoAprovacao","","");
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