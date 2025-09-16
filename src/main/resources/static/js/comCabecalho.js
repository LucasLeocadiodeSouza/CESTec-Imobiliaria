var CONSULTAR;

const ACAOCONSULTAR = {};

window.addEventListener("load", () => {
    CONSULTAR = new consulForm_init();
    filaFetchInit();

    if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');

    const params = new URLSearchParams(window.location.search);

    const modulo    = params.get("idmodulo");
    const aplicacao = params.get("idapl");
    const separador = " » ";

    const textModulo     = document.createElement("label");
    textModulo.id        = "cabtextModulo";

    const textFlecha     = document.createElement("label");
    textFlecha.innerHTML = "&nbsp" + separador + "&nbsp";

    const textAplica     = document.createElement("label");
    textAplica.id        = "cabtextAplica";
    textAplica.title     = params.get("prog");

    getNomeApl(aplicacao,modulo);

    $("dcaminhoapl").innerHTML = "";
    $("dcaminhoapl").appendChild(textModulo);
    $("dcaminhoapl").appendChild(textFlecha);
    $("dcaminhoapl").appendChild(textAplica);

    $("lrazaomini").innerText = "Cestec Sistemas";
    
    event_click("dfecharcab");
    event_click("cabecalhominim");
    event_click("dperfilcab");
    
    function $(opc){
        return document.getElementById(opc);
    }
    
    function event_click(opc){
        if(opc == "dfecharcab"){
            $(opc).addEventListener("click", function () {
                setMinimizarCabecalho('true');

                $("dopcoescab").disabled = false;
                $("dperfilcab").disabled = false;
                $("dfecharcab").disabled = false;
                $("cabecalhoextendido").style.display = "none";
                
                $("cabecalhominim").style.display = "flex";
                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '20px');

                $("ddivexpandido").innerHTML = "";
                $("ddivexpandido").className = "container-expandido";
            });
        }

        if(opc == "cabecalhominim"){
            $(opc).addEventListener("click", function () {
                setMinimizarCabecalho('false');

                $("cabecalhominim").style.display = "none";
                
                $("dopcoescab").disabled = true;
                $("dperfilcab").disabled = true;
                $("dfecharcab").disabled = true;
                $("cabecalhoextendido").style.display = "flex";

                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
            });
        }

        if(opc == "dperfilcab"){
            $(opc).addEventListener("click", function () {
                if($("ddivexpandido").classList.contains("cabecalhoexpandido")) {
                    $("ddivexpandido").innerHTML = "";
                    $("ddivexpandido").classList.remove("cabecalhoexpandido");
                    if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
                }
                else {
                    expandirCabecalhoPerfil();
                }
            });
        }
    }

    function getNomeByIdeusu(){
        CONSULTAR.consultar("getNomeByIdeusu",`/gen/getNomeByIdeusu`,['ideusu=' + $("cab_ideusu").innerText],"",{},[],true);
    }

    function findCargoByIdeusu(){
        CONSULTAR.consultar("findCargoByIdeusu",`/gen/findCargoByIdeusu`,['ideusu=' + $("cab_ideusu").innerText],"",{},[],true);
    }

    function findSetorByIdeusu(){
        CONSULTAR.consultar("findSetorByIdeusu",`/gen/findSetorByIdeusu`,['ideusu=' + $("cab_ideusu").innerText],"",{},[],true);
    }

    function getCodFuncByIdeusu(){
        CONSULTAR.consultar("getCodFuncByIdeusu",`/gen/getCodFuncByIdeusu`,['ideusu=' + $("cab_ideusu").innerText],"",{},[],true);
    }

    function getUserLogin(){
        CONSULTAR.consultar("getUserLogin",`/home/userlogin`,[],"",{},[],true);
    }

    function getNomeApl(codapl, codmod) {
        ACAOCONSULTAR.getNomeApl = {
            codapl: codapl,
            codmod: codmod
        }
        CONSULTAR.consultar("getNomeApl",`/gen/getDescricaoAplicacao`,['codapl=' + codapl],"",{},[],true);
    }
     
    function getDescricaoModulo(codmod) {
        ACAOCONSULTAR.getDescricaoModulo = {
            codmod: codmod
        }

        CONSULTAR.consultar("getDescricaoModulo",`/gen/getDescricaoModulo`,['codmod='+codmod],"",{},[],true);
    }

    function ehMinimizarCabecalho() {
        CONSULTAR.consultar("ehMinimizarCabecalho",`/user/getMinimizarCabecalho`,[],"",{},[],true);
    }

    function setMinimizarCabecalho(acao) {
        CONSULTAR.consultar("setMinimizarCabecalho",`/user/setMinimizarCabecalho`,['ativo='+acao],"",{},[],true);
    }

    function buscarAgendamentos(){
        CONSULTAR.consultar("buscarAgendamentos",`/home/buscarAgendamentosFunc`,["ideusu="+ $("cab_ideusu").innerText],'','',{},true);
    }

    function filaFetchInit(){
        CONSULTAR.filaFetch = (retorno, error)=>{
            if(error) return;

            switch (CONSULTAR.obj) {
            case               "getNomeApl": $("cabtextAplica").innerText = ACAOCONSULTAR.getNomeApl.codapl + " - " + retorno;
                                             getDescricaoModulo(ACAOCONSULTAR.getNomeApl.codmod);
                                             break;

            case             "getUserLogin": $("cab_ideusu").innerText = retorno;
                                             getNomeByIdeusu();
                                             break;

            case          "getNomeByIdeusu": $("cab_nomeusu").innerText = retorno;
                                             getCodFuncByIdeusu();
                                             break;

            case       "getCodFuncByIdeusu": $("cab_codusu").innerText = retorno;
                                             findCargoByIdeusu();
                                             break;

            case        "findCargoByIdeusu": $("cab_cargo").innerText = retorno;
                                             findSetorByIdeusu();
                                             break;

            case        "findSetorByIdeusu": $("cab_setor").innerText = retorno;
                                             buscarAgendamentos();
                                             break;

            case       "getDescricaoModulo": $("cabtextModulo").innerText = ACAOCONSULTAR.getDescricaoModulo.codmod + " - " + retorno;
                                             const textcaminhomini     = document.createElement("label");
                                             textcaminhomini.innerText = textModulo.innerText + " " + textFlecha.innerText + " " + textAplica.innerText;
                                             textcaminhomini.title     = params.get("prog");
                                                     
                                             $("dcaminhoaplmini").innerHTML = "";
                                             $("dcaminhoaplmini").appendChild(textcaminhomini);

                                             ehMinimizarCabecalho();
                                             break;

            case     "ehMinimizarCabecalho": if(retorno) {
                                                $("dopcoescab").disabled = false;
                                                $("dperfilcab").disabled = false;
                                                $("dfecharcab").disabled = false;
                                                $("cabecalhoextendido").style.display = "none";

                                                $("cabecalhominim").style.display = "flex";
                                                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '20px');
                                             }
                                             else{
                                                $("cabecalhominim").style.display = "none";
                
                                                $("dopcoescab").disabled = true;
                                                $("dperfilcab").disabled = true;
                                                $("dfecharcab").disabled = true;
                                                $("cabecalhoextendido").style.display = "flex";

                                                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
                                             }
                                             break;

            case       "buscarAgendamentos":const divcontainerinfoagenda       = document.createElement("div");
                                            divcontainerinfoagenda.style.width = "100%";
                                            divcontainerinfoagenda.className   = "container-infousu";

                                            const divtitulo       = document.createElement("div");
                                            divtitulo.className   = "dmf fundo-titulo";
                                            
                                            const imgagenda        = document.createElement("img");
                                            imgagenda.style.height = "30px";
                                            imgagenda.src          = "/icons/agenda_icon.png";

                                            const labelperfil     = document.createElement("div");
                                            labelperfil.className = "label-perfil";
                                            labelperfil.innerText = "Agenda";

                                            divtitulo.appendChild(imgagenda);
                                            divtitulo.appendChild(labelperfil);

                                            const divdescricao         = document.createElement("div");
                                            divdescricao.style.height  = "119px";
                                            divdescricao.style.padding = "5px 0px";
                                            divdescricao.style.display = "flex";
                                            divdescricao.style.gap     = "10px";

                                            const divdescagend     = document.createElement("div");
                                            divdescagend.id        = "ddescagendamentos";
                                            divdescagend.className = "container-descagendamento";
                                            
                                            divcontainerinfoagenda.appendChild(divtitulo);

                                            retorno.forEach((agend, index) =>{
                                               const [ano, mes, dia]   = agend.datagen.split('-');

                                               const div = criarDivInfoAgenda(agend.corAgend, `${dia}/${mes}/${ano}`, agend.horagen2, agend.titulo);

                                               divdescagend.appendChild(div);
                                            });

                                            divdescricao.appendChild(divdescagend);
                                            
                                            //legenda
                                            const legenda = Array.from(new Map(retorno.map(item => [item.motivo, { color: item.corAgend, text: item.descMotivo }])).values());
                                            divdescricao.appendChild(criarConainerLegenda(legenda));
                                            
                                            divcontainerinfoagenda.appendChild(divdescricao);
                                            $("ddivexpandido").appendChild(divcontainerinfoagenda);


                                            break;
            }
        }
    }

    function expandirCabecalhoPerfil(){
        $("ddivexpandido").innerHTML = "";
        $("ddivexpandido").classList.add("cabecalhoexpandido");
        
        criarContainerPerfil();

        //criarContainerAgendamentos();

        if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '240px');
    }

    function criarContainerPerfil(){
        const divinfousu     = document.createElement("div");
        divinfousu.className = "container-infousu";
        
        const divinfundousu     = document.createElement("div");
        divinfundousu.className = "fundo-titulo dmf";

        const imagemPerfil        = document.createElement("img");
        imagemPerfil.src          = "/icons/work_icon4.png";
        imagemPerfil.style.height = "30px";

        const labelPerfil     = document.createElement("label");
        labelPerfil.className = "label-perfil";
        labelPerfil.innerText = "Perfil";

        divinfundousu.appendChild(imagemPerfil);
        divinfundousu.appendChild(labelPerfil);
        divinfousu.appendChild(divinfundousu);

        const divinformacoes = document.createElement("div");

        divinformacoes.appendChild(criarDivInfoPerfil("ID:","cab_codusu"));
        divinformacoes.appendChild(criarDivInfoPerfil("Ideusu:","cab_ideusu"));
        divinformacoes.appendChild(criarDivInfoPerfil("Nome:","cab_nomeusu"));
        divinformacoes.appendChild(criarDivInfoPerfil("Cargo:","cab_cargo"));
        divinformacoes.appendChild(criarDivInfoPerfil("Setor:","cab_setor"));

        divinfousu.appendChild(divinformacoes);
        $("ddivexpandido").appendChild(divinfousu);

        getUserLogin(); //Buscar informacoes do usuario
    }

    function criarContainerAgendamentos(){
        const divinfousu     = document.createElement("div");
        divinfousu.className = "container-infousu";
        
        const divinfundousu     = document.createElement("div");
        divinfundousu.className = "fundo-titulo dmf";

        const imagemAgenda        = document.createElement("img");
        imagemAgenda.src          = "/icons/agenda_icon.png";
        imagemAgenda.style.height = "30px";

        const labelAgenda     = document.createElement("label");
        labelAgenda.className = "label-perfil";
        labelAgenda.innerText = "Agenda";

        divinfundousu.appendChild(imagemAgenda);
        divinfundousu.appendChild(labelAgenda);
        divinfousu.appendChild(divinfundousu);

        const divinformacoes = document.createElement("div");

        divinformacoes.appendChild(criarDivInfoPerfil("ID:","cab_codusu"));
        divinformacoes.appendChild(criarDivInfoPerfil("Ideusu:","cab_ideusu"));
        divinformacoes.appendChild(criarDivInfoPerfil("Nome:","cab_nomeusu"));
        divinformacoes.appendChild(criarDivInfoPerfil("Cargo:","cab_cargo"));
        divinformacoes.appendChild(criarDivInfoPerfil("Setor:","cab_setor"));

        divinfousu.appendChild(divinformacoes);
        $("ddivexpandido").appendChild(divinfousu);

        getUserLogin(); //Buscar informacoes do usuario
    }

    function criarDivInfoPerfil(label, idlabelinfo){
        const div       = document.createElement("div");
        div.className   = "dmf";
        
        const labelText     = document.createElement("label");
        labelText.innerText = label;

        const labelInfo = document.createElement("label");
        labelInfo.id    = idlabelinfo;

        div.appendChild(labelText);
        div.appendChild(labelInfo);

        return div;
    }

    function criarDivInfoAgenda(coricon, data, hora, desc){
        const div       = document.createElement("div");
        div.className   = "div-descagen";

        const divspanicon            = document.createElement("div");
        divspanicon.className        = "span-icon-color";
        divspanicon.style.background = coricon;

        const divdataagenda     = document.createElement("div");
        divdataagenda.className = "container-dataagenda";
        
        const labelData     = document.createElement("label");
        labelData.innerText = data;

        const labelHora     = document.createElement("label");
        labelHora.innerText = hora;

        const labelAssunto     = document.createElement("label");
        labelAssunto.className = "label-dataagenda";
        labelAssunto.innerText = desc;

        const textseparador     = document.createElement("label");
        textseparador.innerHTML = "&nbsp" + separador + "&nbsp";
        const textDataHora      = document.createElement("label");
        textDataHora.innerHTML  = "/";

        divdataagenda.appendChild(labelData);
        divdataagenda.appendChild(textDataHora);
        divdataagenda.appendChild(labelHora);
        divdataagenda.appendChild(textseparador);
        divdataagenda.appendChild(labelAssunto);
        div.appendChild(divspanicon);
        div.appendChild(divdataagenda);

        return div;
    }

    function criarConainerLegenda(legendas){
        const div       = document.createElement("div");
        div.className   = "container-legenda";

        const divtitulo                = document.createElement("div");
        divtitulo.className            = "dmf fundo-titulo";
        divtitulo.style.display        = "flex";
        divtitulo.style.justifyContent = "center";
        
        const labeltitulo     = document.createElement("label");
        labeltitulo.innerText = "Legenda";

        divtitulo.appendChild(labeltitulo);

        const divlista           = document.createElement("div");
        divlista.style.padding   = "5px";
        divlista.style.overflowY = "auto";

        legendas.forEach(leg =>{
            const divlegenda            = document.createElement("div");
            divlegenda.className        = "div-descagen";

            const divspanicon            = document.createElement("div");
            divspanicon.className        = "span-icon-color";
            divspanicon.style.background = leg.color;

            const divdesclegenda            = document.createElement("div");
            divdesclegenda.className        = "container-dataagenda";
            
            const labelLeg     = document.createElement("label");
            labelLeg.innerText = leg.text;

            divdesclegenda.appendChild(labelLeg);
            divlegenda.appendChild(divspanicon);
            divlegenda.appendChild(divdesclegenda);
            divlista.appendChild(divlegenda);
        });

        div.appendChild(divtitulo);
        div.appendChild(divlista);

        return div;
    }
});