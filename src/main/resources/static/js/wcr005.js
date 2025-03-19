/* 
    Dev: Lucas Leocadio de Souza
    Data: 13/03/25
    IM: 00017
*/
window.addEventListener("load", function () {
    createGrid("tabela_metas",
               "codmeta,codcorretor,nome,vlrmeta,datiniciometa,situacao",
               "Código,Código,Nome,Meta,Periodo,Situacão",
               "10,40,10,20,20");
    
    iniciarEventos();
    buscarUserName();
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
    
    event_change("codcorretor");
    event_change("mcodcorretor");

    imgFormat();
}

function event_click_table(id,index){
    if(id == "tabela_metas"){
        form("sacao").innerText   = ehConsulta()?"Consultando":"Alterando";
        form("stitulo").innerText = "Cadastro de Metas - " + form("sacao").innerText;
        
        //preencherModal(index);

        controlaTela("modal");
        form("DMF_external").style.display = "flex"; 
    }
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
            buscarMetasCorretoresGrid();
        });        
    }
    if(obj == 'binserir'){
        form(obj).addEventListener("click", function () {
            form("sacao").innerText   = "Inserindo";
            form("stitulo").innerText = "Cadastro de Meta - " + form("sacao").innerText;
                        
            controlaTela("modal");
            form("DMF_external").style.display = "flex";
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
            salvarMetaCorretor();

            form("DMF_external").style.display = "none";
        });
    }
}

function event_change(obj){
    if(obj == "mcodcorretor"){
        form(obj).addEventListener("change", function(){
            getDescCorretor('mcodcorretor', "mdesccorretor");           
        });
    }
    if(obj == "codcorretor"){
        form(obj).addEventListener("change", function(){
            getDescCorretor('codcorretor', "desccorretor");           
        });
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'buscar'){
        desabilitaCampo('bnovabusca',    true);
        desabilitaCampo('bbuscar',       false);
        desabilitaCampo('codcorretor',   false);
        desabilitaCampo('periodoini',    false);
        desabilitaCampo('periodofin',    false);

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "novabusca"){
        desabilitaCampo('bnovabusca',    false);
        desabilitaCampo('bbuscar',       true);
        desabilitaCampo('codcorretor',   true);
        desabilitaCampo('periodoini',    true);
        desabilitaCampo('periodofin',    true);
    }
    if(opc == "modal"){
        desabilitaCampo('mvlrmeta',     ehConsulta());
        desabilitaCampo('mcodcorretor', ehConsulta());
        desabilitaCampo('mperiodoini',  ehConsulta());
        desabilitaCampo('mperiodofin',  ehConsulta());
        desabilitaCampo('bcadastro',    ehConsulta());
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'buscar'){        
        form('codcorretor').value  = "";
        form('desccorretor').value = "Todos os Corretores";
        form('periodoini').value   = "";
        form('periodofin').value   = "";

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "modal"){
        form('mvlrmeta').value      = "0";
        form('mcodcorretor').value  = "";
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

function preencherModal(index){
    fetch(`/contrato/${index}/buscarContratoGrid`, {
        method: "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.json()})
    .then(data => {})
    .catch(error => alert(error.message))
}

function buscarMetasCorretoresGrid(){
    fetch("/wcr005/buscarMetasCorretoresGrid",{
        method:  "GET",
        headers: {"Content-Type":"application/json"}
    })
    .then(response => {return response.json()})
    .then(data => {carregaGrid("tabela_metas",
                                 data) })
    .catch(error => alert(error.message))
}

function getDescCorretor(obj, retorno){
    fetch(`/contrato/${form(obj).value}/getNomeByIdeusu`,{
        method: "GET",
        headers: {"Content-type":"application/json"}
    })
    .then(response => { return response.text()})
    .then(data => {form(retorno).value = data})
    .catch(error => alert(error.message))
}

function salvarMetaCorretor(){
    const meta = { login:         form("mcodcorretor").value,
                   vlrmeta:       form("mvlrmeta").value,
                   datiniciometa: form("mperiodoini").value,
                   datfinalmeta:  form("mperiodofin").value,
                   nome:          form("ideusu").value
                 };

    fetch(`/wcr005/salvarMetaCorretor`,{
        method:  "POST",
        headers: {
            "Content-type":"application/json"
        },
        body: JSON.stringify(meta)
    })
    .then(response => {return response.json()})
    .catch(error => alert(error.message));
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

function createGrid(id,column,columnName,columnWidth){
    form(id).innerText = '';
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

        form("tabela_metas").appendChild(row);
    }
}

function clearGrid(id){
    const linha = form(id).rows;
    for(var i = 1; i < linha.length; i++){
        linha[i].innerText = "";
    }
}