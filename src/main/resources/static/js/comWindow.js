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
var chart;

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

    carregarNotificacoes();

    event_click("dbackagenda");
    event_click("dnextagenda");
    event_mouseover_table();

    filaFetchInit();
    filaFetchGridInit();

    buscarUserId("wcodfunc");

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

            divbuttonint.id = "bim" + botao.codmodulo + "&" + botaoint.codapl + "int";
            //divbuttonint.id = "bim" + botaoint.descricao.replace(/\s+/g, '').toLowerCase()  + "int";
            divbuttonint.className = "botoesinternosapl bimmenu botaointerno";

            divbuttonint.addEventListener("click", ()=>{
                if(botaoint.codapl == "2"){
                    window.open("/buscarPath/" + botaoint.codapl, "Consulta de Tabelas", "_blank,width=1300,height=680");
                }else{
                    salvarHistoricoApl(botao.codmodulo, botaoint.codapl);
                    salvarCookieAplicacao("/buscarPath/" + botaoint.codapl, botao.codmodulo, botaoint.codapl);
                    window.open("/buscarPath/" + botaoint.codapl + "?idmodulo=" + botao.codmodulo + "&idapl=" + botaoint.codapl, "_blank", "noopener");
                    //window.location.href = "/buscarPath/" + botaoint.codapl;
                }

                location.reload();
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
        case      'inativarNotificacao': carregarNotificacoes();
                                         break;

        case             "buscarUserId": form("wcodfunc").value  = retorno;
                                         form("lid").textContent = retorno;
                                         buscarUserName();
                                         break;

        case           "buscarUserName": form("ideusu").value      = retorno;
                                         form("huser").textContent = retorno;
                                         getCargoIdeusu();
                                         break;

        case           "getCargoIdeusu": form("lcargo").innerText = retorno;
                                         valorMetaMensal();
                                         break;

        case          "valorMetaMensal": form("vlrmeta").innerText = retorno;
                                         setGraficoMeta();
                                         break;

        case           "setGraficoMeta": form("dgraficointerno").style.width = retorno + "%"
                                         getVlrEfetivadoCorretor();
                                         break;

        case "getVlrEfetivadoCorretor" : form("vlrefetivadometa").innerText = retorno;
                                         getPeriodoMeta();
                                         break;

        case           "getPeriodoMeta": form("datperiodometa").innerText = retorno;
                                         getBotoesAplMenu();
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

                                          buscarHistoricoAcessoApl();
                                          break;

        case 'buscarHistoricoAcessoApl': retornoBuscarHistoricoAcesso(retorno);
                                         buscarAgendamentos();
                                         break;

        case       "buscarAgendamentos": AGENDSJSON = retorno;
                                         carregaMes();
                                         findAllChamadosByIdeusu();
                                         break;

        case "findAllChamadosByIdeusu": const chamados   = retorno;
                                        var direcionadas = 0;
                                        var iniciadas    = 0;
                                        var concluidas   = 0;

                                        chamados.forEach(chamado =>{
                                            if(chamado.estado == 1) direcionadas ++;
                                            else if(chamado.estado == 2) iniciadas ++;
                                            else if(chamado.estado == 3) concluidas ++;
                                        })
                                          
                                        criarGraficoTarefas(direcionadas, concluidas, iniciadas);
                                        
                                        buscarAplicacoesFav();
                                        break;

        case    "buscarAplicacoesFav": var divcontainer_fr = document.getElementById("aplicacoes_favoritas");
        
                                       if(!divcontainer_fr) {
                                            document.getElementById("container-descaplfav").innerHTML = '<div id="aplicacoes_favoritas"></div>';
                                            divcontainer_fr = document.getElementById("aplicacoes_favoritas");
                                       }

                                       divcontainer_fr.innerHTML = "";
                                       retorno.forEach(aplfav => { criarContainerFrameFav(aplfav.aplicacoes.modulo.id, aplfav.aplicacoes.modulo.descricao, aplfav.aplicacoes.id, aplfav.aplicacoes.descricao) });

                                       if(retorno.length == 0){
                                          divcontainer_fr.parentNode.innerHTML = "<div style='width: 100%; height: 100%; display: flex; justify-content: center;align-items: center;'><label style='color: var(--color-lb-claro, #f5f5f5)'>Nenhuma Aplicação Favoritada</label></div>";
                                       } 

                                       break;
        }
    }
}

