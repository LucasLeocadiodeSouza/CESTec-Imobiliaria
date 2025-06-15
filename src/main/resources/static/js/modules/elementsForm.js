/* 
   Dev: Lucas Leocadio de Souza
   Data: 03/06/25
*/

export function elementsForm_init(){

    //
    //Padronizacao dos botoes
    const botoes = document.querySelectorAll(".button");

    botoes.forEach(botao =>{
        const iconUrl = botao.getAttribute("data-icon");
        const blabel  = botao.getAttribute("value");

        botao.innerHTML = '';

        const divpai = document.createElement("div");
        divpai.className = "buttonfe";

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

        const divlabel = document.createElement("div");
        divlabel.style.width          = "calc(100% - 20px)";
        divlabel.style.display        = "flex";
        divlabel.style.justifyContent = "flex-start";
        divlabel.style.alignItems     = "center";

        const inputlabel     = document.createElement("input");
        inputlabel.type      = "button";
        inputlabel.className = "buttonfe";
        inputlabel.value     = blabel;

        divlabel.appendChild(inputlabel);

        // Monta a hierarquia corretamente
        divpai.appendChild(divimg);
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