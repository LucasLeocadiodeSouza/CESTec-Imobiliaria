/* 
    Dev: Lucas Leocadio de Souza
    Data: 22/03/25
    IM: 00008
*/
window.addEventListener("load", function () {
    createGrid("tabela_boletos",
               "id,nmrBoleto,codconvenio,certificado,nome,situacao,vlrboleto",
               "Código,Número do Boleto,Número do Convênio,CNPJ/CPF,Nome,Situacão,Valor (R$)",
               "10,10,10,20,20,10,10",
               "auto");
    
    iniciarEventos();
    buscarUserName();
});

import { GridForm_init }     from "../modules/gridForm.js";
import { DMFForm_init }      from "../modules/dmfForm.js";
import { abaForm_init }      from "../modules/abaForm.js";
import { consulForm_init }   from "../modules/consulForm.js";
import { elementsForm_init } from "../modules/elementsForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay } from "../modules/utils.js";

var ABA,DMFDiv,CONSUL,METAS_GRID;

function iniciarEventos() {
    controlaTela("inicia");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("bclose");
    event_click("blimpar");
    event_click("binserir");
    event_click("benviaboleto");

    event_change("mcodcliente");
    imgFormat();
}


function event_click(obj,dado) {
    if(obj == "bnovabusca"){
        form(obj).addEventListener("click", function () {
            controlaTela("buscar");
        });
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
        });
    }
    if(obj == "bbuscar"){
        form(obj).addEventListener("click", function () {
            controlaTela("novabusca");

        });        
    }
    if(obj == 'bclose'){
        form(obj).addEventListener("click", function () {
            form("DMF_external").style.display = "none";
        });
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Gerando";
            form("stitulo").innerText = form("sacao").innerText + " o Metodo de Pagamento";
            controlaTela("modal");


            form("DMF_external").style.display = "flex";
        });
    }
    if(obj == "benviaboleto"){
        form(obj).addEventListener("click", ()=>{
            registrarBoleto();
        })
    }
}

function event_change(obj){
    if(obj == "mcodcliente"){
        form(obj).addEventListener("change", function(){
            form("mbenef").value = getDescCliente(obj);
        });
    }
}

function event_click_table(id,index){
    if(id == "tabela_boletos"){
        form("sacao").innerText   = ehConsulta()?"Consultando":"Analisando";
        form("stitulo").innerText = form("sacao").innerText + " o Contrato - " + form("sacao").innerText;
        
        //puxarFichaContrato(3);

        controlaTela("modal");
        form("DMF_external").style.display = "flex"; 
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'buscar'){
        desabilitaCampo("codboleto",        false);
        desabilitaCampo("codconvenio",      false);
        desabilitaCampo("codcliente",       false);
        desabilitaCampo('bnovabusca',       true);
        desabilitaCampo('bbuscar',          false);
    }
    if(opc == "novabusca"){
        desabilitaCampo("codboleto",        true);
        desabilitaCampo("codconvenio",      true);
        desabilitaCampo("codcliente",       true);
        desabilitaCampo('bnovabusca',       false);
        desabilitaCampo('bbuscar',          true);
    }
    if(opc == "modal"){
        desabilitaCampo("mbenef",           true);
        desabilitaCampo("mdocumento",       true);
        desabilitaCampo("mcodcontrato",     true);
        form("dsituacao").style.display  = form("sacao").innerText == "Gerando"?"none":"block";
        form("dnmrboleto").style.display = form("sacao").innerText == "Gerando"?"none":"block";
        form("dfatura").style.display    = form("sacao").innerText == "Gerando"?"none":"block";
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'buscar'){
        form('codboleto').value    = "";
        form('codconvenio').value  = "";
        form('codcliente').value   = "";

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "modal"){
        form("mfatura").value = "0";
    }
} 

function ehConsulta(){
    return form("aba1").classList.contains('ativa');
}

function ehManutencao(){
    return form("aba2").classList.contains('ativa');
}

function puxarFichaContrato(codcontrato){
    fetch(`http://localhost:8080/fichaContrato?codcontrato=${codcontrato}`)
    .then(response => response.text())
    .then(data => {form('fichacontrato').innerHTML = data})
    .catch(error => console.error('Não foi possivel carregar a ficha. \n', error));
}

