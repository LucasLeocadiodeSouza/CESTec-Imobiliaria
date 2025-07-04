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
            mes  = date.getMonth();

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

        case      "buscarUserId": form("wcodfunc").value  = retorno;
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

function criarIconAgendamentos(day, mes, year){
    const mesFormatado = String(mes).padStart(2, '0');
    const dayFormatado = String(day).padStart(2, '0');
    
    const dataAnalise = `${year}-${mesFormatado}-${dayFormatado}`;
    
    const temAgendamento = AGENDSJSON.some(agenda => {
        const dataAgenda = agenda.codagenda.datagen.split('T')[0];
        return dataAgenda === dataAnalise;
    });
    
    return temAgendamento?"<div style='height:5px;width:5px;background:#ff8300;border-radius:5px;position:absolute;top:0;'></div>": "";
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

    let diasDoMes = "";

    for(let i = comeco; i > 0; i--){
        diasDoMes += `<li class="inativa">${dataFinalPrev - i + 1}</li>`;
    }

    for(let i = 1; i<= dataFinal; i++){
        let classHoje = (i === date.getDate() && mes === new Date().getMonth() && ano === new Date().getFullYear() ? "class='hoje'":"");

        diasDoMes += `<li ${classHoje} id='lidia_${i}_mes_${mes + 1}_ano_${ano}'> ${criarIconAgendamentos(i,mes + 1,ano)} ${i}</li>`;
    }

    for(let i = final; i < 6; i++){
        diasDoMes += `<li class="inativa">${i - final + 1}</li>`;
    }

    dates.innerHTML = diasDoMes;
    form("lnomeMes").innerText = `${nomeMes[mes]}`;
}