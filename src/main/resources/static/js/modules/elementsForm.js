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
}