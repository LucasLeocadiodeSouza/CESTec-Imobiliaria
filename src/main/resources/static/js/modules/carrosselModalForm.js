/* 
    Carrossel para modais
    Importante: Deve ser usado uma estrutura onde o this.container seja o container que contem as divs, separadas. Por exemplo:
        <div id="containermodais">
            <div id="modal1">
                ...
            </div>

            <div id="modal2">
                ...
            </div>
        </div>
*/


function carrosselform_init(){
    this.container      = "";
    this.posicaoInicial = 0;

    this.createCarrossel = ()=>{
        const divpai = document.getElementById(this.container);
        if(!divpai) throw new Error("Div " + this.container + " não encontrada");

        const newcontainerint               = document.createElement("div");
        newcontainerint.style.display       = "flex";
        newcontainerint.style.flexDirection = "column";

        const divcontainercarr                = document.createElement("div");
        divcontainercarr.className            = "container-carrossel-interno";
        divcontainercarr.style.scrollBehavior = "smooth";
        
        const divradiomodais     = document.createElement("div");
        divradiomodais.className = "container-carrossel";
        divradiomodais.id        = "div_container_carrossel";

        const modais = Array.from(divpai.children);
        modais.forEach((modal,index) =>{
            const radiobuttonmodal     = document.createElement("input");
            radiobuttonmodal.type      = "radio";
            radiobuttonmodal.className = "radio-carrossel";
            radiobuttonmodal.id        = "radiocarrossel_" + index;
            radiobuttonmodal.name      = "radiocarrossel";
            
            const spanbuttonmodal      = document.createElement("label");
            spanbuttonmodal.setAttribute("for", "radiocarrossel_" + index);

            if(this.posicaoInicial == index) {
                radiobuttonmodal.checked = true;
                modal.classList.add("modal-carr-on");
            }
            else modal.classList.add("modal-carr-off");

            modal.classList.add("efeito-transicao-carrossel");

            radiobuttonmodal.addEventListener("click", ()=>{
                modais.forEach((modal2) =>{
                    modal2.classList.remove("modal-carr-off", "modal-carr-on");
                    modal2.classList.add("modal-carr-off");
                });

                modal.classList.remove("modal-carr-off");
                modal.classList.add("modal-carr-on");
                radiobuttonmodal.checked = true;

                const scrollPosition = index * divcontainercarr.offsetWidth;
                divcontainercarr.scrollTo({
                    left: scrollPosition,
                    behavior: "smooth"
                });
            });

            divradiomodais.appendChild(radiobuttonmodal);
            divradiomodais.appendChild(spanbuttonmodal);

            divcontainercarr.appendChild(modal);
        });

        newcontainerint.appendChild(divcontainercarr);
        newcontainerint.appendChild(divradiomodais);
        divpai.appendChild(newcontainerint);
    }

    this.setPositionInicial = (position) => {
        setTimeout(() => { // precisa do timeout para o DOM atualizar primeiro e depois executar
            const radio = document.getElementById("radiocarrossel_" + position);
            
            if (!radio) {
                console.error("Elementos não encontrados para posição:", position);
                return;
            }

            radio.click();
        }, 100);
    };
}