export function imgFormat(){
    /*document.querySelectorAll(".button").forEach(button => {
        let iconUrl = button.getAttribute("data-icon");
        button.style.paddingLeft = "30px";
        button.style.position    = "relative";
        
        button.style.setProperty("--icon-url",          `url(${iconUrl})`);
        button.style.setProperty("content",             `""`);
        button.style.setProperty("background-image",    `url(${iconUrl})`);
        button.style.setProperty("background-position", "10px center");
        button.style.setProperty("background-repeat",   "no-repeat");
        button.style.setProperty("background-size",     "20px 25px");
    });*/
}

export function form(obj){
    return document.getElementById(obj);
}

export function event_selected_init(obj){
    const campos = obj.split(",");

    campos.forEach(opc => {
        form(opc).addEventListener('click', function() {
            this.select();
        });
    });
}

export function event_blur_init(obj){
    const campos = obj.split(",");

    campos.forEach(opc => {
        form(opc).addEventListener('blur', function(e) {
            e.target.value = parseFloat(e.target.value || 0).toFixed(2);
        });
    });
}

export function desabilitaCampo(obj,desahabilita){
    const element = form(obj);

     const isButton = element.tagName === 'BUTTON' ||
                    (element.tagName === "DIV" && 
                     element.className === "button") ||
                    (element.tagName === 'INPUT' && 
                     (element.type === 'button' || element.type === 'submit' || element.type === 'reset'));
    
    if (isButton) {
        element.style.opacity       = desahabilita ? '0.4' : '1';
        element.style.pointerEvents =  desahabilita?"none":"auto";
    } else {
        element.style.backgroundColor = desahabilita ? '#d2d4da' : '';
    }

    form(obj).disabled = desahabilita;
    form(obj).style.cursor = desahabilita?'not-allowed':'pointer';
}

export function setDisplay(obj, display){
    return document.getElementById(obj).style.display = display;
}

export function getDisplay(obj){
    return document.getElementById(obj).style.display;
}

export function fillSelect(id,options,selectedFirst){
    const select = document.getElementById(id);

    if(!select) throw new Error("Ocorreu um erro ao tentar buscar o elemento " + id);
    if(select.tagName !== "SELECT") throw new Error("O elemento " + id + " é diferente do tipo permitido [select]")

    if(!options) return;
    
    options.forEach(option =>{
       const option_ele      = document.createElement("option");
       option_ele.textContent = option.descricao;
       option_ele.value       = option.id;

       select.appendChild(option_ele);
    });

    if(selectedFirst) select.value = options[0].id;
}