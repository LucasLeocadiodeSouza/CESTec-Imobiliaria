/* 
   Dev: Lucas Leocadio de Souza
   Data: 03/06/25
*/

function elementsForm_init(){

    //
    //Padronizacao dos botoes
    const botoes = document.querySelectorAll(".button");

    botoes.forEach(botao =>{
        const iconUrl = botao.getAttribute("data-icon");
        const blabel  = botao.getAttribute("value");

        botao.innerHTML = '';

        const divpai = document.createElement("div");
        divpai.className = "buttonfe";

        if(iconUrl){
            const divimg = document.createElement("div");
            const img    = document.createElement("img");
            img.src      = iconUrl;
            img.style.width             = "20px";
            img.style.height            = "60%";
            img.style.margin            = "3px";
            img.style.marginLeft        = "6px";
            divimg.style.width          = "20px";
            divimg.style.height         = "100%";
            divimg.style.display        = "flex";
            divimg.style.justifyContent = "center";
            divimg.style.alignItems     = "center";

            divimg.appendChild(img);
            divpai.appendChild(divimg);
        }

        const divlabel = document.createElement("div");
        divlabel.style.width          = iconUrl?"calc(100% - 25px)":"100%";
        divlabel.style.display        = "flex";
        divlabel.style.justifyContent = "flex-start";
        divlabel.style.alignItems     = "center";

        const inputlabel     = document.createElement("input");
        inputlabel.type      = "button";
        inputlabel.className = "buttonfe";
        inputlabel.value     = blabel;
        if(!iconUrl) inputlabel.style.justifyContent = "center";

        divlabel.appendChild(inputlabel);
        
        divpai.appendChild(divlabel);
        botao.appendChild(divpai);
    });

    document.querySelectorAll('input, select, textarea').forEach(element => {
        element.addEventListener('keydown', function(e) {
            // Verifica se a tecla pressionada foi Enter (código 13)
            if (e.key === 'Enter' || e.keyCode === 13) {
                e.preventDefault(); // Evita o comportamento padrão do Enter
                
                // Obtém todos os elementos focáveis
                const focableElements = Array.from(document.querySelectorAll(
                    'input:not([disabled]):not([readonly]), ' +
                    'select:not([disabled]):not([readonly]), ' +
                    'textarea:not([disabled]):not([readonly]), ' +
                    'button:not([disabled]), ' +
                    '[tabindex]:not([disabled]):not([tabindex="-1"])'
                )).filter(el => el.offsetWidth > 0 || el.offsetHeight > 0); // Filtra elementos visíveis
                
                // Encontra o índice do elemento atual
                const currentIndex = focableElements.indexOf(this);
                
                if (currentIndex >= 0 && currentIndex < focableElements.length - 1) {
                    // Foca no próximo elemento
                    focableElements[currentIndex + 1].focus();
                } else if (currentIndex === focableElements.length - 1) {
                    // Se for o último elemento, volta para o primeiro
                    focableElements[0].focus();
                }
            }
        });
    });
}

function alert(titulo, message, timeout){
    let timeoutId;

    var divcontainer     = document.createElement("div");
    divcontainer.id        = "container-alert-form";
    divcontainer.className = "container-alert";

    const alertAtivo = document.body.contains(document.getElementById(divcontainer.id)); 

    if(alertAtivo){
        divcontainer = document.getElementById("container-alert-form");
    }

    const ol         = document.createElement("ol");
    ol.className     = "olalert";

    const li = document.createElement("li");

    const divalertmess     = document.createElement("div");
    divalertmess.className = "alert-message";

    const divcontinertitulo     = document.createElement("div");
    divcontinertitulo.className = "title_info";

    const divcontinermessage     = document.createElement("div");
    divcontinermessage.className = "message_info";

    const labeltitle     = document.createElement("label");
    labeltitle.innerText = titulo;

    const labelmessage     = document.createElement("label");
    labelmessage.innerText = message;

    divcontinertitulo.appendChild(labeltitle);
    divcontinermessage.appendChild(labelmessage);
    divalertmess.appendChild(divcontinertitulo);
    divalertmess.appendChild(divcontinermessage);

    setTimeout(() => {
        divalertmess.classList.remove("entrada");
        divalertmess.classList.add("visible");
    }, 10);

    li.appendChild(divalertmess);

    if(!alertAtivo) {
        ol.appendChild(li);

        divcontainer.appendChild(ol);
        document.body.appendChild(divcontainer);
    }else{
        document.getElementById("container-alert-form").querySelector("ol").appendChild(li);
    }

    const removeAlert = ()=>{ 
        divalertmess.classList.add("saida");
        
        setTimeout(() => {
            li.remove();
        
            if(ol.children.length === 0){
                divcontainer.remove();
            }
        }, 1000);
    }

    timeoutId = setTimeout(removeAlert, timeout * 1000);

    divalertmess.addEventListener('mouseover', () => {
        divalertmess.classList.remove("sombradestaque");
        divalertmess.classList.add("sombradestaque");
        
        clearTimeout(timeoutId);
    });

    divalertmess.addEventListener('mouseout', () => {
        divalertmess.classList.remove("sombradestaque");
        timeoutId = setTimeout(removeAlert, timeout * 1000);
    });

}