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
    ABA_init();
    form("aba1").classList.add('ativa');

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("binserir");
    event_click("blimpar");
    event_click("bclose");
    event_click("bcadastro");
    imgFormat();

    buscarDadosTable();         
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
            buscarDadosTable(); 
        });        
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Cadastro de Cliente - " + form("sacao").innerText;

            form("DMF_external").style.display = "flex";
            controlaTela("modal");
        });
    }
    if(obj == 'blimpar'){
        form(obj).addEventListener("click", function () {
            controlaTela("inicia");
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
            form("DMF_external").style.display = "none";
        });
    }
}

function event_click_table(id,index){
    if(id == "tabela_contrato"){
        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Proprietario - " + form("sacao").innerText;

        buscarPropriGrid(index);                
        controlaTela("modal");
        form("DMF_external").style.display = "flex";
    }
}

function ABA_init(){
    document.querySelectorAll(".aba").forEach(aba => {
        aba.addEventListener("click", function () {

            document.querySelectorAll(".aba").forEach(abareset => {
                abareset.querySelector('.indentaba').style.backgroundColor =  'rgb(81, 81, 81)';
                abareset.querySelector('.indentaba').style.removeProperty("background-color"); 
                abareset.querySelector('.abaint').style.removeProperty("background-color");
                abareset.querySelector('.abaint').style.pointerEvents = 'visible';
                abareset.classList.remove('ativa'); 
            });
            this.classList.add('ativa');

            let abatraco   = this.querySelector(".indentaba");
            let abainterna = this.querySelector(".abaint");                

            abatraco.style.backgroundColor   = "rgb(41, 76, 141)";                
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
        

        aba.addEventListener("click", function () {
            buscarDadosTable();
        });
    });
}

function buscarPropriGrid(index){
    fetch(`/contratosCadastroClientes/proprietario/${index}/buscarPropriGrid`,{
        method: "POST",
        headers: {"Content-Type": "application/json"}
    })
    .then(response =>{return response.json()})
    .then(data => {
        form("mcodproprietario").value  = data.codproprietario;
        form("mnome").value         	= data.nome;
        form("mcpf").value          	= data.documento;
        form("mddd").value          	= data.numtel.substring(0,2);
        form("mtelefone").value     	= data.numtel.substring(2);
        form("memail").value        	= data.email;
        form('mbairro').value           = data.endereco_bairro;
        form('mnmr').value              = data.endereco_numero;
        form('mcidade').value           = data.endereco_cidade;
        form('muf').value               = data.endereco_uf;
    })
    .catch(error => alert(error.message));
}

function buscarDadosTable(){
    fetch("/contratosCadastroClientes/proprietario")
        .then(response => response.json()) //quando chega a mensagem vc converte para json
        .then(data => {                    // quando chega o dados na forma de JSON vc faz  ...           
            createGrid("tabela_contrato",
                       "codproprietario,nome,documento,endereco_bairro,numtel",
                       "Cod. Prop., Nome, Documento, Endereco, Telefone",
                       "10,40,10,30,10",
                       data);
        })
        .catch(error => console.log("Erro ao buscar dados: ",error));
}

function adicionarProprietario() {
    const proprietario = { codproprietario: form("mcodproprietario").value,
                           nome:            form("mnome").value,
                           documento:       form("mcpf").value,
                           numtel:          form("mddd").value + form("mtelefone").value,
                           email:           form("memail").value,
                           endereco_bairro: form('mbairro').value,
                           endereco_numero: form('mnmr').value,
                           endereco_cidade: form('mcidade').value,
                           endereco_uf:     form('muf').value };

    fetch("/contratosCadastroClientes/proprietario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(proprietario)
    })
    .then(response => {
        return response.json();
    })
    .then(data => {        
        alert("Dados Salvos Com Sucesso!");
        if(form("sacao").innerText == "Inserindo")if(confirm("Deseja enviar um Email de Boas Vindas para o Proprietario?")) enviarEmail();
    })
    .catch(error => alert(error.stringify(error)));
}

function enviarEmail(){
    const email = {
        to: form("memail").value,
        subject: "Bem Vinda a CESTec Enterprise 😊",
        body: "Somos Uma Imobiliaria que focamos em oferecer o melhor Atendimento e Serviços para nossos colaboradores! \n"
            + "Gostariamos de Agradecer pela confiança e compromisso."
    }

    fetch("/email", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(email)        
    })
    .then(response => {})
    .then(data => {})
    .catch(error => alert(error.message));
}

function controlaTela(opc){
    limparTela(opc);
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
    if(opc == "modal"){
        desabilitaCampo('mnome',     ehConsulta());
        desabilitaCampo('mcpf',      ehConsulta());
        desabilitaCampo('mddd',      ehConsulta());
        desabilitaCampo('mtelefone', ehConsulta());
        desabilitaCampo('memail',    ehConsulta());
        desabilitaCampo('mbairro',   ehConsulta());
        desabilitaCampo('mnmr',      ehConsulta());
        desabilitaCampo('mcidade',   ehConsulta());
        desabilitaCampo('muf',       ehConsulta());
        desabilitaCampo('bcadastro', ehConsulta());
    }
}

function limparTela(opc){
    if(opc == "inicia" || opc == 'buscar'){        
        form('codproprietario').value = "0";

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "modal"){
        form('mnome').value     = "";
        form('mcpf').value      = "";
        form('mddd').value      = "";
        form('mtelefone').value = "";
        form('memail').value    = "";
        form('mbairro').value   = "";
        form('mnmr').value      = "";
        form('mcidade').value   = "";
        form('muf').value       = "";
    }
}


function ehConsulta(){
    return form("aba1").classList.contains('ativa');
}

function ehManutencao(){
    return form("aba2").classList.contains('ativa');
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

function createGrid(id,column,columnName,columnWidth,dados){
    form(id).innerText = '';
    const table     = document.getElementById(id);
    const thead     = document.createElement("thead");
    const headerRow = document.createElement("tr");      
    const colunas   = column.split(",");

    var pi = 0;
    colunas.forEach((coluna,index) => {
        const th = document.createElement("th");
        th.id = "th" + pi;
        th.textContent =  coluna.trim();
        headerRow.appendChild(th);
        pi += 1;
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement("tbody");
    tbody.setAttribute("id", `${id}-tbody`);
    table.appendChild(tbody);

    dados.forEach((dado, index ) => {
        const row = document.createElement("tr");        

        colunas.forEach(coluna => {
            const td = document.createElement("td");
            const valor = dado[coluna.trim()];
            td.textContent = valor? valor :"N/A";
            row.appendChild(td);
        });    

        row.addEventListener("click", ()=>{
            form(id).querySelectorAll("tr").forEach(row => {
                row.style.border = "none";
            });            
            event_click_table(id,index);
            row.style.border = " 2px solid black";
        });

        tbody.appendChild(row);
    });

    var pi = 0;
    colunas.forEach((coluna, index)=>{
        form("th"+pi).innerText = columnName.split(",")[pi];
        form("th"+pi).style.width = columnWidth.split(",")[pi] +"%"; 
        pi += 1;
    });
    return table;
}