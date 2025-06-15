/*  Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00002
*/

import { imgFormat,form,desabilitaCampo,setDisplay,event_selected_init,getDisplay } from "./modules/utils.js";

window.addEventListener("load", function () {
    buscarUserId("lid");
    buscarUserId("wcodfunc");
    buscarUserName("ideusu");
    buscarUserName("huser");
        
    iniciarEventos();
    carregaMes();
});

const dates   = document.querySelector(".diadomes");
const nomeMes = ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"];
let date = new Date();
let mes  = date.getMonth();
let ano  = date.getFullYear();


function iniciarEventos() {
    valorMetaMensal();
    setGraficoMeta();
    getVlrEfetivadoCorretor();
    getCargoIdeusu();
    //getPeriodoMeta();

    criarBotaoEsterno("dintensint","Menu,Contratos,Corretores,Pagamentos,Relatório",["Liberar Aplicacão,Consulta de Tabelas","Cadastro de Contrato,Cadastro de Imóvel,Cadastro de Cliente,Cadastro de Proprietario,Assinaturas,Aprovacão,Simular Financiamento","Cadastro de Metas,Cadastrar Corretor","Gerar Crédito","Relatório do Imovel"]);
    abrirFecharContainerBotoesInt();
    controlaTela("inicio");

    event_click("bimcadastrodeimóvelint"); 
    event_click("bimcadastrodeproprietarioint");
    event_click("bimcadastrodeclienteint");
    event_click("bimcadastrodecontratoint");
    event_click("bimcadastrodemetasint");
    event_click("dnextagenda");
    event_click("dbackagenda");
    event_click("bimaprovacãoint");
    event_click("bimgerarcréditoint");
    event_click("bimcadastrarcorretorint");
}

function event_click(obj) {
    if(obj == "bimcadastrodeimóvelint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastro"; 
        }); 
    }
    if(obj == "bimcadastrodeproprietarioint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroPropri"; 
        });
    }
    if(obj == "bimcadastrodeclienteint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroClientes"; 
        });
    }
    if(obj == "bimcadastrodecontratoint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroContrato"; 
        });
    }
    if(obj == "bimcadastrodemetasint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroMetas";
        });
    }
    if(obj == "bimaprovacãoint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratoAprovacao";
        });
    }
    if(obj == "bimgerarcréditoint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/gerarCredito";
        });
    }
    if(obj == "bimcadastrarcorretorint"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/cadastroCorretor";
        });
    }

    if(obj == "dbackagenda"){
        document.getElementById(obj).addEventListener("click", function() {
            if(mes === 0){
                ano--;
                mes = 11;
            }else{
                mes = mes - 1;
            }

            date = new Date(ano,mes, new Date().getDate());
            ano  = date.getFullYear();
            mes = date.getMonth();

            carregaMes();
        });
    }
    if(obj == "dnextagenda"){
        document.getElementById(obj).addEventListener("click", function() {
            if(mes === 11){
                ano++;
                mes = 0;
            }else{
                mes = mes + 1;
            }

            date = new Date(ano,mes, new Date().getDate());
            ano  = date.getFullYear();
            mes = date.getMonth();

            carregaMes();
        });
    }
}

function controlaTela(opc){
    if(opc == "inicio"){
        ocultarbotoesinternos();
    }
}

function ocultarbotoesinternos(){
    const containerBotoesint = document.querySelectorAll(".container-botoes-int");
        
    containerBotoesint.forEach(div=>{
        div.querySelectorAll("div").forEach(divint=>{
            divint.style.display = "none";
        })
    });
}

function criarBotaoEsterno(divpai,botoesExt,botoesInt){
    const botoessplit  = botoesExt.split(",");
    const divprincipal = document.getElementById(divpai);
        
    botoessplit.forEach((botao,index) => {
        const divexterna = document.createElement("div");
        divexterna.id = "ditens" + botao.replace(/\s+/g, '').toLowerCase() + "ext";
        divexterna.className = "container-botao";

        const divprimint = document.createElement("div");
        divprimint.id = "dim" + botao.replace(/\s+/g, '').toLowerCase() + "primint";
        divprimint.className = "botoesmenuesq bimmenu botaopri";

        const labelprimi = document.createElement("label");
        labelprimi.className = "labelbotaoapl";
        labelprimi.innerText = botao;

        divprimint.appendChild(labelprimi);
        divexterna.appendChild(divprimint);

        /*
        <div id="ditensmenuext" class="container-botao">
            <div id="bimmenuint" class="botoesmenuesq bimmenu botaopri">
                <label class="labelbotaoapl">Menu</label>
            </div>
             ...
        */

        const botoesinternos = botoesInt[index].split(",");

        botoesinternos.forEach((botaoint,indexint) => {
            /* 
                ...
                <div class="container-botoes-int">
                    <div id="bimmfinanciamento" class="botoesinternosapl bimmenu botaointerno">
                        <label class="labelbotaoapl">Simular Financiamento</label>
                    </div>
                </div>
            </div>
            */

            const divcontainerbutton = document.createElement("div");
            divcontainerbutton.className = "container-botoes-int";

            const divbuttonint = document.createElement("div");
            divbuttonint.id = "bim" + botaoint.replace(/\s+/g, '').toLowerCase()  + "int";
            divbuttonint.className = "botoesinternosapl bimmenu botaointerno";

            const labelint = document.createElement("label");
            labelint.className = "labelbotaoapl";
            labelint.innerText = botaoint;

            divbuttonint.appendChild(labelint);
            divcontainerbutton.appendChild(divbuttonint);
            divexterna.appendChild(divcontainerbutton);
        });

        divprincipal.appendChild(divexterna);
    });
}

