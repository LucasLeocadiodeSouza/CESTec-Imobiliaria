/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00007
*/
window.addEventListener("load", function () {
    wcr001_init();
});

import { GridForm_init } from "./modules/gridForm.js";
import { DMFForm_init } from "./modules/dmfForm.js";
import { abaForm_init } from "./modules/abaForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay } from "./modules/utils.js";

var IMOVEIS_GRID;
var DMFDiv, ABA;

function wcr001_init(){
    IMOVEIS_GRID               = new GridForm_init();
    IMOVEIS_GRID.id            = "tabela_CTO";
    IMOVEIS_GRID.columnName    = "codimovel,codproprietario,nome,tipo,status,preco,negociacao";
    IMOVEIS_GRID.columnLabel   = "Cód.Imovel,Cod. Prop.,Nome,Tipo,Situacao,Valor (R$),Contrato";
    IMOVEIS_GRID.columnWidth   = "5,8,35,17,16,8,10";
    IMOVEIS_GRID.columnAlign   = "c,c,e,c,c,d,c";
    IMOVEIS_GRID.mousehouve    = false;
    IMOVEIS_GRID.destacarclick = false;
    IMOVEIS_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Manutencão";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_contrato";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    iniciarEventos();
}

function iniciarEventos() {
    controlaTela("inicia");

    event_click_table();
    event_click_aba();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bcadastro");
    
    event_change("codproprietario");
    event_change("mcodproprietario");

    imgFormat();

    carregaGridImoveis();
}

function event_click_table(){
    IMOVEIS_GRID.click_table = ()=>{
        const valoresLinha = IMOVEIS_GRID.getRowNode(event.target.closest('tr'));                      
        console.log(valoresLinha);

        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;

        controlaTela("modal");
        DMFDiv.openModal("dmodalf_contrato");
    };
}

function event_click(obj) {
    if(obj == "bnovabusca"){
        form(obj).addEventListener("click", function () {
            controlaTela("buscar");
        });
    }
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("novabusca");
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
            DMFDiv.closeModal();
        });
    }
}

function event_change(obj){
    if(obj == "codproprietario"){
        form(obj).addEventListener("change", function(){
            form(obj).value!=""?descProprietario(obj,"descproprietario") : form("descproprietario").value = "Todos";
        });
    }
    if(obj == "mcodproprietario"){
        form(obj).addEventListener("change", function(){
            form(obj).value!=""?descProprietario(obj,"mdescproprietario") : form("mdescproprietario").value = "";
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
    if(opc == "inicia" || opc == 'buscar'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codcontrato',     false);
        desabilitaCampo('codproprietario', false);
        desabilitaCampo('periodoini',      false);
        desabilitaCampo('periodofin',      false);
        desabilitaCampo('periodoindef',    false);
        desabilitaCampo('raluguel',        false);
        desabilitaCampo('rvenda',          false);

        setDisplay("binserir", ehManutencao()?"block":"none");
    }
    if(opc == "novabusca"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);
        desabilitaCampo('codcontrato',     true);
        desabilitaCampo('codproprietario', true);
        desabilitaCampo('periodoini',      true);
        desabilitaCampo('periodofin',      true);
        desabilitaCampo('periodoindef',    true);
        desabilitaCampo('raluguel',        true);
        desabilitaCampo('rvenda',          true);
    }
    if(opc == "modal"){
        desabilitaCampo('mcodproprietario',  ehConsulta());
        desabilitaCampo('mstpimovel',        ehConsulta());
        desabilitaCampo('mstpcontrato',      ehConsulta());
        desabilitaCampo('mmetrosquad',       ehConsulta());
        desabilitaCampo('mquartos',          ehConsulta());
        desabilitaCampo('mcondominio',       ehConsulta());
        desabilitaCampo('mloc',              ehConsulta());
        desabilitaCampo('mvlr',              ehConsulta());
        desabilitaCampo('mperiodoini',       ehConsulta());
        desabilitaCampo('bcadastro',         ehConsulta());
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'buscar'){        
        form('codproprietario').value  = "0";
        form('descproprietario').value = "0";
    }
    if(opc == "modal"){
        form('mcodproprietario').value     = "";
        form('mdescproprietario').value    = "";
        form('mstpimovel').value           = "0";
        form('mstpcontrato').value         = "0";
        form('mmetrosquad').value          = "";
        form('mquartos').value             = "";
        form('mcondominio').value          = "";
        form('mloc').value                 = "";
        form('mvlr').value                 = "";
        form('mperiodoini').value          = "";
    }
}

function preencherDadosModal(index){
    fetch(`/contratosCadastroClientes/proprietario/${index}/buscarImovelGrid`,{
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => {return response.json()})
    .then(data => {
        form("mcodimovel").value        = data.codimovel;
        form("mcodproprietario").value  = data.codproprietario;
        form("mdescproprietario").value = data.nome;
        form("mstpimovel").value        = data.tipo;
        form("msituacao").value         = data.status;
        form("mvlr").value          	= data.preco;
        form("mstpcontrato").value     	= data.negociacao;
        form("mmetrosquad").value     	= data.area;
        form("mquartos").value       	= data.quartos;
        form("mcondominio").value     	= data.vlrcondominio;
        form("mloc").value     	        = data.endereco;
        form("mperiodoini").value     	= data.datinicontrato;
    })
    .catch(error => console.log(error.message))
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

    fetch(`/contratosCadastroClientes/proprietario/${form("mcodproprietario").value}/salvarImovel`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(imovel,form("mcodproprietario").value)
    })
    .then(response => {return response.json()})
    .then(data => {})
    .catch(error => alert("Error: " + error.message));
}

function descProprietario(codigo,retorno) {
    fetch(`/contratosCadastroClientes/proprietario/${form(codigo).value}/nomepropri`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(form(codigo).value)
    })
    .then(response => {return response.text()})
    .then(data => { form(retorno).value = data })
    .catch(error => alert(error.message));
}

function carregaGridImoveis(){
    IMOVEIS_GRID.carregaGrid("/contratosCadastroClientes/proprietario/buscarImoveis","","");
}