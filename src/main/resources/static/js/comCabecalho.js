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

    getNomeApl(aplicacao,modulo);

    $("dcaminhoapl").innerHTML = "";
    $("dcaminhoapl").appendChild(textModulo);
    $("dcaminhoapl").appendChild(textFlecha);
    $("dcaminhoapl").appendChild(textAplica);

    $("lrazaomini").innerText = "Cestec Sistemas";
    
    event_click("dfecharcab");
    event_click("cabecalhominim");
    event_click("dperfilcab");
    event_click("dopcoescab");
    
    function $(opc){
        return document.getElementById(opc);
    }

    function event_click(opc){
        $(opc).addEventListener("click", function () {
            switch (opc) {
                case "dfecharcab": setMinimizarCabecalho('true');

                                   $("dopcoescab").disabled = false;
                                   $("dperfilcab").disabled = false;
                                   $("dfecharcab").disabled = false;
                                   $("cabecalhoextendido").style.display = "none";
                
                                   $("cabecalhominim").style.display = "flex";
                                   if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '20px');

                                   $("ddivexpandido").innerHTML = "";
                                   $("ddivexpandido").className = "container-expandido";
                                   break;
            
                case "dopcoescab": if($("ddivexpandido").classList.contains("cabecalhoexpandido")) {
                                        $("ddivexpandido").innerHTML = "";
                                        $("ddivexpandido").classList.remove("cabecalhoexpandido");
                                        if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
                                   } else expandirCabecalhoPerfil("preferencias");
                                   break;

                
                case "cabecalhominim": setMinimizarCabecalho('false');
                                       $("cabecalhominim").style.display = "none";
                                       $("dopcoescab").disabled = true;
                                       $("dperfilcab").disabled = true;
                                       $("dfecharcab").disabled = true;
                                       $("cabecalhoextendido").style.display = "flex";
                                       if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
                                       break;

                case     "dperfilcab": if($("ddivexpandido").classList.contains("cabecalhoexpandido")) {
                                           $("ddivexpandido").innerHTML = "";
                                           $("ddivexpandido").classList.remove("cabecalhoexpandido");
                                           if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
                                       } else expandirCabecalhoPerfil("perfil");
                                       break;

                case        "rclaro": setTemaMenu();
                                      break;

                case       "rescuro": setTemaMenu();
                                      break;

                case "rfontpequena": setFontTextMenu();
                                     break;

                case  "rfontnormal": setFontTextMenu();
                                     break;

                case  "rfontgrande": setFontTextMenu();
                                     break;
            }
        });
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

    function getProgInicial() {
        CONSULTAR.consultar("getProgInicial",`/gen/getProgInicial`,['codapl='+aplicacao],"",{},[],true);
    }

    function ehMinimizarCabecalho() {
        CONSULTAR.consultar("ehMinimizarCabecalho",`/user/getMinimizarCabecalho`,[],"",{},[],true);
    }

    function setMinimizarCabecalho(acao) {
        CONSULTAR.consultar("setMinimizarCabecalho",`/user/setMinimizarCabecalho`,['ativo='+acao],"POST",{},[],true);
    }

    function buscarAgendamentos(){
        CONSULTAR.consultar("buscarAgendamentos",`/home/buscarAgendamentosFunc`,["ideusu="+ $("cab_ideusu").innerText],'','',{},true);
    }

    function buscarAplicacoesFav(){
        CONSULTAR.consultar("buscarAplicacoesFav",`/home/buscarAplicacoesFav`,[],'','',{},true);
    }

    function inserirDeletarAplicacaoFav(){
        CONSULTAR.consultar("inserirDeletarAplicacaoFav",`/home/inserirDeletarAplicacaoFav`,['codapl='+aplicacao],'POST','',{},true);
    }

    function ehAplicacaoFavUsu(){
        CONSULTAR.consultar("ehAplicacaoFavUsu",`/home/ehAplicacaoFavUsu`,['codapl='+aplicacao],'','',{},true);
    }

    function getTemaUsuPref(acao) {
        ACAOCONSULTAR.getTemaUsuPref = {
            busca: acao
        }

        CONSULTAR.consultar("getTemaUsuPref",`/user/getTemaUsuPref`,[],"",{},[],true);
    }

    function getFonteTextoUsuPref(acao) {
        ACAOCONSULTAR.getFonteTextoUsuPref = {
            busca: acao
        }

        CONSULTAR.consultar("getFonteTextoUsuPref",`/user/getFonteTextoUsuPref`,[],"",{},[],true);
    }

    function setTemaMenu() {
        CONSULTAR.consultar("setTemaMenu",`/user/setTemaMenu`,["tema=" + getRadioValue("rtemamenu")],"POST",{},[],true);
    }

    function setFontTextMenu() {
        CONSULTAR.consultar("setFontTextMenu",`/user/setFontTextMenu`,["tamanho=" + getRadioValue("rfont")],"POST",{},[],true);
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

                                             getProgInicial();
                                             break;

            case           "getProgInicial": $("cabtextAplica").title = retorno;
                                             ehAplicacaoFavUsu();
                                             break;

            case        "ehAplicacaoFavUsu": if($("dfavaplcab")) $("dfavaplcab").remove();

                                             const div = document.createElement("div");
                                             div.id = "dfavaplcab";
                                             div.className = "botao-cabecalho";
            
                                             const img = document.createElement("img");
                                             img.src = retorno?"/icons/fav_icon2.png":"/icons/fav_icon.png";
                                             img.style.filter = "invert(100%)";

                                             div.appendChild(img);
                                             div.addEventListener("click", ()=>{inserirDeletarAplicacaoFav()});

                                             $("dbotoescab").appendChild(div);
                                             ehMinimizarCabecalho();
                                             break;

            case     "ehMinimizarCabecalho": if(retorno) {
                                                $("dopcoescab").disabled = false;
                                                $("dperfilcab").disabled = false;
                                                $("dfecharcab").disabled = false;
                                                $("cabecalhoextendido").style.display = "none";
                                                
                                                $("cabecalhominim").style.display = "flex";
                                                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '20px');
                                                $("ddivexpandido").innerHTML      = "";
                                             }
                                             else{
                                                $("cabecalhominim").style.display = "none";
                                                
                                                $("dopcoescab").disabled = true;
                                                $("dperfilcab").disabled = true;
                                                $("dfecharcab").disabled = true;
                                                $("cabecalhoextendido").style.display = "flex";
                                                
                                                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
                                                if($("ddivexpandido").classList.contains("cabecalhoexpandido")) $("ddivexpandido").classList.remove("cabecalhoexpandido");
                                                $("ddivexpandido").innerHTML = "";
                                             }

                                             getTemaUsuPref("inicio");
                                             break;

            case "inserirDeletarAplicacaoFav": ehAplicacaoFavUsu();
                                               break;

            case      "buscarAplicacoesFav": retorno.forEach(aplfav => {$("cab_listaplfav").appendChild(criarDivInfoAplFav(aplfav.aplicacoes.modulo.id, aplfav.aplicacoes.modulo.descricao, aplfav.aplicacoes.id, aplfav.aplicacoes.descricao)) });
                                             getTemaUsuPref("buscarAplicacoesFav");
                                             break;

            case           "getTemaUsuPref": if(ACAOCONSULTAR.getTemaUsuPref.busca == "buscarAplicacoesFav") setRadioValue("rtemamenu", retorno);
                                             getFonteTextoUsuPref(ACAOCONSULTAR.getTemaUsuPref.busca);
                                             break;

            case     "getFonteTextoUsuPref": if(ACAOCONSULTAR.getFonteTextoUsuPref.busca == "buscarAplicacoesFav") setRadioValue("rfont", retorno);
                                             tamanhoLabel(retorno);
                                             break;

            case          "setFontTextMenu": getFonteTextoUsuPref();
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

    function expandirCabecalhoPerfil(acao){
        $("ddivexpandido").innerHTML = "";
        $("ddivexpandido").classList.add("cabecalhoexpandido");
        
        if(acao == "perfil") criarContainerPerfil();
        else if(acao == "preferencias") criarContainerFav();

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

    function criarContainerFav(){
        const divinfousu       = document.createElement("div");
        divinfousu.className   = "container-infousu";
        divinfousu.style.width = "50%";
        
        const divinfundousu     = document.createElement("div");
        divinfundousu.className = "fundo-titulo dmf";

        const imagemFav        = document.createElement("img");
        imagemFav.src          = "/icons/fav_icon2.png";
        imagemFav.style.height = "30px";

        const labelFav     = document.createElement("label");
        labelFav.className = "label-perfil";
        labelFav.innerText = "Favoritos";

        divinfundousu.appendChild(imagemFav);
        divinfundousu.appendChild(labelFav);
        divinfousu.appendChild(divinfundousu);

        const divinformacoes     = document.createElement("div");
        divinformacoes.className = "divbuttonaplfav"
        divinformacoes.id        = "cab_listaplfav";

        divinfousu.appendChild(divinformacoes);
        $("ddivexpandido").appendChild(divinfousu);

        criarContainerPrefusu();

        buscarAplicacoesFav();
    }

    function criarDivInfoAplFav(codmodulo,descmod,codapl,descapl){
        const div       = document.createElement("div");
        div.className   = "aplicacao-fav";

        const labelInfo     = document.createElement("label");
        labelInfo.innerText = codmodulo + ":" + descmod + " - " + codapl + ":" + descapl;

        div.appendChild(labelInfo);

        div.addEventListener("click", ()=>{window.open("/buscarPath/" + codapl + "?idmodulo=" + codmodulo + "&idapl=" + codapl, "_blank", "noopener")});

        return div;
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

    function criarContainerPrefusu(){
        const divcontainerinfopref = document.createElement("div");
        divcontainerinfopref.style.width = "100%";
        divcontainerinfopref.className   = "container-infousu";

        const divtitulo       = document.createElement("div");
        divtitulo.className   = "dmf fundo-titulo";
        
        const imgagenda        = document.createElement("img");
        imgagenda.style.height = "30px";
        imgagenda.src          = "/icons/preferencias_icon.png";

        const labelperfil     = document.createElement("div");
        labelperfil.className = "label-perfil";
        labelperfil.innerText = "Preferencias";

        divtitulo.appendChild(imgagenda);
        divtitulo.appendChild(labelperfil);
        divcontainerinfopref.appendChild(divtitulo);

        const divdescricao         = document.createElement("div");
        divdescricao.style.height  = "119px";
        divdescricao.style.padding = "5px 0px";
        divdescricao.style.display = "flex";
        divdescricao.style.gap     = "10px";

        const divprefeusu     = document.createElement("div");
        divprefeusu.id        = "dcabpreferenusu";
        divprefeusu.className = "container-prefusu";

        //Tema do Menu
        const divtemamenu     = document.createElement("div");
        divtemamenu.className = "dmf";

        const labeltema       = document.createElement("label");
        labeltema.style.width = "135px";
        labeltema.innerText   = "Tema do Menu:";
        
        const inputRadioClaro = document.createElement("input");
        inputRadioClaro.type  = "radio";
        inputRadioClaro.value = "claro";
        inputRadioClaro.name  = "rtemamenu";
        inputRadioClaro.id    = "rclaro";

        const labelradioclaro           = document.createElement("label");
        labelradioclaro.style.textAlign = "left";
        labelradioclaro.style.width     = "65px";
        labelradioclaro.innerText       = "Claro";
        labelradioclaro.setAttribute('for', 'rclaro');

        const inputRadioEscuro = document.createElement("input");
        inputRadioEscuro.type  = "radio";
        inputRadioEscuro.value = "escuro";
        inputRadioEscuro.name  = "rtemamenu";
        inputRadioEscuro.id    = "rescuro";

        const labelradioescuro           = document.createElement("label");
        labelradioescuro.style.textAlign = "left";
        labelradioescuro.style.width     = "65px";
        labelradioescuro.innerText       = "Escuro";
        labelradioescuro.setAttribute('for', 'rescuro');

        divtemamenu.appendChild(labeltema);
        divtemamenu.appendChild(inputRadioClaro);
        divtemamenu.appendChild(labelradioclaro);
        divtemamenu.appendChild(inputRadioEscuro);
        divtemamenu.appendChild(labelradioescuro);
        divprefeusu.appendChild(divtemamenu);

        //Tamanho da fonte
        const divfont     = document.createElement("div");
        divfont.className = "dmf";

        const labelfont       = document.createElement("label");
        labelfont.style.width = "135px";
        labelfont.innerText   = "Tamanho da fonte:";
        
        const inputRadioPequena = document.createElement("input");
        inputRadioPequena.type  = "radio";
        inputRadioPequena.value = "pequena";
        inputRadioPequena.name  = "rfont";
        inputRadioPequena.id    = "rfontpequena";

        const labelradioPequena           = document.createElement("label");
        labelradioPequena.style.textAlign = "left";
        labelradioPequena.style.width     = "65px";
        labelradioPequena.innerText       = "Pequena";
        labelradioPequena.setAttribute('for', 'rfontpequena');

        const inputRadioNormal = document.createElement("input");
        inputRadioNormal.type  = "radio";
        inputRadioNormal.value = "normal";
        inputRadioNormal.name  = "rfont";
        inputRadioNormal.id    = "rfontnormal";

        const labelradionormal           = document.createElement("label");
        labelradionormal.style.textAlign = "left";
        labelradionormal.style.width     = "65px";
        labelradionormal.innerText       = "Normal";
        labelradionormal.setAttribute('for', 'rfontnormal');

        const inputRadioGrande = document.createElement("input");
        inputRadioGrande.type  = "radio";
        inputRadioGrande.value = "grande";
        inputRadioGrande.name  = "rfont";
        inputRadioGrande.id    = "rfontgrande";

        const labelradiogrande           = document.createElement("label");
        labelradiogrande.style.textAlign = "left";
        labelradiogrande.style.width     = "65px";
        labelradiogrande.innerText       = "Grande";
        labelradiogrande.setAttribute('for', 'rfontgrande');

        divfont.appendChild(labelfont);
        divfont.appendChild(inputRadioPequena);
        divfont.appendChild(labelradioPequena);
        divfont.appendChild(inputRadioNormal);
        divfont.appendChild(labelradionormal);
        divfont.appendChild(inputRadioGrande);
        divfont.appendChild(labelradiogrande);
        divprefeusu.appendChild(divfont);

        divdescricao.appendChild(divprefeusu);
        divcontainerinfopref.appendChild(divdescricao);
        $("ddivexpandido").appendChild(divcontainerinfopref);

        event_click("rclaro");
        event_click("rescuro");
        event_click("rfontpequena");
        event_click("rfontnormal");
        event_click("rfontgrande");
    }

    function tamanhoLabel(tamanho){
        switch (tamanho) {
            case "pequena": document.documentElement.style.setProperty('--font-size-label', '13px');
                           break;

            case  "normal": document.documentElement.style.setProperty('--font-size-label', '15px');
                            break;

            case "grande": document.documentElement.style.setProperty('--font-size-label', '18px');
                           break;
        }
    }
});