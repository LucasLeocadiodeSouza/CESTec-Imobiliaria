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
        img.style.width     = "90%";
        img.style.height    = "90%";
        divimg.style.width  = "20px";
        divimg.style.height = "100%";

        divimg.appendChild(img);

        const divlabel = document.createElement("div");
        divlabel.style.width = "calc(100% - 20px)";
        divlabel.style.display = "flex";
        divlabel.style.justifyContent = "center";
        divlabel.style.alignItems = "center";

        const inputlabel = document.createElement("input");
        inputlabel.type  = "button";
        inputlabel.className = "button";
        inputlabel.value = blabel;

        divlabel.appendChild(inputlabel);

        // Monta a hierarquia corretamente
        divpai.appendChild(divimg);
        divpai.appendChild(divlabel);
        botao.appendChild(divpai);
    });
}