/*  Dev: Lucas Leocadio de Souza
    Data: 15/02/25
    IM: 00002
*/

window.addEventListener("load", function () {    
    iniciarEventos();
});

const dates   = document.querySelector(".diadomes");
const nomeMes = ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"];
let date = new Date();
let mes  = date.getMonth();
let ano  = date.getFullYear();
var CONSUL,NOTIFY_GRID,AGENDSJSON;

function iniciarEventos() {
    CONSUL = new consulForm_init();

    NOTIFY_GRID               = new GridForm_init();
    NOTIFY_GRID.id            = "table_notif";
    NOTIFY_GRID.columnName    = "descricao,data,_idnotifi,_ehativo";
    NOTIFY_GRID.columnLabel   = "Descricao,Data";
    NOTIFY_GRID.columnWidth   = "65,35";
    NOTIFY_GRID.columnAlign   = "e,c";
    NOTIFY_GRID.mousehouve    = true;
    NOTIFY_GRID.ocultarhead   = true;
    NOTIFY_GRID.fullParent    = true;
    NOTIFY_GRID.destacarclick = false;
    NOTIFY_GRID.miniModalOver = true;
    NOTIFY_GRID.colsModalOver = "0,1";
    NOTIFY_GRID.tema          = '2';
    NOTIFY_GRID.createGrid();

    valorMetaMensal();
    setGraficoMeta();
    getVlrEfetivadoCorretor();
    getCargoIdeusu();
    //getPeriodoMeta();
    carregarNotificacoes();

    event_click("dbackagenda");
    event_click("dnextagenda");
    event_mouseover_table();

    filaFetchInit();
    filaFetchGridInit();

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
            mes  = date.getMonth();

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

function event_mouseover_table(){
    NOTIFY_GRID.mouseover_table = ()=>{
        const valoresLinha = NOTIFY_GRID.getRowNode(event.target.closest('tr'));

        inativarNotificacao(valoresLinha[3]);
    };
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
            divint.classList.add("ocultardisplaybotoes");
        })
    });
}

function criarBotaoExterno(divpai,botoes){
    const divprincipal = document.getElementById(divpai);    
    const olprincipal  = document.createElement("ol");
    olprincipal.style.transition = "1s ease-in";

    botoes.forEach(botao => {
        const li = document.createElement("ls");

        const divexterna = document.createElement("div");
        divexterna.id = "ditens" + botao.menu.replace(/\s+/g, '').toLowerCase() + "ext";
        divexterna.className = "container-botao ocultarcontainerbotao";

        const divprimint = document.createElement("div");
        divprimint.id = "dim" + botao.menu.replace(/\s+/g, '').toLowerCase() + "primint";
        divprimint.className = "botoesmenuesq bimmenu botaopri";
        divprimint.style.marginBottom = "5px";

        const divlabel     = document.createElement("div");
        divlabel.className = "container-label-bim";

        divprimint.addEventListener("click", ()=>{
            const resultcontainerbotaoativo = containerBotaoAtivo(divexterna);

            form(divexterna.id).classList.remove(resultcontainerbotaoativo?"mostrarcontainerbotao":"ocultarcontainerbotao");
            form(divexterna.id).classList.add(resultcontainerbotaoativo?"ocultarcontainerbotao":"mostrarcontainerbotao");

            const botoesinterno = divexterna.querySelectorAll(".botaointerno");

            botoesinterno.forEach(botaoint => {
                const allbuttons = document.querySelectorAll(".botaointerno");
                allbuttons.forEach(button =>{
                    const containerBotao = button.parentNode.parentNode.parentNode.parentNode;
                    if(containerBotao.id != divexterna.id){
                        button.classList.remove("Abrirdisplaybotoes");
                        button.classList.remove("ocultardisplaybotoes");
                        button.classList.add("ocultardisplaybotoes");

                        containerBotao.classList.remove("mostrarcontainerbotao");
                    }
                });

                botaoint.classList.remove(resultcontainerbotaoativo?"Abrirdisplaybotoes":"ocultardisplaybotoes");
                botaoint.classList.add(resultcontainerbotaoativo?"ocultardisplaybotoes":"Abrirdisplaybotoes");
            });

            const allbotoesprinc = document.querySelectorAll(".botaopri");
            allbotoesprinc.forEach(botao => {
                const arrowiconrel = botao.querySelector("img");
                arrowiconrel.classList.remove("arrowInativo");
                arrowiconrel.classList.remove("arrayAtivo");
                arrowiconrel.classList.add("arrayAtivo");
            });

            const arrowicon = divprimint.querySelector("img");
            arrowicon.classList.remove(resultcontainerbotaoativo?"arrowInativo":"arrayAtivo");
            arrowicon.classList.add(resultcontainerbotaoativo?"arrayAtivo":"arrowInativo");
        });

        const labelprimi = document.createElement("label");
        labelprimi.className = "labelbotaoapl";
        labelprimi.innerText = botao.menu;

        divlabel.appendChild(labelprimi);

        const imgarrow     = document.createElement("img");
        imgarrow.src       = "/icons/arrow_icon.png";
        imgarrow.className = "arrayAtivo arrowIcon";

        divprimint.appendChild(divlabel);
        divprimint.appendChild(imgarrow);
        divexterna.appendChild(divprimint);

        const botoesinternos = botao.botoesinternos;

        const listainterna = document.createElement("ol");
        listainterna.classList.add("limite-ol");
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
                    salvarHistoricoApl(botao.codmodulo, botaoint.codapl);
                    window.open("/buscarPath/" + botaoint.codapl, "_blank", "noopener");
                    //window.location.href = "/buscarPath/" + botaoint.codapl;
                }
            });

            const labelint = document.createElement("label");
            labelint.className = "labelbotaoapl";
            labelint.innerText = botaoint.descricao;
            labelint.title     = botaoint.prog_ini;

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
        if(botaoint.classList.contains("Abrirdisplaybotoes")) condicao = true;
    });

    return condicao;
}

