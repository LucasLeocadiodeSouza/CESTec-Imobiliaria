window.addEventListener("load", () => {
    if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');

    const params = new URLSearchParams(window.location.search);

    const modulo    = params.get("idmodulo") + " - " + params.get("nomemodulo");
    const aplicacao = params.get("idapl") + " - " + params.get("nomeapl");
    const separador = " » ";

    const textModulo     = document.createElement("label");
    textModulo.innerText = modulo;

    const textFlecha     = document.createElement("label");
    textFlecha.innerHTML = "&nbsp" + separador + "&nbsp";

    const textAplica     = document.createElement("label");
    textAplica.innerText = aplicacao;
    textAplica.title     = params.get("prog");


    $("dcaminhoapl").innerHTML = "";
    $("dcaminhoapl").appendChild(textModulo);
    $("dcaminhoapl").appendChild(textFlecha);
    $("dcaminhoapl").appendChild(textAplica);

    const textcaminhomini     = document.createElement("label");
    textcaminhomini.innerText = modulo + " " + separador + " " + aplicacao;
    textcaminhomini.title     = params.get("prog");

    $("dcaminhoaplmini").innerHTML = "";
    $("dcaminhoaplmini").appendChild(textcaminhomini);

    $("lrazaomini").innerText = "Cestec Sistemas";
    
    event_click("dfecharcab");
    event_click("cabecalhominim");
    
    
    function $(opc){
        return document.getElementById(opc);
    }
    
    function event_click(opc){
        if(opc === "dfecharcab"){
            $(opc).addEventListener("click", function () {
                $("dopcoescab").disabled = false;
                $("dperfilcab").disabled = false;
                $("dfecharcab").disabled = false;
                $("cabecalhoextendido").style.display = "none";
                
                $("cabecalhominim").style.display = "flex";
                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '20px');
            });
        }

        if(opc === "cabecalhominim"){
            $(opc).addEventListener("click", function () {
                $("cabecalhominim").style.display = "none";
                
                $("dopcoescab").disabled = true;
                $("dperfilcab").disabled = true;
                $("dfecharcab").disabled = true;
                $("cabecalhoextendido").style.display = "flex";

                if($("comcab_init")) document.documentElement.style.setProperty('--bd-mg-tp', '45px');
            });
        }
    }
});