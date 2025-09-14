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

    function filaFetchInit(){
        CONSULTAR.filaFetch = (retorno, error)=>{
            if(error) return;

            switch (CONSULTAR.obj) {
            case               "getNomeApl": $("cabtextAplica").innerText = ACAOCONSULTAR.getNomeApl.codapl + " - " + retorno;
                                             getDescricaoModulo(ACAOCONSULTAR.getNomeApl.codmod);
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
            }
        }
    }
});