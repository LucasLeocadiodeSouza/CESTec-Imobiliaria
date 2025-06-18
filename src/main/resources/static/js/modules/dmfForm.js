var modalStack = []; //controle de index

export function DMFForm_init(){
    this.divs         = "";
    this.tema         = 0;
    this.cortinaclose = false;
    this.fullScream   = false;

    //
    //criar cortina externa
    this.formModal = ()=>{
        const divs = this.divs.split(",");

        const cortina = document.createElement("div");
        cortina.id        = "DMF_external";
        cortina.className = "DMF_external";

        divs.forEach(div => {
            const modal   = document.getElementById(div.trim());
            const body    = document.querySelector("body").childNodes[1];

            const divfechar   = document.createElement("div");
            const botaofechar = document.createElement("div");

            divfechar.className   = "dmf closediv";
            botaofechar.className = "bpc closediv";
            botaofechar.id        = "bclose";

            const imgclose = document.createElement("img");
            imgclose.src   = "/icons/fechartela.png";
            imgclose.id	   = "imgclose";
            imgclose.style.width  = "80%";
            imgclose.style.height = "80%";
            imgclose.style.margin = "0px";

            modal.style.display = "none";

            botaofechar.appendChild(imgclose);
            divfechar.appendChild(botaofechar);

            if (modal.firstChild) {modal.insertBefore(divfechar, modal.firstChild)}
            else {modal.appendChild(divfechar)}

            cortina.appendChild(modal);
            body.appendChild(cortina);

            eventClose(imgclose.id);
            if(this.cortinaclose) eventClose(cortina.id);

            if(this.tema === 1) modal.className              = "DMF_tema1";
            if(this.tema === 1) divfechar.style.borderRadius = "10px";

            cortina.style.display = "none";
        });
    }

    this.openModal = (idDiv)=>{
        const modal = document.getElementById(idDiv);

        modalStack.push(idDiv);

        if(this.fullScream) {
            modal.style.width  = "95%";
            modal.style.height = "95%";
        }

        modal.style.display = "block";
        document.getElementById("DMF_external").style.display = "flex";
    }

    this.closeModal = ()=>{
        if(!document.getElementById("DMF_external")) throw new Error("Modal Index não encontrada");

        const currentModalId = modalStack.pop();
        const currentModal = document.getElementById(currentModalId);
        
        currentModal.style.display = "none";
        document.getElementById("DMF_external").style.display = "none";
    }
}

function eventClose(obj){
    document.getElementById(obj).addEventListener("click", function (e) {
        if(e.target === this){
            const DMFForm = new DMFForm_init();
            DMFForm.closeModal();
        }
    });
}