function getBotoesAplMenu(){
    CONSUL.consultar("getBotoesAplMenu", `/home/getBotoesAplMenu`)
}

function buscarAgendamentos(){
    CONSUL.consultar("buscarAgendamentos",`/home/buscarAgendamentosFunc`,["ideusu:ideusu"],'','',{},true);
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
        div.className             = "icon-point";
        div.style.backgroundColor = agend.corAgend;

        const div2     = document.createElement("div");
        div2.className = "desc-point";

        const datagend          = document.createElement("label");
        const dataFormatada     = `${dia}/${mes}/${ano}`;
        datagend.innerText      = dataFormatada;

        const separador         = document.createElement("label");
        separador.innerText     = " - ";

        const titulo     = document.createElement("label");
        titulo.innerText = agend.titulo;
        titulo.className = "titulo-point";

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
    CONSUL.consultar("salvarHistoricoApl",`/home/salvarHistoricoApl`,["codmod="+modulo,"codapl="+aplicacao],"POST");
} 
 
function inativarNotificacao(idnotific){
    CONSUL.consultar("inativarNotificacao",`/home/inativarNotificacao`,["idnotific="+idnotific],"POST",'',{},true);
}

function adicionarRowHistoricoAcesso(div, text){
    const divprinc = document.createElement("div");
    divprinc.style.width     = "100%";
    divprinc.style.height    = "30px";
    divprinc.style.display   = "flex";
    divprinc.style.overflowX = "hidden";

    const labelcodapl     = document.createElement("label");
    labelcodapl.className = "titulo-point";
    labelcodapl.innerText = text;

    divprinc.appendChild(labelcodapl);
    div.appendChild(divprinc);
}

