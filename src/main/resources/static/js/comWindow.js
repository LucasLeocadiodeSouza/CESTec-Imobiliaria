/*  Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00002
*/

import { consulForm_init } from "./modules/consulForm.js";
import { imgFormat,form,desabilitaCampo,setDisplay,event_selected_init,getDisplay } from "./modules/utils.js";

window.addEventListener("load", function () {    
    iniciarEventos();
});

const dates   = document.querySelector(".diadomes");
const nomeMes = ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"];
let date = new Date();
let mes  = date.getMonth();
let ano  = date.getFullYear();
var CONSUL,AGENDSJSON;

function iniciarEventos() {
    CONSUL = new consulForm_init();
    filaFetchInit();

    valorMetaMensal();
    setGraficoMeta();
    getVlrEfetivadoCorretor();
    getCargoIdeusu();
    //getPeriodoMeta();

    event_click("dnextagenda");
    event_click("dbackagenda");

    buscarUserId("wcodfunc");
    buscarUserName();
    
    getBotoesAplMenu();
    controlaTela("inicio");
}

function event_click(obj) {
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

            buscarAgendamentos();
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
            mes  = date.getMonth();

            buscarAgendamentos();
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

function criarBotaoEsterno(divpai,botoes){
    const divprincipal = document.getElementById(divpai);    
    const olprincipal  = document.createElement("ol");

    botoes.forEach(botao => {
        const li = document.createElement("ls");

        const divexterna = document.createElement("div");
        divexterna.id = "ditens" + botao.menu.replace(/\s+/g, '').toLowerCase() + "ext";
        divexterna.className = "container-botao";

        const divprimint = document.createElement("div");
        divprimint.id = "dim" + botao.menu.replace(/\s+/g, '').toLowerCase() + "primint";
        divprimint.className = "botoesmenuesq bimmenu botaopri";

        divprimint.addEventListener("click", ()=>{
            const resultcontainerbotaoativo = containerBotaoAtivo(divexterna);
            form(divexterna.id).style.backgroundColor = resultcontainerbotaoativo?"#dedede":"#192B4A";

            const botoesinterno = divexterna.querySelectorAll(".botaointerno");

            botoesinterno.forEach(botaoint => {
                setDisplay(botaoint.id, resultcontainerbotaoativo?"none":"flex");
            });
        });

        const labelprimi = document.createElement("label");
        labelprimi.className = "labelbotaoapl";
        labelprimi.innerText = botao.menu;

        divprimint.appendChild(labelprimi);
        divexterna.appendChild(divprimint);

        const botoesinternos = botao.botoesinternos;

        const listainterna = document.createElement("ol");
        divexterna.appendChild(listainterna);

        /*
        <ol>
            <li>
                <div id="ditensmenuext" class="container-botao">
                    <div id="bimmenuint" class="botoesmenuesq bimmenu botaopri">
                        <label class="labelbotaoapl">Menu</label>
                    </div>
                    ...
        */

        botoesinternos.forEach(botaoint => {
            /*          ...
                        <ol>
                            <li>
                                <div class="container-botoes-int">
                                    <div id="bimmfinanciamento" class="botoesinternosapl bimmenu botaointerno">
                                        <label class="labelbotaoapl">Simular Financiamento</label>
                                    </div>
                                </div>
                            </li>
                        </ol>
                    </div>
                </li>
            </ol>
            */

            const liinterna = document.createElement("li");

            const divcontainerbutton = document.createElement("div");
            divcontainerbutton.className = "container-botoes-int";

            const divbuttonint = document.createElement("div");
            divbuttonint.id = "bim" + botaoint.descricao.replace(/\s+/g, '').toLowerCase()  + "int";
            divbuttonint.className = "botoesinternosapl bimmenu botaointerno";

            divbuttonint.addEventListener("click", ()=>{
                if(botaoint.codapl == "2"){
                    window.open("/buscarPath/" + botaoint.codapl, "Consulta de Tabelas", "_blank,width=1300,height=680");
                }else{
                    window.open("/buscarPath/" + botaoint.codapl, "_blank", "noopener");
                    //window.location.href = "/buscarPath/" + botaoint.codapl;
                }

            });


            const labelint = document.createElement("label");
            labelint.className = "labelbotaoapl";
            labelint.innerText = botaoint.descricao;

            divbuttonint.appendChild(labelint);
            divcontainerbutton.appendChild(divbuttonint);

            liinterna.appendChild(divcontainerbutton);
            listainterna.appendChild(liinterna);
            li.appendChild(divexterna);
        });

        olprincipal.appendChild(li);
        divprincipal.appendChild(olprincipal);
    });

    ocultarbotoesinternos();
}

function containerBotaoAtivo(obj){
    const botoesinterno = obj.querySelector(".container-botoes-int").querySelectorAll("div");
    var condicao = false;

    botoesinterno.forEach(botaoint => {
        if(getDisplay(botaoint.id) != "none") condicao = true;
    });

    return condicao;
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{
        switch (CONSUL.obj) {
        case  "buscarUserName": form("ideusu").value      = retorno;
                                form("huser").textContent = retorno;
                                buscarAgendamentos();
                                break;

        case       "buscarUserId": form("wcodfunc").value  = retorno;
                                   form("lid").textContent = retorno;
                                   break;

        case "buscarAgendamentos": AGENDSJSON = retorno;
                                   carregaMes();
                                   break;

        case  "getBotoesAplMenu": let botoes = [];
                                  for(const idModulo in retorno) {
                                     const dados = retorno[idModulo];
                                     let botoesint = [];

                                     dados.forEach(dado =>{
                                         botoesint.push({descricao:dado.descricao, codapl:dado.id});
                                     });

                                     botoes.push({menu:dados[0].modulo.descricao, botoesinternos:botoesint});
                                   };

                                   criarBotaoEsterno("dintensint",botoes);
                                   break;
        
        }
    }
}

function getBotoesAplMenu(){
    CONSUL.consultar("getBotoesAplMenu", `/home/getBotoesAplMenu`)
}

function buscarAgendamentos(){
    CONSUL.consultar("buscarAgendamentos",`/home/buscarAgendamentosFunc?ideusu=${form("ideusu").value}`);
}

function criarDescricaoData(mesanalise){
    const divdescagen = form("ddescagendamentos");

    divdescagen.innerHTML = "";

    AGENDSJSON.forEach((agend, index) =>{
        const [ano, mes, dia]   = agend.datagen.split('-');
        if(mesanalise != mes) return;

        const divagend = document.createElement("div");
        divagend.classList.add("div-descagen");
        if(index == 0) divagend.style.marginTop = "10px";

        const div                 = document.createElement("div");
        div.style.height          = "5px";
        div.style.width           = "5px";
        div.style.borderRadius    = "5px";
        div.style.position        = "absolute";
        div.style.transform       = "translate(50%, 50%)";
        div.style.top             = "0";
        div.style.backgroundColor = agend.corAgend;

        const div2                = document.createElement("div");
        div2.style.marginLeft     = "15px";
        div2.style.gap            = "5px";
        div2.style.display        = "flex";
        div2.style.overflow       = "hidden";
        div2.style.textColor      = "#FFF";

        const datagend          = document.createElement("label");
        const dataFormatada     = `${dia}/${mes}/${ano}`;
        datagend.innerText      = dataFormatada;

        const separador         = document.createElement("label");
        separador.innerText     = " - ";

        const titulo              = document.createElement("label");
        titulo.innerText          = agend.titulo;
        titulo.style.whiteSpace   = "nowrap";
        titulo.style.overflow     = "hidden";
        titulo.style.textOverflow = "ellipsis";

        div2.appendChild(datagend);
        div2.appendChild(separador);
        div2.appendChild(titulo);

        divagend.appendChild(div);
        divagend.appendChild(div2);
        divdescagen.appendChild(divagend);
    });
}

function criarIconAgendamentos(day, mes, year){
    const mesFormatado = String(mes).padStart(2, '0');
    const dayFormatado = String(day).padStart(2, '0');
    var corIcon        = "";

    const dataAnalise = `${year}-${mesFormatado}-${dayFormatado}`;

    const temAgendamento = AGENDSJSON.some(agenda => {
        const dataAgenda = agenda.datagen.split('T')[0];
        corIcon          = agenda.corAgend;
        return dataAgenda === dataAnalise;
    });
    
    if(!temAgendamento) return;

    const div = document.createElement("div");
    div.style.height       = "5px";
    div.style.width        = "5px";
    div.style.background   = corIcon;
    div.style.borderRadius = "5px";
    div.style.position     = "absolute";
    div.style.top          = "0";

    return div;
}

function criarModalHoverAgendamento(div, day, mes, year){
    var dataagen,horagen,motivo,titulo,descricao,ideusu;
    
    const modal = document.getElementById("dmodal-agendamento");
    const mesFormatado = String(mes).padStart(2, '0');
    const dayFormatado = String(day).padStart(2, '0');
    const dataAnalise  = `${year}-${mesFormatado}-${dayFormatado}`;

    const temAgendamento   =  AGENDSJSON.some(agenda => {
        const [ano, mes, dia]   = agenda.datagen.split('-');

        const dataAgenda   =  agenda.datagen.split('T')[0];
        dataagen  = `${dia}/${mes}/${ano}`;
        horagen   = agenda.horagen2;
        motivo    = agenda.descMotivo;
        titulo    = agenda.titulo;
        descricao = agenda.titulo;
        ideusu    = agenda.ideusu;

        return dataAgenda === dataAnalise;
    });

    if(!temAgendamento) return;

    div.addEventListener("mouseover", ()=>{
        const rect  = div.getBoundingClientRect();
        
        form("datagendamento").innerText    = dataagen + " - ";
        form("horaagendamento").innerText   = horagen  + " - ";
        form("motivoagendamento").innerText = motivo;
        form("tituloagendamento").innerText = titulo;
        form("descagendamento").innerText   = descricao;
        form("descideusuagend").innerText   = "Aberto por: " + ideusu;

        modal.style.top  = (rect.bottom + modal.offsetHeight) + "px";
        modal.style.left = (rect.left   + modal.offsetLeft)   + "px";

        setDisplay(modal.id, "flex");
    });

    div.addEventListener("mouseout",  (e)=>{
       setDisplay(modal.id, "none");
    });
} 

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}

