/* 
    Dev: Lucas Leocadio de Souza
    Data: 13/03/25
    IM: 00017
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

import { GridForm_init }     from "../modules/gridForm.js";
import { DMFForm_init }      from "../modules/dmfForm.js";
import { abaForm_init }      from "../modules/abaForm.js";
import { consulForm_init }   from "../modules/consulForm.js";
import { elementsForm_init } from "../modules/elementsForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay,event_selected_init } from "../modules/utils.js";

var ABA,DMFDiv,CONSUL,METAS_GRID;

function iniciarEventos() {
    elementsForm_init();

    METAS_GRID               = new GridForm_init();
    METAS_GRID.id            = "tabela_metas";
    METAS_GRID.columnName    = "codmeta,codcorretor,nome,vlrmeta,periodo,situacao,datiniciometa,datfinalmeta";
    METAS_GRID.columnLabel   = "Cód. Meta,Cód. Corretor,Nome,Meta (R$),Periodo,Situação";
    METAS_GRID.columnWidth   = "10,10,35,10,20,15";
    METAS_GRID.columnAlign   = "c,c,e,d,c,c";
    METAS_GRID.mousehouve    = true;
    METAS_GRID.destacarclick = false;
    METAS_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutenção";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_meta";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");
    
    event_change("codcorretor");
    event_change("mcodcorretor");

    event_click_table();
    event_selected_init("codcorretor");
    event_click_aba();

    controlaTela("inicia");
}

function event_click_table(){
    METAS_GRID.click_table = ()=>{
        const valoresLinha = METAS_GRID.getRowNode(event.target.closest('tr'));
        controlaTela("modal");

        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Metas - " + form("sacao").innerText;
        
        preencherModal(valoresLinha);

        DMFDiv.openModal("dmodalf_meta"); 
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

function event_click(obj) {
    if(obj == "bnovabusca"){
        form(obj).addEventListener("click", function () {
            controlaTela("novabusca");
        });
    }
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("buscar");
            buscarMetasCorretoresGrid();
        });        
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Cadastro de Meta - " + form("sacao").innerText;
                        
            controlaTela("modal");
            DMFDiv.openModal("dmodalf_meta");
        });
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == 'bcadastro'){
        form(obj).addEventListener("click", function () {
            salvarMetaCorretor();
        });
    }
}

function event_change(obj){
    if(obj == "mcodcorretor"){
        form(obj).addEventListener("change", function(){
            getDescCorretor(form('mcodcorretor').value, "mdesccorretor");           
        });
    }
    if(obj == "codcorretor"){
        form(obj).addEventListener("change", function(){
            getDescCorretor(form('codcorretor').value, "desccorretor");           
        });
    }
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{

        switch (CONSUL.obj) {
        case        "buscarUserName": form("ideusu").value = retorno;
                                      break;

        case    "salvarMetaCorretor": if(retorno != "OK") return alert(retorno);
                                      alert("Meta adicionada com sucesso!");

                                      DMFDiv.closeModal();
                                      break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',    true);
        desabilitaCampo('bbuscar',       false);
        desabilitaCampo('codcorretor',   false);
        desabilitaCampo('periodoini',    false);
        desabilitaCampo('periodofin',    false);

        setDisplay("binserir", ehConsulta()?"none":"flex");
    }
    if(opc === "inicia" || opc === "novabusca"){
        METAS_GRID.clearGrid();
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',    false);
        desabilitaCampo('bbuscar',       true);
        desabilitaCampo('codcorretor',   true);
        desabilitaCampo('periodoini',    true);
        desabilitaCampo('periodofin',    true);
    }
    if(opc == "modal"){
        desabilitaCampo('mvlrmeta',      ehConsulta());
        desabilitaCampo('mcodcorretor',  ehConsulta());
        desabilitaCampo('mdesccorretor', true);
        desabilitaCampo('mperiodoini',   ehConsulta());
        desabilitaCampo('mperiodofin',   ehConsulta());
        desabilitaCampo('bcadastro',     ehConsulta());
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'buscar'){        
        form('codcorretor').value  = "";
        form('desccorretor').value = "Todos os Corretores";
        form('periodoini').value   = "";
        form('periodofin').value   = "";
    }
    if(opc == "modal"){
        form('mcodcorretor').value  = "";
        form("mdesccorretor").value = "";
        form('mvlrmeta').value      = "0";
        form("ssituacao").value     = "";
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

function preencherModal(valoresLinha){
    form("mcodcorretor").value  = valoresLinha[1];
    form("mdesccorretor").value = valoresLinha[2];
    form("mvlrmeta").value      = valoresLinha[3];
    form("mperiodoini").value   = valoresLinha[6];
    form("ssituacao").innerText = "* " + valoresLinha[5];
    form("mperiodofin").value   = valoresLinha[7];

    if(valoresLinha[5] !== 2) form("ssituacao").classList.add("vermelho");
    else {
        form("ssituacao").classList.remove("vermelho");
        form("ssituacao").classList.add("verde");
    }
}

function buscarMetasCorretoresGrid(){
    METAS_GRID.carregaGrid("/wcr005/buscarMetasCorretoresGrid","","");
}

function getDescCorretor(idCorretor, retorno){
    CONSUL.consultar("getDescCorretor", `/contrato/${idCorretor}/getNomeByIdeusu`)
    .then(data =>{ form(retorno).value = data});
}

function salvarMetaCorretor(){
    const meta = { idlogin:       form("mcodcorretor").value,
                   vlrmeta:       form("mvlrmeta").value,
                   datiniciometa: form("mperiodoini").value,
                   datfinalmeta:  form("mperiodofin").value,
                   nome:          form("ideusu").value};

    CONSUL.consultar("salvarMetaCorretor",`/wcr005/salvarMetaCorretor`,"POST","",{body: meta});
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`);
}