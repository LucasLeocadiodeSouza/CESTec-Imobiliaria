function form(obj){
    return document.getElementById(obj);
}

function event_selected_init(obj){
    const campos = obj.split(",");

    campos.forEach(opc => {
        form(opc).addEventListener('click', function() {
            this.select();
        });
    });
}

function event_blur_init(obj){
    const campos = obj.split(",");

    campos.forEach(opc => {
        form(opc).addEventListener('blur', function(e) {
            e.target.value = parseFloat(e.target.value || 0).toFixed(2);
        });

        form(opc).dispatchEvent(new Event("blur"));
    });
}

function inputOnlyNumber(obj){
    const campos = obj.split(",");

    campos.forEach(opc => {
        const element = form(opc);

        element.addEventListener('input', function () {
            if(isNaN(Number(element.value))) element.value = element.value.substring(0, element.value.length - 1);
        });
    });
}

function desabilitaCampo(obj,desahabilita){
    const element = form(obj);

     const isButton = element.tagName === 'BUTTON' ||
                    (element.tagName === "DIV" && 
                     element.className === "button") ||
                    (element.tagName === 'INPUT' && 
                     (element.type === 'button' || element.type === 'submit' || element.type === 'reset'));
    
    if (isButton) {
        element.style.opacity       = desahabilita ? '0.4' : '1';
        element.style.pointerEvents = desahabilita?"none":"auto";
        element.style.cursor        = desahabilita?'not-allowed':'pointer';
    }

    element.disabled = desahabilita;
}

function setDisplay(obj, display){
    return document.getElementById(obj).style.display = display;
}

function getDisplay(obj){
    return document.getElementById(obj).style.display;
}

function fillSelect(id,options,selectedFirst){
    const select = document.getElementById(id);

    if(!select) throw new Error("Ocorreu um erro ao tentar buscar o elemento " + id);
    if(select.tagName !== "SELECT") throw new Error("O elemento " + id + " é diferente do tipo permitido [select]")

    if(!options) return;
    
    select.innerHTML = "";

    options.forEach(option =>{
       const option_ele      = document.createElement("option");
       option_ele.textContent = option.descricao;
       option_ele.value       = option.id;

       select.appendChild(option_ele);
    });

    if(selectedFirst && options.length != 0) select.value = options[0].id;
}

function getRadioValue(opc){
    const radioSelecionado = document.querySelector('input[name="' + opc + '"]:checked');
    return radioSelecionado.value
}

function setRadioValue(opc,valor) {
    const radio = document.querySelector(`input[name="${opc}"][value="${valor}"]`);
    if (radio) {
        radio.checked = true;
    }
}

function formatDocToCpf(doc){
    const numeros = doc.replace(/\D/g, '');

    const cpfLimpo = numeros.substring(0, 11);
    
    if (!cpfLimpo || cpfLimpo.length < 11) return doc;
    
    return cpfLimpo.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
}

function formatDocToCnpj(doc){
    const numeros = doc.replace(/\D/g, '');
    
    const cnpjLimpo = numeros.substring(0, 14);
    
    if (!cnpjLimpo || cnpjLimpo.length < 14) return doc;

    return cnpjLimpo.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5');
}

function retirarFormatDoc(doc){
    return doc.replace(/\D/g, '');
}

function formatarData(data, mesant) {
    const dataAjustada = new Date(data);
    dataAjustada.setMonth(dataAjustada.getMonth() - mesant);
    return dataAjustada.toISOString().split('T')[0];
}

function capitalizarCadaPalavra(texto) {
  if (!texto) return "";
  return texto
    .split(" ")
    .map(p => p.charAt(0).toUpperCase() + p.slice(1).toLowerCase())
    .join(" ");
}