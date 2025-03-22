/* 
    Dev: Lucas Leocadio de Souza
    Data: 04/03/25
    IM: 00013
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

function iniciarEventos() {
    controlaTela("inicia");
    ABA_init();
    form("aba1").classList.add('ativa');
    buscarContratoGrid();

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("binserir");
    event_click("bclose");
    event_click("bcadastro");
    
    event_change("codproprietario");
    event_change("mcodprop");
    event_change("codcliente");
    event_change("mcodcliente");
    event_change("msimovel");
    event_change("mvendedor");

    imgFormat();
}

function event_click_table(id,index){
    if(id == "tabela_contrato"){
        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Contrato - " + form("sacao").innerText;
        
        preencherModal(index);

        controlaTela("modal");
        form("DMF_external").style.display = "flex"; 
    }
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

            buscarContratoGrid();
        });        
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Cadastro de Contrato - " + form("sacao").innerText;
                        
            controlaTela("modal");
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
            inserirAlterarContrato();
            form("DMF_external").style.display = "none";
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
            form(obj).value!=""?descProprietario(obj,"mdescprop") : form("mdescprop").value = "";

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
            form(obj).value!=""?getDescCliente(obj,"mdesccliente") : "--";            
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

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'buscar'){
        desabilitaCampo('bnovabusca',      true);
        desabilitaCampo('bbuscar',         false);
        desabilitaCampo('codproprietario', false);
        desabilitaCampo('codcliente',      false);
        desabilitaCampo('raluguel',        false);
        desabilitaCampo('rvenda',          false);

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "novabusca"){
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
    if(opc == "inicia" || opc == 'buscar'){        
        form('codproprietario').value  = "0";
        form('descproprietario').value = "";
        form('codcliente').value       = "0";
        form('desccliente').value      = "";

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
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
        });
    });
}

function ehConsulta(){
    return form("aba1").classList.contains('ativa');
}

function ehManutencao(){
    return form("aba2").classList.contains('ativa');
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

function preencherModal(index){
    var tipo, negociacao;
    fetch(`/contrato/${index}/buscarContratoGrid`, {
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.json()})
    .then(data => { form('mcodprop').value     = data.codproprietario;
                    tipo = data.tipo;
                    negociacao = data.negociacao;
                    
                    getOptionImovel().then(()=>{form('msimovel').value = data.codimovel});

                    if(tipo==1){tipo = "Apartamento"}
                    if(tipo==2){tipo = "Casa"}
                    if(tipo==3){tipo = "Terreno"};                
                    negociacao = negociacao==1?"Aluguel":"Venda";                    

                   form('mcodcliente').value   = data.codcliente;
                   form('mdesccliente').value  = data.nomeCliente;                   
                   form('mdescprop').value     = data.nomeProp;
                   form('mtpimovel').value     = tipo;
                   form('mloc').value          = data.endereco;
                   form('mtpcontrato').value   = negociacao;
                   form('mvlrimovel').value    = data.preco;
                   form('mvlrnegociado').value = data.valor;
                   form("mperiodoini").value   = data.datinicio;                   
                   form("mperiodofin").value   = data.datfinal;})
    .catch(error => alert(error.message))
}

function buscarContratoGrid(){
    fetch("/contrato/buscarContratoGrid",{
        method:  "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.json()})
    .then(data => { createGrid("tabela_contrato",
                               "codcontrato,codcliente,nomeCliente,codproprietario,nomeProp,codimovel,tipo,negociacao,preco",
                               "Contrato,Cod,Nome,Cod,Nome,Cod,Tipo,Contrato,Valor",
                               "10,10,15,10,15,10,10,10,10",
                               data)})
    .catch(error => alert(error.message))
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

    fetch(`/contrato/inserirAlterarContrato`,{
        method: "POST",
        headers: { 
            "Content-Type": "application/json"
        },
        body: JSON.stringify(contratoDTO)
    })
    .then(response => {return response.json()})
    .then(data => {})
    .catch(error => alert(error.message));
}

async function getOptionImovel(){
    return fetch(`/contratosCadastroClientes/proprietario/${form("mcodprop").value}/getOptionImovel`,{
                method: "GET",
                headers: {"Content-Type":"application/json"}
            })
            .then(response => {return response.text()})
            .then(data => { fillSelect("msimovel", data);
                            form("msimovel").options[0].disabled = true; })
            .catch(error => alert(error.message))
}

function getTipoImovel(){
    fetch(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getTipoImovel`,{
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.text()})
    .then(data => { form("mtpimovel").value = data })
    .catch(error => alert(error.message))
} 

function getEnderecoImovel(){
    fetch(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getEnderecoImovel`,{
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.text()})
    .then(data => { form("mloc").value = data; })
    .catch(error => alert(error.message))
}

function getTipoContratoImovel(){
    fetch(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getTipoContratoImovel`,{
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.text()})
    .then(data => { form("mtpcontrato").value = data;
                    form("mperiodofin").style.display = data==="Aluguel"?"inline":"none"})
    .catch(error => alert(error.message))
} 

function getValorImovel(){
    fetch(`/contratosCadastroClientes/proprietario/${form("msimovel").value}/getValorImovel`,{
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.text()})
    .then(data => { form("mvlrimovel").value = data })
    .catch(error => alert(error.message))
}

function getDescCliente(codigo, retorno){
    fetch(`/cliente/${form(codigo).value}/findNomeClienteById`,{
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.text()})
    .then(data => { form(retorno).value = data })
    .catch(error => alert(error.message))
}

function getDescCorretor(){
    fetch(`/contrato/${form("mvendedor").value}/getNomeByIdeusu`,{
        method: "GET",
        headers: {"Content-type":"application/json"}
    })
    .then(response => { return response.text()})
    .then(data => {form("mnome").value = data })
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
    
    pi = 0;
    dados.forEach((dado,index) => {
        const row = document.createElement("tr");        
        
        colunas.forEach(coluna => {
            const td  = document.createElement("td");
            td.id = coluna + dado.codcontrato;
            var valor = dado[coluna.trim()];
            if(coluna == "tipo"){
                if(valor==1){valor = "Apartamento"}
                if(valor==2){valor = "Casa"}
                if(valor==3){valor = "Terreno"};
            }
            if(coluna == "negociacao"){
                valor = valor==1?"Aluguel":"Venda";
            }

            row.appendChild(td);

            if(td.id == "codcontrato"+dado.codcontrato) {
                td.innerHTML = "<a id='fichaContrato" + pi + "'>" + (valor? valor :"N/A") + "</a>"
                
                td.addEventListener("click", function (event) {
                    event.stopPropagation();
                    window.open(`http://localhost:8080/fichaContrato?codcontrato=${dado.codcontrato}`,"FichaDeContrato","_blank,width=760,height=600");
                });
            }
            else{ td.textContent = valor? valor :"N/A" }            
        });    

        row.addEventListener("click", ()=>{
            form(id).querySelectorAll("tr").forEach(row => {
                row.style.border = "none";
            });            
            event_click_table(id,index);
            row.style.border = " 2px solid black";
        });

        tbody.appendChild(row);

        pi++;
    });

    pi = 0;
    colunas.forEach((coluna, index)=>{
        form("th"+pi).innerText   = columnName.split(",")[pi];
        form("th"+pi).style.width = columnWidth.split(",")[pi] +"%"; 
        pi += 1;
    });


    return table;
}