function filaFetchGridInit(){
    NOTIFY_GRID.filaFetchGrid = ()=>{
        temNotificacaoPendente();
    }
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{
        switch (CONSUL.obj) {
        case  "buscarUserName": form("ideusu").value      = retorno;
                                form("huser").textContent = retorno;
                                buscarHistoricoAcessoApl();
                                break;

        case       "buscarUserId": form("wcodfunc").value  = retorno;
                                   form("lid").textContent = retorno;
                                   break;

        case "buscarAgendamentos": AGENDSJSON = retorno;
                                   carregaMes();
                                   break;

        case 'buscarHistoricoAcessoApl': retornoBuscarHistoricoAcesso(retorno);
                                         buscarAgendamentos();
                                         break;

        case         "getBotoesAplMenu": let botoes = [];
                                         for(const idModulo in retorno) {
                                            const dados = retorno[idModulo];
                                            let botoesint = [];

                                            dados.forEach(dado =>{
                                                botoesint.push({descricao:dado.descricao, codapl:dado.id, prog_ini: dado.arquivo_inic});
                                            });

                                            botoes.push({menu:dados[0].modulo.descricao, codmodulo:dados[0].modulo.id, botoesinternos:botoesint});
                                          };

                                          criarBotaoExterno("dintensint",botoes);
                                          break;

        case      'inativarNotificacao': carregarNotificacoes();
                                         break;
        }
    }
}

function getBotoesAplMenu(){
    CONSUL.consultar("getBotoesAplMenu", `/home/getBotoesAplMenu`)
}

function buscarAgendamentos(){
    CONSUL.consultar("buscarAgendamentos",`/home/buscarAgendamentosFunc?ideusu=${form("ideusu").value}`,'','',{},true);
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

    const dataAnalise = `${year}-${mesFormatado}-${dayFormatado}`;

    const div = document.createElement("div");
    div.style.position     = "absolute";
    div.style.top          = "0";
    div.style.gap          = "3px";
    div.style.display      = "flex";
    div.style.maxWidth     = "25px";
    div.style.overflow     = "hidden";

    AGENDSJSON.forEach(agendamento =>{
        const dataAgenda = agendamento.datagen.split('T')[0];

        if(dataAgenda === dataAnalise){
            const divicon = document.createElement("div");
            divicon.style.height       = "5px";
            divicon.style.width        = "5px";
            divicon.style.background   = agendamento.corAgend;
            divicon.style.borderRadius = "5px";

            div.appendChild(divicon);
        };
    });

    return div;
}

