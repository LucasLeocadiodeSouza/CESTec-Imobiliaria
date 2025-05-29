let abasArray = [];

export function abaForm_init(){
    this.id          = "";
    this.name        = "";
    this.icon        = "";
    
    this.createAba = ()=>{
        
        if(!document.getElementById(this.id)) throw new Error("Div " + this.id + " não encontrada");;

        const abas  = this.name.split(",");
        const icons = this.icon.split(",");

        const divAbaExt = document.createElement("div");
        divAbaExt.className = "dabaexterna gen";

        var pi = 0;
        abas.forEach(abafe=>{
            const divAba     = document.createElement("div");
            const linhaDecor = document.createElement("div");
            const divAbaInt  = document.createElement("div");

            divAba.id        = "divabares_" + pi;
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
            document.getElementById(this.id).appendChild(divAbaExt);
            pi++;
        });

        //
        //Efeito click
        abasArray[0].divAba.classList.add('ativa')
        abasArray[0].linhaDecor.style.backgroundColor = "rgb(41, 76, 141)";
        abasArray[0].divAbaInt.style.pointerEvents    = 'none';
        abasArray[0].divAbaInt.style.backgroundColor  = "rgb(193, 192, 192)"

        abasArray.forEach(obj=>{
            obj.divAba.addEventListener("click", function () {
                abasArray.forEach(obj2 => {
                    obj2.divAba.classList.remove('ativa');
                    obj2.linhaDecor.style.backgroundColor = 'rgb(81, 81, 81)';
                    obj2.divAbaInt.style.pointerEvents = 'visible';
                    obj2.divAbaInt.style.backgroundColor = ''; // Remove o background-color inline
                });


                obj.divAba.classList.add('ativa');

                obj.linhaDecor.style.backgroundColor   = "rgb(41, 76, 141)";
                obj.divAbaInt.style.pointerEvents   = 'none';
                obj.divAbaInt.style.backgroundColor = "rgb(193, 192, 192)";
            });
        });
    }

    this.setAba_init = (onclick)=>{
        abasArray.forEach(obj=>{
            obj.divAba.addEventListener("click", onclick)
        });
    }

    //
    //Retorna qual o index da aba ativa
    this.getIndex = ()=>{
        return abasArray.findIndex(obj => {
            return obj.divAba.classList.contains("ativa");
        });
    }
}