/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00011
*/

//import { createGrid } from './gridForm.js';

window.addEventListener("load", function () {
    iniciarEventos();
});

function iniciarEventos() {
    controlaTela("inicia");
    event_click("abas");
    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("bclose");
    event_click("bcadastro");
    imgFormat();

    const dados = [
        { CodigoProp: 1, Nome: "João", CPF: "123.456.789-00", Endereco: "Rua A", Telefone: "9999-8888" },
        { CodigoProp: 2, Nome: "Maria", CPF: "987.654.321-00", Endereco: "Rua B", Telefone: "7777-6666" }
    ];

    createGrid("tabela_contrato",
               "Codigo Prop.,Nome,CPF,Endereco,Num. Telefone",
               "800",
               dados);
}

function event_click(obj) {
    if(obj == "abas"){        
        document.querySelectorAll(".aba").forEach(aba => {
            aba.addEventListener("click", function () {

                document.querySelectorAll(".aba").forEach(abareset => {
                    abareset.querySelector('.indentaba').style.backgroundColor =  'rgb(81, 81, 81)';
                    abareset.querySelector('.indentaba').style.removeProperty("background-color"); 
                    abareset.querySelector('.abaint').style.removeProperty("background-color");
                    abareset.querySelector('.abaint').style.pointerEvents = 'visible';
                });

                let abatraco   = this.querySelector(".indentaba");
                let abainterna = this.querySelector(".abaint");                

                abatraco.style.backgroundColor   = "rgb(0, 97, 7)";                
                abainterna.style.pointerEvents   = 'none';
                abainterna.style.backgroundColor = "rgb(193, 192, 192)";
            });
            form('aba1').addEventListener("click", function () {
                form('aba2').style.pointerEvents   = 'visible';              
                form('aba1').style.pointerEvents   = 'none';
                controlaTela('inicia');
            });
            form('aba2').addEventListener("click", function () {
                form('aba1').style.pointerEvents   = 'visible';               
                form('aba2').style.pointerEvents   = 'none';
                controlaTela('inicia');
            });
            
        });       
    }
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
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("DMF_external").style.display = "flex";
        });
    }
    if(obj == 'bclose'){
        form(obj).addEventListener("click", function () {
            form("DMF_external").style.display = "none";    
        }); 
    }
    if(obj == 'bcadastro'){
        form(obj).addEventListener("click", function () {
            adicionarProprietario();
        });
    }
}

function adicionarProprietario() {
    const proprietario = { nome:     form("mnome").value,
                           cpf:      form("mcpf").value,
                           cnpj:     '0',
                           numtel:   form("mddd").value + form("mtelefone").value,
                           email:    form("memail").value,
                           endereco: form("mloc").value };

    fetch("/contratosCadastroClientes/proprietario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(proprietario)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(errorMsg => { throw new Error(errorMsg); });
        }
        return response.json();
    })
    .then(data => {
        alert("Resposta do servidor: " + data);        
    })
    .catch(error => alert(error.message));
}

function controlaTela(opc){
    if(opc == "inicia" || opc == 'buscar'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codproprietario', false);

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "novabusca"){
        desabilitaCampo('bnovabusca',      false);
        desabilitaCampo('bbuscar',         true);        
        desabilitaCampo('codproprietario', true);
    }
}




function imgFormat(){
    document.querySelectorAll(".button").forEach(button => {
        let iconUrl = button.getAttribute("data-icon");
        button.style.paddingLeft = "30px";
        button.style.position    = "relative";
        
        button.style.setProperty("--icon-url",          `url(${iconUrl})`);
        button.style.setProperty("content",             `""`);
        button.style.setProperty("background-image",    `url(${iconUrl})`);
        button.style.setProperty("background-position", "10px center");
        button.style.setProperty("background-repeat",   "no-repeat");
        button.style.setProperty("background-size",     "20px 25px");
    });
}

function form(obj){
    return document.getElementById(obj);
}

function desabilitaCampo(obj,desahabilita){
    if (obj.substring(0,1) == "b"){
        form(obj).style.backgroundColor =  desahabilita?'rgb(210 212 218)': '#b4b6ba';
    }
    form(obj).disabled = desahabilita;
    form(obj).style.cursor = desahabilita?'not-allowed':'pointer';
}

function createGrid(id,column,gridWidth,dados){
    const table     = document.getElementById(id);
    const thead     = document.createElement("thead");
    const headerRow = document.createElement("tr");      

    const colunas = column.split(",");
    colunas.forEach((coluna,index) => {
        const th = document.createElement("th");
        th.textContent =  coluna.trim();
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement("tbody");
    tbody.setAttribute("id", `${id}-tbody`);
    table.appendChild(tbody);

    dados.forEach(dado => {
        const row = document.createElement("tr");        
        Object.values(dado).forEach(valor => {
            const td = document.createElement("td");
            td.textContent = valor;
            row.appendChild(td);
        });    
        tbody.appendChild(row);
    });    
    return table;
}