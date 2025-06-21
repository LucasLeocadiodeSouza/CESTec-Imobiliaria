/* 
    Dev: Lucas Leocadio de Souza
    Data: 04/03/25
    IM: 00013
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

import { GridForm_init }   from "../modules/gridForm.js";
import { DMFForm_init }    from "../modules/dmfForm.js";
import { abaForm_init }    from "../modules/abaForm.js";
import { consulForm_init } from "../modules/consulForm.js";
import { elementsForm_init } from "../modules/elementsForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay,event_selected_init } from "../modules/utils.js";

var ABA,DMFDiv,CONSUL,CONTRATOS_GRID;

function iniciarEventos() {
    elementsForm_init();

    CONTRATOS_GRID               = new GridForm_init();
    CONTRATOS_GRID.id            = "tabela_contrato";
    CONTRATOS_GRID.columnName    = "codcontrato,codcliente,nomeCliente,codproprietario,nomeProp,codimovel,tipo,negociacao,preco,datinicio,datfinal,valor,endereco_bairro,codcorretor,codtipo,nomevendedor";
    CONTRATOS_GRID.columnLabel   = "Contrato,Cod. Cli.,Nome Cli.,Cod. Prop.,Nome Pro.,Cod. Imov.,Tipo,Contrato,Valor (R$)";
    CONTRATOS_GRID.columnWidth   = "10,10,15,10,15,10,10,10,10";
    CONTRATOS_GRID.columnAlign   = "c,c,eoe,c,eoe,c,c,c,d";
    CONTRATOS_GRID.mousehouve    = true;
    CONTRATOS_GRID.destacarclick = false;
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

    event_click_table();
    event_click_aba();
    event_selected_init("codproprietario,codcliente");

    controlaTela("inicia");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("binserir");
    event_click("bcadastro");
    
    event_change("codproprietario");
    event_change("mcodprop");
    event_change("codcliente");
    event_change("mcodcliente");
    event_change("msimovel");
    event_change("mvendedor");
}

function event_click_table(){
    CONTRATOS_GRID.click_table = ()=>{
        const valoresLinha = CONTRATOS_GRID.getRowNode(event.target.closest('tr'));

        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Contrato - " + form("sacao").innerText;

        controlaTela("modal");

        preencherModal(valoresLinha);

        DMFDiv.openModal("dmodalf_contrato"); 
    }
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

            buscarContratoGrid();
        });        
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Cadastro de Contrato - " + form("sacao").innerText;
                        
            controlaTela("modal");
            DMFDiv.openModal("dmodalf_contrato");
        });
    }
    if(obj == 'bcadastro'){
        form(obj).addEventListener("click", function () {
            inserirAlterarContrato();
            DMFDiv.openModal("dmodalf_contrato");
        });
    }
}

function event_change(obj){
    if(obj == "codproprietario"){
        form(obj).addEventListener("change", function(){
            form(obj).value!=""?descProprietario(obj,"descproprietario") : form("descproprietario").value = "Todos";
        });
    }
    if(obj == "mcodprop"){
        form(obj).addEventListener("change", function(){
            descProprietario(obj,"mdescprop");

            getOptionImovel();
        });
    }
    if(obj == "codcliente"){
        form(obj).addEventListener("change", function(){
            form(obj).value!=""?getDescCliente(obj,"desccliente") : "--";
        });
    }
    if(obj == "mcodcliente"){
        form(obj).addEventListener("change", function(){
            form(obj).value!=""?getDescCliente(obj) : "--";            
        });
    }
    if(obj == "msimovel"){
        form(obj).addEventListener("change", function(){
            getTipoImovel();
            getEnderecoImovel();
            getTipoContratoImovel();
            getValorImovel();            
        });
    }
    if(obj == "mvendedor"){
        form(obj).addEventListener("change", function(){
            getDescCorretor();           
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
        desabilitaCampo('mcodcliente',   ehConsulta());
        desabilitaCampo('mdesccliente',  true);
        desabilitaCampo('mcodprop',      ehConsulta());
        desabilitaCampo('mdescprop',     true);
        desabilitaCampo('msimovel',      ehConsulta());
        desabilitaCampo('mtpimovel',     true);
        desabilitaCampo('mloc',          true);
        desabilitaCampo('mtpcontrato',   true);
        desabilitaCampo('mvlrnegociado', ehConsulta());
        desabilitaCampo('mvlrimovel',    true);
        desabilitaCampo('mvendedor',     ehConsulta());
        desabilitaCampo('mnome',         ehConsulta());
        desabilitaCampo('mperiodoini',   ehConsulta());
        desabilitaCampo('mperiodofin',   ehConsulta());
        desabilitaCampo('bcadastro',     ehConsulta());
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){        
        form('codproprietario').value  = "0";
        form('descproprietario').value = "";
        form('codcliente').value       = "0";
        form('desccliente').value      = "";
    }
    if(opc === "inicia" || opc === "novabusca"){
        CONTRATOS_GRID.clearGrid();
    }
    if(opc == "modal"){
        fillSelect("msimovel","0|Selecione um Imovel");

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

function descProprietario(codigo) {
    CONSUL.consultar(`/contratosCadastroClientes/proprietario/${form(codigo).value}/nomepropri`)
    .then(data =>{
        form("mdescprop").value = data;
    });
}

function preencherModal(valoresLinha){                
    form('mcodprop').value      = valoresLinha[3];
    getOptionImovel();

    form('mcodcliente').value   = valoresLinha[1];
    form('mdesccliente').value  = valoresLinha[2];
    form('mdescprop').value     = valoresLinha[4];
    form("msimovel").value      = valoresLinha[14];
    form('mtpimovel').value     = valoresLinha[6];
    form('mloc').value          = valoresLinha[12];
    form('mtpcontrato').value   = valoresLinha[7];
    form('mvlrimovel').value    = valoresLinha[8];
    form('mvlrnegociado').value = valoresLinha[11];
    form("mperiodoini").value   = valoresLinha[9];
    form("mperiodofin").value   = valoresLinha[10];
    form("mvendedor").value     = valoresLinha[13];
    form("mnome").value         = valoresLinha[15];
}

function buscarContratoGrid(){
    CONTRATOS_GRID.carregaGrid(`/contrato/buscarContratoGrid?codprop=${form("codproprietario").value}&codcliente=${form("codcliente").value}`,"","");
}

function inserirAlterarContrato(){
    const contratoDTO = {codimovel:       form("msimovel").value,
                         codcliente:      form("mcodcliente").value,
                         codproprietario: form("mcodprop").value,
                         datinicio:       form("mperiodoini").value,
                         datfinal:        form("mperiodofin").value,
                         preco:           form("mvlrnegociado").value,
                         ideusuCorretor:  form("mvendedor").value,
                         ideusu:          form("ideusu").value};

    CONSUL.consultar(`/contrato/inserirAlterarContrato`,"POST","",contratoDTO)
    .then(data =>{
        alert("Contrato adicionado com sucesso!");

        DMFDiv.closeModal();
    });
}

function getOptionImovel(){
    CONSUL.consultar(`/contratosCadastroClientes/proprietario/${form("mcodprop").value}/getOptionImovel`)
    .then(data =>{
        fillSelect("msimovel", data);
        form("msimovel").options[0].disabled = true; 
    });
}

function getTipoImovel(){
    CONSUL.consultar(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getTipoImovel`)
    .then(data =>{
        form("mtpimovel").value = data;
    });
} 

function getEnderecoImovel(){
    CONSUL.consultar(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getEnderecoImovel`)
    .then(data =>{
        form("mloc").value = data; 
    });
}

function getTipoContratoImovel(){
    CONSUL.consultar(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getTipoContratoImovel`)
    .then(data =>{
        form("mtpcontrato").value = data;
        form("mperiodofin").style.display = data === "Aluguel"?"inline":"none";
    });
} 

function getValorImovel(){
    CONSUL.consultar(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getValorImovel`)
    .then(data =>{
        form("mvlrimovel").value = data;
    });
}

function getDescCliente(codigo){
    CONSUL.consultar(`/cliente/${form(codigo).value}/findNomeClienteById`)
    .then(data =>{
        form("mdesccliente").value = data;
    });
}

function getDescCorretor(){
    CONSUL.consultar(`/contrato/${form("mvendedor").value}/getNomeByIdeusu`)
    .then(data =>{
        form("mnome").value = data;
    });
}

function buscarUserName(){
    CONSUL.consultar(`/home/userlogin`)
    .then(data =>{
        form("ideusu").value = data
    });
}


function fillSelect(selectId, data) {
    let select = document.getElementById(selectId);
    select.innerHTML = "";

    let items = data.split("#");

    items.forEach(item => {
        let parts = item.split("|");
        if (parts.length === 2) {
            let option         = document.createElement("option");
            option.value       = parts[0];
            option.textContent = parts[1];
            select.appendChild(option);
        }
    });
}