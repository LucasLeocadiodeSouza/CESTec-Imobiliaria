window.addEventListener("load", function () {
    iniciarEventos();
});

function iniciarEventos() {
    elementsForm_init();

    event_click("bimptable");

    document.getElementById("ldataatual").innerText = getDataAtual();
    document.getElementById("lhoraatual").innerText = getHoraAtual();
}

function event_click(obj) {
    form(obj).addEventListener("click", function () {
        switch (obj) {
            case "bimptable": 
                     window.print();
                     break;
        }
    })
}

function getHoraAtual(){
    const data = new Date();

    return data.toLocaleTimeString();
}

function getDataAtual(){
    const data = new Date();

    return data.toLocaleDateString();
}