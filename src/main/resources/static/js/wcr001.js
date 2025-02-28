/* 
    Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00007
*/

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
    
    event_change("codproprietario");
    event_change("mcodproprietario");

    imgFormat();

    buscarDadosTable();
}

function event_click_table(id){
    if(id == "tabela_CTO"){
        form("DMF_external").style.display = "flex";
        controlaTela("modal");
    }
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
            buscarDadosTable();
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
            adicionarContratoImovel();
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
    if(obj == "mcodproprietario"){
        form(obj).addEventListener("change", function(){
            form(obj).value!=""?descProprietario(obj,"mdescproprietario") : form("mdescproprietario").value = "";
        });
    }
}

function controlaTela(opc){
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

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
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
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'buscar'){        
        form('codproprietario').value  = "0";
        form('descproprietario').value = "0";

        form("binserir").style.display     = form("aba1").style.pointerEvents == 'visible'?"flex":"none";
        form("DMF_external").style.display = "none";
    }
    if(opc == "modal"){
        form('mcodproprietario').value     = "";
        form('mdescproprietario').value    = "";
        form('mstpimovel').value           = "0";
        form('mstpcontrato').value         = "0";
        form('mmetrosquad').value          = "";
        form('mquartos').value             = "";
        form('mCondominio').value          = "";
        form('mloc').value                 = "";
        form('mvlr').value                 = "";
        form('mperiodoini').value          = "";
    }
} 

function buscarDadosTable(){
    fetch("/contratosCadastroClientes/proprietario/buscarImoveis")
        .then(response => response.json()) //quando chega a mensagem vc converte para json
        .then(data => {                    // quando chega o dados na forma de JSON vc faz  ...           
            createGrid("tabela_CTO",
                        "codimovel,codproprietario,nome,tipo,status,preco,negociacao",
                        "Cód.Imovel,Cod. Prop., Nome,Tipo,Situacao,Valor(R$),Contrato",
                        "5,8,35,20,18,5,10",
                        data);
        })
        .catch(error => console.log("Erro ao buscar dados: ",error));
}

function adicionarContratoImovel() {
    const imovel = { tipo:              form("mstpimovel").value,
                     negociacao:        form('mstpcontrato').value,
                     quartos:           form('mquartos').value,
                     area:              parseFloat(form("mmetrosquad").value),
                     vlrcondominio:     parseFloat(form("mCondominio").value),
                     preco:             parseFloat(form("mvlr").value),
                     status:            1,
                     endereco:          form("mloc").value,
                     periodo:           form("mperiodoini").value, 
                     datiregistro:      new Date().toISOString().split('T')[0],
                     datinicontrato:    form("mperiodoini").value};

    fetch(`/contratosCadastroClientes/proprietario/${form("mcodproprietario").value}/imoveis`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(imovel,form("mcodproprietario").value)
    })
    .then(response => {response.ok?alert("Dados Salvos Com Sucesso!"):alert("Algo deu errado, por favor insira os dados corretamente");})
    .then(data => {})
    .catch(error => alert(error.message));
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

    dados.forEach(dado => {
        const row = document.createElement("tr");        

        colunas.forEach(coluna => {
            const td  = document.createElement("td");
            var valor = dado[coluna.trim()];
            if(coluna == "tipo"){
                if(valor==1){valor = "Apartamento"}
                if(valor==2){valor = "Casa"}
                if(valor==3){valor = "Terreno"};
            }
            if(coluna == "status"){
                valor = valor==1?"Disponivel":"Indisponivel";
            }
            if(coluna == "negociacao"){
                valor = valor==1?"Aluguel":"Venda";
            }

            td.textContent = valor? valor :"N/A";
            row.appendChild(td);
        });    

        row.addEventListener("click", ()=>{
            form(id).querySelectorAll("tr").forEach(row => {
                row.style.border = "none";
            });            
            event_click_table(id);
            row.style.border = " 2px solid black";
        });

        tbody.appendChild(row);
    });

    var pi = 0;
    colunas.forEach((coluna, index)=>{
        form("th"+pi).innerText   = columnName.split(",")[pi];
        form("th"+pi).style.width = columnWidth.split(",")[pi] +"%"; 
        pi += 1;
    });


    return table;
}