function criarModalHoverAgendamento(div, day, mes, year){
    var dataagen,horagen,motivo,titulo,descricao,ideusu;
    
    const modal = document.getElementById("dmodal-agendamento");
    const mesFormatado = String(mes).padStart(2, '0');
    const dayFormatado = String(day).padStart(2, '0');
    const dataAnalise  = `${year}-${mesFormatado}-${dayFormatado}`;

    const temAgendamento   =  AGENDSJSON.some(agenda => {
        const [ano, mes, dia] = agenda.datagen.split('-');
        const horaAnalise     = agenda.horagen2.split(":");

        const dataAgenda   =  agenda.datagen.split('T')[0];
        dataagen  = `${dia}/${mes}/${ano}`;
        horagen   = horaAnalise[0] + ":" + horaAnalise[1];
        motivo    = agenda.descMotivo;
        titulo    = agenda.titulo;
        descricao = agenda.descricao;
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

function salvarHistoricoApl(modulo, aplicacao){
    CONSUL.consultar("salvarHistoricoApl",`/home/salvarHistoricoApl?codmod=${modulo}&codapl=${aplicacao}`,"POST");
} 
 
function inativarNotificacao(idnotific){
    CONSUL.consultar("inativarNotificacao",`/home/inativarNotificacao?idnotific=${idnotific}`,"POST",'',{},true);
}

function adicionarListaHistoricoAcesso(li, codapl, codmodulo, nomemodulo, nomeapl, numacesso){

    const divprinc = document.createElement("div");
    divprinc.style.width = "100%";

    const labelcodapl = document.createElement("label");
    labelcodapl.className = "";
    
    const linkcodapl = document.createElement("a");
    linkcodapl.innerText = codmodulo + " [" + nomemodulo + "] " + " - " + codapl + " - " + nomeapl + " | Aplicacão acessada " + numacesso + (numacesso > 1?" vezes":" vez");
    linkcodapl.href = "/buscarPath/" + codapl;
    labelcodapl.appendChild(linkcodapl);

    divprinc.appendChild(labelcodapl);
    li.appendChild(divprinc);
}

function retornoBuscarHistoricoAcesso(historico){
    const divpai = form("dacessosint");
    divpai.innerHTML = "";

    if(historico.length == 0){
        divpai.style.alignItems     = "center";
        divpai.style.justifyContent = "center";

        const emptyLabel          = document.createElement("label");
        emptyLabel.style.color    = "#FFF";
        emptyLabel.style.fontSize = "larger";
        emptyLabel.innerText      = "Vazio";

        divpai.appendChild(emptyLabel);
    }

    historico.forEach(hist => {
        const liinterna = document.createElement("li");
        liinterna.classList.add("liacessos");
        adicionarListaHistoricoAcesso(liinterna, hist.idaplicacao, hist.idmodulos, hist.descmodulo, hist.descapl, hist.numacess)
        
        divpai.appendChild(liinterna);
    });
}

function buscarHistoricoAcessoApl(){
    CONSUL.consultar("buscarHistoricoAcessoApl",`/home/buscarHistoricoAcessoApl`)
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`,'','',{},true)
}

function buscarUserId(){
    CONSUL.consultar("buscarUserId",`/home/userid`,'','',{},true)
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

function carregarNotificacoes(){
    NOTIFY_GRID.carregaGrid("/home/buscarNotificacoesGrid","","",true);
}

function temNotificacaoPendente(){
    const rows = NOTIFY_GRID.getTableNode();

    form("icon_notif").style.display = "none";

    rows.forEach(row =>{
        const colunas = row.childNodes;

        if(colunas[2].innerText == "true") form("icon_notif").style.display = "block";
    });
}