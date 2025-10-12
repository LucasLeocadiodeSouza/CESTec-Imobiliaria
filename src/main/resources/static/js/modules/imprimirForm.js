
function imprimirForm_init(){
    this.imprimirGrid = (innerhtmlTable, titulo, subtitulo)=>{
        const novaJanela = window.open(`/impressao`, "Imprimir Tabela", "width=1300,height=680");

        novaJanela.onload = function() {
            const divOutput = novaJanela.document.getElementById("table_impressao_form");

            divOutput.innerHTML = innerhtmlTable;

            novaJanela.document.getElementById("ltitulo").innerHTML    = titulo;
            novaJanela.document.getElementById("lsubtitulo").innerHTML = subtitulo;
        };
    }
}