function preencherModal(index){
    fetch(`/contrato/${index}/buscarContratoGrid`, {
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.json()})
    .then(data => {})
    .catch(error => alert(error.message))
}

function getDescCliente(codigo, retorno){
    fetch(`/cliente/${form(codigo).value}/findNomeClienteById`,{
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.text()})
    .then(data => { return data })
    .catch(error => alert(error.message))
}

function getDescCorretor(obj, retorno){
    fetch(`/contrato/${form(obj).value}/getNomeByIdeusu`,{
        method: "GET",
        headers: {"Content-type":"application/json"}
    })
    .then(response => { return response.text()})
    .then(data => {return data })
    .catch(error => alert(error.message))
}

function registrarFatura(){
    const json = {  "id"              : form("mfatura").value,
                    "tipo"            : "RECEITA",
                    "situacao"        : "NAO_PAGA",
                    "valor"           : form("mvlr").value,
                    "data_vencimento" : form("mdatavenc").value
                 }

    fetch(`/faturas/registrarFatura/${form("mcodcliente").value}`, {
        method:  "POST",
        headers: {"Content-Type":"application/json"},
        body:   JSON.stringify(json)
    })
    .then(response => {return response.json()})
    .then(data => {})
    .catch(error => alert(error.message))
}

function registrarBoleto(){
    fetch(`/faturas/registrarBoleto/${form("mfatura").value}`, {
        method:  "POST",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.json()})
    .then(data => { /* mostrarBoletoEmIframe(form("mfatura").value) */ })
    .catch(error => alert(error.message))
}

function buscarUserName(){
    fetch("/home/userlogin", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response =>{return response.text()})
    .then(data => { form("ideusu").value = data })
    .catch(error => alert(error.message));
}


function downloadPDF(base64, filename) {
    const link = document.createElement('a');
    link.href = `data:application/pdf;base64,${base64}`;
    link.download = filename;
    link.click();
}

function mostrarBoletoEmIframe(faturaId) {
    const iframe = document.createElement('iframe');
    iframe.src = `/faturas/${faturaId}/boleto/pdf`;
    iframe.style.width = '100%';
    iframe.style.height = '100vh';
    iframe.style.border = 'none';
    
    // Abre nova janela e adiciona o iframe
    const novaJanela = window.open('', '_blank');
    novaJanela.document.body.appendChild(iframe);
    novaJanela.document.title = 'Boleto - ' + faturaId;
  }

function createGrid(id,column,columnName,columnWidth,width){    
    const table     = document.getElementById(id);
    const thead     = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const colunas   = column.split(",");

    var pi = 0;
    colunas.forEach((coluna,index) => {
        const th = document.createElement("th");
        th.id = coluna.trim() + "__" + pi;        
        th.textContent =  coluna.trim();
        headerRow.appendChild(th);
        pi += 1;
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement("tbody");
    tbody.setAttribute("id", `${id}-tbody`);
    table.appendChild(tbody);

    var pi = 0;
    colunas.forEach((coluna, index)=>{
        form(coluna.trim() + "__" + pi).innerText   = columnName.split(",")[pi];
        form(coluna.trim() + "__" + pi).style.width = columnWidth.split(",")[pi] +"%"; 
        pi += 1;
    });

    table.style.width = width + "px";
    return table;
}

function carregaGrid(id,dados){
    clearGrid(id);
    let colunas = form(id).children[0].children[0].children;

    for(var i = 0; i < dados.length; i++){
        const row = document.createElement("tr");

        for(var j = 0; j < colunas.length; j++){
            const td  = document.createElement("td");
            var valor = dados[i][colunas[j].id.replace("__" + j ,"").trim()];
            if(colunas[j].id.replace("__"+j,"") == "situacao"){
                if(valor == "0") valor = "Não Concluida"
                if(valor == "1") valor = "Em progresso"
                if(valor == "2") valor = "Concluida"
            }
            td.textContent = valor? valor :"N/A";
            row.appendChild(td);
            
            row.addEventListener("click", ()=>{
                form(id).querySelectorAll("tr").forEach(row => {
                    row.style.border = "none";
                });            
                event_click_table(id,i);
                row.style.border = " 2px solid black";
            });
        }

        form(id).appendChild(row);
    }
}

function clearGrid(id){
    const tbody = document.getElementById(id).querySelector('tbody');
    while (tbody.rows.length > 0) {
        tbody.deleteRow(0);
    }
}