function retornoBuscarHistoricoAcesso(historico){
    const h1_cadastro  = form("h1qtde-cadas");
    const h1_liberacao = form("h1qtde-lib");
    const h1_analise   = form("h1qtde-analise");
    const h1_gestao    = form("h1qtde-gestao");
    

    const coluna_descapls = form("rows-descapls");
    const coluna_descmods = form("rows-descmods");
    const coluna_inds     = form("rows-inds");
    const coluna_codapls  = form("rows-codapls");
    const coluna_codmods  = form("rows-codmods");
    const coluna_qtdacess = form("rows-qtdeacess");

    h1_cadastro.innerHTML     = "";
    h1_liberacao.innerHTML    = "";
    h1_analise.innerHTML      = "";
    h1_gestao.innerHTML       = "";
    coluna_descapls.innerHTML = "";
    coluna_descmods.innerHTML = "";
    coluna_inds.innerHTML     = "";
    coluna_codapls.innerHTML  = "";
    coluna_codmods.innerHTML  = "";
    coluna_qtdacess.innerHTML = "";


    var indcadastro  = 0;
    var indliberacao = 0;
    var indanalise   = 0;
    var indgestao    = 0;

    historico.forEach((hist,index) => {
        if(hist.indcadastro)  indcadastro ++;
        if(hist.indliberacao) indliberacao ++;
        if(hist.indanalise)   indanalise ++;
        if(hist.indgestao)    indgestao ++;

        var indicador = "";
        var temvirg   = false;
        if(hist.indcadastro) {
            indicador = (temvirg? indicador + ",":"") + " Cadastro";
            temvirg   = true;
        }
        if(hist.indliberacao) {
            indicador = (temvirg? indicador + ",":"") + " Liberação";
            temvirg   = true;
        }
        if(hist.indanalise) {
            indicador = (temvirg? indicador + ",":"") + " Análise";
            temvirg   = true;
        }
        if(hist.indgestao) {
            indicador = (temvirg? indicador + ",":"") + " Gestão";
            temvirg   = true;
        }
        if(!temvirg) indicador = "-";

        adicionarRowHistoricoAcesso(coluna_descapls, hist.descapl);
        adicionarRowHistoricoAcesso(coluna_descmods, hist.descmodulo);
        adicionarRowHistoricoAcesso(coluna_inds, indicador);
        adicionarRowHistoricoAcesso(coluna_codapls, hist.idaplicacao);
        adicionarRowHistoricoAcesso(coluna_codmods, hist.idmodulos);
        adicionarRowHistoricoAcesso(coluna_qtdacess, hist.numacess);
    });

    h1_cadastro.innerText  = indcadastro;
    h1_liberacao.innerText = indliberacao;
    h1_analise.innerText   = indanalise;
    h1_gestao.innerText    = indgestao;
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

function salvarCookieAplicacao(caminho, idmodulo, idaplicacao){
    CONSUL.consultar("pathAplication",`/auth/pathAplication`,["path="+caminho,
                                                              "codmod="+idmodulo,
                                                              "codapl="+idaplicacao],
                                                              "POST");
}

function findAllChamadosByIdeusu(){
    CONSUL.consultar("findAllChamadosByIdeusu",`/home/findAllChamadosByIdeusu`,[]);
}

function valorMetaMensal(){
    CONSUL.consultar("valorMetaMensal",`/home/valorMetaMensal`);
}

function setGraficoMeta(){
    CONSUL.consultar("setGraficoMeta",`/home/getPercentMetaMes`);
}

function getVlrEfetivadoCorretor(){
    CONSUL.consultar("getVlrEfetivadoCorretor",`/home/getVlrEfetivadoCorretor`);
}

function getCargoIdeusu(){
    CONSUL.consultar("getCargoIdeusu",`/home/getCargoIdeusu`);
}

function getPeriodoMeta(){
    CONSUL.consultar("getPeriodoMeta",`/home/getPeriodoMeta`);
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
    NOTIFY_GRID.carregaGrid("/home/buscarNotificacoesGrid",[],"","",true);
}

function temNotificacaoPendente(){
    const rows = NOTIFY_GRID.getTableNode();

    if(!rows) return;
    
    form("icon_notif").style.display = "none";

    rows.forEach(row =>{
        const colunas = row.childNodes;

        if(colunas[2].innerText == "true") form("icon_notif").style.display = "block";
    });
}

function buscarAplicacoesFav(){
    CONSUL.consultar("buscarAplicacoesFav",`/home/buscarAplicacoesFav`,[],'','',{},true);
}

function criarContainerFrameFav(codmodulo, descmodulo, codapl, descapl){
    const divfrfav = document.createElement("div");
    divfrfav.classList.add("fr-fav");

    const divtitulofrfav = document.createElement("div");
    divtitulofrfav.classList.add("titulo-fr-fav");
    
    const labeltitulo = document.createElement("label");
    labeltitulo.innerText = codmodulo + " - " + descmodulo;

    const divdescfrfav = document.createElement("div");
    divdescfrfav.classList.add("desc-fr-fav");

    const labeldec = document.createElement("label");
    labeldec.innerText = codapl + " - " + descapl;

    divtitulofrfav.appendChild(labeltitulo);
    divdescfrfav.appendChild(labeldec);
    divfrfav.appendChild(divtitulofrfav);
    divfrfav.appendChild(divdescfrfav);

    divfrfav.addEventListener("click", ()=>{
        const botaoapl = document.getElementById("bim" + codmodulo + "&" + codapl + "int");
        botaoapl.click();
    });

    document.getElementById("aplicacoes_favoritas").appendChild(divfrfav);
}

function criarGraficoTarefas(direcionadas, concluidas, iniciadas){
    const chartDom = form('charttarefas');

    var chart = echarts.init(chartDom);

    form("tarefas-inic").innerText  = iniciadas;
    form("tarefas-direc").innerText = direcionadas;
    form("tarefas-concl").innerText = concluidas;

    const option = {
        tooltip: {
            trigger: 'item'
        },
        color: [
            '#9d4444',
            '#449d5f',
            '#5060af'
        ],
        label: {
            show: true,
            color: '#f5f5f5',
            fontSize: 12
        },
        labelLine: {
          show: true,
          length: 20,
          length2: 30
        },
        series: [{
            name: 'Tarefas',
            type: 'pie',
            radius: ['40%', '70%'],
            data: [
                { value: direcionadas, name: 'Direcionadas' },
                { value: concluidas,   name: 'Concluidas' },
                { value: iniciadas,    name: 'Iniciadas' },
            ],
            itemStyle: {
                borderWidth: 1,
                borderColor: '#fff'
            }
        }]
    };

    option && chart.setOption(option);
}