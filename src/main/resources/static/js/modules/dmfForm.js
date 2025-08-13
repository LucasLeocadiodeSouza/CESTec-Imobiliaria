/* 
   Dev: Lucas Leocadio de Souza
   Data: 22/02/25

    GridForm_init -> funcao para importar a criacao do objeto grid, necessario importar import { GridForm_init }   from (caminho, ex: "../modules/gridForm.js");

    Objeto Grid->
        DMFDiv             = new GridForm_init(); //obrigatório

        DMFDiv.divs        = "exemplo"; 
                              Parametro obrigatório, id da div onde sera criado a modal: <div id="exemplo"></div>
                              É possivel informar mais de uma modal, separando por ",";

        DMFDiv.tema        = 1; 
                              Parametro nao obrigatório, por padrao é atribuido como 0. parametro para selecao de temas da modal
                              0 -> padrao modal quadrado sem muito estilo
                              1 -> modal com bordas arredondadas mais customizadas;

        DMFDiv.cortinaclose = true;
                              Parametro nao obrigatorio, inicialmente atribuido como false. Caso seja true ele permite o fechamento
                                                         da modal em destaque com um click fora dela;


        / * * * * * * * * * * * * * * * /

        DMFDiv.openModal("dmodalf_libacess");
                            Abre a modal. Parametro obrigatória sendo o id da modal que vai ser mostrada na tela. Deve ser referenciada
                                          no objeto id do dmfForm;

        DMFDiv.closeModal();
                            Fecha a modal que esta mais sobreposta sobre outras (caso estiver com mais de uma modal aberta);


        DMFDiv.fullScream  = true;
                              Parametro nao obrigatorio, inicialmente atribuido como false. Caso seja true ele abre a modal em modo tela cheia;
                              
                              Exemplo:
                              DMFDiv.fullScream = true;
                              DMFDiv.openModal("dmodalf_libacess");
                              DMFDiv.fullScream = false;
*/

var modalStack = []; //controle de index

function DMFForm_init(){
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
            const razaoDiv    = document.createElement("div");
            const botaofechar = document.createElement("div");

            razaoDiv.classList.add("razao-modal")

            const labelrazao     = document.createElement("label");
            labelrazao.innerText = "Cestec Sistemas Imobiliarios";

            razaoDiv.appendChild(labelrazao);

            divfechar.className   = "dmf closediv";
            botaofechar.className = "bpc closediv";
            botaofechar.id        = "bclose" + div;

            const imgclose = document.createElement("img");
            imgclose.src   = "/icons/fechartela.png";
            imgclose.id	   = "imgclose"+ div;
            imgclose.style.width  = "80%";
            imgclose.style.height = "80%";
            imgclose.style.margin = "0px";

            modal.style.display = "none";

            botaofechar.appendChild(imgclose);
            divfechar.appendChild(razaoDiv);
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