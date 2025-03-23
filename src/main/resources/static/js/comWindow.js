/*  Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00002
*/
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
        controlaTela("inicio");
        event_click("dimcontratoint");
        event_click("bimmenuint");
        event_click("ditensrelatorioint");
        event_click("bimcnovoimovel"); 
        event_click("bimcnovopropr");
        event_click("bimcnovocliente");
        event_click("bimcnovocontrato");
        event_click("bimccadastrometa");
        event_click("dnextagenda");
        event_click("dbackagenda");
        event_click("bimcaprovacao");
        event_click("bimcboletoscont");
        event_click("ditenspagamentoint");

        valorMetaMensal();
        setGraficoMeta();
        getVlrEfetivadoCorretor();
        getPeriodoMeta();
}

function event_click(obj) {
    if(obj == "dimcontratoint"){
        document.getElementById(obj).addEventListener("click", function() {
            form("ditenscontratoext").style.backgroundColor = form("bimcnovopropr").style.display == "flex"?"#dedede":"#192B4A";
            controlaTela("menuitens");
        });
    }
    if(obj == "bimmenuint"){
        document.getElementById(obj).addEventListener("click", function() {
            form("ditensmenuext").style.backgroundColor = form("dimmfinanciamento").style.display == "flex"?"#dedede":"#192B4A";
            controlaTela("menu");
        });
    }
    if(obj == "ditensrelatorioint"){
        document.getElementById(obj).addEventListener("click", function() {
            form("ditensrelatorioext").style.backgroundColor = form("bimccadastrometa").style.display == "flex"?"#dedede":"#192B4A";
            controlaTela("relatorio");
        });
    }
    if(obj == "ditenspagamentoint"){
        document.getElementById(obj).addEventListener("click", function() {
            form("ditenspagamentoext").style.backgroundColor = form("bimcboletoscont").style.display == "flex"?"#dedede":"#192B4A";
            controlaTela("pagamento");
        });
    }
    if(obj == "bimcnovoimovel"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastro"; 
        }); 
    }
    if(obj == "bimcnovopropr"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroPropri"; 
        });
    }
    if(obj == "bimcnovocliente"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroClientes"; 
        });
    }
    if(obj == "bimcnovocontrato"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroContrato"; 
        }); //contratosCadastroMetas
    }
    if(obj == "bimccadastrometa"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratosCadastroMetas";
        });
    }
    if(obj == "bimcaprovacao"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/contratoAprovacao";
        });
    }
    if(obj == "bimcboletoscont"){
        document.getElementById(obj).addEventListener("click", function() {
            window.location.href = "/gerarCredito";
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
            form("dimmfinanciamento").style.display = "none";
            form("bimcnovocontrato").style.display  = "none";
            form("bimcnovopropr").style.display     = "none";
            form("bimcnovoimovel").style.display    = "none";
            form("bimcnovocliente").style.display   = "none";
            form("bimcassinatura").style.display    = "none";
            form("bimcaprovacao").style.display     = "none";
            form("bimccadastrometa").style.display  = "none";
            form("bimcboletoscont").style.display  = "none";
            form("dimrimovel").style.display        = "none"; 
    }
    if(opc == "menu"){
        form("dimmfinanciamento").style.display  = form("dimmfinanciamento").style.display == "flex"?"none":"flex";
    }
    if(opc == "menuitens"){
        form("bimcnovocontrato").style.display  = form("bimcnovocontrato").style.display == "flex"?"none":"flex";
        form("bimcnovopropr").style.display     = form("bimcnovopropr").style.display    == "flex"?"none":"flex";
        form("bimcnovoimovel").style.display    = form("bimcnovoimovel").style.display   == "flex"?"none":"flex";
        form("bimcassinatura").style.display    = form("bimcassinatura").style.display   == "flex"?"none":"flex";
        form("bimcaprovacao").style.display     = form("bimcaprovacao").style.display    == "flex"?"none":"flex";
        form("bimcnovocliente").style.display   = form("bimcnovocliente").style.display   == "flex"?"none":"flex"; 
    }
    if(opc == "pagamento"){
        form("bimcboletoscont").style.display  = form("bimcboletoscont").style.display == "flex"?"none":"flex";
    }
    if(opc == "relatorio"){
        form("bimccadastrometa").style.display  = form("bimccadastrometa").style.display == "flex"?"none":"flex";
        form("dimrimovel").style.display        = form("dimrimovel").style.display       == "flex"?"none":"flex";
    }
}

function form(obj){
    return document.getElementById(obj);
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