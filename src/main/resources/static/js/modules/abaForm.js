/* 
   Dev: Lucas Leocadio de Souza
   Data: 31/05/25
   IM: 00019

   abaForm_init -> funcao para importar a criacao do objeto consulta, necessario importar: import { abaForm_init }    from "../modules/abaForm.js";

    Objeto abaForm_init -> 

        ABA      = new abaForm_init(); 
                    Necessario para criacao do objeto abaForm;

        ABA.id   = "exemplo"; 
                    Parametro obrigatório, id da div onde sera criado a aba: <div id="exemplo"></div>

        ABA.name = "Liberação de Acesso,Cadastro de Aplicação";
                    Parametro obrigatório, recebe uma String separa por ",", cada nome sera uma aba;\

        ABA.icon = "/icons/acess_icon.png,/icons/clips_icon.png";
                    Parametro nao obrigatório, recebe uma String separada por ",", cada um sendo o caminho da imagem que sera posicionada a esquerda do label na aba;

        ABA.createAba();
                    metodo final para criacao  da ABA;
*/

function abaForm_init(){
    this.id          = "";
    this.name        = "";
    this.icon        = "";
    
    let abasArray = [];

    this.createAba = ()=>{
        const divaba = document.getElementById(this.id);
        if(!divaba) throw new Error("Div " + this.id + " não encontrada");

        const abas  = this.name.split(",");
        const icons = this.icon.split(",");

        const divAbaExt = document.createElement("div");
        divAbaExt.className = "dabaexterna";

        var pi = 0;
        abas.forEach(abafe=>{
            const divAba     = document.createElement("div");
            const linhaDecor = document.createElement("div");
            const divAbaInt  = document.createElement("div");

            divAba.id        = "divabares_" + abafe + "_" + pi;
            divAba.className = "aba";

            linhaDecor.className = "indentaba";
            
            divAbaInt.className  = "abaint";

            //
            //Icons da aba
            if(icons[pi]){
                const abaImgIcon = document.createElement("img");
                
                abaImgIcon.src       = icons[pi];
                abaImgIcon.className = "gen";

                divAbaInt.appendChild(abaImgIcon);
            }

            const label = document.createElement("label");
            label.style.marginBlock = "0px";
            label.innerText         = abafe;

            abasArray.push({linhaDecor, divAbaInt, divAba, pi});

            divAbaInt.appendChild(label);
            divAba.appendChild(linhaDecor);
            divAba.appendChild(divAbaInt);
            divAbaExt.appendChild(divAba);
            divaba.appendChild(divAbaExt);
            pi++;
        });

        //
        //Efeito click
        this.setIndex(0);

        abasArray.forEach(obj=>{
            obj.divAba.addEventListener("click", function () {
                abasArray.forEach(obj2 => {
                    obj2.divAba.classList.remove('abaativa');
                    obj2.linhaDecor.classList.remove("indentabaativa");
                    obj2.divAbaInt.classList.remove("abaintativa");
                    obj2.divAbaInt.classList.remove("destacaaba");
                    obj2.linhaDecor.classList.add("indentabainativa");
                    obj2.divAbaInt.classList.add("abaintinativa");
                });

                obj.divAba.classList.add('abaativa');

                obj.linhaDecor.classList.remove("indentabainativa");
                obj.divAbaInt.classList.remove("abaintinativa");
                obj.divAbaInt.classList.remove("destacaaba");
                obj.linhaDecor.classList.add("indentabaativa");
                obj.divAbaInt.classList.add("abaintativa");
                obj.divAbaInt.classList.add("destacaaba");

                event_click_aba(this.parentNode.parentNode);
            });
        });

        //Da o evento quando inicia a ABA
        event_click_aba(divaba);
    }



    //
    //Retorna qual o index da aba ativa
    this.getIndex = ()=>{
        return abasArray.findIndex(obj => {
            return obj.divAba.classList.contains("abaativa");
        });
    }

    this.setIndex = (index)=>{
        abasArray.forEach(obj2 => {
            obj2.divAba.classList.remove('abaativa');
            obj2.linhaDecor.classList.remove("indentabaativa");
            obj2.divAbaInt.classList.remove("abaintativa");
            obj2.divAbaInt.classList.remove("destacaaba");
            obj2.linhaDecor.classList.add("indentabainativa");
            obj2.divAbaInt.classList.add("abaintinativa");
        });

        abasArray[index].divAbaInt.classList.remove("abaintinativa");
        abasArray[index].linhaDecor.classList.remove("indentabainativa");
        abasArray[index].divAba.classList.add('abaativa');
        abasArray[index].linhaDecor.classList.add("indentabaativa");
        abasArray[index].divAbaInt.classList.add("abaintativa");
        abasArray[index].divAbaInt.classList.add("destacaaba");

        abasArray.at(0).divAba.classList.add("aba-round-border-left");
        abasArray.at(-1).divAba.classList.add("aba-round-border-right");
    }
}