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