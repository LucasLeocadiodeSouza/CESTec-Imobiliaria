


function legendaForm_init(){
    this.id          = "";
    this.name        = "";
    this.color       = "";

    this.createLegenda = ()=>{
        const divlegendapai = document.getElementById(this.id);
        if(!divlegendapai) throw new Error("Div " + this.id + " não encontrada");

        divlegendapai.style.display = "flex";

        const legendas  = this.name.split(",");
        const cores     = this.color.split(",");

        const containerlegendas               = document.createElement("div");
        containerlegendas.style.display       = "flex";
        containerlegendas.style.flexDirection = "row";

        legendas.forEach((leg,index) =>{
            const divlegenda     = document.createElement("div");
            divlegenda.className = "legendaform";
            divlegenda.title     = leg;

            const divlegendaexterna     = document.createElement("div");
            divlegendaexterna.className = "lgde";

            const divlegendainterna            = document.createElement("div");
            divlegendainterna.className        = "lgdi";
            divlegendainterna.style.background = cores[index].trim();

            const labellegenda     = document.createElement("label");
            labellegenda.innerText = leg;

            divlegendaexterna.appendChild(divlegendainterna);
            divlegenda.appendChild(divlegendaexterna);
            divlegenda.appendChild(labellegenda);
            containerlegendas.appendChild(divlegenda);
        });
        
        divlegendapai.appendChild(containerlegendas);
    }
}