function buscarUserId(){
    CONSUL.consultar("buscarUserId",`/home/userid`)
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

function carregaMes(){ //IM: 00004 - montar calendario/agenda
    const comeco        = new Date(ano, mes, 1).getDay();
    const dataFinal     = new Date(ano, mes + 1, 0).getDate();
    const final         = new Date(ano, mes, dataFinal).getDay();
    const dataFinalPrev = new Date(ano, mes, 0).getDate();

    dates.innerHTML = "";

    for(let i = comeco; i > 0; i--){
        const li1 = document.createElement('li');
        li1.className   = "inativa";
        li1.textContent = dataFinalPrev - i + 1;

        dates.appendChild(li1);
    }

    for(let i = 1; i<= dataFinal; i++){
        let classHoje = (i === date.getDate() && mes === new Date().getMonth() && ano === new Date().getFullYear() ? "hoje":"");

        const li2 = document.createElement('li');
        li2.className   = classHoje
        li2.id          = `lidia_${i}_mes_${mes + 1}_ano_${ano}`;

        li2.textContent = i;

        if(criarIconAgendamentos(i,mes + 1,ano)) {
            li2.appendChild(criarIconAgendamentos(i,mes + 1,ano));
        }

        dates.appendChild(li2);
        
        criarModalHoverAgendamento(li2, i, mes + 1, ano);
    }

    for(let i = final; i < 6; i++){
        const li3 = document.createElement('li');
        li3.className   = "inativa";
        li3.textContent = i - final + 1;

        dates.appendChild(li3);
    }

    form("lnomeMes").innerText = `${nomeMes[mes]}`;


    criarDescricaoData(mes + 1);
}