function abrirFecharContainerBotoesInt(){
    const botoesPrimarios = document.querySelectorAll(".botaopri");

    botoesPrimarios.forEach((botaopri,index) => {
        const divitensexterna = document.querySelectorAll(".container-botao")[index];

        botaopri.addEventListener("click", function() {
            const resultcontainerbotaoativo = containerBotaoAtivo(divitensexterna);
            form(divitensexterna.id).style.backgroundColor = resultcontainerbotaoativo?"#dedede":"#192B4A";

            const botoesinterno = divitensexterna.querySelectorAll(".botaointerno");

            botoesinterno.forEach(botaoint => {
                setDisplay(botaoint.id, resultcontainerbotaoativo?"none":"flex");
            });
        });
    });
}

function containerBotaoAtivo(obj){
    const botoesinterno = obj.querySelector(".container-botoes-int").querySelectorAll("div");
    var condicao = false;

    botoesinterno.forEach(botaoint => {
        if(getDisplay(botaoint.id) != "none") condicao = true;
    });

    return condicao;
}

function buscarUserName(obj){
    fetch("/home/userlogin", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response =>{return response.text()})
    .then(data => { form(obj).textContent = data; })    
    .catch(error => alert(error.message));
}

function buscarUserId(obj){
    fetch("/home/userid", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => {return response.text()})
    .then(data => { form(obj).textContent = data })
    .catch(error => alert(error.message));
}

function valorMetaMensal(){
    fetch("/home/userlogin", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response =>{return response.text()})
    .then(data => {fetch(`/home/${data}/valorMetaMensal`, {
                       method: "GET",
                       headers: {
                           "Content-Type":"application/json"
                       }
                   })
                   .then(response => {return response.text()})
                   .then(data     => { form("vlrmeta").innerText = data })
                   .catch(error   => alert("Erro ao buscar meta mensal: " + error.message))
    })
}

function setGraficoMeta(){
    fetch("/home/getPercentMetaMes", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    .then(response => {return response.text()})
    .then(data     => { form("dgraficointerno").style.width = data + "%" })
    .catch(error   => alert("Erro ao Mudar as dimensoes do grafico de metas: " + error.message))
}

function getVlrEfetivadoCorretor(){
    fetch("/home/getVlrEfetivadoCorretor", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    .then(response => {return response.text()})
    .then(data     => { form("vlrefetivadometa").innerText = data })
    .catch(error   => alert("Erro ao buscar Valor Efetivado: " + error.message))
}

function getCargoIdeusu(){
    fetch("/home/getCargoIdeusu", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    .then(response => {return response.text()})
    .then(data     => { form("lcargo").innerText = data })
    .catch(error   => alert("Erro ao buscar Cargo: " + error.message))
}

function getPeriodoMeta(){
    fetch("/home/getPeriodoMeta", {
        method: "GET",
        headers: {
            "Content-Type":"application/json"
        }
    })
    .then(response => {return response.text()})
    .then(data => {form("datperiodometa").innerText = data})
    .catch(error => alert("Erro ao buscar Período da meta: " + error.message))
}

function carregaMes(){ //IM: 00004 
    const comeco        = new Date(ano, mes, 1).getDay();
    const dataFinal     = new Date(ano, mes + 1, 0).getDate();
    const final         = new Date(ano, mes, dataFinal).getDay();
    const dataFinalPrev = new Date(ano, mes, 0).getDate();

    let diasDoMes = "";

    for(let i = comeco; i > 0; i--){
        diasDoMes += `<li class="inativa">${dataFinalPrev - i + 1}</li>`;
    }

    for(let i = 1; i<= dataFinal; i++){
        let classHoje = (i === date.getDate() && mes === new Date().getMonth() && ano === new Date().getFullYear() ? "class='hoje'":"");
        
        diasDoMes += `<li ${classHoje}>${i}</li>`;
    }

    for(let i = final; i < 6; i++){
        diasDoMes += `<li class="inativa">${i - final + 1}</li>`;
    }

    dates.innerHTML = diasDoMes;
    form("lnomeMes").innerText = `${nomeMes[mes]}`;
}